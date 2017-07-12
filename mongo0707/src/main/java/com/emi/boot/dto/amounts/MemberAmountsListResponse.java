package com.emi.boot.dto.amounts;

import com.emi.boot.dto.RestResponse;
import com.emi.boot.dto.amounts.vo.AmountsVo;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by emi on 2017/6/27.
 */
public class MemberAmountsListResponse extends RestResponse {

    @ApiModelProperty("明细列表")
    private List<AmountsVo> amountsVoList;

    public MemberAmountsListResponse() {
    }

    public MemberAmountsListResponse(List<AmountsVo> amountsVoList) {
        this.amountsVoList = amountsVoList;
    }

    public List<AmountsVo> getAmountsVoList() {
        return amountsVoList;
    }

    public void setAmountsVoList(List<AmountsVo> amountsVoList) {
        this.amountsVoList = amountsVoList;
    }
}
