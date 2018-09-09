/*
 * Copyright 2002-2017 the original huodull or egan.
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


package com.egzosn.infrastructure.utils.validator;

import org.springframework.validation.BindException;
import org.springframework.web.bind.WebDataBinder;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;

/**
 * 校验器工具
 * @author: egan
 * @email egzosn@gmail.com
 * @date 2016/11/16 11:54
 */
public class ValidateUtils {
    /**
     * 针对校验使用
     */
    private static final ValidatorAdapter validator = new ValidatorAdapter(Validation.buildDefaultValidatorFactory().getValidator());

    /**
     * @param object 需要校验的队形
     * @param <T>
     * @return
     * @throws ConstraintViolationException 约束违反异常
     */
    public static <T>T validate(T object) throws BindException {
        WebDataBinder binder = new WebDataBinder(object, object.getClass().getSimpleName());
        validator.validate(object, binder.getBindingResult());
        if (binder.getBindingResult().hasErrors() ) {
            throw new BindException(binder.getBindingResult());
        }
        return  object;
    }

    /**
     *  校验对象的某个参数
     * @param object 需要校验的对象
     * @param propertyName 校验的参数
     * @param <T>
     * @return
     * @throws BindException 字段绑定异常
     */
    public static <T>T  validateProperty(T object, String propertyName) throws BindException {
        WebDataBinder binder = new WebDataBinder(object, object.getClass().getSimpleName());
        validator.validateProperty(object, propertyName, binder.getBindingResult());
        if (binder.getBindingResult().hasErrors() ) {
            throw new BindException(binder.getBindingResult());
        }

        return  object;
    }

}
