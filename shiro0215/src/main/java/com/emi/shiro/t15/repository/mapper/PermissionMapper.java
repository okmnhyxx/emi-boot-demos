package com.emi.shiro.t15.repository.mapper;

import com.emi.shiro.t15.dto.vo.ResourceBaseVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by emi on 2017/3/3.
 */
@Mapper
public interface PermissionMapper {

    List<ResourceBaseVo> findRolePermission(@Param("roleId") Long roleId);
}
