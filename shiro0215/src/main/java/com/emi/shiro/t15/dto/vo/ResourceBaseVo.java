package com.emi.shiro.t15.dto.vo;

/**
 * Created by emi on 2017/3/3.
 */
public class ResourceBaseVo {

    private long id;

    private String name;

    public ResourceBaseVo() {
    }

    public ResourceBaseVo(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
