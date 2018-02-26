package com.nexer.nexerbluetooth.main.obd;

import android.content.Intent;
import android.util.Log;

import com.nexer.nexerbluetooth.main.aux.ChinaAux;
import com.nexer.nexerbluetooth.main.aux.Constants;
import com.nexer.nexerbluetooth.main.aux.NyitechMessages;
import com.nexer.nexerbluetooth.main.model.CompletedTrip;
import com.nexer.nexerbluetooth.main.model.DynamicData;
import com.nexer.nexerbluetooth.main.BluetoothChatService;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by guilhermecoelho on 2/20/18.
 */

public class NyitechDriver implements DeviceDriverInterface {

    private static final String TAG = "NyitechDriver";

    //==========================================================================
    // GLOBAL VARIABLES
    //==========================================================================

    private ArrayList<OBDDeviceReceiveInterface> mOBDDeviceReceiverListeners;
    private BluetoothChatService mBluetoothChatService;

    private String recMessage = "";

    // Sequence ID

    private BigInteger squenceId = BigInteger.ONE;

    // Trip Object

    private ArrayList<CompletedTrip> trips;
    private CompletedTrip currentTrip;

    // MESSAGES

    private boolean QUERYLASTDAY = false;

    // Variable used to see difference  in dates between devices
    private static long mDateDifference = 0;

    //==========================================================================
    // CONSTRUCTOR
    //==========================================================================

    public NyitechDriver(BluetoothChatService bluetoothChatService) {

        mOBDDeviceReceiverListeners = new
                ArrayList<OBDDeviceReceiveInterface>();
        mBluetoothChatService = bluetoothChatService;

        trips = new ArrayList<CompletedTrip>();
    }

    //==========================================================================
    // INTERFACE REGISTER
    //==========================================================================

    /**
     * Adds a listener to receive async messages from Sinocastel OBD device
     *
     * @param listener object that will receive messages
     */
    @Override
    public void addOBDReceivedMessagesListener(
            OBDDeviceReceiveInterface listener) {

        mOBDDeviceReceiverListeners.add(listener);

    }

    /**
     * Notifies all listeners when a OBD message occur
     *
     * @param notification the notification to be made
     * @param parameter    the parameter to notify
     */
    private void notifyOBDReceivedMessagesToAll(String notification,
                                                Object parameter) {

        for (int i = 0; i < mOBDDeviceReceiverListeners.size(); i++) {

            notifyOBDReceivedMessages(mOBDDeviceReceiverListeners.get(i),
                    notification, parameter);

        }

    }

    /**
     * Notify one message to specific listener
     *
     * @param listener     the listener to be notififed
     * @param notification the notification to be made
     * @param parameter    the parameter to be notified
     */
    private void notifyOBDReceivedMessages(OBDDeviceReceiveInterface listener,
                                           String notification,
                                           Object parameter) {

    }

    @Override
    public void login(long lastPackageReceived, long lastTripId, long lastTripSummaryTripId, long lastTripTotalPackages, long lastTripStartTime, long lastTripEndTime, long lastTripTotalKm) {

    }

    @Override
    public void configure(int parameter) {

    }

    @Override
    public void getVIN() {

    }

    //==========================================================================
    // WRITER
    //==========================================================================

