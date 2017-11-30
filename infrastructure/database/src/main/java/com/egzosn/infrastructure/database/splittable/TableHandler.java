package com.egzosn.infrastructure.database.splittable;

/**
 *  分表处理器
 * @author egan
 * @email egzosn@gmail.com
 * @date 2017/11/22
 */
public interface TableHandler<T> {
    /**
     *  获取分表表名的处理器
     * @param prefix             分表的前缀
     * @param field              字段名
     * @param value        字段对应的值
     * @return 分表表名
     */
    String handler(String prefix, String field, T value);
}
