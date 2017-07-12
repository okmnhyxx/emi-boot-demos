package com.emi.boot.controller;

import com.emi.boot.common.exception.RestException;
import com.emi.boot.common.util.IpUtils;
import com.emi.boot.dto.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by emi on 2016/11/14.
 */
@Controller
public class ExceptionController implements ErrorController {


    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionController.class);
    private static final String ERROR_PATH = "/error";


    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping({"/error"})
    @ResponseBody
    public RestResponse handleError(HttpServletRequest request, HttpServletResponse response) {
        int code = 0;
        String message = "";

        try {
            response.setStatus(200);
            Map e = this.getErrorAttributes(request);
            if(e != null) {
                if(e.get("code") != null) {// Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
                    code = Integer.parseInt(e.get("code").toString());
                }

                if(e.get("message") != null) {
                    message = e.get("message").toString();
                }

                if(e.get("debugMessage") != null) {
                    String path = (String)request.getAttribute("javax.servlet.error.request_uri");
//                    LOGGER.error(String.format("\r\n-=-=- [%s]  [path:%s]  [exception:%s]  %s", new Object[]{IpUtils.getIpAddress(request), path, e.get("exception"), e.get("debugMessage").toString()}));
                    LOGGER.error(String.format("\r\n-=-=-=- [%s]  [path:%s]  [exception:%s]  %s", IpUtils.getIpAddress(request), path, e.get("exception"), e.get("debugMessage").toString()));
                }
            }
            return new RestResponse(code, message);
        } catch (Exception var7) {
            LOGGER.error("SystemErrorController-Exception:" + var7.getMessage());
            return new RestResponse(100001, "服务器内部异常");
        }
    }

    private Map<String, Object> getErrorAttributes(HttpServletRequest request) {
        ServletRequestAttributes requestAttributes = new ServletRequestAttributes(request);
        LinkedHashMap errorAttributes = new LinkedHashMap();
        this.addErrorDetails(errorAttributes, requestAttributes);
        return errorAttributes;
    }

    private void addErrorDetails(Map<String, Object> errorAttributes, RequestAttributes requestAttributes) {
        Throwable error = this.getError(requestAttributes);
        if(error != null) {
            String errorMsg = error.getMessage().trim();
            String errorObj = error.toString();
            if(error instanceof RestException) {
                RestException message1 = (RestException)error;
                errorAttributes.put("code", Integer.valueOf(message1.getCode()));
                errorAttributes.put("message", message1.getMessage());
                errorAttributes.put("debugMessage", message1.getDebugMessage());
            } else if(!errorObj.contains("BindException") && !errorObj.contains("MethodArgumentNotValidException")) {
                if(!errorMsg.contains("timedout") && !errorMsg.contains("timeout")) {
                    if(errorMsg.contains("refused")) {
                        errorAttributes.put("code", Integer.valueOf(100008));
                        errorAttributes.put("message", "服务器连接失败");
                        errorAttributes.put("debugMessage", error.getMessage());
                    } else {
                        errorAttributes.put("code", Integer.valueOf(100001));
                        errorAttributes.put("message", "服务器内部异常");
                        errorAttributes.put("debugMessage", error.getMessage());
                    }
                } else {
                    errorAttributes.put("code", Integer.valueOf(100009));
                    errorAttributes.put("message", "服务器连接超时");
                    errorAttributes.put("debugMessage", error.getMessage());
                }
            } else {
                String message = this.getBindMessage(error.getMessage());
                errorAttributes.put("code", Integer.valueOf(100002));
                errorAttributes.put("message", message);
                errorAttributes.put("debugMessage", message);
            }

            errorAttributes.put("exception", error.getClass().getName());
        } else {
            errorAttributes.put("code", Integer.valueOf(100003));
            errorAttributes.put("message", "请求路径不合法");
            errorAttributes.put("debugMessage", "请求路径不合法");
        }

    }

    private Throwable getError(RequestAttributes requestAttributes) {
        Throwable exception = (Throwable)requestAttributes.getAttribute(DefaultErrorAttributes.class.getName() + ".ERROR", 0);
        if(exception == null) {
            exception = (Throwable)requestAttributes.getAttribute("javax.servlet.error.exception", 0);
        }

        return exception;
    }

    private String getBindMessage(String str) {
        if(StringUtils.hasText(str)) {
            String[] sa = str.split("message");
            if(sa != null && sa.length > 0) {
                for(int i = sa.length - 1; i >= 0; --i) {
                    if(sa[i].getBytes().length != sa[i].length()) {
                        str = sa[i].trim().replace("[", "");
                        String[] st = str.split("]");
                        if(st != null && st.length > 0) {
                            str = st[0];
                        }
                        break;
                    }
                }
            }
        }

        return str;
    }

}
