package com.egzosn.infrastructure.params;

/**
 * 分页列表，保存了分页内容，当前页，每页记录数，总记录数和页数
 * @author egan
 *
 */
public class Pageing {

	private int pageIndex = 1; //  当前页
	
	private int pageSize = 10; // 每页记录数

    public int getPageIndex() {
        return pageIndex;
    }

    protected void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    protected void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
