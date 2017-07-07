package com.emi.shiro.t15.service;

import com.emi.shiro.t15.common.enums.DomainType;
import com.emi.shiro.t15.common.enums.OperateType;
import com.emi.shiro.t15.common.enums.ViewType;
import com.emi.shiro.t15.domain.Permission;
import com.emi.shiro.t15.domain.User;
import com.emi.shiro.t15.dto.LoginMenuResponse;
import com.emi.shiro.t15.dto.vo.*;
import com.emi.shiro.t15.repository.PermissionRepository;
import com.emi.shiro.t15.repository.RolePermissionRepository;
import com.emi.shiro.t15.repository.UserRepository;
import com.emi.shiro.t15.repository.UserRoleRepository;
import com.emi.shiro.t15.service.utils.ServiceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private ServiceUtils serviceUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RolePermissionRepository rolePermissionRepository;


    Logger logger = LoggerFactory.getLogger(PermissionService.class);

    /**
     * 创建初始角色的初始权限
     */
    public void ensureResourceInit() {

        if ( !permissionRepository.exists(1L)) {
            List<Permission> permissionList = new ArrayList<>();
//            Permission permission = new Permission(0L, 0L, "0", ViewType.Root, "", "", "资源");
//            permissionList.add(permission);
            Permission resourceRoot = new Permission("mainManager.resource", "/view/resource.html", "resource:*","资源管理");//todo:页面可以访问否
            permissionList.add(resourceRoot);
            Permission roleRoot = new Permission("mainManager.role", "/view/role.html", "role:*", "角色管理");
            permissionList.add(roleRoot);
            Permission userRoot = new Permission("mainManager.user", "/view/user.html", "user:*", "用户管理");
            permissionList.add(userRoot);
            Permission organizationRoot = new Permission("mainManager.organization", "/view/organization.html","organization:*", "组织管理");
            permissionList.add(organizationRoot);
            permissionRepository.save(permissionList);

            permissionList.clear();
            permissionList.addAll(this.generate4TypeCRUD(resourceRoot, permissionList));
            permissionList.addAll(this.generate4TypeCRUD(roleRoot, permissionList));
            permissionList.addAll(this.generate4TypeCRUD(userRoot, permissionList));
            permissionList.addAll(this.generate4TypeCRUD(organizationRoot, permissionList));
            permissionRepository.save(permissionList);
        }
    }

    private List<Permission> generate4TypeCRUD(Permission resourceRoot, List<Permission> permissionList) {
        Permission permission;

        permission = new Permission(OperateType.Create, resourceRoot.getId(), resourceRoot.getPermissionCode(), resourceRoot.getPermissionName());
        permissionList.add(permission);
        permission = new Permission(OperateType.Retrieve, resourceRoot.getId(), resourceRoot.getPermissionCode(), resourceRoot.getPermissionName());
        permissionList.add(permission);
        permission = new Permission(OperateType.Update, resourceRoot.getId(), resourceRoot.getPermissionCode(), resourceRoot.getPermissionName());
        permissionList.add(permission);
        permission = new Permission(OperateType.Delete, resourceRoot.getId(), resourceRoot.getPermissionCode(), resourceRoot.getPermissionName());
        permissionList.add(permission);

        return permissionList;
    }


    public Set<String> fetchPermissionCodesByRoles(Set<Long> roleIds) {

        List<Permission> permissionList = this.fetchPermissionsByRoleIds(roleIds);

        Set<String> permNames = new HashSet<>();
        for (Permission p : permissionList) {
            permNames.add(p.getPermissionCode());
        }
        return permNames;
    }


    public LoginMenuResponse fetchPermissionByUser(String username) {
        User user = userRepository.findByUsername(username);
        if (null == user) {
            return null;
        }
        Set<Long> roleIds = userRoleRepository.findRoleIdByUserId(user.getId());
        List<Permission> permissions = this.fetchPermissionsByRoleIds(roleIds);

        return this.generateMenusByPermissions(permissions, new UserVo(user.getId(), username));
    }



    public List<ResourceVo> list() {

        Permission per;
        ResourceVo currentResourceVo;
        ResourceVo tmpResourceVo;
        Long id;
        Long parentId;

        List<Permission> permissionList = permissionRepository.findByDeleted(false);
        List<ResourceVo> resourceVoList = null;
//        List<Long> rootIdList = new ArrayList<>();
        Map<Long, ResourceVo> menuMap = new LinkedHashMap<>();//所有菜单  包括跟节点
        List<ResourceVo> rootList = new ArrayList<>();
        for (int i = 0; i < permissionList.size(); i ++) {
            per = permissionList.get(i);
            id = per.getId();
            parentId = per.getParentId();
            currentResourceVo = new ResourceVo(id, parentId,per.getViewType(), per.getStateUrl(), per.getTemplateUrl(), per.getPermissionCode(), per.getPermissionName());

            if (ViewType.Button.getValue() == per.getViewType()) {
                if (menuMap.containsKey(parentId)) {
                    tmpResourceVo =  menuMap.get(parentId);
                    tmpResourceVo.getSubList().add(currentResourceVo);
                } else {
                    tmpResourceVo = new ResourceVo();
                    tmpResourceVo.getSubList().add(currentResourceVo);
                    menuMap.put(parentId, tmpResourceVo);
                }

            } else {//菜单类型的
                if (menuMap.containsKey(id)) {//添加该节点前已添加了子节点
                    tmpResourceVo = menuMap.get(id);
                    currentResourceVo.setSubList(tmpResourceVo.getSubList());
                    menuMap.replace(id, currentResourceVo);
                } else {
                    menuMap.put(id, currentResourceVo);
                }

                if (parentId == ViewType.Root.getValue()) {
//                    rootIdList.add(id);
                    rootList.add(currentResourceVo);
                }
            }
        }

//        resourceVoList = generateRootResource(rootIdList, menuMap);//根节点
        this.loopEnsureResource(rootList, menuMap, null);
//        resourceVoList = generateRootResource(rootIdList, menuMap);//赋值后的根节点

        return rootList;
    }

    public List<ResourceVo> list2() {

        Permission per;
        ResourceVo currentResourceVo;
        ResourceVo tmpResourceVo;
        Long id;
        Long parentId;

        List<Permission> permissionList = permissionRepository.findByDeletedOrderById(false);
        List<ResourceVo> resourceVoList = null;
        List<Long> rootIdList = new ArrayList<>();
        Map<Long, ResourceVo> menuMap = new LinkedHashMap<>();//所有菜单  包括跟节点
        List<ResourceVo> rootList = new ArrayList<>();
        for (int i = 0; i < permissionList.size(); i ++) {
            per = permissionList.get(i);
            id = per.getId();
            parentId = per.getParentId();
            currentResourceVo = new ResourceVo(id, parentId,per.getViewType(), per.getStateUrl(), per.getTemplateUrl(), per.getPermissionCode(), per.getPermissionName());

            if( id > 21) {
                logger.info(" -------" + id);
            }
            if (menuMap.containsKey(parentId)) {
                menuMap.get(parentId).getSubList().add(currentResourceVo);
            } else {
                tmpResourceVo = new ResourceVo();
                tmpResourceVo.getSubList().add(currentResourceVo);
                menuMap.put(parentId, tmpResourceVo);
            }


            if (ViewType.Button.getValue() != per.getViewType()) {//菜单类型的
                if (menuMap.containsKey(id)) {//添加该节点前已添加了子节点
                    tmpResourceVo = menuMap.get(id);
                    currentResourceVo.setSubList(tmpResourceVo.getSubList());
                    menuMap.replace(id, currentResourceVo);
                } else {
                    menuMap.put(id, currentResourceVo);
                }

                if (parentId == ViewType.Root.getValue()) {
                    rootIdList.add(id);
                }
            }
        }

        resourceVoList = generateRootResource(rootIdList, menuMap);//根节点
//        this.loopEnsureResource(rootList, menuMap, null);
//        resourceVoList = generateRootResource(rootIdList, menuMap);//赋值后的根节点

        return resourceVoList;
    }




    public ResourceVisibleVo detail(long permissionId) {

        Permission permission = permissionRepository.findOne(permissionId);
        serviceUtils.checkNull(DomainType.Permission, permission, permissionId);

        return new ResourceVisibleVo(permission.getViewType(), permission.getStateUrl(), permission.getTemplateUrl(), permission.getPermissionCode(), permission.getPermissionName());
    }

    public void modify(long updaterId, long permissionId, ResourceVisibleVo resourceVo) {

        Permission permission = permissionRepository.findOne(permissionId);
        serviceUtils.checkNull(DomainType.Permission, permission, permissionId);

        permission.modify(updaterId, resourceVo.getViewType(), resourceVo.getStateUrl(), resourceVo.getTemplateUrl(), resourceVo.getPermissionCode(), resourceVo.getPermissionName());
        permissionRepository.save(permission);
     }


    public void addSubNode(long creatorId, long parentId, ResourceVisibleVo resourceVo) {
        Permission parentPerm = permissionRepository.findOne(parentId);
        serviceUtils.checkNull(DomainType.Permission, parentPerm, parentId);
        parentPerm.checkNotBtn();

        Permission permission = new Permission(creatorId, parentId, parentPerm.getAncestorsId() + "/" + parentId, resourceVo.getViewType(), resourceVo.getStateUrl(), resourceVo.getTemplateUrl(), resourceVo.getPermissionCode(), resourceVo.getPermissionName());
        permissionRepository.save(permission);
    }



    private void loopEnsureResource(List<ResourceVo> resourceVoList, Map<Long, ResourceVo> menuMap, ResourceVo parentResourceVo) {
        if (resourceVoList.size() == 0) {
            return;
        }
        Long id;
        ResourceVo tmpResourceVo;

        for (int i = 0; i < resourceVoList.size(); i ++) {
            id = resourceVoList.get(i).getId();
            tmpResourceVo = menuMap.get(id);
            if(null == tmpResourceVo) {
                continue;
            }

//            for (Long j = 0L; i < menuMap.size(); j ++) {
//                tmpResourceVo2 = menuMap.get(j);
//                if (tmpResourceVo2.getParentId() == id) {
//                    resourceVoList.add(tmpResourceVo2);
//                }
//            }
              for(ResourceVo r : menuMap.values()) {
                  if (r.getParentId() == id) {
                      tmpResourceVo.getSubList().add(r);
                  }
              }

            if (0 != tmpResourceVo.getSubList().size()) {
                if (null != parentResourceVo) {
                    parentResourceVo.getSubList().get(i).setSubList(tmpResourceVo.getSubList());
                    loopEnsureResource(tmpResourceVo.getSubList(), menuMap, parentResourceVo.getSubList().get(i));
                } else {
                    loopEnsureResource(tmpResourceVo.getSubList(), menuMap, menuMap.get(tmpResourceVo.getId()));
                }
            }
        }
    }


    public List<ResourceTreeVo> treeList(long roleId) {

        Set<Long> permissionIdList = rolePermissionRepository.findDistinctPermissionIdByRoleIdsIn(new HashSet<Long>() {{
            add(roleId);
        }});
        List<ResourceTreeVo> resourceVoList = permissionRepository.findPermissionTree();
        for (ResourceTreeVo r: resourceVoList) {
            if (permissionIdList.contains(r.getId())) {
                r.setChecked(true);
            }
        }
        return resourceVoList;
    }



    private List<ResourceVo> generateRootResource(List<Long> rootIdList, Map<Long, ResourceVo> menuMap) {

        List<ResourceVo> resourceVoList = new ArrayList<>();
        for (int i = 0; i < rootIdList.size(); i ++) {
            resourceVoList.add(menuMap.get(rootIdList.get(i)));
            menuMap.remove(rootIdList.get(i));
        }
        return resourceVoList;
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


    private LoginMenuResponse generateMenusByPermissions(List<Permission> permissions, UserVo userVo) {//todo: 由原来的只返回 List<MenuVo> 变为 在多返回 List<PermissionVo>

        MenuVo menuVo;
        List<String> permissionList = new ArrayList<>();//用户拥有的权限
        List<MenuVo> menuVoList;
        Map<Long, MenuVo> menuMap = new HashMap<>();//用来存储根菜单的
        long permissionId = 0;
        long parentId = 0;
        for (Permission p : permissions) {
            permissionId = p.getId();
            parentId = p.getParentId();
            permissionList.add(p.getPermissionCode());
            menuVo = new MenuVo(p.getStateUrl(), p.getPermissionName());

            if (ViewType.VirtueMenu.getValue() == p.getViewType()) {//当前菜单是主菜单列，即菜单是无页面的，菜单下是子菜单
                if (menuMap.containsKey(permissionId)) {//创建该主菜单之前 已经添加了子菜单  排序情况慎重
                    menuVo.setMenuVoList(menuMap.get(permissionId).getMenuVoList());
                    menuMap.replace(permissionId, menuVo);
                } else {
                    if (parentId == ViewType.Root.getValue()) {//该主菜单是根菜单， 一般都是这样
                        menuMap.put(permissionId, menuVo);
                    } else {// 该主菜单 不是根菜单
                        logger.warn(String.format(" --- 主菜单[%d]不是根菜单，父菜单为[%d], 请核实 --- ", permissionId, parentId));
                        menuVoList = new ArrayList<>();
                        this.loopParentResource(parentId, p, menuVoList, menuMap);
                    }
                }
            } else if (ViewType.Menu.getValue() == p.getViewType()) {//当前菜单是子菜单，即菜单是有页面的
                if (parentId == ViewType.Root.getValue()) {//该子菜单是根菜单
                    menuMap.put(p.getId(), menuVo);
                } else {
                    if (menuMap.containsKey(parentId)) {//放置子菜单前  已经放置了主菜单
                        menuMap.get(parentId).getMenuVoList().add(menuVo);
                    } else {//  排序情况慎重
                        menuVoList = new ArrayList<>();
                        menuVoList.add(menuVo);
                        menuMap.put(parentId, new MenuVo(menuVoList));
                    }
                }
            } else {//当前时按钮， ！！需确保按钮所在的菜单在不在
                menuVoList = new ArrayList<>();
                this.loopParentResource(parentId, p, menuVoList, menuMap);
            }

            if (p.getViewType() != ViewType.Button.getValue()) {
                this.loopSonResource(p, menuVo);
            }

        }
        List<MenuVo> menuVoList2 = new ArrayList<>(menuMap.values());//collection转list
        return new LoginMenuResponse(userVo, menuVoList2, permissionList);
    }

    private void loopSonResource(Permission p, MenuVo menuVo) {
        List<Permission> permissionList = permissionRepository.findByParentIdAndViewTypeNotAndDeleted(p.getId(), ViewType.Button.getValue(), false);
        if (0 == permissionList.size()) {
            return;
        }
        MenuVo sonMenuVo;
        for (Permission s : permissionList) {
            sonMenuVo = new MenuVo(s.getStateUrl(), s.getPermissionName());
            menuVo.getMenuVoList().add(sonMenuVo);
            loopSonResource(s, sonMenuVo);
        }
    }

    private void loopParentResource(long parentId, Permission permission, List<MenuVo> menuVoList, Map<Long, MenuVo> menuMap) {

        MenuVo menuVo = new MenuVo(permission.getStateUrl(), permission.getPermissionName(), menuVoList);

        if ( !menuMap.containsKey(parentId)) {//元素的父菜单不存在

            permission = permissionRepository.findOne(parentId);
            menuVoList= new ArrayList<>();
            menuVoList.add(menuVo);

            if (permission.getParentId() == ViewType.Root.getValue()) {
                menuMap.put(permission.getId(), new MenuVo(permission.getStateUrl(), permission.getPermissionName(), menuVoList));
            } else {
                loopParentResource(permission.getParentId(), permission, menuVoList, menuMap);
            }
        } else {//元素的父菜单在
            menuMap.get(parentId).getMenuVoList().add(menuVo);
        }
    }


//    private LoginMenuResponse generateMenusByPermissions(List<Permission> permissions, UserVo userVo) {//todo: 由原来的只返回 List<MenuVo> 变为 在多返回 List<PermissionVo>
//
//        MenuVo menuVo;//主菜单及 其下的子菜单
//        MenuItemVo menuItemVo;//单菜单
//        List<MenuItemVo> itemVoList;//单子菜单
//        List<String> permissionList = new ArrayList<>();//用户拥有的权限
//        Map<Long, MenuVo> menuMap = new HashMap<>();
//        long permissionId = 0;
//        long parentId = 0;
//        for (Permission p : permissions) {
//            permissionId = p.getId();
//            parentId = p.getParentId();
//            permissionList.add(p.getPermissionCode());
//
////            if (ViewType.Menu.getValue() == p.getViewType() || ViewType.VirtueMenu.getValue() == p.getViewType()) {
////                menuItemVo = new MenuItemVo(p.getMenuUrl(), p.getStateUrl(), p.getPermissionCode());
////                if (parentId == ViewType.Root.getValue()) {
////                    if (menuMap.containsKey(permissionId)) {//创建该主菜单之前已经添加了子菜单  排序情况慎重
////                        menuVo = menuMap.get(permissionId);
////                        menuVo.setMainItemVo(menuItemVo);
////                    } else {
////                        menuMap.put(permissionId, new MenuVo(menuItemVo, new ArrayList<>()));
////                    }
////                } else {
////                    if (menuMap.containsKey(parentId)) {//放置子菜单前  已经放置了主菜单
////                        menuVo = menuMap.get(parentId);
////                        menuVo.getItemVoList().add(menuItemVo);
////                    } else {//  排序情况慎重
////                        itemVoList = new ArrayList<>();
////                        itemVoList.add(menuItemVo);
////                        menuMap.put(parentId, new MenuVo(null, itemVoList));
////                    }
////                }
////            }
//
//            if (ViewType.VirtueMenu.getValue() == p.getViewType()) {//当前菜单是主菜单列，即菜单是无页面的，菜单下是子菜单，子菜单才有页面
//                menuItemVo = new MenuItemVo(p.getMenuUrl(), p.getStateUrl(), p.getPermissionCode());
//                if (menuMap.containsKey(permissionId)) {//创建该主菜单之前已经添加了子菜单  排序情况慎重
//                    menuVo = menuMap.get(permissionId);
//                    menuVo.setMainItemVo(menuItemVo);
//                } else {
//                    menuMap.put(permissionId, new MenuVo(menuItemVo, new ArrayList<>()));
//                }
//            } else if (ViewType.Menu.getValue() == p.getViewType()) {//当前菜单是子菜单，即菜单是有页面的
//                menuItemVo = new MenuItemVo(p.getMenuUrl(), p.getStateUrl(), p.getPermissionCode());
//                if (menuMap.containsKey(parentId)) {//放置子菜单前  已经放置了主菜单
//                    menuVo = menuMap.get(parentId);
//                    menuVo.getItemVoList().add(menuItemVo);
//                } else {//  排序情况慎重
//                    itemVoList = new ArrayList<>();
//                    itemVoList.add(menuItemVo);
//                    menuMap.put(parentId, new MenuVo(null, itemVoList));
//                }
//            } else {//当前时按钮， ！！需确保按钮所在的菜单在不在
//                if (menuMap.containsKey(p.getParentId())) {
//
//                }
//            }
//
//        }
//        List<MenuVo> menuVoList = new ArrayList<>(menuMap.values());//collection转list
//        return new LoginMenuResponse(userVo, menuVoList, permissionList);
//    }

}
