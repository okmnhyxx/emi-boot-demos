package com.emi.shiro.controller;

import com.emi.shiro.dto.common.RestResponse;
import com.emi.shiro.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by emi on 2017/2/15.
 */
@Controller
@RequestMapping("/api/resource")
public class ResourceController {

    @Autowired
    private PermissionService resourceService;

    @RequestMapping(value = "/self/reference", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse testSelfReference() {

        return resourceService.testSelfReference();
    }

}
