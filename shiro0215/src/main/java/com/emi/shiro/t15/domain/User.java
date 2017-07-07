package com.emi.shiro.t15.domain;

import com.emi.shiro.t15.common.enums.DomainType;
import com.emi.shiro.t15.common.exceptions.RecordStatusException;

import javax.persistence.*;

/**
 * Created by emi on 2017/2/10.
 */
@Entity
@Table(name = "t15_user")
public class User extends BaseDomain {

    private long organizationId;

    private long updaterId;

    private String username;

    private String password;

    private boolean locked = Boolean.FALSE;

    //@OneToMany(mappedBy=“由One的一方指向Many的一方，并且，这个属性应该等于Many的一方中含有One类的属性的属性名，否则会出错啦 ”)
//    @ManyToMany(fetch=FetchType.LAZY)
//    @JoinTable(name = "t15_user_role", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
//            @JoinColumn(name = "role_id") })
//    private List<Role> roleList;// 一个用户具有多个角色

    public User() {
        super();
    }

    public User(long organizationId, long updaterId, String username, String password) {
        super();
        this.organizationId = organizationId;
        this.updaterId = updaterId;
        this.username = username;
        this.password = password;
    }

    public long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(long organizationId) {
        this.organizationId = organizationId;
    }

    public long getUpdaterId() {
        return updaterId;
    }

    public void setUpdaterId(long updaterId) {
        this.updaterId = updaterId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public void modify(long organizationId, long updaterId, String username, String password) {
        this.organizationId = organizationId;
        this.updaterId = updaterId;
        this.username = username;
        this.password = password;
    }

    public void checkLocked() {
        if (this.locked) {
            throw new RecordStatusException(true, DomainType.User, this.getId(), false);
        }
    }

    public void lock(long updaterId, boolean lock) {
        this.updaterId = updaterId;
        this.locked = lock;
    }


//    @Transient
//    public Set<String> fetchRolesNames() {
//        List<Role> roles = getRoleList();
//        Set<String> set = new HashSet<String>();
//        for (Role role : roles) {
//            set.add(role.getRolename());
//        }
//        return set;
//    }
//
//    @Transient
//    public Set<String> fetchPermissions() {
//    }
}
