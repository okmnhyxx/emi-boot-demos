package com.emi.boot.dto.hire;

import com.emi.boot.dto.RestResponse;
import com.emi.boot.dto.hire.vo.HireVo;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by emi on 2017/6/16.
 */
public class HireListResponse extends RestResponse {

    @ApiModelProperty("出租信息列表")
    private List<HireVo> hireVoList;

    public HireListResponse() {
    }

    public HireListResponse(List<HireVo> hireVoList) {
        this.hireVoList = hireVoList;
    }

    public List<HireVo> getHireVoList() {
        return hireVoList;
    }

    public void setHireVoList(List<HireVo> hireVoList) {
        this.hireVoList = hireVoList;
    }
}
