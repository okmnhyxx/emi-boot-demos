package com.emi.shiro.dto.vo;

/**
 * Created by emi on 2017/2/15.
 */
public class MenuItemVo {

    private String menuUrl;

    private String permissionDesc;

    public MenuItemVo(String menuUrl, String permissionDesc) {
        this.menuUrl = menuUrl;
        this.permissionDesc = permissionDesc;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getPermissionDesc() {
        return permissionDesc;
    }

    public void setPermissionDesc(String permissionDesc) {
        this.permissionDesc = permissionDesc;
    }
}
