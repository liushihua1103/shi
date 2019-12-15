package com.gdt.bean;

public class QueryVo {

    private String tbl_name;

    public String getTbl_name() {
        return tbl_name;
    }

    public void setTbl_name(String tbl_name) {
        this.tbl_name = tbl_name;
    }

    @Override
    public String toString() {
        return "QueryVo{" +
                "tbl_name='" + tbl_name + '\'' +
                '}';
    }
}
