package com.example.timeassistant.entity;

public class DevicesListItem {

    public static final int HISTORY=0;
    public static final int CONNECTED=1;

    private String Name;
    private int status;

    public DevicesListItem() {
    }

    public DevicesListItem(String name, int status) {
        Name = name;
        this.status = status;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
