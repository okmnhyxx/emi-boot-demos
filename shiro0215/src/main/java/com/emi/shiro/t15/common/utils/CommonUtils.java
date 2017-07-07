package com.emi.shiro.t15.common.utils;

import com.emi.shiro.t15.common.enums.TimeUnitType;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created by emi on 2016/11/30.
 */
public class CommonUtils {


    public static double toYuan(long fen) {
        return new BigDecimal(fen/100.0).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static String toYuanStr(long fen) {
        return new BigDecimal(fen/100.0).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }

    public static long toFen(double yuan) {
        return (int)(yuan * 100);
    }

    public static long toFen(String yuan) {
        return toFen(Double.parseDouble(yuan));
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
    public static Date generateTimeOut(Date date,TimeUnitType unitType, int outDegree, boolean ifAfter) {
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

    /**
     * date4 与 data2的差值超出范围
     * @param date4 开始时间
     * @param date2 结束时间
     * @param degree  1:分钟    2:小时    3:天   4：月 为单位
     * @param outDegree  时间差
     * @return
     */
    public static boolean checkDayBetweenOut(Date date4, Date date2, int degree, int outDegree) {

        if (null == date4 || null ==  date2) {
            return true;
        }
        if (date4.after(date2)) {
            return false;
        }
        Calendar cld = Calendar.getInstance();
        cld.setTime(date4);
        if (1 == degree) {
            int minute =  cld.get(Calendar.MINUTE) + outDegree;
            cld.set(Calendar.MINUTE, minute);
        } else if (2 == degree) {
            int hour = cld.get(Calendar.HOUR) + outDegree;
            cld.set(Calendar.HOUR, hour);
        } else if(3 == degree) {
            int day = cld.get(Calendar.DATE) + outDegree;
            cld.set(Calendar.DATE, day);
        } else if(4 == degree) {
            int day = cld.get(Calendar.MONTH) + outDegree;
            cld.set(Calendar.MONTH, day);
        }
        date4 = cld.getTime();
        if (date4.after(date2)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 生成随机字符串
     * @param length  随机字符串长度
     * @param type	  1:数字    2:字母   3及其他:数字和字符串类型
     * @return
     */
    public static String generateRandom(int length,int type) {

        if (length < 1) {
            return "";
        }
        String base = null;
        if (1 == type) {
            base = "0123456789";
        } else if (2 == type) {
            base = "abcdefghijklmnopqrstuvwxyz";
        } else {
            base = "abcdefghijklmnopqrstuvwxyz0123456789";
        }
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        if (sb.substring(0,1).equals("0")) {
            return (new Random().nextInt(9) + 1) + sb.substring(1);
        }
        return sb.toString();
    }

    public static String generateDistanceStr(Long distance) {
        if (null == distance) {
            return null;
        }
        if (distance > 1000) {
            return new BigDecimal(distance).divide(new BigDecimal(1000), 2, BigDecimal.ROUND_HALF_UP).toString() + "km";
        } else {
            return distance + "m";
        }
    }


    public static String generateFeedRate(double feedBackRate) {
        if (Double.valueOf(feedBackRate).intValue() == feedBackRate) {
            return Double.valueOf(feedBackRate).intValue() + "%";
        } else {
            return new BigDecimal(feedBackRate).setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "%";
        }
    }


    ////[总评价星级 * 20 + 好评 + (20 +订单量/100)(8000封顶,大于100的话算100)]/distance
    public static double calculateStoreScore(double totalStarLevel, double feedBackRate, int orderQuantity, Long distance) {

        int orderCountScore = 2000 + orderQuantity;
        double addSum = totalStarLevel * 2000 + feedBackRate * 100 + (orderCountScore >= 10000 ? 10000 : orderCountScore);
        return new BigDecimal(addSum).divide(new BigDecimal(distance), 8, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static String exceptNull(String... str) {
        if (null == str || 0 == str.length) {
            return "";
        }

        if (str.length > 1) {
            StringBuilder sb = new StringBuilder(str.length);
            for (String s : str) {
                if (!StringUtils.isEmpty(s)) {
                    sb.append(s);
                }
            }
            return sb.toString();
        }

        return str[0].trim();
    }
}
