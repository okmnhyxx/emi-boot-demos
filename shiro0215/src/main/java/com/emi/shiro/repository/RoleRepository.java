package com.emi.shiro.repository;

import com.emi.shiro.domain.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

/**
 * Created by emi on 2017/2/15.
 */
public interface RoleRepository extends CrudRepository<Role, Long> {


    @Query("select r.roleName from Role r where r.id in :roleIds and r.enable = true")
    Set<String> findNameAll(@Param("roleIds") Set<Long> roleIds);
}
