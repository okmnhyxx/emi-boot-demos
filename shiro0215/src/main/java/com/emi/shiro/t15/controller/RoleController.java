package com.emi.shiro.t15.controller;

import com.emi.shiro.t15.component.bind.annotation.CurrentUser;
import com.emi.shiro.t15.domain.User;
import com.emi.shiro.t15.dto.RoleCreateModifyRequest;
import com.emi.shiro.t15.dto.RoleDetailResponse;
import com.emi.shiro.t15.dto.RoleListResponse;
import com.emi.shiro.t15.dto.common.RestResponse;
import com.emi.shiro.t15.service.PermissionService;
import com.emi.shiro.t15.service.RoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by emi on 2017/3/3.
 */
@RestController
@RequestMapping("/api/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @RequiresRoles("super admin")
    @RequiresPermissions("role:retrieve")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public RoleListResponse list() {

        return new RoleListResponse(roleService.roleList());
    }

    @RequiresRoles("super admin")
    @RequiresPermissions("role:retrieve")
    @RequestMapping(value = "/{roleId}/detail", method = RequestMethod.GET)
    public RoleDetailResponse detail(@PathVariable("roleId") long roleId) {
        return roleService.roleDetail(roleId);
    }

    @RequiresRoles("super admin")
    @RequiresPermissions("role:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public RestResponse create(@CurrentUser User user, RoleCreateModifyRequest createModifyRequest) {
        roleService.createRole(user.getId(), createModifyRequest);
        return new RestResponse();
    }


    @RequiresRoles("super admin")
    @RequiresPermissions("role:update")
    @RequestMapping(value = "/{roleId}/modify", method = RequestMethod.POST)
    public RestResponse modify(@CurrentUser User user, @PathVariable("roleId")Long roleId, RoleCreateModifyRequest createModifyRequest) {
        roleService.modifyRole(user.getId(), roleId, createModifyRequest);
        return new RestResponse();
    }


}
