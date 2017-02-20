package com.emi.shiro.controller;

import com.emi.shiro.component.bind.annotation.CurrentUser;
import com.emi.shiro.domain.User;
import com.emi.shiro.dto.UserCreateResponse;
import com.emi.shiro.dto.UserListResponse;
import com.emi.shiro.dto.common.PagedRequest;
import com.emi.shiro.dto.common.RestResponse;
import com.emi.shiro.service.UserService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


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
    @RequestMapping(value = "/user/search", method = RequestMethod.GET)
    public UserListResponse search(String username, Boolean locked, PagedRequest pagedRequest) {

        return userService.search(username, locked, pagedRequest);
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
    public RestResponse lock(@CurrentUser User user, @PathVariable("userId")long userId, boolean lock) {
        return userService.lock(user.getId(), userId, lock);
    }


    @RequiresPermissions("user:query")
    @RequestMapping(value = "/user/{userId}/edit/a", method = RequestMethod.POST)
    public String userEdit(@PathVariable("userId") long userId, String name) {
        return name + " edit a ok";
    }

    @RequiresPermissions("user:query")
    @RequestMapping(value = "/user/{userId}/query/a", method = RequestMethod.POST)
    public String userQuery(@PathVariable("userId") long userId, String name) {
        return name + " query a ok";
    }

    @RequiresRoles("manager")
    @RequiresPermissions("query")
    @RequestMapping(value = "/query/b", method = RequestMethod.POST)
    public String query(String name) {
        return name + " query b ok";
    }
}
