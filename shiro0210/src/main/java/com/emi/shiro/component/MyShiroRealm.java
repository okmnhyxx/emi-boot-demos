package com.emi.shiro.component;

import com.emi.shiro.domain.Role;
import com.emi.shiro.domain.User;
import com.emi.shiro.repository.UserRepository;
import com.google.gson.Gson;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by emi on 2017/2/10.
 */
public class MyShiroRealm extends AuthorizingRealm {

    private static final Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);

    private static final Gson gson = new Gson();

    @Autowired
    private UserRepository userRepository;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        logger.info("##################执行Shiro权限认证##################");
        String username = (String)getAvailablePrincipal(principals);//(String) principalCollection.getPrimaryPrincipal();
        User user = userRepository.findByUsername(username);//Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        if (user != null) {
            List<Role> roleList = user.getRoleList();
            if (null != roleList && 0 != roleList.size()) {
                SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();//权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
                for (Role role : roleList) {
                    info.addRole(role.getRolename());
                    info.addStringPermissions(role.getPermissionsName());
                }
                return info;
            }
        }

        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        logger.info("当前subject获取到的token：" + gson.toJson(token));

        User user = userRepository.findByUsername(token.getUsername());
        if (null != user) {
            return new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), getName());
        } else {
            throw new UnknownAccountException();
        }
    }


}
