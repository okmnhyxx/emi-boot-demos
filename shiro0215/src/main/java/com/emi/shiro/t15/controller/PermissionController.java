package com.emi.shiro.t15.controller;

import com.emi.shiro.t15.component.bind.annotation.CurrentUser;
import com.emi.shiro.t15.domain.User;
import com.emi.shiro.t15.dto.ResourceDetailResponse;
import com.emi.shiro.t15.dto.ResourceListResponse;
import com.emi.shiro.t15.dto.ResourceTreeResponse;
import com.emi.shiro.t15.dto.common.RestResponse;
import com.emi.shiro.t15.dto.vo.ResourceVisibleVo;
import com.emi.shiro.t15.service.PermissionService;
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
 * Created by emi on 2017/2/15.
 */
@RestController
@RequestMapping("/api/resource")
public class PermissionController {

    Logger logger = LoggerFactory.getLogger(PermissionController.class);

    @Autowired
    private PermissionService permissionService;

    @RequiresRoles("super admin")
    @RequiresPermissions("resource:retrieve")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResourceListResponse list() {

        return new ResourceListResponse(permissionService.list2());
    }

    @RequiresRoles("super admin")
    @RequiresPermissions("resource:retrieve")
    @RequestMapping(value = "/{permissionId}/detail", method = RequestMethod.GET)
    public ResourceDetailResponse detail(@PathVariable("permissionId") long permissionId) {

        return new ResourceDetailResponse(permissionService.detail(permissionId));
    }


    @RequiresRoles("super admin")
    @RequiresPermissions("resource:update")
    @RequestMapping(value = "/{permissionId}/modify", method = RequestMethod.POST)
    public RestResponse modify(@CurrentUser User user, @PathVariable("permissionId") long permissionId, ResourceVisibleVo resourceVo) {

        permissionService.modify(user.getId(), permissionId, resourceVo);
        return new RestResponse();
    }


    @RequiresRoles("super admin")
    @RequiresPermissions("resource:create")
    @RequestMapping(value = "/{parentId}/sub", method = RequestMethod.POST)
    public RestResponse addSubNode(@CurrentUser User user, @PathVariable("parentId") long parentId, ResourceVisibleVo resourceVo) {

        permissionService.addSubNode(user.getId(), parentId, resourceVo);
        return new RestResponse();
    }


//    @RequestMapping(value = "/role/{roleId}", method = RequestMethod.GET)
//    public ResourceTreeResponse treeResource(@PathVariable("roleId") long roleId) {
//        return new ResourceTreeResponse(permissionService.treeList(roleId));
//    }


}
