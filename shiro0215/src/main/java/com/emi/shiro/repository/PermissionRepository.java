package com.emi.shiro.repository;

import com.emi.shiro.domain.Permission;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

/**
 * Created by emi on 2017/2/16.
 */
public interface PermissionRepository extends CrudRepository<Permission, Long> {

    @Query("select distinct p.permissionName from Permission p where p.id in :permissionIds and p.deleted = false")
    Set<String> findNameById(Set<Long> permissionIds);


    List<Permission> findDistinctIdByIdInAndDeleted(Set<Long> permissionIds, boolean deleted);
}
