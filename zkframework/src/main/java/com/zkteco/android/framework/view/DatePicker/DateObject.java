package com.zkteco.android.framework.view.DatePicker;

import android.content.Context;


import com.zkteco.framework.R;

import java.util.Calendar;

public class DateObject extends Object {
    private Context context;
    private int year;
    private int month;
    private int day;
    private int week;
    private int hour;
    private int minute;
    private String listItem;

    /**
     * 日期对象的4个参数构造器，用于设置日期
     *
     * @author sxzhang
     */
    public DateObject(Context context, int year2, int month2, int day2, int week2) {
        super();
        this.context = context;
        Calendar c = Calendar.getInstance();
        this.year = year2;
        /*int maxDayOfMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);//注释掉 10月份有31号因为这个判断而有bug 是否会造成其他bug?
        if (day2 > maxDayOfMonth) {
            this.month = month2 + 1;
            this.day = day2 % maxDayOfMonth;
        } else {*/
        this.month = month2;
        this.day = day2;
        /*}*/
        this.week = week2 % 7 == 0 ? 7 : week2 % 7;

        if (day == c.get(Calendar.DAY_OF_MONTH) && month == (c.get(Calendar.MONTH) + 1) && year == c.get(Calendar.YEAR)) {
            this.listItem = String.format("%02d", this.month) + context.getString(R.string.date_month) + "  " + String.format("%02d", this.day) +
                    context.getString(R.string.date_day) + "  " + context.getString(R.string.today) + "  ";
//            this.listItem = context.getString(R.string.today) + "  " + String.format("%02d", this.day) + "  " + getMonthOfYear(this.month);
        } else {
            this.listItem = String.format("%02d", this.month) + context.getString(R.string.date_month) + "  " + String.format("%02d", this.day) +
                    context.getString(R.string.date_day) + "  " + getDayOfWeekCN(week) + "  ";
//            this.listItem = getDayOfWeekCN(week) + "  " + String.format("%02d", this.day) + "  " + getMonthOfYear(this.month);
        }

    }

    /**
     * 日期对象的2个参数构造器，用于设置时间
     *
     * @param hour2
     * @param minute2
     * @param isHourType true:传入的是hour; false: 传入的是minute
     * @author sxzhang
     */
    public DateObject(int hour2, int minute2, boolean isHourType) {
        super();
        if (isHourType == true && hour2 != -1) {      //设置小时
            if (hour2 > 24) {
                this.hour = hour2 % 24;
            } else
                this.hour = hour2;
            this.listItem = this.hour + "时";
        } else if (isHourType == false && minute2 != -1) { //设置分钟
            if (minute2 > 60)
                this.minute = minute2 % 60;
            else
                this.minute = minute2;
            this.listItem = this.minute + "分";
        }
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getListItem() {
        return listItem;
    }

    public void setListItem(String listItem) {
        this.listItem = listItem;
    }

    /**
     * 根据day_of_week得到周几
     *
     * @return
     */
    public String getDayOfWeekCN(int day_of_week) {
        String result = null;
        switch (day_of_week) {
            case 1:
                result = context.getString(R.string.sunday);
                break;
            case 2:
                result = context.getString(R.string.monday);
                break;
            case 3:
                result = context.getString(R.string.tuesday);
                break;
            case 4:
                result = context.getString(R.string.wednesday);
                break;
            case 5:
                result = context.getString(R.string.thursday);
                break;
            case 6:
                result = context.getString(R.string.friday);
                break;
            case 7:
                result = context.getString(R.string.saturday);
                break;
            default:
                break;
        }
        return result;
    }

    /**
     * 根据day_of_week得到周几
     *
     * @return
     */
    public String getMonthOfYear(int month) {
        String result = null;
        switch (month) {
            case 1:
                result = context.getString(R.string.january);
                break;
            case 2:
                result = context.getString(R.string.february);
                break;
            case 3:
                result = context.getString(R.string.march);
                break;
            case 4:
                result = context.getString(R.string.april);
                break;
            case 5:
                result = context.getString(R.string.may);
                break;
            case 6:
                result = context.getString(R.string.june);
                break;
            case 7:
                result = context.getString(R.string.july);
                break;
            case 8:
                result = context.getString(R.string.august);
                break;
            case 9:
                result = context.getString(R.string.september);
                break;
            case 10:
                result = context.getString(R.string.october);
                break;
            case 11:
                result = context.getString(R.string.november);
                break;
            case 12:
                result = context.getString(R.string.december);
                break;
            default:
                break;
        }
        return result;
    }

    @Override
    public String toString() {
        return year + context.getString(R.string.date_year) +
                String.format("%02d", this.month) + context.getString(R.string.date_month) +
                String.format("%02d", this.day) + context.getString(R.string.date_day) +
                " " + String.format("%02d", this.hour) + ":" +
                String.format("%02d", this.minute);
    }

}
