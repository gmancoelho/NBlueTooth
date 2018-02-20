package com.nexer.nexerbluetooth.main.aux;

import android.annotation.SuppressLint;

import com.nexer.nexerbluetooth.main.model.DynamicData;

import org.json.JSONException;
import org.json.JSONObject;

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

    private static String endChar = "**";
    private static String beginChar = "$$";


    // Private methods


    // Public methods

    public DynamicData parseReceivedDynamicData(JSONObject realTimeData) {

        DynamicData data = new DynamicData();

        int speed = 0;
        int rpm = 0;
        int engineTemperature = 0;
        int intakeAirTemperature = 0;
        double throttlePosition = 0;
        double loadPercentage = 0;
        int map = 0;
        int maf = 0;
        int fuelStatus1 = 0;
        int fuelStatus2 = 0;
        long tripMileage = 0;
        long distanceWithMilOn = 0;
        int gearEngaged = 0;

        try {

            speed = realTimeData.getInt("speed");
            rpm = realTimeData.getInt("RPM");
            engineTemperature = realTimeData.getInt("engineTemperature");
            tripMileage = realTimeData.getLong("mileage");
            intakeAirTemperature = realTimeData.getInt("intakeAirTemperature");
            throttlePosition = realTimeData.getDouble("throttlePosition");
            loadPercentage = realTimeData.getDouble("loadPercentage");
            fuelStatus1 = realTimeData.getInt("fuelStatus1");
            fuelStatus2 = realTimeData.getInt("fuelStatus2");
            distanceWithMilOn = realTimeData.getInt("distanceWithMilOn");
            gearEngaged = -1;
            if (!realTimeData.isNull("MAP")) {
                map = realTimeData.getInt("MAP");
            }
            if (!realTimeData.isNull("MAF")) {
                maf = realTimeData.getInt("MAF");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        data.setDistanceWithMilOn(distanceWithMilOn);
        data.setFuelStatus1(fuelStatus1);
        data.setFuelStatus2(fuelStatus2);
        data.setGearEngaged(gearEngaged);
        data.setEngineTemperature(engineTemperature);
        data.setIntakeAirTemperature(intakeAirTemperature);
        data.setLoadPercentage(loadPercentage);
        data.setMaf(maf);
        data.setMap(map);
        data.setRpm(rpm);
        data.setSpeed(speed);
        data.setThrottlePosition(throttlePosition);
        data.setTripMileage(tripMileage);

        return  data;
    }


    // Message Builder Methods

    public boolean messageIsCompleted(String message) {

        return message.contains(endChar);
    }

    public boolean messageHasBeginAndEnd(String message) {

        return message.contains(endChar) && message.contains(beginChar);
    }

    public String removeSpecialCharsFrom(String message) {

        String response = message.replace(beginChar, "");

        response = response.replace(endChar,"");

        assert !message.contains(beginChar);
        assert !message.contains(endChar);

        return response;
    }

    public String createMessageToOBD(String message) {

        String response = beginChar + message + endChar;

        assert message.contains(beginChar);
        assert message.contains(endChar);

        return response;
    }

}
