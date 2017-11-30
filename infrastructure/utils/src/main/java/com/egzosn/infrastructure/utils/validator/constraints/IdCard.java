package com.egzosn.infrastructure.utils.validator.constraints;



import com.egzosn.infrastructure.utils.validator.constraintvalidators.IdCardValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by ZaoSheng on 2015/7/2.
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = IdCardValidator.class
)
public @interface IdCard {
    String message() default "IdCard 格式不正确";
    int erroCode() default 0;// 错误码
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default { };

    /**
     * Defines several {@code @NotBlank} annotations on the same element.
     */
    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
    @Retention(RUNTIME)
    @Documented
    @interface List {
        IdCard[] value();
    }
}