    @Override
    public void sendMessageToDevice(String request) {

        Log.d(TAG,"MESSAGE WRITTEN: " + request);

        // Check that we're actually connected before trying anything
        if (mBluetoothChatService.getState() !=
                BluetoothChatService.STATE_CONNECTED) {
            return;
        }

        try {

            byte[] message = request.getBytes();

            mBluetoothChatService.write(message);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /// -- Aux methods to write on device --

    /**
     * Query the log of the last day

     Event generation: from APP Event type=4
     Event code=1
     Event data: nothing
     Query Example: $$APP_0000000000001,,4,1,122417,162647,,07B5**

     */
    private void queryLogForTheLastDay() {

        String request = "";

        String date = new SimpleDateFormat("MMddyy").format(Calendar.getInstance().getTime());
        String time = new SimpleDateFormat("HHmmss").format(Calendar.getInstance().getTime());

        request =
                "APP_0000000000001" + // APP ID
                Constants.COMMA + Constants.COMMA + // SKIP SEQUENCEID
                NyitechMessages.QUERY_LASTDAY + // 4,1 CODE FOR LOG OF TODAY
                Constants.COMMA + date + // DATE
                Constants.COMMA + time + // TIME
                Constants.COMMA +
                Constants.COMMA;// SKIP EVENT DATA

        String checkSum = ChinaAux.getInstance().checkSum(request);

        request = Constants.BEGINCHAR + request + checkSum + Constants.ENDCHAR;
        // Log.w(TAG,request);

        sendMessageToDevice(request);
    }

    /**
     * Reset the device

     Event generation: from APP
     Event type=8
     Event code=1
     Event data: nothing
     Example: $$APP_0000000000001,,8,1,122417,162647,,07B9**

     */
    private void resetDevice() {

        String request = "";

        String date = new SimpleDateFormat("MMddyy").format(Calendar.getInstance().getTime());
        String time = new SimpleDateFormat("HHmmss").format(Calendar.getInstance().getTime());

        request = Constants.BEGINCHAR +
                "APP_0000000000001" + // APP ID
                Constants.COMMA + Constants.COMMA + // SKIP SEQUENCEID
                NyitechMessages.RESET + // 8,1 CODE FOR RESET
                Constants.COMMA + date + // DATE
                Constants.COMMA + time + // TIME
                Constants.COMMA + Constants.COMMA;// SKIP EVENT DATA

         String checkSum = ChinaAux.getInstance().checkSum(request);

        request += checkSum + // CHECKSUM
                Constants.ENDCHAR;

        sendMessageToDevice("AT$SYSS<123456>=|||||||FFFF|*");
    }

    /**
     * Query Next Event
     */
    private void queryNextEvent() {

        String request = "";

        request = Constants.BEGINCHAR + Long.toHexString(this.squenceId.longValue()) + Constants.ENDCHAR;

        sendMessageToDevice(request);
    }

    //==========================================================================
    // PARSER
    //==========================================================================

    @Override
    public void parseDeviceResponse(String message) {

        if ( ChinaAux.getInstance().messageIsCompleted(message) ) {
            // message is completed

            recMessage = recMessage + message;

            assert  ChinaAux.getInstance().messageHasBeginAndEnd(recMessage);

            receivedCompletedMessage(recMessage);

            // clear message
            recMessage = "";

        } else {
            // Message is incompleted -> save and wait for next message with end chars
            recMessage = recMessage + message;
        }

    }

    private void receivedCompletedMessage(String message) {

        // delete special chars from message
        //Log.d(TAG,"Message read :" + message);

        String msg = ChinaAux.getInstance().removeSpecialCharsFrom(message);

        Log.d(TAG,"Formatted Message read :" + msg);

        DynamicData dynamicData = ChinaAux.getInstance().parseReceivedDynamicData(msg);

        Log.d(TAG,"Data :" + dynamicData.toString());

        this.squenceId = dynamicData.getSequenceId();

        if (  ChinaAux.getInstance().enginePowerUp(dynamicData) ) {
            // Engine is powerUp

            // Init a new Trip
            currentTrip = new CompletedTrip();

            currentTrip.setStartTime(dynamicData.getDate() + "_" + dynamicData.getTime());

            ArrayList<DynamicData> listOfData = new ArrayList<DynamicData>();
            listOfData.add(dynamicData);

            currentTrip.setData( listOfData );

        } else if (  ChinaAux.getInstance().engineShutDown(dynamicData) ) {
            // Engine is shutDown

            currentTrip.setEndTime(dynamicData.getDate() + "_" + dynamicData.getTime());

            ArrayList<DynamicData> listOfData = currentTrip.getData();
            listOfData.add(dynamicData);

            currentTrip.setData( listOfData );

            // Add to list of trips
            trips.add(currentTrip);

            // Reset Current Trip
            currentTrip = null;

        } else if (currentTrip != null) {
            // Data from a trip

            ArrayList<DynamicData> listOfData = currentTrip.getData();
            listOfData.add(dynamicData);

            currentTrip.setData( listOfData );
        }

        if (QUERYLASTDAY) {
            queryLogForTheLastDay();
            QUERYLASTDAY = false;
        }

        // Query for Next Message
        queryNextEvent();

    }


}
