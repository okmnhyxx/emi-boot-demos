package com.emi.shiro.t15.repository.mapper;


import com.emi.shiro.t15.dto.vo.UserListVo;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by emi on 2017/2/17.
 */
@Mapper
public interface UserMapper {

    List<UserListVo> searchList(@Param("username") String username, @Param("locked") Boolean locked);//, @Param("currentPage")int currentPage, @Param("pageSize")int pageSize);
}
