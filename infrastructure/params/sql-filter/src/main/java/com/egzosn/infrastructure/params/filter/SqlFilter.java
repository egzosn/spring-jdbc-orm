/*
 * Copyright 2002-2020 the original  egan.
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

package com.egzosn.infrastructure.params.filter;


import com.egzosn.infrastructure.params.Field4Column;
import com.egzosn.infrastructure.params.Order;
import com.egzosn.infrastructure.params.QueryParams;
import com.egzosn.infrastructure.params.Where;
import com.egzosn.infrastructure.params.enums.Restriction;

import java.util.*;

/**
 * Created by egan on 2015/10/25.
 * <p>
 * <p>
 * 表单sql属性过滤器
 */
public class SqlFilter {
    private static final String ignoreStr = "[\\'\\-\\;\\*\\`\\%\\#]";
    /**
     * 分隔符
     */
    private static final String SEPARATOR = "^";
    private SqlFilterRequest request;
    private String column;
    private Order.OrderAD order;
    Where params = new Where().where();
    /**
     * 字段转列集，这里主要用于实现类似ORM的转化，展示为字段，生成则为列
     */
    Map<String, Field4Column> field4Columns;

    /**
     * 默认构造
     */
    public SqlFilter() {
    }


    /**
     * 带参构造
     *
     * @param request 请求参数
     */
    public SqlFilter(SqlFilterRequest request) {
        this(request, null);
    }

    /**
     * 带参构造
     *
     * @param field4Columns 字段转列集
     */
    public SqlFilter(Map<String, Field4Column> field4Columns) {
        this.field4Columns = field4Columns;
    }

    /**
     * 带参构造
     *
     * @param request       请求参数
     * @param field4Columns 字段转列集
     */
    public SqlFilter(SqlFilterRequest request, Map<String, Field4Column> field4Columns) {
        this.field4Columns = field4Columns;
        this.request = request;
        addFilter(request);

    }

    public static final boolean isBlank(String str) {
        return null == str || str.trim().isEmpty();
    }

