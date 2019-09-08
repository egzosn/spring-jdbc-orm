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

import java.util.ArrayList;
import java.util.List;

/**
 * 分组对象
 * <p>
 * Created by egan on 2015/8/10.
 * <pre>
 *     email egzosn@gmail.com
 *  </pre>
 */
public class Group extends QueryParams {
    private List<String[]> groups = null;

    public Group() {
    }

    public Group(String key) {
        add(key, null);
    }

    public Group(String key, String prefix) {
        add(key, prefix);
    }

    public Group add(String key) {

        return add(key, null);
    }

    public QueryParams builderAttrs() {
        if (null != getGroup()) {
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
        if (null != getGroup()) {
            super.builderParas();
        }
        if (null == sql) {
            sql = new StringBuilder();
        }
        sql.append(toFormatSQL());
        return this;
    }

    public Group add(String key, String prefix) {
        if (key == null || "".equals(key)) {
            return this;
        }

        if (null == groups) {
            groups = new ArrayList<String[]>();
        }
        if (null == prefix) {
            prefix = alias;
        }
        groups.add(new String[]{key, prefix});

        return this;
    }

    @Override
    public String toFormatSQL() {
        return toSQL();
    }

    @Override
    public String toSQL() {
        if (null == groups || groups.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(" Group by ");
        for (String[] value : groups) {
            sb.append(String.format("%s%s, ", null == value[1] ? "" : (value[1] + '.'), value[0]));
        }
        sb.deleteCharAt(sb.length() - 2);
        return sb.toString();
    }


}
