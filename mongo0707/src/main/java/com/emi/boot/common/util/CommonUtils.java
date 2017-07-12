package com.emi.boot.common.util;

import com.emi.boot.common.enums.SexType;
import org.apache.commons.lang3.StringUtils;

import java.util.Random;

/**
 * Created by emi on 2016/11/1.
 */
public class CommonUtils {


    public static String fetchNameCall(String realName, int sex) {
        return StringUtils.isEmpty(realName) ? "" : realName.substring(0, 1) + (sex == SexType.Girl.getValue() ? "女士" : "先生");
    }

    public static String generateOrderNo() {
        return "No" + generateRandom(14, 1);
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
}
