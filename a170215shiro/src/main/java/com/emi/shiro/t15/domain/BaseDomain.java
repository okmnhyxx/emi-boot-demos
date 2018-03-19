package com.emi.shiro.t15.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by emi on 2016/11/1.
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseDomain implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @CreatedDate
    private Date createTime;

    @LastModifiedDate
    private Date lastUpdateTime;

  /*  @Column(nullable = true)
    private boolean deleted;*/

//    public BaseDomain() {
//        this.createTime = new Date();
//        this.lastUpdateTime = this.createTime;
//        this.ifDelete = false;
//    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }








   /* public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }*/
}
