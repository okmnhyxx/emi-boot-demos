package com.emi.shiro.controller;


import cn.cloudtop.common.api.basic.RestException;
import cn.cloudtop.common.api.basic.RestResponse;
import com.emi.shiro.common.ErrorCode;
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

//import com.emi.shiro.common.utils.IpUtils;
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
        String preErrorPath = request.getAttribute("javax.servlet.forward.request_uri").toString();
        int code = 0;
        String message = "";

        if (preErrorPath.contains("favicon")) {
            return null;
        }
        LOGGER.error("preErrorPath: " + preErrorPath);

        response.setCharacterEncoding("utf-8");
        RestResponse restResponse = this.checkCommonError(response);
        response.setStatus(200);
        if (null != restResponse) {
            return restResponse;
        }

        try {
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
//                    LOGGER.error(String.format("-=-=- [%s][path:%s][exception:%s]%s", new Object[]{IpUtils.getIpAddress(request), path, e.get("exception"), e.get("debugMessage").toString()}));
                }
            }
            return new RestResponse(code, message);
        } catch (Exception var7) {
            LOGGER.error("SystemErrorController-Exception:" + var7.getMessage());
            return new RestResponse(ErrorCode.EXCEPTION_IN_EXCEPTION, "服务器内部异常");
        }
    }

    private RestResponse checkCommonError( HttpServletResponse response) {

        int status = response.getStatus();
        if (404 == status) {
            return new RestResponse(ErrorCode.CODE_404, "路径错误");
        } else if (405 == status) {
            return new RestResponse(ErrorCode.CODE_405, "请求方法错误");
        } else if (408 == status) {
            return new RestResponse(ErrorCode.CODE_408, "请求超时");
        } else if (414 == status) {
            return new RestResponse(ErrorCode.CODE_414, "请求网址过长");
        } else if (415 == status) {
            return new RestResponse(ErrorCode.CODE_415, "请求媒体类型不支持");
        }
        return null;
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
            } else if (errorObj.contains("BindException") || errorObj.contains("MethodArgumentNotValidException")) {//&& errorObj.contains("MethodArgumentNotValidException")
                String message = this.getBindMessage(error.getMessage());
                errorAttributes.put("code", ErrorCode.CODE_400);
                errorAttributes.put("message", "请求参数异常：" + message);
                errorAttributes.put("debugMessage", errorAttributes.get("message"));
            } else if (errorMsg.contains("timedout") || errorMsg.contains("timeout")) {
                errorAttributes.put("code", ErrorCode.CONNECT_TIMEOUT);
                errorAttributes.put("message", "服务器连接超时");
                errorAttributes.put("debugMessage", error.getMessage());
            } else if (errorMsg.contains("refused")) {
                errorAttributes.put("code", ErrorCode.CONNECT_REFUSE);
                errorAttributes.put("message", "服务器连接拒绝");
                errorAttributes.put("debugMessage", error.getMessage());
            } else {
                errorAttributes.put("code", ErrorCode.SERVER_INTERNAL_EXCEPTION);
                errorAttributes.put("message", "服务器内部异常");
                errorAttributes.put("debugMessage", error.getMessage());
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
