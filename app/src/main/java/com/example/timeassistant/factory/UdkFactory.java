package com.example.timeassistant.factory;

import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.example.timeassistant.entity.UuDevice;
import com.example.timeassistant.utils.SdcardLogTools;
import com.example.timeassistant.utils.UuDeviceBusiness;
import com.zkteco.android.framework.utils.ZKLog;
import com.zkteco.android.framework.utils.ZKTools;
import com.zkteco.coreservice.UdkHelper;

/**
 * Created by xqc on 2015/12/3.
 * ZKTeco App
 */
public class UdkFactory {
    private static final String TAG = "UdkFactory";
    public static final int BINDED=-2011;//返回-2011 绑定就是该手机 重新获取数据

    public static final int RETRY_COUNT = 3;

    // 获取设备使用的语言
    public static final String DEVICEPARAM_LANGUAGE = "Language";

    // 设备类型所属  1: @咖APP可连
    public static final String DEVICE_SUPPORT = "1";
    // 设备查找不存在的参数返回错误码
    public static final int PARAM_UNSUPPORT = -2022;

    public static long connect(String ip, String pwd, int port) {
        if (pwd == null) pwd = "";
        return UdkHelper.connect(UdkHelper.TCP, ip, port, 3000, pwd);
    }

    public static long connect(UuDevice uuDevice) {
        String ip = uuDevice.getIpAddr();
        if (TextUtils.isEmpty(ip)) {
            ip = UuDeviceBusiness.getGateway();
        }
        return connect(ip, uuDevice.getUdkPwd(), uuDevice.getUdkPort());
    }

    /**
     * 解绑设备
     * UdkService.UdkMobileAtt(Handle,7,"hwid=5e:cf:7f:09:af:cc-1471339101471,pin=2,pwd=",_buffer,_bufferlength);
     */
    public static int unBindDev(UuDevice uuDevice, String pin, String pwd){
        String md5Pwd = UuDeviceBusiness.getMD5(pwd);
        String hwId = getUniqueIDFromBuilder();
        String parameters = UuDeviceBusiness.getAttParameters(hwId, pin, md5Pwd);
        byte[] buffer = new byte[2048];
        int ret = UdkHelper.mobileAtt(7, parameters, buffer);
        return ret;
    }

    public static int bindDev(UuDevice uuDevice, String pin, String pwd) {
        String md5Pwd = UuDeviceBusiness.getMD5(pwd);
        String hwId = getUniqueIDFromBuilder();
        String parameters = UuDeviceBusiness.getAttParameters(hwId, pin, md5Pwd);
        byte[] buffer = new byte[2048];
        SdcardLogTools.saveCrashInfoFile("UdkFactory.bindDev : UdkHelper.mobileAtt  start");
        int ret = UdkHelper.mobileAtt(2, parameters, buffer);
        SdcardLogTools.saveCrashInfoFile("UdkFactory.bindDev : UdkHelper.mobileAtt  end ret = " + ret);
        ZKLog.d(TAG, "bindDev: ret: " + ret);
        if (ret ==BINDED){      //该手机绑定过该账号，获取不到数据，这里先设置，上层有获取权限的时候会重新设置
            uuDevice.setBindPin(pin);
            uuDevice.setBindName(pin);
            uuDevice.setBindPwd(md5Pwd);
            uuDevice.setRight(1);
            uuDevice.setBindHwId(hwId);
            return ret;
        }
        if (ret < 0) return ret;
        try {
            ZKLog.d(TAG, "bindDev: " + ZKTools.getHexString(buffer, true));
            String data = new String(buffer).trim();
            ZKLog.d(TAG, "bindDev: buffer: " + data);

            String[] datas = data.split(",");
            ZKLog.d(TAG, uuDevice.toString());
            uuDevice.setBindPin(datas[0].replace("pin=", ""));
            uuDevice.setBindName(datas[1].replace("name=", ""));
            uuDevice.setBindPwd(md5Pwd);
            uuDevice.setRight(Integer.parseInt(datas[2].replace("right=", "")));
            uuDevice.setBindHwId(hwId);
            ZKLog.d(TAG, uuDevice.toString());
            return ret;
        } catch (Exception e) {
            ZKLog.e(TAG, "bindDev: decode error", e);
            return -1;
        }
    }

    //只用于绑定
    private static String getHwId() {
        String hwId = UuDeviceBusiness.getWifiMAC() /*+ "-" + System.currentTimeMillis()注释掉，一台设备只能绑定一个固定的工号，绑定过的返回值是-2011*/;
        ZKLog.d(TAG, "getHdId: " + hwId);
        return hwId;
    }


    /**
     * 从Build中提取出设备信息，进行拼接，进行MD5加密后，形成16位UniqueID
     * @return
     */
    public static String getUniqueIDFromBuilder(){
        StringBuilder sb = new StringBuilder();
        sb.append(Build.ID);
        sb.append(Build.DISPLAY);
        sb.append(Build.DEVICE);
        sb.append(Build.MANUFACTURER);
        sb.append(Build.BRAND);
        sb.append(Build.MODEL);
        sb.append(Build.SERIAL);
        return UuDeviceBusiness.getMD5(sb.toString());
    }

    /**
     *
     * @param item
     * @return
     */
    public static String getDeviceParam(String item){
        byte[] buffer = new byte[2048];
        int ret = UdkHelper.udkGetDeviceParam( buffer ,item);
        SdcardLogTools.saveCrashInfo2File("GetDeviceParam : " + item + "  ret = " + ret);
        if(ret == 0 || ret == PARAM_UNSUPPORT){
            return "0";
        }
        if (ret < 0){
            return "";
        }
        try{
            String data = new String(buffer,"GBK").trim();
            ZKLog.d(TAG, "getDeviceParam: buffer: " + data);
            SdcardLogTools.saveCrashInfo2File("GetDeviceParam : " + item + "  ret = " + ret + "  buffer : " + data);
            String[] datas = data.split(",");
            String str = datas[0].replace(item+"=", "");
            return str;
        }catch (Exception e){
            ZKLog.e(TAG, "bindDev: decode error", e);
            SdcardLogTools.saveCrashInfo2File("GetDeviceParam : " + item + "  ret = " + ret + "  decode error");
            return "";
        }
    }


    public static int getRight(UuDevice uuDevice) {
        String parameters = "hwid=" + uuDevice.getBindHwId();
        byte[] buffer = new byte[2048];
        int ret = UdkHelper.mobileAtt(1, parameters, buffer);//ios那边 0是普通用户 1是管理员
        ZKLog.d(TAG, "getRight: ret: " + ret);
        if (ret < 0) return ret;
        String data = new String(buffer).trim();
        ZKLog.d(TAG, "getRight: buffer: " + data);
        try {
            String[] datas = data.split(",");
            ZKLog.d(TAG, uuDevice.toString());
            uuDevice.setBindPin(datas[0].replace("pin=", ""));
            uuDevice.setBindName(datas[1].replace("name=", ""));
            uuDevice.setRight(Integer.parseInt(datas[2].replace("right=", "")));
            ZKLog.d(TAG, uuDevice.toString());
            return ret;
        } catch (Exception e) {
            ZKLog.e(TAG, "bindDev: decode error", e);
            return -1;
        }
    }

    public static void disconnectInOtherThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: disconnect device");
                UdkHelper.disconnect();
            }
        }).start();
    }

}
