package com.emi.shiro.service;

import com.emi.shiro.domain.User;
import com.emi.shiro.repository.RoleRepository;
import com.emi.shiro.repository.UserRoleRepository;
import com.emi.shiro.service.utils.ServiceUtils;
import com.emi.shiro.vo.RoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public RoleVo fetchRoleByUsername(String username) {
        User user = serviceUtils.fetchUserByName(username);
        if (null != user) {
            Set<Long> roleIds = userRoleRepository.findByUserId(user.getId());
            Set<String> roleNames = roleRepository.findNameAll(roleIds);
            return new RoleVo(roleIds, roleNames);
        }
        return null;
    }
}
