package com.egzosn.infrastructure.database.splittable;


import java.lang.annotation.*;

/**
 *  分表字段标识
 * @author egan
 * @email egzosn@gmail.com
 * @date 2017/11/22
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@SplitTableField
public @interface SplitTable {
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

    /**
     *
     * @return 分表所需的字段
     */
    String field();



}
