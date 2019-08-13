package com.example.timeassistant.entity;

/**
 * 描述：
 * <p/>
 * 日期：2016/7/26
 * 作者：kangjj
 */


import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.annotation.Unique;
import com.litesuits.orm.db.enums.AssignType;

/**
 *
 */
@Table("SchedulePerson")
public class SchedulePerson extends BaseScheduleItem{
    public static final String COL_PIN2 = "pin2";
    public static final String COL_MNAME = "mname";
    public static final String COL_ISSELECT = "isselect";
    public static final String COL_SHIFTID = "shiftId";
    public static final String COL_DEVICESN = "deviceSn";
    public static final String COL_PINANDDEVICESN = "pinAndDevicesn";

    @PrimaryKey(AssignType.BY_MYSELF)
    @Column(COL_PINANDDEVICESN)
    @Unique
    private String pinAndDevicesn;              //liteOrm 找不到联合主键的用法 ，所以添加该字段用作主键 格式是：“pin2-devicesn”
    @Column(COL_PIN2)
    private String pin2;
    @Column(COL_MNAME)
    private String name;
    @Column(COL_ISSELECT)
    private boolean isSelect;
    @Column(COL_SHIFTID)
    private int shiftId=-1;
    @Column(COL_DEVICESN)
    private String deviceSn;

    public String getPinAndDevicesn() {
        return pinAndDevicesn;
    }

    public void setPinAndDevicesn(String pinAndDevicesn) {
        this.pinAndDevicesn = pinAndDevicesn;
    }

    public String getDeviceSn() {
        return deviceSn;
    }

    public void setDeviceSn(String deviceSn) {
        this.deviceSn = deviceSn;
    }

    public int getShiftId() {
        return shiftId;
    }

    public void setShiftId(int shiftId) {
        this.shiftId = shiftId;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getPin2() {
        return pin2;
    }

    public void setPin2(String pin2) {
        this.pin2 = pin2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
