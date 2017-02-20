package com.emi.shiro.dto.common.vo;



/**
 * Created by emi on 2016/11/1.
 */
public class PageRespVo {

    private int currentPage;

    private int pageSize;

    private long totalRows;

    public PageRespVo() {
    }

    public PageRespVo(int currentPage, int pageSize, long totalRows) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalRows = totalRows;
    }


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

    public long getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(long totalRows) {
        this.totalRows = totalRows;
    }
}

