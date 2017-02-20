package com.emi.shiro.dto;

import com.emi.shiro.dto.common.RestResponse;
import com.emi.shiro.dto.common.vo.PageRespVo;
import com.emi.shiro.dto.vo.UserListVo;

import java.util.List;

/**
 * Created by emi on 2017/2/17.
 */
public class UserListResponse extends RestResponse {

    private PageRespVo pageVo;

    private List<UserListVo> userVoList;

    public UserListResponse() {
    }

    public UserListResponse(PageRespVo pageVo, List<UserListVo> userVoList) {
        this.pageVo = pageVo;
        this.userVoList = userVoList;
    }

    public PageRespVo getPageVo() {
        return pageVo;
    }

    public void setPageVo(PageRespVo pageVo) {
        this.pageVo = pageVo;
    }

    public List<UserListVo> getUserVoList() {
        return userVoList;
    }

    public void setUserVoList(List<UserListVo> userVoList) {
        this.userVoList = userVoList;
    }
}
