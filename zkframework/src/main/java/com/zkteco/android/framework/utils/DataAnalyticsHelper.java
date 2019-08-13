package com.zkteco.android.framework.utils;

import android.content.Context;

import com.flurry.android.FlurryAgent;
import com.flurry.android.FlurryAgentListener;
import java.util.Map;

/**
 * Created by jealousy.sun on 2016/1/7.
 */
public class DataAnalyticsHelper {

//    public  static Map<String,String> customEventList;
//    private static Context appContext;

//    public  static  void setupUmeng(Context context, String umengAppID)
//    {
//        appContext = context;
//        MobclickAgent.setDebugMode(true);
//        MobclickAgent.openActivityDurationTrack(false);
//        MobclickAgent.setSessionContinueMillis(1000);
//        MobclickAgent.setScenarioType(context, EScenarioType.E_UM_NORMAL);
//    }

    public static void init(Context context, String key) {
        FlurryAgent.init(context, key);
    }

    public static void setLogEnabled(boolean enabled) {
        FlurryAgent.setLogEnabled(enabled);
    }

    public static void setLogEvents(boolean enabled) {
        FlurryAgent.setLogEvents(enabled);
    }

    public static void setReportLocation(boolean enabled) {
        FlurryAgent.setReportLocation(enabled);
    }

    public static void setCaptureUncaughtExceptions(boolean enabled) {
        FlurryAgent.setCaptureUncaughtExceptions(enabled);
    }

    public static void setContinueSessionMillis(long l) {
        FlurryAgent.setContinueSessionMillis(l);
    }

    public static void setVersionName(String s) {
        FlurryAgent.setVersionName(s);
    }

    public static void setUserId(String userID) {
        FlurryAgent.setUserId(userID);
    }

    public static String getReleaseVersion() {
        return FlurryAgent.getReleaseVersion();
    }

    public static void logEvent(String eventId) {
        FlurryAgent.logEvent(eventId);
    }

    public static void logEvent(String eventId, boolean timed)
    {
        logFlurryEvent(eventId, timed);
//        logUMengEvent(eventId,timed);
    }

    public static void logFlurryEvent(String eventId, boolean timed)
    {
        FlurryAgent.logEvent(eventId, timed);
    }

    public static void logUMengEvent(String eventLabel, boolean timed)
    {
//        String eventId = customEventList.get(eventLabel);
//        MobclickAgent.onEvent(appContext,eventId);
    }

    public static void logEvent(String eventId, Map<String, String> parameters) {
        FlurryAgent.logEvent(eventId, parameters);
    }

    public static void logEvent(String eventId, Map<String, String> parameters, boolean
            timed) {
        FlurryAgent.logEvent(eventId, parameters, timed);
    }

    public static void onError(String errorId, String message, Throwable exception) {
        FlurryAgent.onError(errorId, message, exception);
    }

    public static void setFlurryAgentListener(FlurryAgentListener flurryAgentListener) {
        FlurryAgent.setFlurryAgentListener(flurryAgentListener);
    }

}
