package com.emi.shiro.t15.service;

import com.emi.shiro.t15.common.enums.DomainType;
import com.emi.shiro.t15.common.exceptions.CommonException;
import com.emi.shiro.t15.configs.ErrorCode;
import com.emi.shiro.t15.dto.RoleCreateModifyRequest;
import com.emi.shiro.t15.domain.Role;
import com.emi.shiro.t15.domain.RolePermission;
import com.emi.shiro.t15.domain.User;
import com.emi.shiro.t15.dto.RoleDetailResponse;
import com.emi.shiro.t15.dto.vo.ResourceBaseVo;
import com.emi.shiro.t15.dto.vo.ResourceTreeVo;
import com.emi.shiro.t15.dto.vo.RoleDetailVo;
import com.emi.shiro.t15.dto.vo.RoleListVo;
import com.emi.shiro.t15.repository.PermissionRepository;
import com.emi.shiro.t15.repository.RolePermissionRepository;
import com.emi.shiro.t15.repository.RoleRepository;
import com.emi.shiro.t15.repository.UserRoleRepository;
import com.emi.shiro.t15.repository.mapper.PermissionMapper;
import com.emi.shiro.t15.service.utils.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.emi.shiro.t15.vo.RoleVo;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by emi on 2017/2/17.
 */
@Service
@Transactional
public class RoleService {

    @Autowired
    private ServiceUtils serviceUtils;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    public RoleVo fetchRoleByUsername(String username) {
        User user = serviceUtils.fetchUserByName(username);
        if (null != user) {
            Set<Long> roleIds = userRoleRepository.findRoleIdByUserId(user.getId());
            Set<String> roleNames = roleRepository.findNameAll(roleIds);
            return new RoleVo(roleIds, roleNames);
        }
        return null;
    }

    public List<RoleListVo> roleList() {

        List<RoleListVo> roleVoList = roleRepository.findRoleList();
//        List<Long> roleIdList = new ArrayList<>();
        List<ResourceBaseVo> resourceVoList;
        Long roleId;
        for (RoleListVo r : roleVoList) {
            roleId = r.getId();
//            roleIdList.add(roleId);
            resourceVoList = permissionMapper.findRolePermission(roleId);
            r.setResourceVoList(resourceVoList);
        }

        return roleVoList;
    }

    public void createRole(long updaterId, RoleCreateModifyRequest createModifyRequest) {

        String perIds = createModifyRequest.getResourceIds();
        Role role = new Role(updaterId, createModifyRequest.getRoleName(), createModifyRequest.getRoleDesc());
        roleRepository.save(role);

        if (StringUtils.isEmpty(perIds)) {
            return;
        }

        Long[] permissionIdsLong = this.generateLongPermissionIds(perIds);
        int countPermission = permissionRepository.countByIdInAndDeleted(permissionIdsLong, false);
        if (countPermission != permissionIdsLong.length) {
            throw new CommonException(ErrorCode.COMMON_ERROR, "传入资源信息异常", String.format("传入资源[%s]，但数据库只找到%d条", perIds, countPermission));
        }

        List<RolePermission> rolePermissionList = new ArrayList<>();
        for (int i = 0; i < countPermission; i ++) {
            rolePermissionList.add(new RolePermission(role.getId(), permissionIdsLong[i]));
        }
        rolePermissionRepository.save(rolePermissionList);

    }


    public void modifyRole(long updaterId, Long roleId, RoleCreateModifyRequest createModifyRequest) {

        String perIds = createModifyRequest.getResourceIds();
//        Role role = new Role(updaterId, createModifyRequest.getRoleName(), createModifyRequest.getRoleDesc());
//        roleRepository.save(role);
        Role role = roleRepository.findOne(roleId);
        role.update(updaterId, createModifyRequest.getRoleName(), createModifyRequest.getRoleDesc());
        serviceUtils.checkNull(DomainType.Role, role, roleId);


        Long[] permissionIdsLong = this.generateLongPermissionIds(perIds);
        if (null == permissionIdsLong || 0 == permissionIdsLong.length) {
            return;
        }

        List<RolePermission> rolePermissionList = new ArrayList<>();
        for (int i = 0; i < permissionIdsLong.length; i ++) {
            rolePermissionList.add(new RolePermission(role.getId(), permissionIdsLong[i]));
        }
        List<RolePermission> oldRolePermList = rolePermissionRepository.findByRoleId(roleId);//todo:将旧的全部删除，新的全部创建， 后期可以考虑增量新增删除

        rolePermissionRepository.save(rolePermissionList);
        rolePermissionRepository.delete(oldRolePermList);
    }



    private Long[] generateLongPermissionIds(String perIds) {
        if (StringUtils.isEmpty(perIds)) {
            return null;
        }
        String[] permissionIdsStr = perIds.split(";");
        Long[] permissionIdsLong = new Long[permissionIdsStr.length];
        for (int i = 0; i < permissionIdsStr.length; i ++) {
            permissionIdsLong[i] = Long.parseLong(permissionIdsStr[i]);
        }
        return permissionIdsLong;
    }


    public RoleDetailResponse roleDetail(long roleId) {

        Role role = roleRepository.findOne(roleId);
        serviceUtils.checkNull(DomainType.Role, role, roleId);
        role.checkEnable();

        Set<Long> permissionIdList = rolePermissionRepository.findDistinctPermissionIdByRoleIdsIn(new HashSet<Long>() {{
            add(roleId);
        }});
        List<ResourceTreeVo> resourceVoList = permissionRepository.findPermissionTree();
        for (ResourceTreeVo r: resourceVoList) {
            if (permissionIdList.contains(r.getId())) {
                r.setChecked(true);
            }
        }
        return new RoleDetailResponse(new RoleDetailVo(role.getId(), role.getRoleName(), role.getRoleDesc()), resourceVoList);
    }
}
