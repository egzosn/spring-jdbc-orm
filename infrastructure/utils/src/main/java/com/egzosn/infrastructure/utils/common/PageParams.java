package com.egzosn.infrastructure.utils.common;


import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * 分页请求参数
 *
 * @author 肖红星
 * @create 2017/2/28
 */
public class PageParams implements Serializable {

    private static final long serialVersionUID = -4606498608333004371L;

    //当前页
    private int page;
    //分页大小
    private int size;
    //排序字段
    private String sort;
    //排序类型：DESC、ASC
    private String order;


    public PageParams(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public PageParams() {

    }

    public int getRealPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPage() {
        if (page <= 0) {
            page = 1;
        }
        return page;
    }

    public int getSize() {
        //默认为10条
        if (size == 0) {
            return 10;
        }
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        //默诵为顺序
        if (StringUtils.isEmpty(order)) {
            return "ASC";
        }
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
