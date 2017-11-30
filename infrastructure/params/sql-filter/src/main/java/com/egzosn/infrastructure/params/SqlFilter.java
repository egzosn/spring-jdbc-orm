/*
 * Copyright 2002-2017 the original  egan.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.egzosn.infrastructure.params;


import com.egzosn.infrastructure.params.Order.OrderAD;
import com.egzosn.infrastructure.params.enums.Restriction;

import java.util.*;

/**
 * Created by egan on 2015/10/25.
 */
public class SqlFilter {
    private SqlFilterRequest request;
    private String column;
    private OrderAD order;
    Where params = new Where().where();

    /**
     * 默认构造
     */
    public SqlFilter() {
    }

    /**
     * 带参构造
     *
     * @param request
     */
    public SqlFilter(SqlFilterRequest request) {
        this.request = request;
        addFilter(request);
    }

    public boolean isBlank(String str) {
        return null == str || str.trim().isEmpty();
    }

    public void setPageing(){
        String page = request.getParameter("page");
        if (!isBlank( page)){
            params.setPageIndex(Integer.parseInt(page));
        }
        String rows = request.getParameter("rows");
        if (!isBlank( rows)){
            params.setPageSize(Integer.parseInt(rows));
        }
    }

    private void setOrderValue(String prefix) {
        switch (order) {
            case DESC:
                params.order(column, prefix);
                break;
            case ASC:
                params.order().ASC(column, prefix);
                break;
        }
    }

    /**
     * 获得添加过滤字段后加上排序字段的Sql
     *
     * @return
     */
    public void setOrder() {


        if (!isBlank(column) && null != order) {
            int index = column.indexOf(".");
            if (index < 1) {
                setOrderValue(null);
            } else {
                setOrderValue(column.substring(index));
            }

        } else {
            if (request != null) {
                String s = request.getParameter("sort");
                String o = request.getParameter("order");
                if (isBlank(s) || isBlank(o)) {
                    return;
                }
                column = s;
                order = OrderAD.valueOf(o.toUpperCase());
                int index = column.indexOf(".");
                if (index < 1) {
                    setOrderValue(null);
                } else {
                    setOrderValue(column.substring(index));
                }

            }
        }

    }

    /**
     * 添加过滤
     *
     * @param request
     */
    public void addFilter(SqlFilterRequest request) {
        Set<String> names = request.getParameterNames();
        for (String name : names) {
//            String value = request.getParameter(name);
            String[] values = request.getParameterValues(name);//需要对应值

            if (values.length > 1) {
                addFilter(name, Arrays.asList(values));
            } else {
                addFilter(name, values[0]);
            }


        }
    }


    /**
     * 添加过滤
     * <p/>
     * 举例，name传递：QUERY^t#id^|^EQ^S
     * 举例，name传递：QUERY^t#id^!|^EQ
     * <p/>
     * 举例，value传递：0
     *
     * @param name
     * @param value
     */
    public void addFilter(String name, Object value) {

        if (name != null && value != null) {
            if (name.startsWith("QUERY^")) {// 如果有需要过滤的字段
                String[] filterParams = name.split("\\^");
//				String[] filterParams = StringUtils.split(name, "_");
                int length = filterParams.length;
                if (length >= 3) {
                    String[] ppn = filterParams[1].split("\\#");
                    String prefix = ppn[0]; //表的别名
                    String propertyName = ppn[1];// 要过滤的字段名称
                    String ao = filterParams[2];// 操作的逻辑
                    Restriction restriction = null;
                    Type type = null;
                    try {
                        restriction = Restriction.valueOf(filterParams[3].toUpperCase());// SQL操作符
                        type = Type.valueOf(filterParams[4]);// 参数类型
                    } catch (Exception e) {
                        restriction = Restriction.EQ;
                        type = Type.S;
                    }
                    switch (restriction) {
                        case BW:
                            List list = (List) value;
                            int size = list.size();
                            Object[] os = new Object[size];
                            for (int i = 0; i < size; i++) {
                                os[i] = type.parse(list.get(i).toString());
                            }
                            value = os;
                            break;
                        case IN:
                        case NIN:
                             list = (List) value;
                             size = list.size();
                            List<Object> vs = new ArrayList<Object>(size);
                            for (int i = 0; i < size; i++) {
                                vs.add(type.parse(list.get(i).toString()));
                            }
                            value = vs;
                            break;
                        default:
                            value = type.parse(value.toString());
                            break;
                    }

                    if ("|".equals(ao)) {
                        params.or(propertyName, value, restriction, prefix);
                    } else {
                        params.and(propertyName, value, restriction, prefix);
                    }
                }
            }
        }
    }

    public SqlFilter setAlias(String alias) {
        params.setAlias(alias);
        return this;
    }


    public QueryParams getQueryParams() {
        return params;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public OrderAD getOrder() {
        return order;
    }

    public void setOrder(OrderAD order) {
        this.order = order;
    }
}
