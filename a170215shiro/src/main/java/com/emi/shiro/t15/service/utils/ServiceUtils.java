package com.emi.shiro.t15.service.utils;

import com.emi.shiro.t15.common.enums.DomainType;
import com.emi.shiro.t15.common.exceptions.RecordNotFoundException;
import com.emi.shiro.t15.domain.BaseDomain;
import com.emi.shiro.t15.domain.User;
import com.emi.shiro.t15.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;

/**
 * Created by emi on 2017/2/17.
 */
@Component
public class ServiceUtils {

    public static SimpleDateFormat sdf_m = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Autowired
    private UserRepository userRepository;

    public User fetchUserByName(String username) {
        if (StringUtils.isEmpty(username)) {
            return null;
        }
        return userRepository.findByUsername(username);
    }

    public static void checkNull(DomainType domainType, BaseDomain domain, Object domainId) {
        if (null == domain) {
            throw new RecordNotFoundException(domainType.getDesc(), domainId);
        }
    }

    public User checkUserExist(long userId) {
        User user = userRepository.findOne(userId);
        checkNull(DomainType.User, user, userId + "");
        return user;
    }
    public User checkUserExistState(long userId) {
        User user = this.checkUserExist(userId);
        user.checkLocked();
        return user;
    }


}
