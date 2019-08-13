package com.example.timeassistant.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.example.timeassistant.activity.MainActivity;
import com.example.timeassistant.utils.AttenAssiSharedPreferenceUtil;
import com.zkteco.android.framework.base.BaseApplication;
import com.zkteco.android.framework.utils.DataAnalyticsConstants;
import com.zkteco.android.framework.utils.DataAnalyticsHelper;
import com.zkteco.android.framework.utils.StartActivityUtil;

/**
 * 描述：
 * <p/>
 * 日期：2016/7/25
 * 作者：kangjj
 */
public class NetBroadcastReceiver extends BroadcastReceiver {
    public static boolean isFromChangeNet=false;
    @Override
    public void onReceive(Context context, Intent intent) {
        DataAnalyticsHelper.logEvent(DataAnalyticsConstants.CHANGENET);
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            /*int netWrokState = NetUtil.getNetWrokState(context);
            // 接口回调传过去状态的类型
            evevt.onNetChange(netWrokState);*/
            if(AttenAssiSharedPreferenceUtil.getIsCheckNetwork()){
                AttenAssiSharedPreferenceUtil.setIsCheckNetwork(false);
                isFromChangeNet = true;
                StartActivityUtil.startActivity(BaseApplication.getInstance().getContext(), MainActivity.class);
            }
        }
    }
}
