package com.zkteco.coreservice;


import android.util.Log;

import com.zkteco.android.framework.utils.ZKLog;

/**
 * Created by xqc on 2015/12/3.
 * ZKTeco App
 */
public class UdkHelper {
    private static final String TAG = "UdkHelper";
    public static final int ERROR_PWD = -14;
    public static final int ERROR_SOCKET_FAIL = -203;   //错误端口号
    public static final int ERROR_TIMEOUT = -307;
    public static final int ERROR_LOWMEMORY = -4;
    public static final int ERROR_UNKNOWN = -9999;
    public static long handle;
    public static String TCP = "TCP";
    public static int PORT_DEFAULT = 4370;
    public static int lastRet;
    //如果ret==ret_connect,那么说明是连接失败
    public static final int RET_CONNECT = 233333;

    public synchronized static long connect(String protocol, String address, int port, int timeOut, String pwd) {
        lastRet = RET_CONNECT;
        if (port == 0) port = PORT_DEFAULT;
        if (pwd == null) pwd = "";
        String parameters = "protocol=" + protocol + ",Address=" + address
                + ",port=" + port + ",timeout=" + timeOut + ",passwd=" + pwd;
        ZKLog.d(TAG, "connect: udk:" + parameters);
        int[] pro = new int[]{UdkService.UDK_PROT_STANDALONE};
        try {
//            // 请求前先disconnect清除一下，再进行连接。
            disconnect();
            handle = UdkService.UdkConnect(pro, parameters);
            ZKLog.d(TAG,"UdkHelper.connect : UdkService.UdkConnect end handle=" + handle);
        } catch (Exception e) {
            ZKLog.e(TAG,"UdkHelper.connect : udk connect error  Exception = " + e.getMessage());
            ZKLog.e(TAG, "udk connect error", e);
            handle = 0;
        }
        ZKLog.d(TAG, "udk connect: handle : " + handle);
        if (handle != 0) {
            return handle;
        } else {
            int errorCode = UdkService.UdkGetLastError(handle);
            ZKLog.e(TAG, "udk error code : " + errorCode);
            return 0;
        }
    }

    /**
     * @return
     */
    public synchronized static int getConnectErrorCode() {
        int errorCode = UdkService.UdkGetLastError(handle);
        ZKLog.d(TAG, "getConnectErrorCode: " + errorCode);
        return errorCode;
    }

    public synchronized static void disconnect() {
        if (handle != 0) {
            UdkService.UdkDisconnect(handle);
            handle = 0;
        }
    }

    public static int getLastError() {
        if (handle == 0) return getConnectErrorCode();
        else return lastRet;
    }

    public static void cleanLastError() {
        lastRet = 0;
    }

    public synchronized static int setDeviceParam(String itemAndValue) {
        if (handle == 0) return -2;
        lastRet = UdkService.UdkSetDeviceParam(handle, itemAndValue);
        ZKLog.d(TAG, "setDeviceParam: str: " + itemAndValue + "---ret: " + lastRet);
        return lastRet;
    }

    public synchronized static int mobileAtt(int operate, String parameters, byte[] buffer) {
        if (handle == 0) return -2;
        int[] bufferLength = new int[]{buffer.length};
        lastRet = mobileAtt(operate, parameters, buffer, bufferLength);
        return lastRet;
    }

    public synchronized static int mobileAtt(int operate, String parameters, byte[] buffer, int[] bufferLength) {
        if (handle == 0) return -2;
        bufferLength[0] = buffer.length;
        lastRet = UdkService.UdkMobileAtt(handle, operate, parameters, buffer, bufferLength);
        ZKLog.d(TAG, "mobileAtt: operate=" + operate + ",parameters=" + parameters + ",ret=" + lastRet);
        return lastRet;
    }

    public synchronized static int udkResetDeviceExt(String params) {
        if (handle == 0) return -2;
        ZKLog.d(TAG, "udkResetDeviceExt: params: " + params);
        lastRet = UdkService.UdkResetDeviceExt(handle, params);
        return lastRet;
    }

    public synchronized static int udkGetDeviceParam(byte[] buffer,String item){
        int bufferLen = buffer.length;
        lastRet = UdkService. UdkGetDeviceParam(handle, buffer , bufferLen , item);
        return lastRet;
    }

    public static boolean isConnect() {
        return handle != 0;
    }

    public static long getHandle() {
        return handle;
    }
}
