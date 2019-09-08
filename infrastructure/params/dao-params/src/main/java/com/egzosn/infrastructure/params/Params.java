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

/**
 * Created by egan on 2015/7/15.
 * 查询参数集
 */
public interface Params {

    String toFormatSQL();

    String toSQL();

    public Map<String, Object> getAttrs();

    public List<Object> getParas();

    public Pageing getPage();

    public QueryParams builderAttrs();

    public QueryParams builderParas();

    public String alias();

}
