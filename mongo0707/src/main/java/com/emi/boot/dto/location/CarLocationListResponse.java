package com.emi.boot.dto.location;

import com.emi.boot.dto.RestResponse;
import com.emi.boot.dto.location.vo.LocationVo;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by emi on 2017/6/14.
 */
public class CarLocationListResponse extends RestResponse {

    @ApiModelProperty("车位列表，包含各种状态的")
    private List<LocationVo> locationVoList;

    public CarLocationListResponse() {
    }

    public CarLocationListResponse(List<LocationVo> locationVoList) {
        this.locationVoList = locationVoList;
    }

    public List<LocationVo> getLocationVoList() {
        return locationVoList;
    }

    public void setLocationVoList(List<LocationVo> locationVoList) {
        this.locationVoList = locationVoList;
    }
}