    /**
     * 设置分页
     * page 开始页
     * rows 每页展示几行
     *
     * @return 本身
     */
    public SqlFilter setPageing() {
        String page = request.getParameter("page");
        if (!isBlank(page)) {
            params.setPageIndex(Integer.parseInt(page));
        }
        String rows = request.getParameter("rows");
        if (!isBlank(rows)) {
            params.setPageSize(Integer.parseInt(rows));
        }
        return this;
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
     */
    public SqlFilter setOrder() {
        if (!isBlank(column) && null != order) {
            int index = column.indexOf(".");
            if (index < 1) {
                setOrderValue(null);
            } else {
                setOrderValue(column.substring(index));
            }
            return this;
        }
        if (request != null) {
            String s = request.getParameter("sort");
            String o = request.getParameter("order");
            if (isBlank(s) || isBlank(o)) {
                return this;
            }
            column = s;
            order = Order.OrderAD.valueOf(o.toUpperCase());
            int index = column.indexOf(".");
            if (index < 1) {
                setOrderValue(null);
            } else {
                setOrderValue(column.substring(index));
            }

        }
        return this;
    }

    /**
     * 添加过滤
     *
     * @param request 过滤器请求集
     */
    public SqlFilter addFilter(SqlFilterRequest request) {
        Set<String> names = request.getParameterNames();
        for (String name : names) {
//            String value = request.getParameter(name);
            String[] values = request.getParameterValues(name);//需要对应值
            if (null == values) {
                continue;
            }
            int length = values.length;
            //不干扰原对象，用副本做处理
            values = Arrays.copyOf(values, length);
            for (int i = 0; i < length && values[i].contains("="); ++i) {
                String[] valTmp = values[i].split("=");
                if (valTmp.length <= 1) {
                    break;
                }

                String[] operator = valTmp[0].split("\\^");
                boolean flag = false;
                switch (operator.length) {
                    case 1:
                        flag = this.isOperator(operator[0]);
                        break;
                    case 2:
                        if (this.isBlank(operator[0])) {
                            flag = this.isOperator(operator[1]);
                        } else {
                            flag = this.isOperator(operator[0]);
                        }
                        break;
                    case 3:
                        flag = this.isOperator(operator[1]);
                }

                if (flag) {
                    values[i] = valTmp[1];
                    if (valTmp[0].indexOf(SEPARATOR) == 0) {
                        name = name + valTmp[0];
                        break;
                    }

                    name = name + SEPARATOR + valTmp[0];
                }
            }

            if (length > 1) {
                addFilter(name, Arrays.asList(values));
            } else {
                addFilter(name, values[0]);
            }


        }
        return this;
    }


    private boolean isOperator(String operator) {
        try {
            Restriction.valueOf(operator.toUpperCase());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 添加过滤
     *
     * @param name  参数名
     * @param value 参数值
     *              <p>
     *              举例，name传递：Q^t#id^|^EQ^S
     *              举例，name传递：Q^t#id^!|^EQ
     *              </p>
     *              举例，value传递：0
     */
    public SqlFilter addFilter(String name, Object value) {

        if (name != null && value != null) {
            if (name.startsWith("Q^")) {// 如果有需要过滤的字段
                String[] filterParams = name.split("\\^");
//				String[] filterParams = StringUtils.split(name, "_");
                int length = filterParams.length;
                if (length >= 2) {
                    String[] ppn = filterParams[1].split("\\#");
                    //表的别名
                    String prefix = ppn[0];
                    // 要过滤的字段名称
                    String propertyName = ppn[1];
                    // 操作的逻辑
                    String ao = filterParams[2];
                    Restriction restriction = null;
                    Type type = null;

                    try {
                        restriction = Restriction.valueOf(filterParams[3].toUpperCase());
                    } catch (Exception var17) {
                        restriction = Restriction.EQ;
                    }

                    try {
                        type = Type.valueOf(filterParams[4]);
                    } catch (Exception var16) {
                        type = Type.S;
                    }
                    List list;
                    int size;
                    switch (restriction) {
                        case BW:
                            Object os = null;
                            if (value instanceof String) {
                                value = Arrays.asList(((String) value).split(","));
                            }
                            list = (List) value;
                            size = list.size();
                            os = new Object[size];
                            for (int i = 0; i < size; ++i) {
                                ((Object[]) os)[i] = type.parse(list.get(i).toString());
                            }

                            value = os;
                            break;
                        case IN:
                        case NIN:
                            if (value instanceof String) {
                                list = Arrays.asList(((String) value).split(","));
                            } else {
                                list = (List) value;
                            }
                            size = list.size();
                            ArrayList vs = new ArrayList(size);

                            for (int i = 0; i < size; ++i) {
                                vs.add(type.parse(list.get(i).toString()));
                            }
                            value = vs;
                            break;
                        default:
                            value = type.parse(value.toString());
                            break;
                    }

                    propertyName = field4Columns(propertyName);
                    //过滤特殊字符
                    prefix = prefix.replaceAll(ignoreStr, "");
                    if ("|".equals(ao)) {
                        params.or(propertyName, value, restriction, prefix);
                    } else {
                        params.and(propertyName, value, restriction, prefix);
                    }
                }
            }
        }
        return this;
    }

    /**
     * 通过字段获取列名
     *
     * @param field 字段名
     * @return 列名
     */
    private String field4Columns(String field) {
        if (null == field4Columns || !field4Columns.containsKey(field)) {
            return field;
        }

        return field4Columns.get(field).getColumn();
    }

    /**
     * 设置主表别名
     *
     * @param alias 别名
     * @return sql过滤器
     */
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

    public Order.OrderAD getOrder() {
        return order;
    }

    public void setOrder(Order.OrderAD order) {
        this.order = order;
    }

    public Map<String, Field4Column> getField4Columns() {
        return field4Columns;
    }

    public void setField4Columns(Map<String, Field4Column> field4Columns) {
        this.field4Columns = field4Columns;
    }


}
