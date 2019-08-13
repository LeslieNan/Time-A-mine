package com.example.timeassistant.factory;

import com.example.timeassistant.entity.ScheduleDepartment;
import com.example.timeassistant.entity.SchedulePerson;
import com.example.timeassistant.entity.SchedulePersonSetting;
import com.example.timeassistant.entity.ScheduleSetting;
import com.example.timeassistant.entity.ScheduleShift;
import com.example.timeassistant.utils.FastJsonUtils;
import com.example.timeassistant.utils.FileUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.zkteco.android.framework.utils.ZKConstant;
import com.zkteco.android.framework.utils.ZKLog;
import com.zkteco.android.framework.utils.ZKTools;
import com.zkteco.android.framework.w6ssr.JAttOptions;
import com.zkteco.android.framework.w6ssr.ReportManager;
import com.zkteco.coreservice.UdkHelper;
import com.zkteco.coreservice.UdkService;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by xqc on 2016/5/31.
 * ZKTeco App
 */
public class ReportFactory {
    private static final String TAG = ReportFactory.class.getSimpleName();
    public static final String FILL_NAME_STANDARD = "1_标准报表.xls";
    public static final String FILL_NAME_SETTING = "设置报表.xls";

    // 简体中文 83
    public static final int LANGUAGE_SIMCN = 83;
    // 英文 69
    public static final int LANGUAGE_EN = 69;
    // 默认语言
    public static final int LANGUAGE_DEFAULT = LANGUAGE_SIMCN;

    public static final int SECTION_LENGTH = 50 * 1024;

    public static final int DEFAULT_LENGTH = 100 * 1024;
    /**
     * arraywork.dat //个人排班
     * arrayworkbydep.dat//部门排班
     * classtime.dat  // 日原始考勤记录  加班，打卡
     * department.dat //部门信息
     * ssruser.dat//  用户表
     * transaction.dat // 日打卡记录
     * usermonthclasstime.dat //月考勤记录
     */
    public static final String[] DAT_ARRAY = {"arraywork.dat", "arrayworkbydep.dat", "classtime.dat", "department.dat", "ssruser.dat", "transaction.dat", "usermonthclasstime.dat"};

    public static long getHandle() {
        return UdkHelper.getHandle();
    }

    public static List<ScheduleDepartment> getDepList() {
        if (getHandle() == 0) return null;
        // 第三个参数int[] size是存放，提供缓存区的大小。比如传入new int[]{1024};
        // 如果返回的ret是ERROR_LOWMEMORY(-4 意思是这边提供的缓存区不够大)，并且会将所需要的缓存区大小，放在int[] size中，
        // 获取所需缓存区大小，再次调用该方法去获取即可。
        byte[] str = new byte[DEFAULT_LENGTH];
        int[] size = new int[]{DEFAULT_LENGTH};
        int ret = UdkService.UdkGetDeptList(getHandle(), str, size);
        if (ret == UdkHelper.ERROR_LOWMEMORY) {
            int needLength = size[0];
            if (needLength > 0 && needLength > DEFAULT_LENGTH) {
                str = new byte[needLength];
                ret = UdkService.UdkGetDeptList(getHandle(), str, size);
            }
        }
        ZKLog.d(TAG, "getDepList: ret= " + ret + " size ： " + size[0]);
//        return ret > 0 ? FastJsonUtils.decodeGBK(str, ScheduleDepartment.class) : null;
        return ret > 0 ? FastJsonUtils.decodeUTF(str, ScheduleDepartment.class) : null;
    }

    public static List<ScheduleShift> getSchedule() {
        if (getHandle() == 0) return null;
        byte[] str = new byte[DEFAULT_LENGTH];
        int ret = UdkService.UdkGetSche(getHandle(), str);
        ZKLog.d(TAG, "getSchedule: ret= " + ret);
//        return ret > 0 ? FastJsonUtils.decodeGBK(str, ScheduleShift.class) : null;
        return ret > 0 ? FastJsonUtils.decodeUTF(str, ScheduleShift.class) : null;
    }

    public static boolean setDepList(List<ScheduleDepartment> list) {
        if (getHandle() == 0) return false;
        String json = FastJsonUtils.toJson2(list);
        int ret = UdkService.UdkSetDept(getHandle(), json);
        return ret > 0;
    }

