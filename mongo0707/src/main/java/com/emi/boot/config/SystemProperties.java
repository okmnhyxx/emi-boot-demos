package com.emi.boot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Created by emi on 2017/6/16.
 */
@Configuration
public class SystemProperties {

    @Value("${parking.unit.price}")
    private int unitPrice;

    @Value("${parking.go.out.price}")
    private int goOutPrice;

    @Value("${parking.cancel.out.price}")
    private int cancelOutPrice;


    public int getUnitPrice() {
        return unitPrice;
    }

    public int getGoOutPrice() {
        return goOutPrice;
    }

    public int getCancelOutPrice() {
        return cancelOutPrice;
    }
}
