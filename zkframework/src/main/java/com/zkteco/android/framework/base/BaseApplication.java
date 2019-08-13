package com.zkteco.android.framework.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import com.zkteco.android.framework.common.BugCrashHandler;
import com.zkteco.android.framework.common.utils.ZkframeworkCommonUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.zkteco.android.framework.utils.DataAnalyticsHelper;
import com.zkteco.android.framework.utils.LogcatHelper;
import com.zkteco.android.framework.utils.ZKConstant;
import com.zkteco.library.autoupdate.ZKMobilUpdate;

/**
 * *********************************************************************
 * 描述:com.zkteco.attendanceassistant.base.BaseApplication
 *
 * @date: 2016/4/12 14:24
 * @version: 1.0.0
 * @author: Li Yihua
 * @email: leeyh.lee@zkteco.com
 * *********************************************************************
 */
public class BaseApplication extends Application {

    /** 应用实例  */
    public static BaseApplication mBaseApplication;
    /** 是否为调试模式 */
    private boolean DeBug=true;
    /** 用来存放打开的Activity链表 */
    public static ArrayList<Activity> activityList = new ArrayList<Activity>();
    /** 应用上下文  */
    private Context context;

    @Override
    public void onCreate() {
        super.onCreate();
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(this);
        mBaseApplication=this;
        context=getApplicationContext();
        DeBug = ZkframeworkCommonUtil.isDebugMode(this);

        initFlurry();
        initCrashHandler();
//        LogcatHelper.getInstance(context,null).start();
//        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ZKBreakpad/";
//        ZKLog.d("BaseApplication"," Application NativeBreakpad.init path : " + path);
//        File f = new File(path);
//        makeDir(f);
//        NativeBreakpad.init(path);

        //版本更新初始化
        ZKMobilUpdate.initUpdate(this, ""/*ZKConstant.UPDATEURL*/,ZKConstant.SYS,ZKConstant.UPDATE_APPKEY);
    }
    public Context getContext(){
        if(context == null){
            context = getApplicationContext();
        }
        return context;
    }

    private void initFlurry(){
        DataAnalyticsHelper.setLogEnabled(true);
        DataAnalyticsHelper.setLogEvents(true);
        DataAnalyticsHelper.setReportLocation(true);
        DataAnalyticsHelper.setCaptureUncaughtExceptions(true);
        try {
            DataAnalyticsHelper.setVersionName((getPackageManager().getPackageInfo(this.getPackageName(), 0)).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        DataAnalyticsHelper.init(this,ZKConstant.FLURRYKEY);
    }
    /**
     * @return IMApplication
     * @description: 单例模式
     * @date: 2015年3月17日 上午10:00:42
     * @author： Li Yihua
     */
    public static BaseApplication getInstance() {
        if (null == mBaseApplication) {
            mBaseApplication = new BaseApplication();
        }
        return mBaseApplication;
    }

    private void initCrashHandler(){
        /** 崩溃捕捉文件,会在SD卡package/BugLog/下产生崩溃日志. */
        if (DeBug) {
            BugCrashHandler.getInstance().init(getApplicationContext());
        }
    }

    /**
     * @param activity
     * @description: 添加activity到链表中
     * @date: 2015年3月17日 上午10:02:01
     * @author： Li Yihua
     */
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    /**
     * @description: 遍历所有Activity 并 finish
     * @date: 2015年3月17日 上午10:01:37
     * @author： Li Yihua
     */
    public void exit() {
        if (activityList != null && activityList.size() > 0) {
            for (Activity activity : activityList) {
                activity.finish();
            }
        }
        if(activityList != null){
            activityList.clear();
        }
    }

    /**
     * @return Activity
     * @description: 获取链表末的activity
     * @date: 2015年3月17日 上午10:02:39
     * @author： Li Yihua
     */
    public Activity getLastActivity() {
        List<Activity> list = getActivityAlive();
        if (list != null && !list.isEmpty()) {
            return list.get(list.size() - 1);
        }
        return null;
    }

    /**
     * @return
     * @description: 获取所有存在的activity
     * @date: 2015年3月17日 上午10:03:26
     * @author： Li Yihua
     */
    public List<Activity> getActivityAlive() {
        return activityList;
    }

    /**
     * @param activity
     * @description: 删除activity
     * @date: 2015年3月17日 上午10:03:38
     * @author： Li Yihua
     */
    public void removeActivity(Activity activity) {
        if (activityList.contains(activity)) {
            activityList.remove(activity);
        }
    }

    /**
     * @param name
     * @description: 删除activity
     * @date: 2015年3月17日 上午10:03:55
     * @author： Li Yihua
     */
    public void removeActivity(String name) {
        if (activityList != null && activityList.size() > 0) {
            for (Activity activity : activityList) {
                if (activity.getClass().getName().contains(name)) {
                    activityList.remove(activity);
                    activity.finish();
                    break;
                }
            }
        }
    }

    private static void makeDir(File dir) {
        if(dir == null){
            return;
        }
        if (dir.getParentFile() != null && !dir.getParentFile().exists()) {
            makeDir(dir.getParentFile());
        }
        if(!dir.exists())
            dir.mkdir();
    }
}
