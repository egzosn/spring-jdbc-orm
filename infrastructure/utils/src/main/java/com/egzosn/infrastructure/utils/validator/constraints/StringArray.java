package com.egzosn.infrastructure.utils.validator.constraints;

import com.egzosn.infrastructure.utils.validator.constraintvalidators.StringArrayValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by xs on 2016/11/17.
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE ,PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = StringArrayValidator.class)
public @interface StringArray {
    //正则表达式
    String regexp() default "";

    String message() default "validate failure";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
