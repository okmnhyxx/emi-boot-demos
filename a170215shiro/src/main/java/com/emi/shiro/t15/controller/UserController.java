package com.emi.shiro.t15.controller;

import com.emi.shiro.t15.component.bind.annotation.CurrentUser;
import com.emi.shiro.t15.domain.User;
import com.emi.shiro.t15.dto.UserDetailResponse;
import com.emi.shiro.t15.dto.UserListResponse;
import com.emi.shiro.t15.dto.common.PagedRequest;
import com.emi.shiro.t15.dto.common.RestResponse;
import com.emi.shiro.t15.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by emi on 2017/2/13.
 */
@RestController
@RequestMapping("/api")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;


    @RequiresPermissions("user:query")
    @RequestMapping(value = "/user/list", method = RequestMethod.GET)
    public UserListResponse search(String username, Boolean locked, PagedRequest pagedRequest) {

        return userService.search(username, locked, pagedRequest);
    }

    @RequestMapping(value = "/{userId}/detail}", method = RequestMethod.GET)
    public UserDetailResponse detail(@PathVariable("userId") long userId) {

        return userService.detail(userId);
    }


    @RequiresPermissions("user:create")
    @RequestMapping(value = "/user/create", method = RequestMethod.POST)
    public RestResponse create(@CurrentUser User user, long organizationId, String username, String password) {

        return userService.create(organizationId, user.getId(), username, password);
    }

    @RequiresPermissions("user:update")
    @RequestMapping(value = "/user/{userId}/modify", method = RequestMethod.POST)
    public RestResponse modify(@CurrentUser User user, @PathVariable("userId")long userId, long organizationId, String username, String password) {

        return userService.modify(organizationId, user.getId(), userId, username, password);
    }

    @RequiresPermissions("user:delete")
    @RequestMapping(value = "/user/{userId}/lock", method = RequestMethod.POST)
    public RestResponse lock(@CurrentUser User user, @PathVariable("userId")long userId, boolean locked) {
        return userService.lock(user.getId(), userId, locked);
    }


}
