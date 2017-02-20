package com.emi.shiro.domain;

import com.emi.shiro.common.enums.ViewType;
import org.hibernate.annotations.Type;

import javax.persistence.*;

/**
 * Created by emi on 2017/2/10.
 */
@Entity
@Table(name = "t15_resource")
public class Permission extends BaseDomain {

    private long updaterId;

//    @OneToOne(fetch = FetchType.LAZY, mappedBy = "id")
    private long parentId;

    private String ancestorsId;

    @Type(type = "com.emi.shiro.common.enums.EnumUserType",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "enumClass", value = "com.emi.shiro.common.enums.ViewType"),
                    @org.hibernate.annotations.Parameter(name = "recreateEnumMethod", value = "valueOf"),
                    @org.hibernate.annotations.Parameter(name = "getValueMethod", value = "getValue")
            })
    private ViewType viewType;

    private String menuUrl;//只有在viewType为menu时该字段有值

    private String permissionName;//user:create

    private String permissionDesc;//创建用户

    private boolean deleted = false;

    public Permission() {
        this.deleted = false;
    }

    //系统创建的
    public Permission(String menuUrl, String permissionName, String permissionDesc) {
        this.updaterId = 0;
        this.parentId = 0;
        this.ancestorsId = getId() + "";
        this.viewType = ViewType.Menu;
        this.menuUrl = menuUrl;
        this.permissionName = permissionName;
        this.permissionDesc = permissionDesc;
    }

    public Permission(Long updaterId, Long parentId, String ancestorsId, ViewType viewType, String menuUrl, String permissionName, String permissionDesc) {
        this.updaterId = updaterId;
        this.parentId = parentId;
        this.ancestorsId = ancestorsId;
        this.viewType = viewType;
        this.menuUrl = menuUrl;
        this.permissionName = permissionName;
        this.permissionDesc = permissionDesc;
    }

    public long getUpdaterId() {
        return updaterId;
    }

    public void setUpdaterId(long updaterId) {
        this.updaterId = updaterId;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public String getAncestorsId() {
        return ancestorsId;
    }

    public void setAncestorsId(String ancestorsId) {
        this.ancestorsId = ancestorsId;
    }

    public ViewType getViewType() {
        return viewType;
    }

    public void setViewType(ViewType viewType) {
        this.viewType = viewType;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getPermissionDesc() {
        return permissionDesc;
    }

    public void setPermissionDesc(String permissionDesc) {
        this.permissionDesc = permissionDesc;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

}
