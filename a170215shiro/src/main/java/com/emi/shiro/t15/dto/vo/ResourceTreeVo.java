package com.emi.shiro.t15.dto.vo;

/**
 * Created by emi on 2017/3/3.
 */
public class ResourceTreeVo {

    private long id;

    private long pId;

    private String name;

    private boolean checked;

    public ResourceTreeVo() {
    }

    public ResourceTreeVo(long id, long pId, String name) {
        this.id = id;
        this.pId = pId;
        this.name = name;
        this.checked = false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getpId() {
        return pId;
    }

    public void setpId(long pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
