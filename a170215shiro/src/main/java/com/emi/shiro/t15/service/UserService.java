package com.emi.shiro.t15.service;

import com.emi.shiro.t15.common.exceptions.CommonException;
import com.emi.shiro.t15.configs.AdminProperties;
import com.emi.shiro.t15.configs.ErrorCode;
import com.emi.shiro.t15.domain.*;
import com.emi.shiro.t15.dto.UserDetailResponse;
import com.emi.shiro.t15.dto.UserListResponse;
import com.emi.shiro.t15.dto.common.PagedRequest;
import com.emi.shiro.t15.dto.common.RestResponse;
import com.emi.shiro.t15.dto.common.vo.PageRespVo;
import com.emi.shiro.t15.dto.vo.OrganizationTreeVo;
import com.emi.shiro.t15.dto.vo.RoleBaseVo;
import com.emi.shiro.t15.dto.vo.UserListVo;
import com.emi.shiro.t15.repository.*;
import com.emi.shiro.t15.repository.mapper.UserMapper;
import com.emi.shiro.t15.service.utils.ServiceUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by emi on 2017/2/15.
 */
@Service
@Transactional
public class UserService {

    @Autowired
    private ServiceUtils serviceUtils;

    @Autowired
    private AdminProperties adminProperties;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    @Autowired
    private OrganizationRepository organizationRepository;


    public void ensureAdminInit() {
        if (0 == roleRepository.count()) {
            Role role = new Role(0L, adminProperties.getRole(), adminProperties.getDesc());
            roleRepository.save(role);

            List<Permission> permissionList =permissionRepository.findByParentIdAndDeleted(0L, false);
            this.giveRolePermissions(role, permissionList);//给初始角色赋权限

            User user = userRepository.findByUsername(adminProperties.getName());
            if (null == user) {
                user = new User(0L, 0L, adminProperties.getName(), adminProperties.getPass());
                userRepository.save(user);//创建初始账号
            }

            UserRole userRole = new UserRole(user.getId(), role.getId());
            userRoleRepository.save(userRole);//给初始账号授角色
        }
    }

    public UserListResponse search(String username, Boolean locked, PagedRequest pagedRequest) {

        PageHelper.startPage(pagedRequest.getCurrentPage(), pagedRequest.getPageSize());
        List<UserListVo> userVoList = userMapper.searchList(username, locked);
        PageInfo<UserListVo> pageInfo = new PageInfo<>(userVoList);


        Map<Long, RoleBaseVo> roleMap = this.generateRoleBaseVo();
        Set<Long> roleIdList;
        List<RoleBaseVo> roleVoList;
        for (UserListVo u : userVoList) {
            roleIdList = userRoleRepository.findRoleIdByUserId(u.getId());
            roleVoList = new ArrayList<>();
            for (Long id : roleIdList) {
                roleVoList.add(roleMap.get(id));
            }
            u.setRoleVoList(roleVoList);
        }

        return new UserListResponse(new PageRespVo(pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getTotal()), userVoList);//todo:PageInfo的参数对不对
    }


    public UserDetailResponse detail(long userId) {
        User user = serviceUtils.checkUserExistState(userId);

//        List<OrganizationTreeVo> organizationBaseVoList = organizationRepository.findOrganizationTree();
        return null;
    }



    private Map<Long, RoleBaseVo> generateRoleBaseVo() {
        Map<Long, RoleBaseVo> roleMap = new HashMap<>();
        List<RoleBaseVo> roleList = roleRepository.findAllExceptDisable();
        for (RoleBaseVo r : roleList) {
            roleMap.put(r.getId(), new RoleBaseVo(r.getId(), r.getRoleName()));
        }
        return roleMap;
    }


    public RestResponse modify(long organizationId, long updaterId, long userId, String username, String password) {

        User user = serviceUtils.checkUserExistState(userId);
        int count = userRepository.countByUsername(username);
        if (count > 0) {
            throw new CommonException(ErrorCode.COMMON_ERROR, "用户名已存在", "用户名[" + username + "]已存在");
        }
        //todo: 暂时忽略 修改组织的逻辑
        user.modify(organizationId, updaterId, username, password);
        userRepository.save(user);
        return new RestResponse();
    }

    public RestResponse lock(long updaterId, long userId, boolean lock) {

        User user = serviceUtils.checkUserExist(userId);
        if (lock != user.isLocked()) {
            user.lock(updaterId, lock);
            userRepository.save(user);
        }
        return new RestResponse();
    }




    private void giveRolePermissions(Role role, List<Permission> permissionList) {

        if (null == permissionList || 0 == permissionList.size()) {
            return;
        }

        List<RolePermission> rolePermissions = new ArrayList<>();
        for (Permission p : permissionList) {
            rolePermissions.add(new RolePermission(role.getId(), p.getId()));
        }
        rolePermissionRepository.save(rolePermissions);
    }


    public RestResponse create(long organizationId, long creatorId, String username, String password) {

        User user = new User(organizationId, creatorId, username, password);
        userRepository.save(user);
        return new RestResponse();
    }


}
