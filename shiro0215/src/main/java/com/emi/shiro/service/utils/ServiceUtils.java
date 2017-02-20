package com.emi.shiro.service.utils;

import com.emi.shiro.common.enums.DomainType;
import com.emi.shiro.common.exceptions.RecordNotFoundException;
import com.emi.shiro.domain.BaseDomain;
import com.emi.shiro.domain.User;
import com.emi.shiro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Created by emi on 2017/2/17.
 */
@Component
public class ServiceUtils {

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
        user.checkLocked();
        return user;
    }
    public User checkUserExistState(long userId) {
        User user = this.checkUserExist(userId);
        user.checkLocked();
        return user;
    }


}
