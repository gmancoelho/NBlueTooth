package com.nexer.nexerbluetooth.main.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by guilhermecoelho on 2/21/18.
 */

public class CompletedTrip {

    private ArrayList<DynamicData> data;

    private String startTime;
    private String endTime;

    public CompletedTrip() {

        this.startTime = null;
        this.endTime = null;

    }

    public CompletedTrip(ArrayList<DynamicData> data, String startTime, String endTime) {
        this.data = data;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // GETTER SETTER

    public ArrayList<DynamicData> getData() {
        return data;
    }

    public void setData(ArrayList<DynamicData> data) {
        this.data = data;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "CompletedTrip{" +
                "data=" + data +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }

}
