package com.emi.shiro.t15.dto.vo;

import com.emi.shiro.t15.common.enums.ViewType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by emi on 2017/2/24.
 */
public class ResourceVo {

    private long id;

    private long parentId;

    private int viewType;
    private String viewTypeStr;

    private String stateUrl;//angular route 用的到, 只有在viewType为menu时该字段有值
    private String templateUrl;//只有在viewType为menu时该字段有值

    private String permissionCode;//例如：user:create
    private String permissionName;//例如：创建用户

    private List<ResourceVo> subList;

    public ResourceVo() {
        subList = new ArrayList<>();
    }

    public ResourceVo(long id, long parentId, int viewType, String stateUrl, String templateUrl, String permissionCode, String permissionName) {
        this();
        this.id = id;
        this.parentId = parentId;
        this.viewType = viewType;
        this.viewTypeStr = ViewType.valueOf(viewType).getDecs();
        this.stateUrl = stateUrl;
        this.templateUrl = templateUrl;
        this.permissionCode = permissionCode;
        this.permissionName = permissionName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public String getViewTypeStr() {
        return viewTypeStr;
    }

    public void setViewTypeStr(String viewTypeStr) {
        this.viewTypeStr = viewTypeStr;
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

    public List<ResourceVo> getSubList() {
        return subList;
    }

    public void setSubList(List<ResourceVo> subList) {
        this.subList = subList;
    }
}
