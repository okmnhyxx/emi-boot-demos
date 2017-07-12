package com.emi.boot.common.util;


import com.emi.boot.common.enums.DomainParam;
import com.emi.boot.common.enums.DomainType;
import com.emi.boot.common.enums.ParamType;
import com.emi.boot.common.exception.*;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by emi on 2016/12/23.
 */
public class ValidUtils {

    public static final String REG_NUMBER = "[0-9]*";
    public static final String REG_PHONE = "[0-9]{10,12}|[0-9]{3,4}-[0-9]{7,8}|[0-9]{3,4}-[0-9]{3}-[0-9]{3,4}";
    public static final String REG_INTEGER = "[1-9]\\d*";
    public static final String REG_NUM = "[1-9]\\d*|0";
    public static final String REG_BANK_ACCOUNT = "[\\d\\s]+";
    public static final String REG_FULL_URL = "http[s]?:\\/\\/([\\w-]+\\.)+[\\w-]+([\\w-./?%&=]*)?";
    public static final String REG_URL = "http[s]?://[A-Za-z0-9-_]+\\..+";
//    public static final String REG_MOBILE = "(13[0-9]{9}|15[012356789][0-9]{8}|17[678][0-9]{8}|18[0-9]{9}|14[57][0-9]{8})";
    public static final String REG_MOBILE = "(1(3|4|5|7|8)[0-9]{9})";

    public ValidUtils() {
    }

    public static boolean regexp(String exp, String validStr) {
        if(StringUtils.isEmpty(validStr)) {
            return false;
        } else {
            Pattern pattern = Pattern.compile(exp);
            Matcher match = pattern.matcher(validStr);
            return match.matches();
        }
    }

    public static String phoneNotNull(String validStr) {
        notBlank(ParamType.Phone, validStr);
        phone(validStr);
        return validStr;
    }


    public static void phone(String validStr) {
        if (StringUtils.isEmpty(validStr)) {
            return;
        }
        if (!regexp(REG_MOBILE, validStr)) {
            throw new ValidatePatternException(ParamType.Phone, validStr);
        }
    }

    public static String idNotNull(DomainType domainType, String domainId) {
        notZero(domainType, domainId);
        id(domainType, domainId);
        return domainId;
    }

    public static String id(DomainType domainType, String domainId) {
        if (StringUtils.isEmpty(domainId)) {
            throw new ValidatePatternException(domainType, domainId);
        }
        return domainId;
    }


    public static String notBlank(DomainParam domainParam, String validStr) {
        if (StringUtils.isEmpty(validStr)) {
            throw new ValidateNullException(domainParam);
        }
        return validStr;
    }

    private static void notZero(DomainParam domainParam, String domainId) {
        if (StringUtils.isEmpty(domainId)) {
            throw new ValidateNullException(domainParam);
        }
    }

    public static String notBlankMax(DomainParam domainParam, String validStr, int maxLength) {

        if (StringUtils.isEmpty(validStr)) {
            throw new ValidateNullException(domainParam);
        }
        return lengthMax(domainParam, validStr, maxLength);
    }

    public static String notBlankMin(DomainParam domainParam, String validStr, int minLength) {

        if (StringUtils.isEmpty(validStr)) {
            throw new ValidateNullException(domainParam);
        }
        return lengthMin(domainParam, validStr, minLength);
    }

    public static String notBlankMinMax(DomainParam domainParam, String validStr, int minLength, int maxLength) {

        if (StringUtils.isEmpty(validStr)) {
            throw new ValidateNullException(domainParam);
        }
        return lengthMax(domainParam, lengthMin(domainParam, validStr, minLength), maxLength);
    }


    public static int range(ParamType paramType, int validNum, int min, int max) {
        min(paramType, validNum, min);
        max(paramType, validNum, max);
        return validNum;
    }

    public static int min(ParamType paramType, int validNum, int min) {
        if (validNum < min) {
            throw new ValidateRangeException(paramType, validNum, min, true);
        }
        return validNum;
    }

    private static int max(ParamType paramType, int validNum, int max) {
        if (validNum > max) {
            throw new ValidateRangeException(paramType, validNum, max, false);
        }
        return validNum;
    }

    public static double amountsNot0(double amounts) {
        if (amounts <= 0) {
            throw new ValidateRangeException(ParamType.Amounts, amounts, 0, true);
        }

        if (new BigDecimal(amounts * 100).intValue() != amounts * 100) {
            throw new ValidateAmountsException(amounts);
        }
        return amounts;
    }

    public static String lengthMax(DomainParam domainParam, String validStr, int maxLength) {
        if (StringUtils.isEmpty(validStr)) {
            return null;
        }
        if (validStr.length() > maxLength) {
            throw new ValidateLengthException(domainParam, validStr, maxLength, true);
        }
        return validStr;
    }

    public static String lengthMin(DomainParam domainParam, String validStr, int minLength) {
        if (StringUtils.isEmpty(validStr)) {
            return null;
        }
        if (validStr.length() < minLength) {
            throw new ValidateLengthException(domainParam, validStr, minLength, false);
        }
        return validStr;
    }
}
