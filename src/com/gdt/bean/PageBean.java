package com.gdt.bean;

import java.util.List;

public class PageBean {
    private long total;         //总记录数6
    private long currentPage ;  //当前页1
    private int  rows;          //每页展示条数3
    private long totalPage ;    //总页码2
    private List<QueryVo> list;  //一页数据

    public PageBean() {
    }

    public PageBean(long total, long currentPage, int rows, long totalPage, List<QueryVo> list) {
        this.total = total;
        this.currentPage = currentPage;
        this.rows = rows;
        this.totalPage = totalPage;
        this.list = list;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(long currentPage) {
        this.currentPage = currentPage;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    public List<QueryVo> getList() {
        return list;
    }

    public void setList(List<QueryVo> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "total=" + total +
                ", currentPage=" + currentPage +
                ", rows=" + rows +
                ", totalPage=" + totalPage +
                ", list=" + list +
                '}';
    }
}
