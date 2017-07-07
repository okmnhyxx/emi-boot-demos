package com.emi.shiro.t15.domain;

import com.emi.shiro.t15.common.enums.OperateType;
import com.emi.shiro.t15.common.enums.ViewType;
import com.emi.shiro.t15.common.exceptions.CommonException;
import com.emi.shiro.t15.configs.ErrorCode;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by emi on 2017/2/10.
 */
@Entity
@Table(name = "t15_permission")
public class Permission extends BaseDomain {

    private long updaterId;

//    @OneToOne(fetch = FetchType.LAZY, mappedBy = "id")
    private long parentId;

    private String ancestorsId;

//    @Type(type = "com.emi.shiro.t15.common.enums.EnumUserType",
//            parameters = {
//                    @org.hibernate.annotations.Parameter(name = "enumClass", value = "com.emi.shiro.t15.common.enums.ViewType"),
//                    @org.hibernate.annotations.Parameter(name = "recreateEnumMethod", value = "valueOf"),
//                    @org.hibernate.annotations.Parameter(name = "getValueMethod", value = "getValue")
//            })
    private int viewType;

//    private String menuUrl;//
    private String stateUrl;//angular route 用的到, 只有在viewType为menu时该字段有值
    private String templateUrl;//只有在viewType为menu时该字段有值

    private String permissionCode;//user:create
    private String permissionName;//创建用户

    private boolean deleted = false;

    public Permission() {
        this.deleted = false;
    }

    //系统创建的各类资源别的根
    public Permission(String stateUrl, String templateUrl, String permissionCode, String permissionName) {
        this();
        this.updaterId = 0;
        this.parentId = 0;
        this.ancestorsId = getId() + "";
        this.viewType = ViewType.Menu.getValue();
//        this.menuUrl = menuUrl;
        this.stateUrl = stateUrl;
        this.templateUrl = templateUrl;
        this.permissionCode = permissionCode;
        this.permissionName = permissionName;
    }

    //系统创建的各类资源别的根 下的 基础增删改查
    public Permission(OperateType operateType, long parentId, String basePermissionCode, String basePermissionName) {
        this();
        this.updaterId = 0;
        this.parentId = parentId;
        this.ancestorsId = parentId + "";
        this.viewType = ViewType.Button.getValue();
        this.permissionCode = basePermissionCode.replace("*", "") + operateType.getCode();
        this.permissionName = basePermissionName + " - " + operateType.getDesc();
    }


    public Permission(Long updaterId, Long parentId, String ancestorsId, int viewType, String stateUrl, String templateUrl, String permissionCode, String permissionName) {
        this();
        this.updaterId = updaterId;
        this.parentId = parentId;
        this.ancestorsId = ancestorsId;
        this.viewType = viewType;
        this.stateUrl = stateUrl;
        this.templateUrl = templateUrl;
        this.permissionCode = permissionCode;
        this.permissionName = permissionName;
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void modify(long updaterId, int viewType, String stateUrl, String templateUrl, String permissionCode, String permissionName) {

        setUpdaterId(updaterId);
        this.viewType = viewType;
        this.stateUrl = stateUrl;
        this.templateUrl = templateUrl;
        this.permissionCode = permissionCode;
        this.permissionName = permissionName;
    }

    public void checkNotBtn() {
        if (this.viewType == ViewType.Button.getValue()) {
            throw new CommonException(ErrorCode.COMMON_ERROR, "按钮无法添加子节点", String.format("按钮[%d]无法添加子节点", getId()));
        }
    }
}
