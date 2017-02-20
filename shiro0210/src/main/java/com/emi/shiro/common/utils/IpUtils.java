package com.emi.shiro.common.utils;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by emi on 2016/11/15.
 */
public class IpUtils {
    public IpUtils() {
    }

    private static String getClientIP(HttpServletRequest request) {
        return request.getHeader("x-forwarded-for") == null?request.getRemoteAddr():request.getHeader("x-forwarded-for");
    }

    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if(StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if(StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return "0:0:0:0:0:0:0:1".equals(ip)?"127.0.0.1":ip;
    }
}
