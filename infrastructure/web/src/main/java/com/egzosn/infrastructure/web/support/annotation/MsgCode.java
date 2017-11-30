package com.egzosn.infrastructure.web.support.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by  on 2014/10/24.
 */
 @Target( {  FIELD ,PARAMETER})
 @Retention(RUNTIME)
public @interface MsgCode {

    int value() default -1;
}
