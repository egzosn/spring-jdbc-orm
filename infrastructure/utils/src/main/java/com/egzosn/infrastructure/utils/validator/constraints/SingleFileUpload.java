package com.egzosn.infrastructure.utils.validator.constraints;




import com.egzosn.infrastructure.utils.validator.constraintvalidators.SingleFileUploadValidate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Target( { METHOD, FIELD, ANNOTATION_TYPE,PARAMETER })
@Retention(RUNTIME)
@Constraint(validatedBy = SingleFileUploadValidate.class)
/**
 *  @author  Linjie
 *  验证上传文件的正确性，可验证大小是否合适，文件格式是否正确等。
 *  可直接设置参数值:size 和 contentTypes 两个参数
 *  size 单位为KB, 默认为 0 ，代表不限制，contentTypes 默认为空数组，代表不限制。
 *  如:
 *  size = 1000000l
 *  contentTypes = {"image/png","image/jpg"}
 *
 *  也可以采用配置文件(useConfig)，配置文件和size,contentTypes 为互斥，useConfig优先
 */
public @interface SingleFileUpload {

    /**
     * @return
     */
    long size()  default  0L;
    String[] contentTypes() default {};
    boolean allowEmpty() default false;
    String message() default "文件不存在！";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
