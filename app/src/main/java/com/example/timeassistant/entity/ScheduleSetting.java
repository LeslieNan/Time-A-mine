package com.example.timeassistant.entity;


public class ScheduleSetting {
    private int shiftId;
    private int deptId;
    private int pin2;

    public int getPin2() {
        return pin2;
    }

    public void setPin2(int pin2) {
        this.pin2 = pin2;
    }

    public int getShiftId() {
        return shiftId;
    }

    public void setShiftId(int shiftId) {
        this.shiftId = shiftId;
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    @Override
    public String toString() {
        return "ScheduleSetting{" +
                "shiftId=" + shiftId +
                ", deptId=" + deptId +
                '}';
    }
}
