package com.enigma.dif.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Events")
public class Events {

    @PrimaryKey(autoGenerate = true)
    private int eid;

    @ColumnInfo(name = "message")
    private String message;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "time")
    private String time;

    @ColumnInfo(name = "location")
    private String location;

    @ColumnInfo(name = "category")
    private String category;

    @ColumnInfo(name = "remainder")
    private Boolean remainder;

    @Ignore
    public Events(int eid, String message, String date, String time, String category) {
        this.eid = eid;
        this.message = message;
        this.date = date;
        this.time = time;
        this.category = category;
    }

    public Events() {

    }

    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getRemainder() {
        return remainder;
    }

    public void setRemainder(Boolean remainder) {
        this.remainder = remainder;
    }
}
