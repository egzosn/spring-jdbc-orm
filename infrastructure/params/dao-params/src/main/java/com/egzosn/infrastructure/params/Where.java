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

import com.egzosn.infrastructure.params.enums.AndOr;
import com.egzosn.infrastructure.params.enums.Restriction;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by egan on 2015/8/5.
 * 条件集
 */
public class Where extends QueryParams {
    private String first = null;
    private Map<String, Object[]> wheres = new LinkedHashMap<String, Object[]>();
    protected Map<String, Object> attrs = new LinkedHashMap<String, Object>();
    protected List<Object> paras = new ArrayList<Object>();

    public Where() {
        where();
    }

    public Where setAlias(String alias) {
        this.alias = alias;
        return this;
    }


    public Where(String propertyName, Object value, String prefix) {

        add(propertyName, value, AndOr.NUL, Restriction.EQ, prefix);
    }

    public Where(String propertyName, Object value) {
        this(propertyName, value, null);
    }

    public Map<String, Object[]> getWheres() {
        return wheres;
    }


    public QueryParams builderAttrs() {
        if (null != getWhere()) {
            super.builderAttrs();
            return this;
        }
        if (null == sql) {
            sql = new StringBuilder();
        }
        sql.append(toSQL());
        return this;
    }

    public QueryParams builderParas() {
        if (null != getWhere()) {
            super.builderParas();
            return this;
        }
        if (null == sql) {
            sql = new StringBuilder();
        }
        sql.append(toFormatSQL());
        return this;
    }

    public Where and(String propertyName, Object value, Restriction restriction, String prefix) {
        add(propertyName, value, AndOr.AND, restriction, prefix);
        return this;
    }

    public Where and(String propertyName, Object value, Restriction restriction) {
        add(propertyName, value, AndOr.AND, restriction, null);
        return this;
    }

    public Where and(String propertyName, Object value) {
        return and(propertyName, value, Restriction.EQ);
    }

    public Where and(String propertyName, Object value, String prefix) {
        return and(propertyName, value, Restriction.EQ, prefix);
    }

    public Where or(String propertyName, Object value, Restriction restriction) {
        add(propertyName, value, AndOr.OR, restriction, null);
        return this;
    }

    public Where or(String propertyName, Object value, Restriction restriction, String prefix) {
        add(propertyName, value, AndOr.OR, restriction, prefix);
        return this;
    }

    public Where or(String propertyName, Object value) {
        return or(propertyName, value, Restriction.EQ);
    }

    public Where or(String propertyName, Object value, String prefix) {
        return or(propertyName, value, Restriction.EQ, prefix);
    }

    public Where add(String propertyName, Object value) {
        add(propertyName, value, null);
        return this;
    }

    public Where add(String propertyName, Object value, String prefix) {
        if (null == first) first = propertyName;
        add(propertyName, value, AndOr.NUL, Restriction.EQ, prefix);
        return this;
    }

    protected Where add(String key, Object value, AndOr andor, Restriction restriction, String prefix) {
        if (null == first) {
            first = key;
            andor = AndOr.NUL;
        }
        if (null == prefix) {
            prefix = alias;
        }
        switch (restriction) {
            case NUL:
            case NNUL:
                wheres.put(key, new Object[]{null, andor, restriction, prefix});
                break;
            default:
                if (null == value || "".equals(value)) {
                    if (key.equals(first)) {
                        first = wheres.keySet().iterator().next();
                    }
                    wheres.remove(key);
                } else {
                    wheres.put(key, new Object[]{value, andor, restriction, prefix});
                }
        }
        return this;

    }


    public String toSQL() {
        if (wheres.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        if (null != first) setSql(first, wheres.get(first), sb);

        for (String key : wheres.keySet()) {
            if (key.equals(first)) {
                continue;
            }
            Object[] objects = wheres.get(key);
            setSql(key, objects, sb);
        }

        return " where " + sb.toString();
    }

    private void setSql(String key, Object[] objects, StringBuilder sb) {
        AndOr andOr = (AndOr) objects[1];
        Restriction restriction = (Restriction) objects[2];
        String prefix = null == objects[3] ? "" : (objects[3] + ".");
        switch (restriction) {
            case LK:
            case LLK:
            case RLK:
                sb.append(andOr.toMatchString(prefix + key, " like :" + key));
                attrs.put(key, restriction.toMatchString(objects[0].toString()));
                break;
            case NUL:
            case NNUL:
                sb.append(andOr.toMatchString(prefix, restriction.toMatchString(key)));
                break;
            case BW:
                sb.append(andOr.toMatchString(prefix, restriction.toMatchString(key)));
                Object[] value = (Object[]) objects[0];
                attrs.put(String.format("%s1", key), value[0]);
                attrs.put(String.format("%s2", key), value[1]);
                break;
            default:
                sb.append(andOr.toMatchString(prefix + key, restriction.toMatchString(key)));
                attrs.put(key, objects[0]);
        }
    }

    @Override
    public String toFormatSQL() {
        return toFormatSQL(toSQL(), attrs, paras);
    }


    public String alias() {
        return alias;
    }


    @Override
    public Map<String, Object> getAttrs() {
        return attrs;
    }

    @Override
    public List<Object> getParas() {
        return paras;
    }

}
