package com.nexer.nexerbluetooth.main.obd;

/**
 * Created by guilhermecoelho on 2/20/18.
 */

public interface OBDDeviceStateInterface {


        // Device States
        void onDeviceStateConnected();

        void onDeviceStateConnecting();

        void onDeviceStateDisconnected();

}


