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


import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by egan on 2015/7/15.
 * 查询参数集
 */
public abstract class QueryParams implements Params {
    private Pageing page = new Pageing();
    protected StringBuilder sql = null;
    protected Where where = null;
    protected Order order = null;
    protected Group group = null;
    protected String alias = "";

    public Pageing getPage() {
        return page;
    }

    public void setPageIndex(Integer pageIndex){
        page.setPageIndex(pageIndex);
    }

    public void setPageSize(Integer pageSize){
        page.setPageSize(pageSize);
    }

    public Where getWhere() {
        return where;
    }

    public Order getOrder() {
        return order;
    }

    public Group getGroup() {
        return group;
    }

    public Map<String, Object> getAttrs() {
        return null == where ? null : where.getAttrs();
    }

    public List<Object> getParas() {
        return null == where ? null : where.getParas();
    }

    public String getSqlString() {
        return  sql.toString();
    }

    public StringBuilder getSql() {
        return sql;
    }


    public QueryParams builderAttrs(StringBuilder sb) {
        sb.append(toSQL());
        return this;
    }

    public QueryParams builderParas(StringBuilder sb) {
        sb.append(toFormatSQL());
        return this;
    }
    public String alias(){
        return where.alias();
    }

    public QueryParams builderAttrs() {
        if (null == sql) sql = new StringBuilder(); else sql.setLength(0);
        if (null != where)  sql.append(where.toSQL());
        if (null != group)  sql.append(group.toSQL());
        if (null != order)  sql.append(order.toSQL());
        return this;
    }

    public QueryParams builderParas() {
        if (null == sql) sql = new StringBuilder(); else sql.setLength(0);

        if (null != where)  sql.append(where.toFormatSQL());
        if (null != group)  sql.append(group.toFormatSQL());
        if (null != order)  sql.append(order.toFormatSQL());

        return this;
    }

    public QueryParams builder() {

        return this;
    }

    public Where where() {
        if (this instanceof Where){
            where = (Where) this;
            return where;
        }
        if (null == where){
            where = new Where();
            init(where);
        }
        return where;
    }

    public Where where(String key, Object value) {

        return where().add(key, value);
    }

    public Where where(String key, Object value, String prefix) {
        return where().add(key, value, prefix);
    }

    protected  QueryParams init(QueryParams params){
        params.where = where;
        params.group = group;
        params.order = order;
        params.alias = alias;
        return params;
    }
    public Order order() {
        if (null == order){
            order = new Order();
        }
        /*order.where = where;
        order.group = group;
        order.order = order;
        order.alias = alias;*/
        init(order);
        return order;
    }

    public Order order(String key) {
        return order().DESC(key);
    }

    public Order order(String key, String prefix) {
        return order().DESC(key, prefix);
    }

    public Group group() {
        if (null == group) {
            group = new Group();
        }
        init(group);
        return group;
    }

    public Group group(String key) {
        return group().add(key);
    }

    public Group group(String key, String prefix) {
        return group().add(key, prefix);
    }


    public Where WHERE() {
        return new Where();
    }

    public static Where WHERE(String key, Object value) {
        return new Where(key, value);
    }

    public static Where WHERE(String key, Object value, String prefix) {
        return new Where(key, value, prefix).where();
    }


    public static Order ORDER(String key) {
        Order order = new Order(key);

        return order;
    }

    public static Order ORDER(String key, String prefix) {
        return new Order(key, prefix);
    }

    public static Group GROUP(String key, String prefix) {
        return new Group(key, prefix);
    }

    public static Group GROUP(String key) {
        return new Group(key, null);
    }

    /**
     * @param whereSQL
     * @param attrs
     * @param values
     * @return
     */
    public static String toFormatSQL(String whereSQL, Map<String, Object> attrs, List<Object> values) {
        Matcher matcher = Pattern.compile(":([\\w\\$]+)").matcher(whereSQL);
        String rexp = null;
        while (matcher.find()) {
            String group = matcher.group(1);
            Object ov = attrs.get(group);
            if (ov instanceof List) {
                StringBuilder sb = new StringBuilder();
                List vs = (List) ov;
                for (Object v : vs) {
                    sb.append("?,");
                    values.add(v);
                }
                sb.deleteCharAt(sb.length() - 1);
                rexp = sb.toString();

            } else {
                values.add(ov);
                rexp = "?";
            }
            whereSQL = whereSQL.replace(String.format(":%s", group), rexp);
        }
        return whereSQL;
    }

    @Override
    public String toString() {
        return  sql.toString();
    }

}
