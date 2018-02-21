package com.nexer.nexerbluetooth.main.obd;


import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.nexer.nexerbluetooth.main.presentation.BluetoothChatService;
import org.json.JSONObject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import timber.log.Timber;

public class OBDDeviceChatService implements OBDDeviceRequestInterface {

    //==========================================================================
    // GLOBAL VARIABLES
    //==========================================================================

    private static final String TAG = "OBDDeviceCommunicator";

    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothChatService mBluetoothChatService;
    private boolean mAdapterAvailable = true;

    private ArrayList<OBDDeviceStateInterface> mOBDDeviceStateListeners;

    private ArrayList<OBDDeviceReceiveInterface>
            mDelayedOBDDeviceReceiverListeners;

    private DeviceDriverInterface mManufacturerDeviceDriver;


    //==========================================================================
    // CONSTRUCTOR AND LIFECYCLE METHODDS
    //==========================================================================

    /**
     * Constructor
     */
    public OBDDeviceChatService() {

        // Get local Bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {
            //Toast.makeText(mContext, R.string.bluetooth_doesnt_exist,
            //		Toast.LENGTH_LONG).show();
            mAdapterAvailable = false;
        } else {
            mAdapterAvailable = true;
        }

        // Initialize list of listeners
        mOBDDeviceStateListeners = new ArrayList<OBDDeviceStateInterface>();
        mDelayedOBDDeviceReceiverListeners = new
                ArrayList<OBDDeviceReceiveInterface>();


    }

    /**
     * Resumes the communication channel to the point before it was stopped
     */
    public boolean resume() {

        boolean isBluetoothActive = false;

        if (mBluetoothChatService != null) {
            // Only if the state is STATE_NONE, do we know that
            // we haven't started already
            if (mBluetoothChatService.getState() ==
                    BluetoothChatService.STATE_NONE) {
                // Start the Bluetooth chat services
                mBluetoothChatService.start();
            }

            isBluetoothActive = true;
        } else {
            isBluetoothActive = false;
        }

        return isBluetoothActive;
    }

    /**
     * Destroys the communication channel
     */
    public void destroy() {
        if (mBluetoothChatService != null) {
            mBluetoothChatService.stop();
        }
    }

    /**
     * Connects to the communication channel
     */
    public void tryConnection(String macAddress, boolean secure) {

        Log.d(TAG, "OBD_START_CONN");

        if (isAdapterAvailable()) {
            connectToBluetoothDevice(macAddress, secure);
        }

        Log.d(TAG, "OBD_FINISH_CONN");
    }

    /**
     * If there is a physical layer to communicate to device, return true
     *
     * @return If the device is compatible
     */
    public boolean isAdapterAvailable() {

        boolean isAvailable = false;

        if (mBluetoothAdapter != null){

            isAvailable = mAdapterAvailable && mBluetoothAdapter.isEnabled();

        }

        return isAvailable;

    }

    /**
     * If there is a physical layer to communicate to device, return true
     *
     * @return If the device is compatible
     */
    public void forceBluetoothEnable() {
        mBluetoothAdapter.enable();
    }


    /**
     * If there is a physical layer to communicate to device, return true
     *
     * @return If the device is compatible
     */
    public void forceBluetoothDisable() {
        mBluetoothAdapter.disable();
    }


    /**
     * Initializes object that handles bluetooth connection
     */
    public void setupBluetooth() {

        // Setip only if not yet created
        if (mBluetoothChatService == null) {

            boolean usesGenericDevice = false;
            // Initialize the BluetoothChatService to perform bluetooth connections
            mBluetoothChatService = new BluetoothChatService(
                    mBluetoothMessagesHandler, usesGenericDevice);

            // If we need to implement communication with other devices, we need
            // to change below instance
            mManufacturerDeviceDriver = new NyitechDriver(mBluetoothChatService);
            flushDelayedOBDReceivedMessagesListeners();
        }

    }


    /**
     * Verifies if the MAC address is valid
     *
     * @param address
     * @return true if the address is valid
     */
    public boolean assertDeviceAddress(String address) {
        boolean match = false;

        if (address != null) {
            match = address.matches("([\\da-fA-F]{2}(?:\\:|$)){6}");
        } else {
            match = false;
        }

        return match;
    }


    //==========================================================================
    // INTERNAL METHODS
    //==========================================================================

    /**
     * Tries to connect to a given bluetooth device address
     *
     * @param macAddress MAC address of device that we will try to connect
     * @param secure     boolean that specifies if we will try a secure or
     *                   insecure connection
     */
    private void connectToBluetoothDevice(String macAddress, boolean secure) {
        // Get the BluetoothDevice object
        if (assertDeviceAddress(macAddress)) {

            BluetoothDevice device = mBluetoothAdapter
                    .getRemoteDevice(macAddress);

            if (mBluetoothChatService != null && device != null) {

                mBluetoothChatService.connect(device, secure);

            } else if (mBluetoothChatService == null) {

                Log.i(TAG, "chat service not valid");

                // If we do not have a valid chat service, setup it
                destroy();
                setupBluetooth();

            } else {
                Log.i(TAG, "mBluetoothChatService = " + (mBluetoothChatService == null));
                Log.i(TAG, "device = " + (device == null ? "NULL" : macAddress));
            }

        }
    }

    /**
     * Handles communication with the BluetoothChatService class
     */
    @SuppressLint("HandlerLeak")
    private final Handler mBluetoothMessagesHandler = new Handler() {

        @Override
        public void handleMessage(Message bluetoothMessage) {

            switch (bluetoothMessage.what) {

                case BluetoothChatService.MESSAGE_STATE_CHANGE:

                    Log.d(TAG, "MESSAGE_STATE_CHANGE: " + bluetoothMessage.arg1);
                    switch (bluetoothMessage.arg1) {
                        case BluetoothChatService.STATE_CONNECTED:

                            notifyBluetoothStateChangesToAll("onDeviceStateConnected");
                            break;

                        case BluetoothChatService.STATE_CONNECTING:
                            notifyBluetoothStateChangesToAll("onDeviceStateConnecting");
                            break;

                        case BluetoothChatService.STATE_LISTEN:
                        case BluetoothChatService.STATE_NONE:
                            notifyBluetoothStateChangesToAll(
                                    "onDeviceStateDisconnected");
                            break;
                        default:
                            break;
                    }

                case BluetoothChatService.MESSAGE_READ:

                    if (bluetoothMessage.obj != null ) {

                        byte[] readBuf = (byte[]) bluetoothMessage.obj;
                        // construct a string from the valid bytes in the buffer
                        String readMessage = new String(readBuf, 0, bluetoothMessage.arg1);

                        if (readMessage != null) {

                            mManufacturerDeviceDriver
                                    .parseDeviceResponse(readMessage);
                        }
                    }

                    break;

                case BluetoothChatService.MESSAGE_WRITE:

                    break;

                case BluetoothChatService.MESSAGE_DEVICE_NAME:

                    break;

                case BluetoothChatService.MESSAGE_CONNECTION_FAILED:

                    break;

                case BluetoothChatService.MESSAGE_CONNECTION_LOST:

                    break;

                default:
                    break;
            }
        }
    };


    //==========================================================================
    // OBD DEVICE REQUEST INTERFACE METHODS
    //==========================================================================

    /**
     * Initiates communication with OBD device from the point it stopped
     *
     * @param lastPackageReceived the last package we've read from the device
     *                            before the communication was interrupted
     */
    @Override
    public void login(long lastPackageReceived,
                      long lastTripId,
                      long lastTripSummaryTripId,
                      long lastTripTotalPackages,
                      long lastTripStartTime,
                      long lastTripEndTime,
                      long lastTripTotalKm) {

        mManufacturerDeviceDriver.login(
                lastPackageReceived,
                lastTripId,
                lastTripSummaryTripId,
                lastTripTotalPackages,
                lastTripStartTime,
                lastTripEndTime,
                lastTripTotalKm);

    }


    /**
     * Gets the vehicle VIN code
     */
    @Override
    public void getVIN() {

        mManufacturerDeviceDriver.getVIN();

    }


    @Override
    public void configure(int parameter) {

        mManufacturerDeviceDriver.configure(parameter);

    }

    @Override
    public void sendMessageToDevice(String message) {

    }

    //==========================================================================
    // OBD RECEIVE MESSAGES INTERFACE REGISTER
    //==========================================================================

    /**
     * Adds a listener to receive async messages from OBD device. The listeners
     *
     * @param listener object that will receive messages
     */
    public void addOBDReceiveMessagesListener(
            OBDDeviceReceiveInterface listener) {

        if (mManufacturerDeviceDriver == null) {

            mDelayedOBDDeviceReceiverListeners.add(listener);

        } else {

            mManufacturerDeviceDriver
                    .addOBDReceivedMessagesListener(listener);

        }

    }


    /**
     * Instantiates listeners that where waiting the communication channel to be
     * created
     */
    private void flushDelayedOBDReceivedMessagesListeners() {

        for (OBDDeviceReceiveInterface listener :
                mDelayedOBDDeviceReceiverListeners) {

            addOBDReceiveMessagesListener(listener);

        }

    }


    //==========================================================================
    // BLUETOOTH STATE CHANGES INTERFACE REGISTER
    //==========================================================================

    /**
     * Adds a listener to receive async messages from bluetooth stack
     *
     * @param listener object that will receive messages
     */
    public void addBluetoothStateChangesListener(
            OBDDeviceStateInterface listener) {
        mOBDDeviceStateListeners.add(listener);
    }

    /**
     * Notifies all listeners of changes that occurred in the bluetooth stack
     *
     * @param notification what will be notifed
     */
    private void notifyBluetoothStateChangesToAll(String notification) {

        for (OBDDeviceStateInterface listener : mOBDDeviceStateListeners) {
            notifyBluetoothStateChanges(listener, notification);
        }

    }


    /**
     * Notifies each bluetooth state listeners individually
     *
     * @param listener     subscriber of this messages
     * @param notification what will be notifed
     */
    private void notifyBluetoothStateChanges(OBDDeviceStateInterface listener,
                                             String notification) {

        Method method;

        try {
            method = listener.getClass().getMethod(notification);
            method.invoke(listener);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

}