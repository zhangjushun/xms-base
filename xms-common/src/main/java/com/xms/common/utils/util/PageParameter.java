package com.xms.common.utils.util;

public class PageParameter {

	public static final int DEFAULT_PAGE_SIZE = 10;

    private int pageSize;
    private int pageNo;
    private int prePage;
    private int nextPage;
    private int totalPage;
    private int totalCount;

    public PageParameter() {
        this.pageNo = 1;
        this.pageSize = DEFAULT_PAGE_SIZE;
    }
    /**
     * get method for reflect
     * @return
     */
    public PageParameter getPage(){
        return new PageParameter();
    }

    /**
     * 
     * @param currentPage
     * @param pageSize
     */
    public PageParameter(int pageNo, int pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPrePage() {
        return prePage;
    }

    public void setPrePage(int prePage) {
        this.prePage = prePage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
