package com.example.timeassistant.database;

import com.example.timeassistant.entity.UuDevice;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.zkteco.android.framework.liteorm.DBHelper;
import com.zkteco.android.framework.utils.ZKTools;

import java.util.List;

/**
 * 描述：
 * <p/>
 * 日期：2016/7/19
 * 作者：kangjj
 */
public class UuDeviceOperate {

    public synchronized static UuDevice getDevice(String deviceSn){
        UuDevice uuDevice = null;
        QueryBuilder<UuDevice> queryBuilder = new QueryBuilder<UuDevice>(UuDevice.class);
        queryBuilder.whereEquals(UuDevice.COL_DEVICESN, deviceSn);
        List<UuDevice> list = DBHelper.getInstance().getLiteOrm().query(queryBuilder);
        if (list != null && list.size() > 0) {
            uuDevice = list.get(0);
        }
        return uuDevice;
    }

    public synchronized static List<UuDevice> getDeviceListNoEquals(String deviceSn){
        QueryBuilder<UuDevice> queryBuilder = new QueryBuilder<UuDevice>(UuDevice.class);
        queryBuilder.whereNoEquals(UuDevice.COL_DEVICESN, deviceSn);
        List<UuDevice> list = DBHelper.getInstance().getLiteOrm().query(queryBuilder);
        return list;
    }

    public synchronized static List<UuDevice> getAllDeviceList(){
        return DBHelper.getInstance().getLiteOrm().query(UuDevice.class);
    }

    public synchronized static void updateDevice(UuDevice uuDevice){
        DBHelper.getInstance().getLiteOrm().update(uuDevice);
    }

    public synchronized static void delete(UuDevice uuDevice){
        DBHelper.getInstance().getLiteOrm().delete(uuDevice);
    }


    public static boolean hasDeviceSn(String deviceSn) {
        QueryBuilder<UuDevice> queryBuilder = new QueryBuilder<UuDevice>(UuDevice.class);
        queryBuilder.whereEquals(UuDevice.COL_DEVICESN, deviceSn);
        List<UuDevice> list = DBHelper.getInstance().getLiteOrm().query(queryBuilder);
        return !ZKTools.isEmpty(list);
    }
}
