package com.emi.mongo.dto.location;

import com.emi.mongo.dto.RestResponse;
import com.emi.mongo.dto.location.vo.LocationVo;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by emi on 2017/6/14.
 */
public class CarLocationAddResponse extends RestResponse {

    @ApiModelProperty("车位信息")
    private LocationVo locationVo;

    public CarLocationAddResponse() {
    }

    public CarLocationAddResponse(LocationVo locationVo) {
        this.locationVo = locationVo;
    }

    public LocationVo getLocationVo() {
        return locationVo;
    }

    public void setLocationVo(LocationVo locationVo) {
        this.locationVo = locationVo;
    }
}
