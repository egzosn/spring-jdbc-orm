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

import java.util.Map;
import java.util.Set;

/**
 * Created by egan on 2017/6/14.
 */
public class SqlFilterRequest {

    private Map<String, String[]> request;// 为了获取request里面传过来的动态参数

    public Set<String> getParameterNames(){

        return request.keySet();
    }

    public String[] getParameterValues(String key){
        return request.get(key);
    }

    public SqlFilterRequest(Map<String, String[]> request) {
        this.request = request;
    }

    public String getParameter(String key){
        String[] values = request.get(key);
        if (null == values || values.length == 0){
            return null;
        }
        return values[0];
    }

}
