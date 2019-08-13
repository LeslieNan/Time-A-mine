package com.zkteco.android.framework.utils;

/**
 * 描述：
 * <p>
 * 日期：2016/7/20
 * 作者：kangjj
 */
public class ZKConstant {
    public static final String UPDATEURL="http://192.168.2.232:8002/";
    public static final String SYS = "att_assis";
    public static final String UPDATE_APPKEY="976048850410006621";

    public static int MIN_WIFI_PWD_LENGTH = 8;
    public static int MAX_WIFI_PWD_LENGTH = 20;
    public static int MAX_WIFI_SSID_LENGTH = 17;
    public static int MAX_UDK_PWD_LENGTH = 6;
    public static final String REPORT_FORM_DEFAULT_NAME = "temp.xls";

    public static final String TIME_CUBE_PACKAGENAME= "com.zktechnology.timecubeapp";

    public static final String FILE_EXCEL = "/ZK/excel";
    public static final String EXCEL_OUTPUT = "/out";
    public static final String EXCEL_DATA = "/data";
    public static final String ASSETS_TEMP="template";
    public static final String ASSETS_LOCALE="locale";
    public static final String ASSETS_SYSTEMDAT="system_dat";
    public static final String EXCEL_TEMP ="/"+ASSETS_TEMP;
    public static final String EXCEL_LOCALE ="/"+ASSETS_LOCALE;
    public static final String EXCEL_XLS ="/out_xls";
    public static final String EXCEL_SSRTEMPLATES_XLS ="SSRTemplateS.xls";
    public static final String EXCEL_SSRATTRECORDS_XLS ="SSRAttRecordS.xls";
    public static final String EXCEL_SSRTEMPLATES ="/"+EXCEL_SSRTEMPLATES_XLS;
    public static final String EXCEL_SSRATTRECORDS ="/"+EXCEL_SSRATTRECORDS_XLS;
    public static final String EXCEL_DATEFORMULATEMP ="/dateFormulaTemp.dat";

//    public static final String EXCEL_SSRSTANDREPORT_XLS ="SSRStandReport.xls";
//    public static final String EXCEL_SSRATTRECORD_XLS ="SSRAttRecord.xls";

    public static final String EXCEL_LOCALIZE_ZH ="/localize_zh.txt";
    public static final String EXCEL_LOCALIZE_EN ="/localize_en.txt";

    public static final String LANGUAGE_SIMCN = "/zh_cn";
    public static final String LANGUAGE_EN = "/en";

    public static final String FILE_EXCEL_EXCEL_SSRTEMPLATES_CN = FILE_EXCEL + EXCEL_TEMP + LANGUAGE_SIMCN + EXCEL_SSRTEMPLATES;
    public static final String FILE_EXCEL_EXCEL_SSRATTRECORDS_CN = FILE_EXCEL + EXCEL_TEMP + LANGUAGE_SIMCN + EXCEL_SSRATTRECORDS;
    public static final String FILE_EXCEL_EXCEL_SSRTEMPLATES_EN = FILE_EXCEL + EXCEL_TEMP + LANGUAGE_EN + EXCEL_SSRTEMPLATES;
    public static final String FILE_EXCEL_EXCEL_SSRATTRECORDS_EN = FILE_EXCEL + EXCEL_TEMP + LANGUAGE_EN + EXCEL_SSRATTRECORDS;

    public static final String LICENCEKEY ="709A659D0A5EB7132D42D8876AF658F1";

    public static final String FLURRYKEY = "RNXQFM6WKXRFJSQ6KYVH";
}
