package com.emi.boot.dto.location;

import com.emi.boot.dto.RestResponse;
import com.emi.boot.dto.location.vo.LocationWaitVo;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by emi on 2017/6/19.
 */
public class CarLocationWaitListResponse extends RestResponse {

    @ApiModelProperty("待审核的车位列表")
    private List<LocationWaitVo> locationVoList;

    public CarLocationWaitListResponse() {
    }

    public CarLocationWaitListResponse(List<LocationWaitVo> locationVoList) {
        this.locationVoList = locationVoList;
    }

    public List<LocationWaitVo> getLocationVoList() {
        return locationVoList;
    }

    public void setLocationVoList(List<LocationWaitVo> locationVoList) {
        this.locationVoList = locationVoList;
    }
}
