package com.emi.shiro.t15.dto;


import com.emi.shiro.t15.dto.common.RestResponse;
import com.emi.shiro.t15.dto.vo.MenuVo;
import com.emi.shiro.t15.dto.vo.UserVo;

import java.util.List;

/**
 * Created by emi on 2017/2/15.
 */
public class LoginMenuResponse extends RestResponse {

    private UserVo userVo;

    private List<MenuVo> menuVoList;

    private List<String> permissionList;

    public LoginMenuResponse() {
    }

    public LoginMenuResponse(UserVo userVo, List<MenuVo> menuVoList, List<String> permissionList) {
        this.userVo = userVo;
        this.menuVoList = menuVoList;
        this.permissionList = permissionList;
    }

    public UserVo getUserVo() {
        return userVo;
    }

    public void setUserVo(UserVo userVo) {
        this.userVo = userVo;
    }

    public List<MenuVo> getMenuVoList() {
        return menuVoList;
    }

    public void setMenuVoList(List<MenuVo> menuVoList) {
        this.menuVoList = menuVoList;
    }

    public List<String> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<String> permissionList) {
        this.permissionList = permissionList;
    }
}
