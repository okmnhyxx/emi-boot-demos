package com.emi.shiro.dto.common;

/**
 * Created by emi on 2016/11/3.
 */
public abstract class PagedRequest {

    private int currentPage;

    private int pageSize;


    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