    public static boolean setShiftList(List<ScheduleShift> list, boolean isOnlyUploadSelect) {
        if (getHandle() == 0) return false;
        List<ScheduleShift> mList = null;
        if (isOnlyUploadSelect) {
            mList = new ArrayList<>();
            for (ScheduleShift item : list) {
                if (item.ismIsSelect()) {
                    ScheduleShift nShift = new ScheduleShift(item.getShiftId(), item.getName(), item.getOtStartTime(), item.getOtEndTime(),
                            item.getStartTime0(), item.getEndTime0(), item.getStartTime1(), item.getEndTime1());
                    nShift.setDeviceSn(item.getDeviceSn());
                    nShift.setmIsSelect(item.ismIsSelect());
                    mList.add(nShift);
                }
            }
        } else {
            mList = list;
        }
        String json = FastJsonUtils.toJson(mList);
        ZKLog.d(TAG, " setShiftList json : " + json);
//        FileUtil.saveFile(json);
        int ret = UdkService.UdkSetSche(getHandle(), json);
        ZKLog.d(TAG, " setShiftList ret : " + ret);
        return ret > 0;
    }

    /**
     * 设置部门排班
     *
     * @param list
     * @return
     */
    public static boolean setDeptShiftList(List<ScheduleSetting> list) {
        if (getHandle() == 0) return false;
        if (ZKTools.isEmpty(list)) {
            ZKLog.d(TAG, "setDeptShiftList: null data");
            return true;
        }
        String json = FastJsonUtils.toJson(list);
        int ret = UdkService.UdkSetDeptSche(getHandle(), json);
        ZKLog.d(TAG, "setDeptShiftList: ret : " + ret);
        return ret > 0;
    }

    /**
     * 设置个人排班
     *
     * @param list
     * @return
     */
    public static boolean setPersonShiftList(List<SchedulePersonSetting> list) {
        if (getHandle() == 0) return false;
        if (ZKTools.isEmpty(list)) {
            ZKLog.d(TAG, "setPersonShiftList: null data");
            return true;
        }
        String json = FastJsonUtils.toJson(list);
        ZKLog.d(TAG, " setPersonShiftList start  json length : " + json.length() + "   json = " + json);
        FileUtil.saveFile(json);
        int ret = UdkService.UdkSetPersonalSche(getHandle(), json);
        ZKLog.d(TAG, " setPersonShiftList ret : " + ret);
        return ret > 0;
    }

    public static boolean downForm(String fileName, String path) {
        if (getHandle() == 0) return false;
        int ret = UdkService.UdkGetFileByName_ex(getHandle(), fileName, path);
        ZKLog.d(TAG, "generateForm: ret=" + ret);
        return ret >= 0;
    }

//    public static boolean generateStandardForm(long startTime, long endTime) {
//        if (getHandle() == 0) return false;
//        int ret = UdkService.UdkGenerateAttReport(getHandle(), 0, transform(startTime), transform(endTime));
//        Log.d(TAG, "generateStandardForm: ret= " + ret);
//        return ret > 0;
//    }

//    public static void generateSettingForm(long startTime, long endTime) {
//        if (getHandle() == 0) return;
//        int ret = UdkService.UdkGenerateAttReport(getHandle(), 1, transform(startTime), transform(endTime));
//        Log.d(TAG, "generateSettingForm: ret= " + ret);
//    }


    public static int encodeTime(int year, int mon, int day, int hour, int min, int second) {
        return ((year % 100) * 12 * 31 + ((mon) * 31) + day - 1) * (24 * 60 * 60) +
                (hour * 60 + min) * 60 + second;
    }

    public static int transform(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return encodeTime(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE),
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
    }


//    public static boolean deleteFile(String fileName) {
//        if (getHandle() == 0) return false;
//        int ret = UdkService.UdkDeleteFileByName(getHandle(), fileName);
//        Log.d(TAG, "deleteFile: ret= " + ret);
//        return ret > 0;
//    }

    public static boolean setParam(String str) {
        ZKLog.d(TAG, "setParam: " + str);
        return UdkService.UdkSetDeviceParam(getHandle(), str) > 0;
    }

    public static String getParam(String paramName) {
        byte[] buffer = new byte[1024];
        UdkService.UdkGetDeviceParam(getHandle(), buffer, buffer.length, paramName);
        String str = new String(buffer).trim();
        ZKLog.d(TAG, "getParam: " + str);
        return str;
    }

