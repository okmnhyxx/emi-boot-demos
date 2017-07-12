package com.emi.boot.dto.hire;

import com.emi.boot.dto.RestResponse;
import com.emi.boot.dto.hire.vo.HireVo;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by emi on 2017/6/15.
 */
public class HirePublishResponse extends RestResponse {

    @ApiModelProperty("出租信息Vo")
    private HireVo hireVo;

    public HirePublishResponse() {
    }

    public HirePublishResponse(HireVo hireVo) {
        this.hireVo = hireVo;
    }

    public HireVo getHireVo() {
        return hireVo;
    }

    public void setHireVo(HireVo hireVo) {
        this.hireVo = hireVo;
    }
}
