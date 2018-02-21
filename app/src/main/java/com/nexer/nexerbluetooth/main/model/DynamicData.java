package com.nexer.nexerbluetooth.main.model;


public class DynamicData {

    // Dynamic Data from OBD

    private String sourceId;
    private int sequenceId;
    private int eventType;
    private int eventCode;
    private String date;
    private String time;
    private int checksum;

    // ------- Events Data --------

    private String speed;
    private String rpm;
    private String engineTemperature;
    private String fuelLevel;
    private float voltage;
    private int odometer;
    private int totalFuelUsage;
    private int engineHours;
    private boolean malfunction;
    private String vin;

    //==========================================================================
    // SETTER METHODS
    //==========================================================================

    public DynamicData() {

        this.sourceId = Constants.NULL_STR;
        this.sequenceId = -1;
        this.eventType = -1;
        this.eventCode = -1;
        this.date = date;
        this.time = time;
        this.checksum = -1;
        this.speed = speed;
        this.rpm = rpm;
        this.engineTemperature = engineTemperature;
        this.fuelLevel = fuelLevel;
        this.voltage = -1.0;
        this.odometer = -1;
        this.totalFuelUsage = -1;
        this.engineHours = -1;
        this.malfunction = malfunction;
        this.vin = "-";

    }

    public DynamicData(String sourceId,
                       int sequenceId,
                       int eventType,
                       int eventCode,
                       String date,
                       String time,
                       int checksum,
                       String speed,
                       String rpm,
                       String engineTemperature,
                       String fuelLevel,
                       float voltage,
                       int odometer,
                       int totalFuelUsage,
                       int engineHours,
                       boolean malfunction,
                       String vin) {

        this.sourceId = sourceId;
        this.sequenceId = sequenceId;
        this.eventType = eventType;
        this.eventCode = eventCode;
        this.date = date;
        this.time = time;
        this.checksum = checksum;
        this.speed = speed;
        this.rpm = rpm;
        this.engineTemperature = engineTemperature;
        this.fuelLevel = fuelLevel;
        this.voltage = voltage;
        this.odometer = odometer;
        this.totalFuelUsage = totalFuelUsage;
        this.engineHours = engineHours;
        this.malfunction = malfunction;
        this.vin = vin;
    }

    //==========================================================================
    // SETTER METHODS
    //==========================================================================

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public void setSequenceId(int sequenceId) {
        this.sequenceId = sequenceId;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public void setEventCode(int eventCode) {
        this.eventCode = eventCode;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setChecksum(int checksum) {
        this.checksum = checksum;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public void setRpm(String rpm) {
        this.rpm = rpm;
    }

    public void setEngineTemperature(String engineTemperature) {
        this.engineTemperature = engineTemperature;
    }

    public void setFuelLevel(String fuelLevel) {
        this.fuelLevel = fuelLevel;
    }

    public void setVoltage(float voltage) {
        this.voltage = voltage;
    }

    public void setOdometer(int odometer) {
        this.odometer = odometer;
    }

    public void setTotalFuelUsage(int totalFuelUsage) {
        this.totalFuelUsage = totalFuelUsage;
    }

    public void setEngineHours(int engineHours) {
        this.engineHours = engineHours;
    }

    public void setMalfunction(boolean malfunction) {
        this.malfunction = malfunction;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    //==========================================================================
    // Getter METHODS
    //==========================================================================


    public String getSourceId() {
        return sourceId;
    }

    public int getSequenceId() {
        return sequenceId;
    }

    public int getEventType() {
        return eventType;
    }

    public int getEventCode() {
        return eventCode;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public int getChecksum() {
        return checksum;
    }

    public String getSpeed() {
        return speed;
    }

    public String getRpm() {
        return rpm;
    }

    public String getEngineTemperature() {
        return engineTemperature;
    }

    public String getFuelLevel() {
        return fuelLevel;
    }

    public float getVoltage() {
        return voltage;
    }

    public int getOdometer() {
        return odometer;
    }

    public int getTotalFuelUsage() {
        return totalFuelUsage;
    }

    public int getEngineHours() {
        return engineHours;
    }

    public boolean isMalfunction() {
        return malfunction;
    }

    public String getVin() {
        return vin;
    }
}