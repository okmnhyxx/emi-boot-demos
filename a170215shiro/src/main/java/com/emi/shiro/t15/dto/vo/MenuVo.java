package com.emi.shiro.t15.dto.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by emi on 2017/2/15.
 */
public class MenuVo {

    private String stateUrl;

    private String permissionName;


    private List<MenuVo> menuVoList;

    public MenuVo() {
        this.menuVoList = new ArrayList<>();
    }

    //放置子菜单前 未放置主菜单
    public MenuVo(List<MenuVo> menuVoList) {
        this.menuVoList = menuVoList;
    }

    public MenuVo(String stateUrl, String permissionName) {
        this();
        this.stateUrl = stateUrl;
        this.permissionName = permissionName;
    }

    public MenuVo(String stateUrl, String permissionName, List<MenuVo> menuVoList) {
        this.stateUrl = stateUrl;
        this.permissionName = permissionName;
        this.menuVoList = menuVoList;
    }

    public String getStateUrl() {
        return stateUrl;
    }

    public void setStateUrl(String stateUrl) {
        this.stateUrl = stateUrl;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public List<MenuVo> getMenuVoList() {
        return menuVoList;
    }

    public void setMenuVoList(List<MenuVo> menuVoList) {
        this.menuVoList = menuVoList;
    }
}
