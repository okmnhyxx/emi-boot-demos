package com.emi.shiro.t15.repository;

import com.emi.shiro.t15.domain.UserRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Set;

/**
 * Created by emi on 2017/2/16.
 */
public interface UserRoleRepository extends CrudRepository<UserRole, Long> {

    //todo: cache
    @Query("select ur.roleId from UserRole ur where ur.userId = :userId")
    Set<Long> findRoleIdByUserId(@Param("userId") long userId);

}
