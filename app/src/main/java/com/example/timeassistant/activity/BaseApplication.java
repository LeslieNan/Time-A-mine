package com.example.timeassistant.activity;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class BaseApplication extends Application {
    /**
     * 应用实例
     */
    private static BaseApplication mBaseApplication;
    /**
     * 应用上下文
     */
    private Context mContext;
    /**
     * activity链表，用来存放打开的activity
     */
    public static List<Activity> activityList = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        mBaseApplication = this;
        mContext = getApplicationContext();
    }


    public static BaseApplication getInstance() {
        if (mBaseApplication == null) {
            mBaseApplication = new BaseApplication();
        }
        return mBaseApplication;
    }

    public Context getContext() {
        if (mContext == null) {
            mContext = getApplicationContext();
        }
        return mContext;
    }

    /**
     * 添加activity到列表中
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    /**
     * 从列表中删除activity
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        if (activityList.contains(activity)) {
            activityList.remove(activity);
        }
    }

    /**
     * 从列表中删除activity，根据activity类名
     *
     * @param name
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

    /**
     * 获取栈顶的activity
     *
     * @return
     */
    public Activity getLastActivity() {
        if (activityList != null && !activityList.isEmpty()) {
            return activityList.get(activityList.size() - 1);
        }
        return null;
    }

    /**
     * 退出，遍历所有activity，并finish
     */
    public void exit() {
        if (activityList != null && !activityList.isEmpty()) {
            for (Activity activity :
                    activityList) {
                activity.finish();
            }
        }
        if (activityList != null) {
            activityList.clear();
        }
    }
}
