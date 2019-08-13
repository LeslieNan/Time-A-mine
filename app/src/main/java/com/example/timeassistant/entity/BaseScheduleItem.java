package com.example.timeassistant.entity;

import com.litesuits.orm.db.annotation.Column;

/**
 * Created by xqc on 2016/5/31.
 * ZKTeco App
 */
public class BaseScheduleItem implements java.io.Serializable{
    private static final String TAG = "BaseScheduleItem";

    public static final String COL_MISSELECT = "mIsSelect";
    public static final String COL_NAME = "Name";

    @Column(COL_MISSELECT)
    protected boolean mIsSelect;                //在部门表中，代表全部是否选中，选择中的话 在其他班次就不能选
    @Column(COL_NAME)
    protected String mName;

    public BaseScheduleItem() {
    }

    public BaseScheduleItem(boolean isSelect, String name) {
        mIsSelect = isSelect;
        mName = name;
    }

    public boolean ismIsSelect() {
        return mIsSelect;
    }

    public void setmIsSelect(boolean mIsSelect) {
        this.mIsSelect = mIsSelect;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    @Override
    public String toString() {
        return "BaseScheduleItem{" +
                "mIsSelect=" + mIsSelect +
                ", mName='" + mName + '\'' +
                '}';
    }
}
