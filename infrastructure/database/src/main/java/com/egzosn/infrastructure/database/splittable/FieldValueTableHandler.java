package com.egzosn.infrastructure.database.splittable;

/**
 * 字段值作为分表的名字
 * @author egan
 * @email egzosn@gmail.com
 * @date 2017/11/22
 */
public class FieldValueTableHandler implements TableHandler<String> {


    /**
     * 获取分表表名的处理器
     *
     * @param prefix 分表的前缀
     * @param field  字段名
     * @param value  字段对应的值
     *
     * @return 分表表名
     */
    @Override
    public String handler(String prefix, String field, String value) {
        if (null == value){
            throw new NullPointerException("Field is null to name: " + field);
        }

        return prefix + value.toLowerCase();
    }
}
