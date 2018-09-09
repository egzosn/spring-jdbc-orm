package com.egzosn.infrastructure.database.jdbc.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 忽略ORM映射字段
 * @author egan
 *         email egzosn@gmail.com
 *         date 2018/9/9.17:28
 */
@Target({ FIELD})
@Retention(RUNTIME)
public @interface Ignore {
}
