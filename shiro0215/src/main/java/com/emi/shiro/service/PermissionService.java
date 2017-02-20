package com.emi.shiro.service;

import com.emi.shiro.common.enums.ViewType;
import com.emi.shiro.domain.Permission;
import com.emi.shiro.domain.User;
import com.emi.shiro.dto.common.RestResponse;
import com.emi.shiro.dto.vo.MenuItemVo;
import com.emi.shiro.dto.vo.MenuVo;
import com.emi.shiro.repository.PermissionRepository;
import com.emi.shiro.repository.RolePermissionRepository;
import com.emi.shiro.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by emi on 2017/2/15.
 */
@Service
@Transactional
public class PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    public RestResponse testSelfReference() {

//        permissionRepository.save(new Permission(1L, "0", ViewType.Menu, "/api/test", "测试", false));
        return new RestResponse();
    }

    /**
     * 创建初始角色的初始权限
     */
    public void ensureResourceInit() {

        if ( !permissionRepository.exists(1L)) {
            List<Permission> permissionList = new ArrayList<>();
//            Permission permission = new Permission(0L, 0L, "0", ViewType.Root, "", "", "资源");
//            permissionList.add(permission);
            Permission permission = new Permission("/view/resource.html", "/resource:*","资源管理");//todo:页面可以访问否
            permissionList.add(permission);
            permission = new Permission("/view/role.html", "/role:*", "角色管理");
            permissionList.add(permission);
            permission = new Permission("/view/user.html", "/user:*", "用户管理");
            permissionList.add(permission);
            permission = new Permission("/view/organization.html", "/organization:*", "组织管理");
            permissionList.add(permission);
            permissionRepository.save(permissionList);
        }
    }


    public Set<String> fetchPermissionNamesByRoles(Set<Long> roleIds) {

        List<Permission> permissionList = this.fetchPermissionsByRoleIds(roleIds);

        Set<String> permNames = new HashSet<>();
        for (Permission p : permissionList) {
            permNames.add(p.getPermissionName());
        }
        return permNames;
    }


    public List<MenuVo> fetchPermissionByUser(User user) {
        if (null == user) {
            return null;
        }
        Set<Long> roleIds = userRoleRepository.findByUserId(user.getId());
        List<Permission> permissions = this.fetchPermissionsByRoleIds(roleIds);

        return this.generateMenusByPermissions(permissions);
    }


    private List<Permission> fetchPermissionsByRoleIds(Set<Long> roleIds) {
        if (null == roleIds || 0 == roleIds.size()) {
            return null;
        }
        Set<Long> permissionIds = rolePermissionRepository.findDistinctPermissionIdByRoleIdsIn(roleIds);
        if (null == permissionIds || 0 == permissionIds.size()) {
            return null;
        }

        return permissionRepository.findDistinctIdByIdInAndDeleted(permissionIds, false);
    }

    private List<MenuVo> generateMenusByPermissions(List<Permission> permissions) {

        MenuVo menuVo = null;//主菜单及 其下的子菜单
        MenuItemVo menuItemVo = null;//单菜单
        List<MenuItemVo> itemVoList;//单子菜单
        Map<Long, MenuVo> menuMap = new HashMap<>();
        long permissionId = 0;
        long parentId = 0;
        for (Permission p : permissions) {
            permissionId = p.getId();
            parentId = p.getParentId();
            menuItemVo = new MenuItemVo(p.getMenuUrl(), p.getPermissionDesc());
            if (parentId == ViewType.Root.getValue()) {//当前菜单是主菜单列
                if (menuMap.containsKey(permissionId)) {//创建该主菜单之前已经添加了子菜单  排序情况慎重
                    menuVo = menuMap.get(permissionId);
                    menuVo.setMainItemVo(menuItemVo);
                } else {
                    menuMap.put(permissionId, new MenuVo(menuItemVo, new ArrayList<MenuItemVo>()));
                }
            } else {//当前菜单是子菜单
                if (menuMap.containsKey(parentId)) {//放置子菜单前  已经放置了主菜单
                    menuVo = menuMap.get(parentId);
                    menuVo.getItemVoList().add(menuItemVo);
                } else {//  排序情况慎重
                    itemVoList = new ArrayList<>();
                    itemVoList.add(menuItemVo);
                    menuMap.put(parentId, new MenuVo(null, itemVoList));
                }
            }
        }

        return new ArrayList<>(menuMap.values());//collection转list
    }

}
