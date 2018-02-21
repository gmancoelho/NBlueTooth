package com.nexer.nexerbluetooth.main.model;


import com.nexer.nexerbluetooth.main.aux.Constants;

import java.math.BigInteger;

public class DynamicData {

    // Dynamic Data from OBD

    private String sourceId;
    private BigInteger sequenceId;
    private BigInteger eventType;
    private BigInteger eventCode;
    private String date;
    private String time;
    private BigInteger checksum;

    // ------- Events Data --------

    private String speed;
    private String rpm;
    private String engineTemperature;
    private String fuelLevel;
    private float voltage;
    private BigInteger odometer;
    private BigInteger totalFuelUsage;
    private BigInteger engineHours;
    private boolean malfunction;
    private String vin;

    //==========================================================================
    // SETTER METHODS
    //==========================================================================

    public DynamicData() {

        this.sourceId = Constants.NULL_STR;
        this.sequenceId = BigInteger.ZERO;
        this.eventType = BigInteger.ZERO;
        this.eventCode = BigInteger.ZERO;
        this.date = Constants.NULL_STR;
        this.time = Constants.NULL_STR;
        this.checksum = BigInteger.ZERO;
        this.speed = Constants.NULL_STR;
        this.rpm = Constants.NULL_STR;
        this.engineTemperature = Constants.NULL_STR;
        this.fuelLevel = Constants.NULL_STR;
        this.voltage = -1;
        this.odometer = BigInteger.ZERO;
        this.totalFuelUsage = BigInteger.ZERO;
        this.engineHours = BigInteger.ZERO;
        this.malfunction = false;
        this.vin = Constants.NULL_STR;

    }

    public DynamicData(String sourceId,
                       BigInteger sequenceId,
                       BigInteger eventType,
                       BigInteger eventCode,
                       String date,
                       String time,
                       BigInteger checksum,
                       String speed,
                       String rpm,
                       String engineTemperature,
                       String fuelLevel,
                       float voltage,
                       BigInteger odometer,
                       BigInteger totalFuelUsage,
                       BigInteger engineHours,
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

    public void setSequenceId(BigInteger sequenceId) {
        this.sequenceId = sequenceId;
    }

    public void setEventType(BigInteger eventType) {
        this.eventType = eventType;
    }

    public void setEventCode(BigInteger eventCode) {
        this.eventCode = eventCode;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setChecksum(BigInteger checksum) {
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

    public void setOdometer(BigInteger odometer) {
        this.odometer = odometer;
    }

    public void setTotalFuelUsage(BigInteger totalFuelUsage) {
        this.totalFuelUsage = totalFuelUsage;
    }

    public void setEngineHours(BigInteger engineHours) {
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

    public BigInteger getSequenceId() {
        return sequenceId;
    }

    public BigInteger getEventType() {
        return eventType;
    }

    public BigInteger getEventCode() {
        return eventCode;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public BigInteger getChecksum() {
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

    public BigInteger getOdometer() {
        return odometer;
    }

    public BigInteger getTotalFuelUsage() {
        return totalFuelUsage;
    }

    public BigInteger getEngineHours() {
        return engineHours;
    }

    public boolean isMalfunction() {
        return malfunction;
    }

    public String getVin() {
        return vin;
    }

    //==========================================================================
    // ToString
    //==========================================================================

    @Override
    public String toString() {
        return "DynamicData{" +
                "sourceId='" + sourceId + '\'' +
                ", sequenceId=" + sequenceId +
                ", eventType=" + eventType +
                ", eventCode=" + eventCode +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", checksum=" + checksum +
                ", speed='" + speed + '\'' +
                ", rpm='" + rpm + '\'' +
                ", engineTemperature='" + engineTemperature + '\'' +
                ", fuelLevel='" + fuelLevel + '\'' +
                ", voltage=" + voltage +
                ", odometer=" + odometer +
                ", totalFuelUsage=" + totalFuelUsage +
                ", engineHours=" + engineHours +
                ", malfunction=" + malfunction +
                ", vin='" + vin + '\'' +
                '}';
    }
}