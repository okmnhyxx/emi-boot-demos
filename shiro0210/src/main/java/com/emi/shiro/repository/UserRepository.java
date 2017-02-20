package com.emi.shiro.repository;

import com.emi.shiro.domain.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by emi on 2017/2/13.
 */
public interface UserRepository extends CrudRepository<User, Long> {


    User findByUsername(String username);
}
