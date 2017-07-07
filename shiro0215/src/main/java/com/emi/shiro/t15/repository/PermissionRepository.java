package com.emi.shiro.t15.repository;

import com.emi.shiro.t15.domain.Permission;
import com.emi.shiro.t15.dto.vo.ResourceTreeVo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

/**
 * Created by emi on 2017/2/16.
 */
public interface PermissionRepository extends CrudRepository<Permission, Long> {

    @Query("select distinct p.permissionCode from Permission p where p.id in :permissionIds and p.deleted = false")
    Set<String> findNameById(Set<Long> permissionIds);


    List<Permission> findDistinctIdByIdInAndDeleted(Set<Long> permissionIds, boolean deleted);

    List<Permission> findByParentIdAndDeleted(long parentId, boolean deleted);

    List<Permission> findByDeleted(boolean deleted);

    @Query("select new com.emi.shiro.t15.dto.vo.ResourceTreeVo(p.id, p.parentId, p.permissionName) from Permission p where p.deleted = false")
    List<ResourceTreeVo> findPermissionTree();

    int countByIdInAndDeleted(Long[] permissionIdsLong, boolean deleted);

    List<Permission> findByDeletedOrderByIdDesc(boolean deleted);
    List<Permission> findByDeletedOrderById(boolean deleted);

    List<Permission> findByParentIdAndViewTypeNotAndDeleted(long parentId, int viewTypeNot, boolean deleted);
}
