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

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

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
    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * @param object 需要校验的队形
     * @param <T>
     * @return
     * @throws ConstraintViolationException 约束违反异常
     */
    public static <T>T validate(T object){

        Set<ConstraintViolation<T>> constraintViolations = validator.validate(object);
        //判断是否有对应的校验异常集
        if (!constraintViolations.isEmpty()){
            throw  new ConstraintViolationException(constraintViolations);
        }
        return  object;
    }

    /**
     *  校验对象的某个参数
     * @param object 需要校验的对象
     * @param propertyName 校验的参数
     * @param <T>
     * @return
     * @throws ConstraintViolationException 约束违反异常
     */
    public static <T>T  validateProperty(T object, String propertyName){
        Set<ConstraintViolation<T>> constraintViolations = validator.validateProperty(object, propertyName);
        //判断是否有对应的校验异常集
        if (!constraintViolations.isEmpty()){
            throw  new ConstraintViolationException(constraintViolations);
        }
        return  object;
    }

}
