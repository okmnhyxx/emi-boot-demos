package com.emi.common.testing.common;


/**
 * @author kexia.lu on 2017/8/11.
 * 日期格式
 */
public enum DateFormatType {

    YearHalfMonthDayHourMinuteSecondMillis("yyMMddHHmmssSSS"),
    Year_Month_Day_Hour_Minute_Second("yyyy-MM-dd HH:mm:ss"),
    Year_Month_Day_Hour_Minute("yyyy-MM-dd HH:mm"),
    Year_Month_Day_Hour("yyyy-MM-dd HH"),
    Year_Month_Day("yyyy-MM-dd"),
    Hour_Minute("HH:mm");

    private String format;

    DateFormatType(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }
}
