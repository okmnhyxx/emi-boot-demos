package com.emi.shiro.repository;

import com.emi.shiro.domain.RolePermission;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Set;

/**
 * Created by emi on 2017/2/16.
 */
public interface RolePermissionRepository extends CrudRepository<RolePermission, Long> {

    //todo: cache
    @Query("select distinct rp.permissionId from RolePermission rp where rp.roleId in :roleIds")
    Set<Long> findDistinctPermissionIdByRoleIdsIn(@Param("roleIds")Set<Long> roleIds);
}
