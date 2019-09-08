package com.egzosn.infrastructure.params;

/**
 * 字段与列标识
 * Created by egan on 2018/11/2.
 * <a href="mailto:egzosn@gmail.com">郑灶生</a>
 * <br/>
 * email: egzosn@gmail.com
 */
public interface Field4Column {


    /**
     * 获取字段名称
     * @return 字段名称
     */
    String getField();

    /**
     * 获取列
     * @return 列名
     */
    String getColumn();

    /**
     * 获取select 字段  列名 别名(字段名)
     * @return 获取select
     */
    String getSelect();
    /**
     * 获取select 字段  列名 别名(字段名)
     * @param prefix 表别名
     * @return 获取select
     */
    String getSelect(String prefix);
}
