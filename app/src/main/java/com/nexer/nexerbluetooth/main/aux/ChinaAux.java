package com.nexer.nexerbluetooth.main.aux;

import android.annotation.SuppressLint;
import android.util.Log;

import com.nexer.nexerbluetooth.main.model.DynamicData;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by guilhermecoelho on 2/20/18.
 */

@SuppressLint("Assert")
public class ChinaAux {

    // Singleton

    private static ChinaAux uniqueInstance = new ChinaAux();

    private ChinaAux() {

    }

    public static ChinaAux getInstance() {
        return uniqueInstance;
    }

    // Private properties

    private final String TAG = "ChinaAux";

    // Private methods


    // Public methods

    public DynamicData parseReceivedDynamicData(String message) {

        DynamicData data = new DynamicData();

        String sourceId; // 0
        int sequenceId; // 1
        int eventType; // 2
        int eventCode; // 3
        String date; // 4
        String time; // 5
        int checksum; // 7

        // Data pos 6

        int speed = 0;
        int rpm = 0;
        int engineTemperature = 0;
        String fuelLevel;
        float volTAGe;
        int odometer;
        int totalFuelUsage;
        int engineHours;
        boolean malfunction;
        String vin;

        String[] datas = message.split(Constants.COMMA);

        if (datas[0] != null ) {
            data.setSourceId(datas[0]);
        }

        if (datas[1] != null ) {

            BigInteger value = new BigInteger(datas[1],16);
            data.setSequenceId(value);
        }

        if (datas[2] != null ) {

            BigInteger value = new BigInteger(datas[2],16);
            data.setEventType(value);
        }

        if (datas[3] != null ) {

            BigInteger value = new BigInteger(datas[3],16);
            data.setEventCode(value);
        }

        if (datas[4] != null ) {
            data.setDate(datas[4]);
        }

        if (datas[5] != null ) {
            data.setTime(datas[5]);
        }

        /// Event Definitions

        if (  data.getEventType().intValue() == 2 &&  data.getEventCode().intValue() == 1) {

            Log.d(TAG,datas[6]);

            String[] events = datas[6].split(Constants.PIPE);

            if (events[0] != null ) {

                String value = events[0].replace(Constants.KMSUFIX,"");
                data.setSpeed(value);
            }

            if (events[1] != null ) {

                String value = events[1].replace(Constants.RPMSUFIX,"");
                data.setRpm(value);
            }

            if (events[2] != null ) {

                String value = events[2].replace(Constants.TEMPSUFIX,"");
                data.setEngineTemperature(value);
            }

            if (events[3] != null ) {

                String value = events[3].replace(Constants.FUELSUFIX,"");
                data.setFuelLevel(value);
            }

            if (events[4] != null ) {

                Float value = Float.parseFloat(events[4]);
                data.setVoltage(value);
            }

            if (events[5] != null ) {

                BigInteger value = new BigInteger(events[5],16);;
                data.setOdometer(value);
            }

            if (events[6] != null ) {

                BigInteger value = new BigInteger(events[6],16);;
                data.setTotalFuelUsage(value);
            }

            if (events[7] != null ) {

                BigInteger value = new BigInteger(events[7],16);;
                data.setEngineHours(value);
            }

            if (events[8] != null ) {

                boolean value = Boolean.parseBoolean(events[8]);
                data.setMalfunction(value);
            }

            if (events[9] != null ) {
                data.setVin(events[9]);
            }
        }


        return  data;
    }

    // Engine PowerUp and Shutdown

    public boolean enginePowerUp(DynamicData data) {

        return ( data.getEventCode().longValue() == 1 && data.getEventType().longValue() == 3);
    }

    public boolean engineShutDown(DynamicData data) {

        return ( data.getEventCode().longValue() == 2 && data.getEventType().longValue() == 3);
    }

    // Message Builder Methods

    public boolean messageIsCompleted(String message) {

        return message.contains(Constants.ENDCHAR);
    }

    public boolean messageHasBeginAndEnd(String message) {

        return message.contains(Constants.ENDCHAR) && message.contains(Constants.BEGINCHAR);
    }

    public String removeSpecialCharsFrom(String message) {

        String response = message.replace(Constants.BEGINCHAR, "");

        response = response.replace(Constants.ENDCHAR,"");

        assert !message.contains(Constants.BEGINCHAR);
        assert !message.contains(Constants.ENDCHAR);

        String[] datas = response.split(Constants.COMMA);

        ArrayList<String> list = new ArrayList<String>(Arrays.asList(datas));

        list.remove(list.size()-1);

        String s = list.get(0);

        for(int i=1; i<list.size(); i++) {
            s = s + "," + list.get(i);
        }

        return s;
    }

    public String createMessageToOBD(String message) {

        String response = Constants.BEGINCHAR + message + Constants.ENDCHAR;

        assert message.contains(Constants.BEGINCHAR);
        assert message.contains(Constants.ENDCHAR);

        return response;
    }

    // CheckSum

    public String checkSum(String message) {

        int checkSum = 0;

        String hex = toHexString(message.getBytes());

        //Log.d(TAG,"HexList: " + hex);

        byte [] bytes = hexStringToByteArray(hex);

        for(byte b : bytes){
            checkSum += (0xff & b);
        }

        //Log.d(TAG,"CheckSum: " + checkSum);
        String resp = String.format("%04X",checkSum & 0xffff);

        assert resp.length() == 4;

        return resp;
    }

    private String toHexString(byte[] ba) {

        StringBuilder str = new StringBuilder();
        for(int i = 0; i < ba.length; i++)
            str.append(String.format("%x", ba[i]));
        return str.toString();
    }

    private String fromHexString(String hex) {

        StringBuilder str = new StringBuilder();
        for (int i = 0; i < hex.length(); i+=2) {
            str.append((char) Integer.parseInt(hex.substring(i, i + 2), 16));
        }
        return str.toString();
    }

    private byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }


}
