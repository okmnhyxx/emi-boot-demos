package com.emi.mongo.dto.location.vo;

import com.emi.mongo.domain.CarLocation;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by emi on 2017/6/15.
 */
public class LocationBaseVo {

    @ApiModelProperty("车位Id")
    private String id;

    @ApiModelProperty("车位号")
    private String name;

    public LocationBaseVo() {
    }

    public LocationBaseVo(String id, String name) {
        this.id = id;
        this.name = name;
    }
    public LocationBaseVo(CarLocation carLocation) {
        this.id = carLocation.getId();
        this.name = carLocation.getName();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
