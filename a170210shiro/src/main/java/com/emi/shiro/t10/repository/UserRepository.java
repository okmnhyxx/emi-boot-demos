package com.emi.shiro.t10.repository;

import com.emi.shiro.t10.domain.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by emi on 2017/2/13.
 */
public interface UserRepository extends CrudRepository<User, Long> {


    User findByUsername(String username);
}
