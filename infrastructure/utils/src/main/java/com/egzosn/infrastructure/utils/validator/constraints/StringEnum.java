package com.egzosn.infrastructure.utils.validator.constraints;




import com.egzosn.infrastructure.utils.validator.constraintvalidators.StringEnumValidate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by linjie on 15-1-26.
 * 使用这个注解约束表单的字段值，如 type = {"wx","qq"} ，那么值就只能是wx 或qq 
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = StringEnumValidate.class)
public @interface StringEnum {

    String[] type() default {};

    String message() default "validate failure";

    boolean allowNull() default false;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
