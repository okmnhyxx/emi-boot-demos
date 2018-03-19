package com.emi.common.testing.utils;

import com.emi.common.testing.common.RandomType;
import com.emi.common.testing.exception.EnDecodingException;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Random;

/**
 * @author kexia.lu on 2017/9/7.
 */
public class CommonUtils {

    /**
     * 将对象转为x-www-form-urlencoded兼容的字符串格式（属性必须有get方法）
     * @param object 要转换的对象
     * @param class_ 对象的Class
     * @return
     */
    public static String generateFormParams(Object object, Class class_) {
        if (null == object) {
            return null;
        }

        Field[] fields = object.getClass().getDeclaredFields();
        if (fields.length == 0) {
            try {
                object = class_.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                return "";
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < fields.length; i++) {
            sb.append(fields[i].getName()).append("=").append(fetchFieldValueByName(fields[i].getName(), object)).append("&");
        }
        String resultStr = sb.toString();
        return resultStr.substring(0, resultStr.length() - 1);
    }

    /**
     * 生成随机字符串
     * @param length  随机字符串长度
     * @param type	  1:数字    2:字母   3及其他:数字和字符串类型
     * @return
     */
    public static String generateRandom(int length, RandomType type) {

        if (length < 1) {
            return "";
        }
        String base = null;
        if (RandomType.Number == type) {
            base = "0123456789";
        } else if (RandomType.Chars == type) {
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
        if (sb.substring(0, 1).equals("0")) {
            return (new Random().nextInt(9) + 1) + sb.substring(1);
        }
        return sb.toString();
    }

    public static String doEncode(String content) {

        try {
            return URLEncoder.encode(content, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new EnDecodingException(true, content, "UTF-8");
        }
    }

    public static String doDecode(String content) {
        try {
            return URLDecoder.decode(content,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new EnDecodingException(false, content, "UTF-8");
        }
    }

    public static String doEscape(String unescapeStr) {
        return StringEscapeUtils.escapeXml11(unescapeStr);
    }

    //反转义
    public static String doUnEscape(String escapeStr) {
        return StringEscapeUtils.unescapeXml(escapeStr);
    }

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
     * 用这个方法的话，属性必须有get方法，否则值为""
     */
    private static Object fetchFieldValueByName(String fieldName, Object object) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = object.getClass().getMethod(getter);
            Object value = method.invoke(object);
            return null == value ? "" : value;
        } catch (Exception e) {
//            log.error(e.getMessage(), e);
            return "";
        }
    }

}
