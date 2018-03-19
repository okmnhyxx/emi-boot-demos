package com.emi.shiro.t15.dto.vo;

/**
 * Created by emi on 2017/3/1.
 */
public class ResourceVisibleVo {

    private int viewType;

    private String stateUrl;//angular route 用的到, 只有在viewType为menu时该字段有值
    private String templateUrl;//只有在viewType为menu时该字段有值

    private String permissionCode;//例如：user:create
    private String permissionName;//例如：创建用户

    public ResourceVisibleVo() {
    }

    public ResourceVisibleVo(int viewType, String stateUrl, String templateUrl, String permissionCode, String permissionName) {
        this.viewType = viewType;
        this.stateUrl = stateUrl;
        this.templateUrl = templateUrl;
        this.permissionCode = permissionCode;
        this.permissionName = permissionName;
    }


    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public String getStateUrl() {
        return stateUrl;
    }

    public void setStateUrl(String stateUrl) {
        this.stateUrl = stateUrl;
    }

    public String getTemplateUrl() {
        return templateUrl;
    }

    public void setTemplateUrl(String templateUrl) {
        this.templateUrl = templateUrl;
    }

    public String getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }
}
