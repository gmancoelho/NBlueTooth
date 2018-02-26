package com.nexer.nexerbluetooth.main.obd;

import org.json.JSONObject;

import java.util.ArrayList;

public interface DeviceDriverInterface {

    // Configuration parameters
    int DEVICE_CONFIG_DEFAULT = 0;
    int DEVICE_CONFIG_SET_DATE = 1;

    void addOBDReceivedMessagesListener(OBDDeviceReceiveInterface
                                                listener);

    void login(long lastPackageReceived,
               long lastTripId,
               long lastTripSummaryTripId,
               long lastTripTotalPackages,
               long lastTripStartTime,
               long lastTripEndTime,
               long lastTripTotalKm);

    void configure(int parameter);

    void getVIN();


    // Internals
    void parseDeviceResponse(String message);

    void sendMessageToDevice(String request);

    interface View {

        void onLogReceived(ArrayList<String> logs);

    }
}