    public static List<SchedulePerson> getPersonList() {
        if (getHandle() == 0) return null;
        // 第三个参数int[] size是存放，提供缓存区的大小。比如传入new int[]{1024};
        // 如果返回的ret是ERROR_LOWMEMORY(-4 意思是这边提供的缓存区不够大)，并且会将所需要的缓存区大小，放在int[] size中，
        // 获取所需缓存区大小，再次调用该方法去获取即可。
        int[] size = new int[]{DEFAULT_LENGTH};
        byte[] str = new byte[DEFAULT_LENGTH];
        ZKLog.d(TAG, "GetPersonList: start  size :" + size[0] + "   Length :" + str.length);
        int ret = UdkService.UdkGetUserNameAndPin2(getHandle(), str, size);
        ZKLog.d(TAG, "GetPersonList: ret=" + ret + " size :" + size[0] + "   Length :" + str.length);
        if (ret == UdkHelper.ERROR_LOWMEMORY) {
            int needLength = size[0];
            if (needLength > 0 && needLength > DEFAULT_LENGTH) {
                str = new byte[needLength];
                ret = UdkService.UdkGetUserNameAndPin2(getHandle(), str, size);
            }
        }
        ZKLog.e(TAG, "loadPersonList : getPersonList ret 2= " + ret + "  buffer.length " + str.length + "   size : " + size[0]);
        return ret > 0 ? gsonParseSchedulePerson(str) : null;
//        return ret > 0 ? FastJsonUtils.decodeGBK(str, SchedulePerson.class) : null;
//        return ret > 0 ? gsonParseSchedulePerson(str) : null;
    }

    public static List<SchedulePerson> gsonParseSchedulePerson(byte[] data) {
        if (getHandle() == 0) return null;
        try {
            Gson gson = new Gson();

            Type type = new TypeToken<ArrayList<SchedulePerson>>() {
            }.getType();
            ZKLog.e(TAG, "gsonParseSchedulePerson: before ");
//            String tmp = new String(data, "GB18030").trim();
            String tmp = new String(data, "ISO8859-6").trim();

//            ZKLog.e(TAG, "gsonParseSchedulePerson: tmp " );
//            FileUtil.saveFile(tmp);
            ZKLog.e(TAG, "gsonParseSchedulePerson: before  1 ");
            List<SchedulePerson> rs = gson.fromJson(tmp.trim(), type);
            ZKLog.e(TAG, "gsonParseSchedulePerson: after ");
            return rs;
        } catch (UnsupportedEncodingException e) {
            ZKLog.e(TAG, "gsonParseSchedulePerson UnsupportedEncodingException");
            e.printStackTrace();
            return null;
        } catch (JsonSyntaxException e) {
            ZKLog.e(TAG, "gsonParseSchedulePerson JsonSyntaxException : " + e.getMessage());
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            ZKLog.e(TAG, "gsonParseSchedulePerson Exception : " + e.getMessage());
            return null;
        }
    }

    public static boolean downFormNew(String dir) {
        if (getHandle() == 0) return false;
        byte[] buffer = new byte[2048];
        int[] bufferLength = new int[]{buffer.length};
        bufferLength[0] = buffer.length;
        int ret = UdkService.UdkGetFileByName2(getHandle(), dir, buffer, bufferLength);
        ZKLog.d(TAG, "downFormNew: ret=" + ret);
        return ret >= 0;
    }

    public static int downFormNew2(String dirPath, String dat, byte[] buffer, int[] bufferLength) {
        if (getHandle() == 0) return -1;
        File file = new File(dirPath + "/" + dat);
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ZKLog.e(TAG, "downFormNew2 : start UdkGetFileByName2 - 1 ");
        int ret = UdkService.UdkGetFileByName2(getHandle(), dat, buffer, bufferLength);
        ZKLog.e(TAG, "downFormNew2 : start UdkGetFileByName2 - 1 ret : " + ret + "   fileName : " + dat);
        // 防止内存不够
        if (ret == UdkHelper.ERROR_LOWMEMORY) {
            int needLength = bufferLength[0];
            ZKLog.e(TAG, "downFormNew2 :  UdkGetFileByName2  : ERROR_LOWMEMORY : needLength = " + needLength + " buffer.length = " + buffer.length);
            if (needLength > 0 && needLength > buffer.length) {
                buffer = new byte[needLength];
                ret = UdkService.UdkGetFileByName2(getHandle(), dat, buffer, bufferLength);
            } else {
                ret = -1;
            }
        }
        ZKLog.e(TAG, "downFormNew2 :  UdkGetFileByName2  : ret = " + ret + "  writeByte2File");
        if (ret > 0) {
            FileUtil.writeByte2File(buffer, bufferLength, dirPath + "/" + dat, false);
        }
        ZKLog.e(TAG, "downFormNew2 :  UdkGetFileByName2  : End writeByte2File");
//            ret = UdkService.UdkGetFileByName2_ex(getHandle(), dat,dir);
        ZKLog.e(TAG, dat + ": ret=" + ret);
        return ret;
    }

