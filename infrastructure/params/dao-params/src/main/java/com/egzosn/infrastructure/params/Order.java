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

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by egan on 2015/8/5.
 * 排序
 */
public class Order extends QueryParams {
    private Map<String, String[]> orders = null;

    public enum OrderAD {
        ASC, DESC
    }

    public Order() {

    }

    public Order(String key, String prefix) {
        add(key, OrderAD.DESC, prefix);
    }

    public Order(String key) {
        add(key, OrderAD.DESC, null);
    }

    public Order DESC(String key) {
        return add(key, OrderAD.DESC, null);
    }

    public Order DESC(String key, String prefix) {
        return add(key, OrderAD.DESC, prefix);
    }

    public Order ASC(String key, String prefix) {
        return add(key, OrderAD.ASC, prefix);
    }

    public Order ASC(String key) {
        return add(key, OrderAD.ASC, null);
    }

    public QueryParams builderAttrs() {
        if (null != getOrder()) {
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
        if (null != getOrder()) {
            super.builderParas();
            return this;
        }
        if (null == sql) {
            sql = new StringBuilder();
        }
        sql.append(toFormatSQL());
        return this;
    }

    protected Order add(String key, OrderAD value, String prefix) {
        if (null == orders) {
            orders = new LinkedHashMap<String, String[]>();
        }
        if (null == prefix) {
            prefix = alias;
        }
        if (null == value) {
            orders.remove(key);
        } else {
            orders.put(key, new String[]{value.name(), prefix});
        }

        return this;
    }

    @Override
    public String toFormatSQL() {
        return toSQL();
    }

    @Override
    public String toSQL() {
        if (null == orders || orders.isEmpty()) return "";

        StringBuilder sb = new StringBuilder();
        sb.append(" Order by ");
        for (String key : orders.keySet()) {
            String[] value = orders.get(key);
            sb.append(String.format("%s%s %s, ", null == value[1] ? "" : (value[1] + '.'), key, value[0]));
        }
        sb.deleteCharAt(sb.length() - 2);
        return sb.toString();
    }


    public Map<String, String[]> getOrders() {
        return orders;
    }


}