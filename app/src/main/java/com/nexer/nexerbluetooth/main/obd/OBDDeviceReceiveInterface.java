package com.nexer.nexerbluetooth.main.obd;

/**
 * Created by guilhermecoelho on 2/20/18.
 */

public interface OBDDeviceReceiveInterface {


    // Receivers
    void onReceivePing(long lastDate);

    void onReceiveVIN(String vin);

    void onReceiveSerialNumber(String serialNumber);

    void onReceiveDataPacket(long packetNumber);

    void onReceiveTripId(long tripId);

    // {
    //   tripID:  "id of trip"
    //   time: "time of device in ms"
    //   mileage: "mileage of trip"
    //   dataNumber: "number of last packet received"
    //   dynamicsData: [
    //                   {MAP:"", RPM:"", speed:"",
    //                    engineTemeprature:"",
    //                    intakeAirTemperature:""}, {...}, ...
    //                 ]
    //   accelerationData: [
    //                        {accelerationX:"", accelerationY:"",
    //                         accelerationZ:""}, {...}, ...
    //                     ]
    // }
    void onReceiveHistoricalData(String historicalData);

    // {
    //   engineTemp, intakeAirTemp, MAP, RPM, speed, [...], mileage
    // }
    void onReceiveRealTimeData(String realTimeData);

    void onReceiveAlarmsData(String alarmsData);

    void onReceiveStoredDtcs(String storedDtcs);

    void onReceivePendingDtcs(String pendingDtcs);

    // {
    //   totalFuelConsumed, totalMileage, tripId
    // }
    void onReceiveTripSummaryData(String totalTripData);

}
