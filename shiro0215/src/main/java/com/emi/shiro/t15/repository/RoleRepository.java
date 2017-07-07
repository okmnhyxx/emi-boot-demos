package com.emi.shiro.t15.repository;

import com.emi.shiro.t15.domain.Role;
import com.emi.shiro.t15.dto.vo.RoleBaseVo;
import com.emi.shiro.t15.dto.vo.RoleListVo;
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

    @Query("select new com.emi.shiro.t15.dto.vo.RoleListVo(r.id, r.roleName, r.roleDesc, r.enable, r.createTime) from Role r")
    List<RoleListVo> findRoleList();

    @Query("select new com.emi.shiro.t15.dto.vo.RoleBaseVo(r.id, r.roleName) from Role r")
    List<RoleBaseVo> findAllExceptDisable();


}
