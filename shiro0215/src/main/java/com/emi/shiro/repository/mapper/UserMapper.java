package com.emi.shiro.repository.mapper;


import com.emi.shiro.dto.vo.UserListVo;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by emi on 2017/2/17.
 */
@Mapper
public interface UserMapper {

    PageInfo<UserListVo> searchList(@Param("username") String username, @Param("locked") Boolean locked);
}
