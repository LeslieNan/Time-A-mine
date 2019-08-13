package com.example.timeassistant.control;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.StringRes;

import com.example.timeassistant.activity.MainActivity;
import com.example.timeassistant.entity.UuDevice;
import com.example.timeassistant.factory.UdkFactory;
import com.example.timeassistant.receiver.NetBroadcastReceiver;
import com.example.timeassistant.utils.AttenAssiSharedPreferenceUtil;
import com.example.timeassistant.utils.UuAbnormalBusiness;
import com.example.timeassistant.utils.UuDeviceBusiness;
import com.zkteco.android.framework.base.BaseApplication;
import com.zkteco.android.framework.utils.StartActivityUtil;

import com.zkteco.coreservice.UdkHelper;

/**
 * 描述：
 * <p/>
 * 日期：2016/7/20
 * 作者：kangjj
 */
public class BaseUuControl {
    private static final String TAG = "BaseUUAsyncTask";

    protected Context mContext;
    private long handle;
    private UuDevice mDevice;
    private boolean isConnectSucceed;
    private boolean isHaveRight;

    public BaseUuControl(Context context) {
        mContext = context;
        handle = UdkHelper.handle;

    }


    protected void handleUuError() {
        UuAbnormalBusiness.handleError(mContext, getDevice());
    }


    /**
     *  检查连接状态.
     *      -1 : 请连接设备
     *      -2 : 请重试
     *       1 : 成功
     * @return
     */
    public int checkAndConnectState(){
        UuDevice mDevice = getDevice();
        if (!canAccessDevice()) {          //设备为空或者没绑定跳转到主界面
            return -1;
        }
        long handle = UdkFactory.connect(mDevice);
        isConnectSucceed = (handle != 0);
        if(!isConnectSucceed){
            try {
                // 尝试做下重连
                Thread.sleep(500);
                handle = UdkFactory.connect(mDevice);
                isConnectSucceed = (handle != 0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(!isConnectSucceed){
                connectFail(false);
                return -2;
            }
        }
        return 1;
    }

    public boolean checkAndConnect() {
        return (checkAndConnectState() == 1);
    }

    /**
     * 检查是否有绑定设备(设备为空或者没绑定跳转到主界面)
     * @return
     */
    public boolean canAccessDevice(){
        UuDevice mDevice = getDevice();
        if (mDevice == null || mDevice.isUnbind()) {          //设备为空或者没绑定跳转到主界面
            connectFail(true);
            return false;
        }
        return true;
    }

    public void connectFail(boolean isSkip){
        if(isSkip){
            AttenAssiSharedPreferenceUtil.setIsCheckNetwork(false);
            NetBroadcastReceiver.isFromChangeNet = true;
            if(mContext instanceof MainActivity){
                NetBroadcastReceiver.isFromChangeNet = false;//MainActivity的onResume不进行处理
            }else if(mContext instanceof Activity) {
                ((Activity)mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        StartActivityUtil.startActivity(BaseApplication.getInstance().getContext(), MainActivity.class);
                    }
                });
            }
        }
    }

    public UuDevice getDevice() {
        if (mDevice == null)
            return UuDeviceBusiness.getCurrentDevice();
        else
            return mDevice;
    }

    public long getHandle() {
        return handle;
    }

    public Context getContext() {
        return mContext;
    }


    public boolean isConnectSucceed() {
        return isConnectSucceed;
    }

    public boolean checkRight(UuDevice uuDevice) {
        int ret = UdkFactory.getRight(uuDevice);
        isHaveRight = ret > 0 /*&& uuDevice.isAdmin()*/;
        return isHaveRight;
    }

    public boolean checkRight() {
        int ret = UdkFactory.getRight(getDevice());
        isHaveRight = ret > 0 && getDevice().isAdmin();
        return isHaveRight;
    }

    public String getString(@StringRes int resId) {
        return mContext.getString(resId);
    }

    public boolean isHaveRight() {
        return isHaveRight;
    }

    public boolean isMissRight() {
        return isConnectSucceed && !isHaveRight;
    }

}
