package com.nexer.nexerbluetooth.main.model;

import com.nexer.nexerbluetooth.main.aux.Constants;

/**
 * Created by guilhermecoelho on 2/21/18.
 */

public class MessageToOBD {

    private String sourceId;
    private long sequenceId;
    private long eventType;
    private long eventCode;
    private String date;
    private String time;
    private long checksum;

    // Constructor

    public MessageToOBD() {

        this.sourceId = Constants.NULL_STR;
        this.sequenceId = -1;
        this.eventType = -1;
        this.eventCode = -1;
        this.date = Constants.NULL_STR;
        this.time = Constants.NULL_STR;
        this.checksum = -1;
    }

    // GETTER SETTER

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public long getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(long sequenceId) {
        this.sequenceId = sequenceId;
    }

    public long getEventType() {
        return eventType;
    }

    public void setEventType(long eventType) {
        this.eventType = eventType;
    }

    public long getEventCode() {
        return eventCode;
    }

    public void setEventCode(long eventCode) {
        this.eventCode = eventCode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getChecksum() {
        return checksum;
    }

    public void setChecksum(long checksum) {
        this.checksum = checksum;
    }

}
