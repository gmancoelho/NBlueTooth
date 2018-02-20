package com.nexer.nexerbluetooth.main.presentation;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class BluetoothSearchService extends Activity {

    // Debugging
    private static final String TAG = "NEXER_DEVICE_SEARCH";

    // Return Intent extra
    public static final String DEVICE_NOT_FOUND = "DEVICE_NOT_FOUND";

    // Member fields
    private ArrayList<String> mLastAddressReceived;
    private BluetoothAdapter mBluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set result CANCELED in case the user backs out
        setResult(Activity.RESULT_CANCELED);

        mLastAddressReceived = new ArrayList<String>();

        // Register for broadcasts when a device is discovered
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(mReceiver, filter);

        // Register for broadcasts when discovery has finished
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(mReceiver, filter);

        // Get the local Bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        doDiscovery();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    /**
     * Start device discover with the BluetoothAdapter
     */
    private void doDiscovery() {

        // If we're already discovering, stop it
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }

        // Request discover from BluetoothAdapter
        mBluetoothAdapter.startDiscovery();
    }


    public void processResults(BluetoothDevice device, boolean found) {

        // Cancel discovery because it's costly and we're about to connect
        mBluetoothAdapter.cancelDiscovery();

        // Create the result Intent and include the MAC address
        Intent intent = new Intent();
        if (found) {
            intent.putExtra(BluetoothDevice.EXTRA_DEVICE, device);
        } else {
            intent.putExtra(BluetoothDevice.EXTRA_DEVICE, DEVICE_NOT_FOUND);
        }

        // Make sure we're not doing discovery anymore
        if (mBluetoothAdapter != null) {
            mBluetoothAdapter.cancelDiscovery();
        }

        // Unregister broadcast listeners
        this.unregisterReceiver(mReceiver);

        // Set result and finish this Activity
        setResult(Activity.RESULT_OK, intent);
        finish();

    }

    // The BroadcastReceiver that listens for discovered devices and
    // changes the title when discovery is finished
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            // When discovery finds a device
            if (action.equals(BluetoothDevice.ACTION_FOUND)) {

                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(
                        BluetoothDevice.EXTRA_DEVICE);
                // Gets device address
                String deviceAddress = device.getAddress();

                // TODO: add paired device?
                // If it's already paired, skip since it's been listed already
                if (deviceAddress != null) {

                    Log.i(TAG, deviceAddress);

                    String deviceName = device.getName();

                    // TODO: include paired devices?
                    if ( !mLastAddressReceived.contains(deviceAddress)) {

                        // We only add device if it is Nexer
                        if (deviceName != null) {
                            Log.i(TAG, deviceName);

                            mLastAddressReceived.add(deviceAddress);

                           if (deviceName.contains("161B")) {
                                // If found device, returns MAC Address
                               processResults(device, true);

                           }
                        }

                    }
                }

            }

            // When discovery is finished, change the Activity title
            else if (action.equals(BluetoothAdapter
                    .ACTION_DISCOVERY_FINISHED)) {

                processResults(null, false);

            }

        }


    };


}