    /**
     * 封装断点续传方法
     *
     * @param dirPath 文件路径
     * @param dat     表格数据名称
     * @return 结果
     */
    public static int downFormFile(String dirPath, String dat) {
        if (getHandle() == 0) return -1;
        File file = new File(dirPath + "/" + dat);
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }

        byte[] buffer = new byte[100 * 1024];
        int[] bufferLength = new int[1];
        int startIndex = 0;
        int ret = -1;
        while (ret != 0) {
            bufferLength[0] = 100 * 1024;
//            ret = downloadFormSubSection(dat,startIndex,buffer,bufferLength);
            ret = UdkService.UdkGetFile(getHandle(), dat, startIndex, buffer, bufferLength);
            // ret > 0 代表还有数据未读完
            if (ret >= 0) {
                FileUtil.writeByte2File(buffer, bufferLength, dirPath + "/" + dat, true);
                startIndex += bufferLength[0];
            } else {
                break;
            }
        }
        return ret;
    }

    /**
     * 分段下载单独接口
     *
     * @param dirPath 文件路径
     * @param dat     表格数据名称
     * @return 结果
     */
    public static int downFormFileSubsection(String dirPath, String dat, int startIndex, byte[] buffer, int[] bufferLength, boolean needDelete) {
        if (getHandle() == 0) return -1;
        File file = new File(dirPath + "/" + dat);
        if (needDelete && file.exists()) {
            file.delete();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return -1;
            }
        }
        if (!file.exists()) {
            FileUtil.makeDir(file.getParentFile());
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return -1;
            }
        }
        int ret = UdkService.UdkGetFile(getHandle(), dat, startIndex, buffer, bufferLength);
        if (ret >= 0) {
            FileUtil.writeByte2File(buffer, bufferLength, dirPath + "/" + dat, true);
        }
        return ret;
    }

    /**
     * 睡眠Socket
     */
    public static int drmancySocket() {
        if (getHandle() == 0) return -1;
        return UdkService.UdkDormancySocket(getHandle());
    }

    public static int createExcel(String dirPath, String outPutDir, String startTime, String endTime, JAttOptions options, int language,
                                  ReportManager.ProgressUpdateListener listener) {
        ReportManager mReportManager = new ReportManager();
        mReportManager.setBeginDate(startTime);
        mReportManager.setEndDate(endTime);
        mReportManager.setOptions(options);
        mReportManager.setInputFilePath(dirPath + ZKConstant.EXCEL_DATA);
        String languageDir = "";
        if (language == ReportFactory.LANGUAGE_SIMCN) {
            languageDir = ZKConstant.LANGUAGE_SIMCN;
        } else {
            languageDir = ZKConstant.LANGUAGE_EN;
        }
        String xlsPath = dirPath + ZKConstant.EXCEL_TEMP + File.separator + languageDir;
        ZKLog.d(TAG, "setTemplateXlsPath2 : " + xlsPath.replace("//", "/"));
        xlsPath = xlsPath.replace("//", "/");
        mReportManager.setTemplateXlsPath(xlsPath/*+ ZKConstant.EXCEL_SSRTEMPLATES*/);
        // 新增设置语言
        mReportManager.setXlsLanguage(language);
        ZKLog.d(TAG, "dirPath = " + dirPath + "   \noutPutDir = " + outPutDir + "   \nstartTime = " + startTime + "  \nendTime = " + endTime + "  " +
                "\nlanguage = " + language);
        ZKLog.d(TAG, "JAttOptions : mArrayFuction = " + options.mArrayFuction + "  \nmLateMinute = " + options.mLateMinute + "  " +
                "\nmEarlyMinute = " + options.mEarlyMinute + "  \nmDefaultWorkNumber = " + options.mDefaultWorkNumber + "  \nmIsSaturdayAndSundayWork = " +
                options.mIsSaturdayAndSundayWork + "  \nmOutPathDir = " + options.mOutPathDir);
        ZKLog.e(TAG, " DownloadForm :CreateExcel Calc Start. Language=" + language);
//        mReportManager.setProgressCallBack();
//        mReportManager.setProgressListener(listener);
        int ret = mReportManager.calc(outPutDir/*+ZKConstant.EXCEL_TEMP_XLS*/);//暂时返回0代表成功
        ZKLog.e(TAG, " DownloadForm :CreateExcel Calc End. ret = " + ret);
        return ret;

    }

    public static void startPrintLog() {
        ReportManager.printLogFile();
    }

    public static boolean setLocalize(String localizeFile) {
        boolean ret;
        ret = ReportManager.setLocalize(localizeFile);
        return ret;
    }

    public static void setLicenceKey() {
        ReportManager.setLicenceKey(ZKConstant.LICENCEKEY);
    }
}
