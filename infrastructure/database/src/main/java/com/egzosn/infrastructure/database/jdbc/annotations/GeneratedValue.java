package com.egzosn.infrastructure.database.jdbc.annotations;

import com.egzosn.infrastructure.database.jdbc.id.GenerationType;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 *  主键生成值
 * @author egan
 * @email egzosn@gmail.com
 * @date 2017/11/27
 */
@Target({PACKAGE, TYPE, METHOD, FIELD})
@Retention(RUNTIME)
public @interface GeneratedValue {

    GenerationType strategy() default GenerationType.AUTO;

}
