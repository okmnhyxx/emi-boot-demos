package com.emi.shiro.service;

import com.emi.shiro.common.exceptions.CommonException;
import com.emi.shiro.configs.AdminProperties;
import com.emi.shiro.configs.ErrorCode;
import com.emi.shiro.domain.*;
import com.emi.shiro.dto.UserCreateResponse;
import com.emi.shiro.dto.UserListResponse;
import com.emi.shiro.dto.common.PagedRequest;
import com.emi.shiro.dto.common.RestResponse;
import com.emi.shiro.dto.common.vo.PageRespVo;
import com.emi.shiro.dto.vo.UserListVo;
import com.emi.shiro.repository.*;
import com.emi.shiro.repository.mapper.UserMapper;
import com.emi.shiro.service.utils.ServiceUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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


    public void ensureAdminInit() {
        if (0 == roleRepository.count()) {
            Role role = new Role(0L, adminProperties.getRole(), adminProperties.getDesc());

            List<Permission> permissionList = (List<Permission>) permissionRepository.findAll();
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
        PageInfo<UserListVo> userVoList = userMapper.searchList(username, locked);//        PageInfo pageInfo = new PageInfo<>(userVoList);

        return new UserListResponse(new PageRespVo(userVoList.getPageNum(), userVoList.getPageSize(), userVoList.getTotal()), userVoList.getList());//todo:PageInfo的参数对不对
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
}
