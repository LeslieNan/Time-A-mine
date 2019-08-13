package com.zkteco.android.framework.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;


import com.zkteco.android.framework.base.BaseApplication;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Allen-Lan on 2015/7/24.
 */
public class ZKTools {
    private static final String TAG = "ZKTools";
    public static final int MIN_PASSWORD = 8;

    public static boolean checkIfEmail(String s) {
        if (s != null && s.contains("@")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkStringEmpty(String s) {
        if (s == null || "".equals(s)) {
            return true;
        } else {
            return false;
        }
    }

    //判断email格式是否正确
    public static boolean isEmail(String email) {
        if (email == null) {
            return false;
        }

        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();
    }

    //判断手机格式是否正确
    public static boolean isPhoneNum(String mobiles) {
        if (mobiles == null) {
            return false;
        }
        Pattern p = Pattern
                .compile("^((1[0-9][0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);

        return m.matches();
    }

    //判断密码是否正确
    public static boolean isPasswordValid(String password) {
        return password.length() >= MIN_PASSWORD;
    }


    public static String convertDateToHourAndMinute(long mills) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mills);
        String hour = calendar.get(Calendar.HOUR_OF_DAY) + ":";
        if (hour.length() == 2) {
            hour = "0" + hour;
        }
        String minute = calendar.get(Calendar.MINUTE) + "";
        if (minute.length() == 1) {
            minute = "0" + minute;
        }
        return hour + minute;
    }


    private static Toast mToast;

    public static void toastShow(final String string) {
//        if(Models.isModel(Models.NEM_AL10)){
//            mToast=null;
//        }
        Handler mHandler = new Handler(Looper.getMainLooper());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mToast == null) {
                    mToast = Toast.makeText(BaseApplication.getInstance().getContext(), string, Toast.LENGTH_SHORT);
                } else {
                    mToast.setDuration(Toast.LENGTH_SHORT);
                    mToast.setText(string);
                }
                mToast.show();
            }
        });
        ZKLog.e(TAG, "Toast is ---" + string);
    }

    public static void toastShow(@StringRes int resId) {
        if (resId > 0) {
            toastShow(BaseApplication.getInstance().getContext().getString(resId));
        }
    }



    public static String getYearAndMonth(boolean isLastMonth) {
        Calendar calendar = getCalendar(isLastMonth);
        return "" + calendar.get(Calendar.YEAR) + String.format("%02d", (calendar.get(Calendar.MONTH) + 1));
    }

    public static Calendar getCalendar(boolean isLastMonth) {
        Calendar calendar = Calendar.getInstance();
        if (isLastMonth) calendar.add(Calendar.MONTH, -1);
        return calendar;
    }

    public static long getTimeMile(boolean isLastMonth, boolean isStart) {
        Calendar calendar = getCalendar(isLastMonth);
        if (isStart) {
            calendar.set(Calendar.DATE, 1);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
//            ZKLog.d(TAG, "getTimeMile: " + calendar.toString());
        } else {
//            if (isLastMonth) {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
//            Log.d(TAG, "getTimeMile: " + calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 99);
//            }
        }
        ZKLog.d(TAG, "getTimeMile: " + calendar.toString());
        return calendar.getTimeInMillis();
    }

    public static void setCalendarLimit(Calendar calendar, boolean isStart) {
        calendar.set(Calendar.HOUR_OF_DAY, isStart ? 0 : 23);
        calendar.set(Calendar.MINUTE, isStart ? 0 : 59);
        calendar.set(Calendar.SECOND, isStart ? 0 : 59);
        calendar.set(Calendar.MILLISECOND, isStart ? 0 : 59);
    }

    public static String getDateString(Calendar calendar) {
        return calendar.get(Calendar.YEAR) +
                String.format("%02d", calendar.get(Calendar.MONTH) + 1) +
                String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH));
    }

//    public static void showDialogAccordingToNetWorkStatus(Activity activity) {
//        Log.d("showDialogAccordingToNetWorkStatus","111");
//        if (UuDeviceBusiness.isDeviceMode()) {
//            Log.d("showDialogAccordingToNetWorkStatus","222");
//            DialogInfo dialogInfo = new DialogInfo();
//            dialogInfo.setmDialogMessage(activity.getString(R.string.prompt_when_is_bind_wifi));
//            dialogInfo.setmSingleText(activity.getString(R.string.ok));
//            dialogInfo.setmDialogStyle(ZKCustomDialogUtils.DIALOG_SINGLE);
//            ZKCustomDialogUtils.show(activity,dialogInfo);
//        }
//    }

    /***
     * 线程等待
     */
    public static void threadSleep(int time) {
        try {
            Thread.sleep(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean inRangeOfView(View view, MotionEvent ev) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        if (ev.getX() < x
                || ev.getX() > (x + view.getWidth())
                || ev.getY() < y
                || ev.getY() > (y + view.getHeight())) {
            return false;
        }
        return true;
    }

    public static int getPortInt(String str){
        // 用来解析端口的 一般不超过5位数
        if(TextUtils.isEmpty(str) || str.length() > 5){
            return 0;
        }
        return getInt(str);
    }

    public static int getInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            Log.e(TAG, "getInt: error", e);
            return 0;
        }
    }

    public static boolean isEmpty(List list) {
        return list == null || list.size() == 0;
    }

    public static void print(List list) {
        for (Object item : list) {
            Log.d(TAG, "print: " + item.toString());
        }
    }

    public static String getHexString(byte[] data, boolean isHaveSpace) {
        final StringBuilder stringBuilder = new StringBuilder(data.length);
        for (byte byteChar : data) {
            stringBuilder.append(String.format("%02X", byteChar));
            if (isHaveSpace) stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, double dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    /**
     * 判断wifi是否可以连接互联网
     * @return
     */
    public static boolean isWifiConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
                return ping();
            }
        }
        return false;
    }

    /** @author suncat
     * @category 判断是否有外网连接（普通方法不能判断外网的网络是否连接，比如连接上局域网）
     * @return
     */
    public static final boolean ping(){
        String result = null;
        try {
            String ip = "www.baidu.com";// ping 的地址，可以换成任何一种可靠的外网
            Process p = Runtime.getRuntime().exec("ping -w 5 " + ip);// ping网址1次,5秒
            // 读取ping的内容，可以不加
//            InputStream input = p.getInputStream();
//            BufferedReader in = new BufferedReader(new InputStreamReader(input));
//            StringBuffer stringBuffer = new StringBuffer();
//            String content = "";
//            while ((content = in.readLine()) != null) {
//                stringBuffer.append(content);
//            }
//            ZKLog.d("------ping-----", "result content : " + stringBuffer.toString());
            // ping的状态
            int status = p.waitFor();
            if (status == 0) {
                result = "success";
                return true;
            } else {
                result = "failed";
            }
        } catch (IOException e) {
            result = "IOException";
        } catch (InterruptedException e) {
            result = "InterruptedException";
        } finally {
            ZKLog.d("----result---", "result = " + result);
        }
        return false;
    }
}
