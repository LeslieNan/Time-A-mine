package com.example.timeassistant.utils;

import java.text.SimpleDateFormat;

/**
 * Created by revitbaby on 4/4/15.
 */
public class TimeUtil {
    public static final SimpleDateFormat DateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    // 空格和冒号用下划线代替，否则有些机型手机无法创建带有这种符号的文件
    public static final SimpleDateFormat DateTimeDetailFormatter = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss");
    public static final SimpleDateFormat DateTimeDetailFormatter2 = new SimpleDateFormat("yyyyMMddHHmmss");
    public static final SimpleDateFormat DateFormatterNoSlash = new SimpleDateFormat("yyyyMMdd");
    public static final SimpleDateFormat TimeFormatter = new SimpleDateFormat("HH:mm");
    public static final SimpleDateFormat DateTimeFormatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    public static final SimpleDateFormat DateTimeSecFormatter = new SimpleDateFormat("yyyyMM-dd HH:mm");

    public static final SimpleDateFormat YearMonthFormatter = new SimpleDateFormat("yyyy.MM");

    public static final SimpleDateFormat MonthDateFormatter = new SimpleDateFormat("dd");
    public static final SimpleDateFormat MonthDateFormatterNoSlash = new SimpleDateFormat("MMdd");
}
