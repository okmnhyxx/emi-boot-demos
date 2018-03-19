package com.emi.shiro.t15.dto.vo;

/**
 * Created by emi on 2017/2/16.
 */
public class UserVo {

    private long userId;

    private String username;

    public UserVo() {
    }

    public UserVo(long userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
