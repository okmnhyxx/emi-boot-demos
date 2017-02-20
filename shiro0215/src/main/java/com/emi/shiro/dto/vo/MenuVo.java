package com.emi.shiro.dto.vo;

import java.util.List;

/**
 * Created by emi on 2017/2/15.
 */
public class MenuVo {


    private MenuItemVo mainItemVo;

    private List<MenuItemVo> itemVoList;

    public MenuVo() {
    }

    public MenuVo(MenuItemVo mainItemVo, List<MenuItemVo> itemVoList) {
        this.mainItemVo = mainItemVo;
        this.itemVoList = itemVoList;
    }


    public MenuItemVo getMainItemVo() {
        return mainItemVo;
    }

    public void setMainItemVo(MenuItemVo mainItemVo) {
        this.mainItemVo = mainItemVo;
    }

    public List<MenuItemVo> getItemVoList() {
        return itemVoList;
    }

    public void setItemVoList(List<MenuItemVo> itemVoList) {
        this.itemVoList = itemVoList;
    }
}
