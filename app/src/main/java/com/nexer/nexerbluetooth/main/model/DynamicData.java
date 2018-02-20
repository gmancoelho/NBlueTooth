package com.nexer.nexerbluetooth.main.model;


public class DynamicData {

    // Acquired info
    private int speed;
    private int rpm;
    private int engineTemperature;
    private int intakeAirTemperature;
    private double throttlePosition;
    private double loadPercentage;
    private int map;
    private int maf;

    // Not used
    private int fuelStatus1;
    private int fuelStatus2;
    private long distanceWithMilOn;

    // Mileage and distances
    private long tripMileage;

    // Processed info
    private int gearEngaged;

    public DynamicData() {

    }

    //==========================================================================
    // SETTER METHODS
    //==========================================================================

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setRpm(int rpm) {
        this.rpm = rpm;
    }

    public void setEngineTemperature(int engineTemperature) {
        this.engineTemperature = engineTemperature;
    }

    public void setIntakeAirTemperature(int intakeAirTemperature) {
        this.intakeAirTemperature = intakeAirTemperature;
    }

    public void setThrottlePosition(double throttlePosition) {
        this.throttlePosition = throttlePosition;
    }

    public void setLoadPercentage(double loadPercentage) {
        this.loadPercentage = loadPercentage;
    }

    public void setMap(int map) {
        this.map = map;
    }

    public void setMaf(int maf) {
        this.maf = maf;
    }

    public void setFuelStatus1(int fuelStatus1) {
        this.fuelStatus1 = fuelStatus1;
    }

    public void setFuelStatus2(int fuelStatus2) {
        this.fuelStatus2 = fuelStatus2;
    }

    public void setTripMileage(long tripMileage) {
        this.tripMileage = tripMileage;
    }

    public void setDistanceWithMilOn(long distanceWithMilOn) {
        this.distanceWithMilOn = distanceWithMilOn;
    }

    public void setGearEngaged(int gearEngaged) {
        this.gearEngaged = gearEngaged;
    }


    //==========================================================================
    // GETTER METHODS
    //==========================================================================

    public int getSpeed() {
        return this.speed;
    }

    public int getRpm() {
        return this.rpm;
    }

    public int getEngineTemperature() {
        return this.engineTemperature;
    }

    public int getIntakeAirTemperature() {
        return this.intakeAirTemperature;
    }

    public double getThrottlePosition() {
        return this.throttlePosition;
    }

    public double getLoadPercentage() {
        return this.loadPercentage;
    }

    public int getMap() {
        return this.map;
    }

    public int getMaf() {
        return this.maf;
    }

    public int getFuelStatus1() {
        return this.fuelStatus1;
    }

    public int getFuelStatus2() {
        return this.fuelStatus2;
    }

    public long getTripMileage() {
        return this.tripMileage;
    }

    public long getDistanceWithMilOn() {
        return this.distanceWithMilOn;
    }

    public int getGearEngaged() {
        return this.gearEngaged;
    }


}