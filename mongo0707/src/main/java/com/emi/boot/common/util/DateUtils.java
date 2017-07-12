package com.emi.boot.common.util;

import com.emi.boot.common.enums.TimeUnitType;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by emi on 2017/6/15.
 */
public class DateUtils {

    public final static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public final static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public final static SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
    public final static SimpleDateFormat sdf4 = new SimpleDateFormat("HH:mm");

    public static Date appointTodayHour(int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    public static Date fetchInitDay(int addDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DATE, addDay);
        return calendar.getTime();
    }

    /**
     * 以date为基础，生成之前或之后的时间，
     * @param date  当前时间点
     * @param unitType  1:分钟    2:小时    3:天   4:月 为单位
     * @param outDegree  超时时间
     * @param ifAfter  true:生成之后的时间   false:生成之前的时间
     * @return
     * 	 * 获取outDay天后的时间
     */
    public static Date generateTimeOut(Date date, TimeUnitType unitType, int outDegree, boolean ifAfter) {
        Calendar cld = Calendar.getInstance();
        cld.setTime(date);
        int degree = unitType.ordinal() + 1;
        if(ifAfter) {
            if(1 == degree) {
                int minute =  cld.get(Calendar.MINUTE) + outDegree;
                cld.set(Calendar.MINUTE, minute);
            } else if(2 == degree) {
                int hour = cld.get(Calendar.HOUR) + outDegree;
                cld.set(Calendar.HOUR, hour);
            } else if(3 == degree) {
                int day = cld.get(Calendar.DATE) + outDegree;
                cld.set(Calendar.DATE, day);
            } else if (4 == degree) {
                int month = cld.get(Calendar.MONTH) + outDegree;
                cld.set(Calendar.MONTH, month);
            }
        } else {
            if(1 == degree) {
                int minute =  cld.get(Calendar.MINUTE) - outDegree;
                cld.set(Calendar.MINUTE, minute);
            } else if(2 == degree) {
                int hour = cld.get(Calendar.HOUR) - outDegree;
                cld.set(Calendar.HOUR, hour);
            } else if(3 == degree) {
                int day = cld.get(Calendar.DATE) - outDegree;
                cld.set(Calendar.DATE, day);
            } else if (4 == degree) {
                int month = cld.get(Calendar.MONTH) - outDegree;
                cld.set(Calendar.MONTH, month);
            }
        }
        return cld.getTime();
    }

    public static String generateTimeInterval(Date comeTime, Date goTime) {
        if (null != comeTime && null != goTime) {
            return sdf4.format(comeTime) + "-" + sdf4.format(goTime);
        } else {
            return "/";
        }
    }
}
