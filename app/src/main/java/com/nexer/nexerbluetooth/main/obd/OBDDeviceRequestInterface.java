package com.nexer.nexerbluetooth.main.obd;

/**
 * Created by guilhermecoelho on 2/20/18.
 */

public interface OBDDeviceRequestInterface {

    void login(long lastPackageReceived,
               long lastTripId,
               long lastTripSummaryTripId,
               long lastTripTotalPackages,
               long lastTripStartTime,
               long lastTripEndTime,
               long lastTripTotalKm);

    void getVIN();

    void configure(int parameter);

    void sendMessageToDevice(String message);

}