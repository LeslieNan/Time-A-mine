package com.example.timeassistant.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;

import com.example.timeassistant.R;
import com.example.timeassistant.database.UuDeviceOperate;
import com.example.timeassistant.entity.UuDevice;
import com.zkteco.android.framework.base.BaseApplication;
import com.zkteco.android.framework.liteorm.DBHelper;
import com.zkteco.android.framework.utils.ZKLog;
import com.zkteco.coreservice.UdkHelper;

import java.security.MessageDigest;
import java.util.Date;

/**
 * Created by xqc on 2015/12/4.
 * ZKTeco App
 */
public class UuDeviceBusiness {
    private static final String TAG = UuDeviceBusiness.class.getSimpleName();

    public static UuDevice getDeviceWifiInfo() {
        WifiInfo wifiInfo = getWifiInfo();
        UuDevice uuDevice=null;
        if(!TextUtils.isEmpty(AttenAssiSharedPreferenceUtil.getDeviceSnAndName())){
            uuDevice= UuDeviceOperate.getDevice(AttenAssiSharedPreferenceUtil.getDeviceSnAndName());
           if (uuDevice != null && (!uuDevice.getSSID().equals(wifiInfo.getSSID().replace("\"", "")))) {
                uuDevice.setSSID(wifiInfo.getSSID().replace("\"", ""));
                DBHelper.getInstance().getLiteOrm().update(uuDevice);
            }
        }
        if (uuDevice == null) uuDevice = new UuDevice(getWifiInfo());
        return uuDevice;
    }
    /**
     * 修改双优判断方法
     * BSSID MAC地址
     * SSID wifi名称
     *
     * @return
     */
    public static UuDevice getCurrentDevice() {
        UuDevice uuDevice=null;
        if(!TextUtils.isEmpty(AttenAssiSharedPreferenceUtil.getDeviceSnAndName())){
            uuDevice= UuDeviceOperate.getDevice(AttenAssiSharedPreferenceUtil.getDeviceSnAndName());
        }
        return uuDevice;
    }

    /**
     * 获取设备语言
     * @return
     */
    public static String getCurrentDeviceLanguage(){
        UuDevice uuDevice = getCurrentDevice();
        if(uuDevice != null){
            return uuDevice.getLanuage();
        }
        return null;
    }
    /**
     * 获取当前设备SN值
     * @return
     */
    public static String getCurrentDeviceSn(){
        return AttenAssiSharedPreferenceUtil.getDeviceSnAndName();
    }

    public static WifiInfo getWifiInfo() {
        return getWifiManager().getConnectionInfo();
    }

    public static WifiManager getWifiManager() {
        return (WifiManager) BaseApplication.getInstance().getContext().getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
    }


    public static String getGateway() {
//        if (true) return "192.168.11.179";
        WifiManager wifiManager = (WifiManager) BaseApplication.getInstance().getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        DhcpInfo dhcpInfo = wifiManager.getDhcpInfo();
        String gateway = Formatter.formatIpAddress(dhcpInfo.gateway);
        ZKLog.d(TAG, "getGateway: " + gateway);
        return gateway;
    }

    public static String getMD5(String val) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(val.getBytes());
            byte[] m = md5.digest();//加密
            //toString
            StringBuilder sb = new StringBuilder();
            for (byte aM : m) {
                int i = aM;
                if (i < 0) i += 256;
                if (i < 16) sb.append("0");
                sb.append(Integer.toHexString(i));
            }
            return sb.toString().substring(8, 24);   //16位小写
        } catch (Exception e) {
            ZKLog.e(TAG, "getMD5: error", e);
            return "";
        }
    }

    public static String getWifiMAC() {
        String MAC = getWifiInfo().getBSSID();
        ZKLog.d(TAG, "getWifiMAC: " + MAC);
        return MAC;
    }

    public static String getAttParameters(String hwId, String pin, String pwd) {
        if (hwId != null && pin != null && pwd != null) {
            return "hwid=" + hwId + ",pin=" + pin + ",pwd=" + pwd;
        } else return "";
    }

    public static int getErrorStringId(int errorCode) {
        ZKLog.d(TAG,"getErrorStringId errorCode = " + errorCode);
        switch (errorCode) {
            case -2001:
                return R.string.uuBindError2001;
            case -2008:
                return R.string.uuBindError2008;
            case -2009:
                return R.string.uuBindError2009;
            case -2010:
                return R.string.uuBindError2010;
            case -2011:
                return R.string.uuBindError2011;
            case -2012:
                return R.string.uuBindError2012;
            case -2013:
                return R.string.uuBindError2013;
            case -2014:
                return R.string.uuBindError2014;
            case -2015:
                return R.string.uuBindError2015;
            case -2023:
                return R.string.uuBindError2023;
            case -2030:
                return R.string.uuBindError2030;
            case -203:
                return R.string.str_connect_fail;
            case -1:
            case -5:
            case -6:
                return R.string.uuBindError0005;
            case -14:
                return R.string.uuBindError0014;
            case -3:
                return R.string.cannotBuildConnect;
            default:
                ZKLog.d(TAG, "getErrorStringId: null" + errorCode);
                SdcardLogTools.saveCrashInfo2File(TAG + " == -------- " + SdcardLogTools.formatter.format(new Date()) + " ---------");
                SdcardLogTools.saveCrashInfo2File(TAG + " == getErrorStringId: " + errorCode);
                return UdkHelper.ERROR_UNKNOWN;
        }
    }

    public static boolean isDeviceMode() {
        if (true) return true;
        if (!isWifiConnected()) return false;
        WifiInfo wifiInfo = getWifiInfo();
        Log.d("UuDeviceBusiness", "isDeviceMode() " + (wifiInfo != null && wifiInfo.getSSID().contains("ZK-")));
        return wifiInfo != null && wifiInfo.getSSID().contains("ZK-");
    }

    public static boolean isWifiConnected() {
        ConnectivityManager cm = (ConnectivityManager) BaseApplication.getInstance().getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            }
        }
        return false;
    }


}

