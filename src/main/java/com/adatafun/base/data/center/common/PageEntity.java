package com.adatafun.base.data.center.common;

import java.util.List;

/**
 * Created by tiecheng on 2018/1/5.
 */
public class PageEntity<T> {

    private int page = 1;

    private int rows = 10;

    private int startIndex;

    private int total;

    private List<T> list;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getStartIndex() {
        return (this.page - 1) * this.rows;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

}
