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


package com.egzosn.infrastructure.utils.json;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.egzosn.infrastructure.utils.validator.ValidateUtils;
import org.springframework.validation.BindException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.util.List;

/**
 *  Josn转对象并进行校验
 * @author: egan
 * @email egzosn@gmail.com
 * @date 2016/11/16 11:37
 */
public class JsonValidateMapper {

    /**
     * 将字符串转成实体类，允许斜杠等字符串
     */
    public static <T> T jsonToEntity(String json, Class<T> clazz) throws IOException, BindException {
        ObjectMapper mapper = new ObjectMapper();
        // 允许反斜杆等字符
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS,true);
        return ValidateUtils.validate(mapper.readValue(json, clazz));
    }

    /**
     *  根据字符串转对应的类型，并对对象进行字段校验
     * @param text josn字符
     * @param clazz 转化的类
     * @param <T> 对象
     * @return
     */
    public static <T> List<T> parseArray(String text, Class<T> clazz) throws BindException {

        return ValidateUtils.validate(JSON.parseArray(text, clazz));
    }

    /**
     *  根据字符串转对应的类型，并对对象进行字段校验
     * @param text josn字符
     * @param clazz 转化的类
     * @param <T> 对象
     * @return
     */
    public static <T> T parseObject(String text, Class<T> clazz) throws BindException {

        return ValidateUtils.validate(JSON.parseObject(text, clazz));
    }


}
