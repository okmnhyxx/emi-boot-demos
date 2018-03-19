package com.emi.shiro.t10.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * Created by emi on 2017/2/13.
 */
@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/")
    public String index() {
        return "redirect:alogin.html";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public String[] login(String username, String password) {

        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject currentUser = SecurityUtils.getSubject();
        try {
            currentUser.login(token);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new String[] {"login fail 1"};
        }
        //验证是否登录成功
        if (currentUser.isAuthenticated()) {
            return  new String[] {"login success"};
        } else {
            token.clear();
            return  new String[] {"login fail 2"};
        }
    }


    @RequestMapping(value = "/login2", method = RequestMethod.POST)
    @ResponseBody
    public String[] login2(String username, String password) {

        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject currentUser = SecurityUtils.getSubject();
        try {
            currentUser.login(token);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new String[] {"login fail 1"};
        }
        //验证是否登录成功
        if (currentUser.isAuthenticated()) {
            return  new String[] {"login success"};
        } else {
            token.clear();
            return  new String[] {"login fail 2"};
        }
    }

    @RequestMapping(value="/logout",method=RequestMethod.GET)
    public String logout(RedirectAttributes redirectAttributes ){
        //使用权限管理工具进行用户的退出，跳出登录，给出提示信息
        SecurityUtils.getSubject().logout();
        redirectAttributes.addFlashAttribute("message", "您已安全退出");
        return "redirect:/alogin.html";
    }


    @RequestMapping(value = "/403", method = RequestMethod.GET)
    @ResponseBody
    public String noAuthorization(String username, String password, RedirectAttributes attributes) {

        return "403 gggggggg";
    }

    @RequiresPermissions("user:query")
    @RequestMapping(value = "/user/{userId}/edit/a", method = RequestMethod.POST)
    @ResponseBody
    public String userEdit(@PathVariable("userId") long userId, String name) {
        return name + " edit a ok";
    }

    @RequiresPermissions("user:query")
    @RequestMapping(value = "/user/{userId}/query/a", method = RequestMethod.POST)
    @ResponseBody
    public String userQuery(@PathVariable("userId") long userId, String name) {
        return name + " query a ok";
    }

    @RequiresRoles("manager")
    @RequiresPermissions("query")
    @RequestMapping(value = "/query/b", method = RequestMethod.POST)
    @ResponseBody
    public String query(String name) {
        return name + " query b ok";
    }
}
