package com.nexer.nexerbluetooth.main.obd;

import android.util.Log;

import com.nexer.nexerbluetooth.main.aux.ChinaAux;
import com.nexer.nexerbluetooth.main.presentation.BluetoothChatService;

import org.json.JSONObject;

import java.util.ArrayList;

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

    // Variable used to see difference  in dates between devices
    private static long mDateDifference = 0;

    //==========================================================================
    // CONSTRUCTOR
    //==========================================================================

    public NyitechDriver(BluetoothChatService bluetoothChatService) {

        mOBDDeviceReceiverListeners = new
                ArrayList<OBDDeviceReceiveInterface>();
        mBluetoothChatService = bluetoothChatService;

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

    @Override
    public void sendMessageToDevice(String request) {

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
        message = ChinaAux.getInstance().removeSpecialCharsFrom(message);

        Log.i(TAG, "Completed Message: " + message);



    }
}
