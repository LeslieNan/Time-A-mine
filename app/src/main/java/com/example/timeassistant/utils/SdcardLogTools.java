package com.example.timeassistant.utils;

import android.os.Environment;

import com.zkteco.android.framework.utils.ZKLog;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/11/30.
 */

public class SdcardLogTools {

    public static DateFormat formatter = new SimpleDateFormat("MM-dd-HH:mm:ss");

    public static String saveCrashInfo2File(String logInfo){
//        saveCrashInfo2File("log.txt",logInfo);
        return null;
    }

    public static void saveCrashInfoFile(String logInfo){
        ZKLog.d("attendance-assistant",logInfo);
//        String filePath = Environment.getExternalStorageDirectory().getPath()
//                + "/ZKTestLog/";
//        File f = new File(filePath);
//        makeDir(f);
//        saveCrashInfo2File(filePath,logInfo);
    }


    public static String saveCrashInfo2File(String filePath,String logInfo) {
        if(!ZKLog.DEBUG){
            ZKLog.d("attendance-assistant",logInfo);
            return "";
        }
        StringBuffer sb = new StringBuffer();
        sb.append(formatter.format(new Date()));
        sb.append("--");
        String result = logInfo + "\n";
        sb.append(result);
//        sb.append('\n');
        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//				String path = "/sdcard/crash/";
                File dir = new File(filePath);
                if (!dir.exists()) {
                    makeDir(dir);
                }
                FileOutputStream fos = new FileOutputStream(filePath,true);
                fos.write(sb.toString().getBytes());
                fos.close();
            }
            return null;
        } catch (Exception e) {
            ZKLog.e("SdcardLogTools", "an error occured while writing file...", e);
        }
        return null;
    }

    private static void makeDir(File dir) {
        if(dir == null){
            return;
        }
        if (dir.getParentFile() != null && !dir.getParentFile().exists()) {
            makeDir(dir.getParentFile());
        }
        if(!dir.exists()) {
            dir.mkdir();
        }
    }
}
