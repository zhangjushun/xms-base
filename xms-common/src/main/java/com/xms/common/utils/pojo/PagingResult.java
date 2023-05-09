package com.xms.common.utils.pojo;

import java.util.List;

public class PagingResult {
    private int code;

    private String msg;

    private long total;

    private int taotalPage;

    private List<?> rows;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

    public int getTaotalPage() {
        return taotalPage;
    }

    public void setTaotalPage(int taotalPage) {
        this.taotalPage = taotalPage;
    }

}
