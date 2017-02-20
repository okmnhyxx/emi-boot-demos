package com.emi.shiro.dto;


import com.emi.shiro.dto.common.RestResponse;
import com.emi.shiro.dto.vo.MenuVo;
import com.emi.shiro.dto.vo.UserVo;

import java.util.List;

/**
 * Created by emi on 2017/2/15.
 */
public class LoginMenuResponse extends RestResponse {

    private UserVo userVo;

    private List<MenuVo> menuVo;

    public LoginMenuResponse() {
    }

    public LoginMenuResponse(UserVo userVo, List<MenuVo> menuVo) {
        this.userVo = userVo;
        this.menuVo = menuVo;
    }

    public UserVo getUserVo() {
        return userVo;
    }

    public void setUserVo(UserVo userVo) {
        this.userVo = userVo;
    }

    public List<MenuVo> getMenuVo() {
        return menuVo;
    }

    public void setMenuVo(List<MenuVo> menuVo) {
        this.menuVo = menuVo;
    }
}
