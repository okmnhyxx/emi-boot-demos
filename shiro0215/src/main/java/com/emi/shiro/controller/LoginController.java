package com.emi.shiro.controller;

import com.emi.shiro.component.bind.annotation.CurrentUser;
import com.emi.shiro.configs.ErrorCode;
import com.emi.shiro.domain.User;
import com.emi.shiro.dto.LoginMenuResponse;
import com.emi.shiro.dto.common.RestResponse;
import com.emi.shiro.dto.vo.MenuVo;
import com.emi.shiro.dto.vo.UserVo;
import com.emi.shiro.service.PermissionService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by emi on 2017/2/15.
 */
@RestController
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private PermissionService permissionService;

    @RequestMapping("/index")
    public LoginMenuResponse defaults(@CurrentUser User user) {

        List<MenuVo> menuVoList = permissionService.fetchPermissionByUser(user);
        return new LoginMenuResponse(new UserVo(user.getId(), user.getUsername()), menuVoList);
    }

    @RequestMapping(value = "/login")
    public RestResponse showLoginForm(HttpServletRequest req) {

        String exceptionClassName = (String)req.getAttribute("shiroLoginFailure");
        String errorMessage = null;
        if(UnknownAccountException.class.getName().equals(exceptionClassName)) {
            errorMessage = "用户名/密码错误";
        } else if (LockedAccountException.class.getName().equals(exceptionClassName)) {
            errorMessage = "账号已被锁定";
        } else if(IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
            errorMessage = "用户名/密码错误";
        } else if(exceptionClassName != null) {
            errorMessage = "其他错误：" + exceptionClassName;
        } else {
            return new RestResponse();
        }
        return new RestResponse(ErrorCode.CODE_403, errorMessage);
    }

    @RequestMapping(value="/logout",method= RequestMethod.GET)
    public RestResponse logout(){
        //使用权限管理工具进行用户的退出，跳出登录，给出提示信息
        SecurityUtils.getSubject().logout();
        return new RestResponse();
    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public RestResponse noAuthorization(@CurrentUser User user, HttpServletRequest request) {

        logger.warn("user[" + user.getId() + "]无权限访问此接口");//todo:
        return new RestResponse(ErrorCode.CODE_403, "抱歉，您无此权限");
    }

}
