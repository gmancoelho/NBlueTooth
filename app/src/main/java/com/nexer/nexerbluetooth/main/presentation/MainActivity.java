package com.nexer.nexerbluetooth.main.presentation;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nexer.nexerbluetooth.main.BluetoothConnector;
import com.nexer.nexerbluetooth.main.obd.OBDDeviceChatService;

import butterknife.BindView;
import butterknife.ButterKnife;
import comunicacao.bluetooth.R;


public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_SELECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;

    public static final String TAG = "MainActivity";

    // Properties

    // OBD CHAT SERVICE

    OBDDeviceChatService mChatService;

    private ListView messageListView;
    private ArrayAdapter<String> listAdapter;
    private Button btnConnectDisconnect;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.deviceName)
    TextView mDeviceText;

    // Life Cycle

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean startConnection = true;

        setUpViews();

        // Start Bluetooth connection

        mChatService =  new OBDDeviceChatService();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            startConnection = askCoarseLocation();
        }

    }

    // Class Methods

    public void setUpViews(){

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        listAdapter = new ArrayAdapter<String>(this, R.layout.message_detail);

        messageListView = (ListView) findViewById(R.id.listMessage);
        messageListView.setAdapter(listAdapter);
        messageListView.setDivider(null);

        btnConnectDisconnect = (Button) findViewById(R.id.btn_select);

        // Handle Disconnect & Connect button
        btnConnectDisconnect.setOnClickListener(deviceListener);

    }

    public boolean askCoarseLocation() {

        boolean startConnection = true;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if ( this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED ) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setTitle("This app needs location access");
                builder.setMessage("Please grant location access so this app can detect beacons.");
                builder.setPositiveButton(android.R.string.ok, null);

                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                    @TargetApi(Build.VERSION_CODES.M)
                    @Override

                    public void onDismiss(DialogInterface dialog) {
                        requestPermissions(new String[]{
                                        Manifest.permission.ACCESS_COARSE_LOCATION},
                                PERMISSION_REQUEST_COARSE_LOCATION);
                    }
                });

                builder.show();
                startConnection = false;
            }
        }
        return startConnection;
    }

    private void connectDeviceList() {

        Intent newIntent = new Intent(MainActivity.this,
                BluetoothSearchService.class);

        startActivityForResult(newIntent, REQUEST_SELECT_DEVICE);

    }

    private void connectedDevice(BluetoothDevice device) {

        String deviceAddress = device.getAddress();

        Toast.makeText(this,deviceAddress,Toast.LENGTH_SHORT).show();

        mDeviceText.setText(deviceAddress);
        btnConnectDisconnect.setEnabled(false);

        mChatService.setupBluetooth();

        mChatService.tryConnection(deviceAddress,true);

    }

    // Listerners

    private View.OnClickListener deviceListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            if (btnConnectDisconnect.getText().equals("Connect")) {
                connectDeviceList();
            }
        }
    };

    private Button.OnClickListener btnListPairedDevicesOnClickListener
            = new Button.OnClickListener(){
        @Override
        public void onClick(View arg0) {

            // class, with popup windows that scan for devices
            Intent newIntent = new Intent(MainActivity.this, BluetoothChatService.class);
            startActivityForResult(newIntent, REQUEST_SELECT_DEVICE);
        }};

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {

            case REQUEST_SELECT_DEVICE:

                if (resultCode == Activity.RESULT_OK) {

                    BluetoothDevice selectedDevice = data.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    if (selectedDevice != null ) {
                        this.connectedDevice( selectedDevice );
                    } else {
                        Toast.makeText(this, "Device not found",
                                Toast.LENGTH_SHORT).show();
                    }
                }

                break;

            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {

                    Toast.makeText(this, "Bluetooth has turned on ",
                            Toast.LENGTH_SHORT).show();

                } else {

                    // User did not enable Bluetooth or an error occurred
                    Log.d(TAG, "BT not enabled");
                    Toast.makeText(this, "Problem in BT Turning ON ",
                            Toast.LENGTH_SHORT).show();
                    finish();

                }
                break;

            default:
                Log.e(TAG, "wrong request code");
                break;

        }
    }

    private void deviceIsConnected(String stringExtra) {
    }

}