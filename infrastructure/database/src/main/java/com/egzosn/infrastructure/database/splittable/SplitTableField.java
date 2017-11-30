package com.egzosn.infrastructure.database.splittable;


import java.lang.annotation.*;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;

/**
 *  分表字段标识
 * @author egan
 * @email egzosn@gmail.com
 * @date 2017/11/22
 */
@Target({TYPE, FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SplitTableField {
    /**
     *  是否需要主表当成分表的前缀,
     * @return  是否主表当成分表的前缀
     */
    boolean prefix() default true;

    /**
     *  获取表名称的处理器 默认字段值处理器
     * @return 获取表名称的处理器
     */
    Class handler() default FieldValueTableHandler.class;

}
