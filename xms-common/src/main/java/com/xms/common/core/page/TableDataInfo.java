package com.xms.common.core.page;

import org.slf4j.MDC;

import java.io.Serializable;
import java.util.List;

/**
 * 表格分页数据对象
 * 
 * @author xms
 */
public class TableDataInfo implements Serializable
{
    private static final long serialVersionUID = 1L;
    private static final String TRACE_ID = "request_id";
    /**
     * 日志跟踪标识
     */

    private String request_id;
    /** 总记录数 */
    private long total;

    /** 列表数据 */
    private List<?> rows;

    /** 消息状态码 */
    private int code;

    /** 消息内容 */
    private String msg;

    /**
     * 表格数据对象
     */
    public TableDataInfo()
    {
        this.request_id = MDC.get("request_id");
    }

    /**
     * 分页
     * 
     * @param list 列表数据
     * @param total 总记录数
     */
    public TableDataInfo(List<?> list, int total)
    {
        this.rows = list;
        this.total = total;
        this.request_id = MDC.get("request_id");
    }

    public long getTotal()
    {
        return total;
    }

    public void setTotal(long total)
    {
        this.total = total;
    }

    public List<?> getRows()
    {
        return rows;
    }

    public void setRows(List<?> rows)
    {
        this.rows = rows;
    }

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }
}
