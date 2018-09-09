package com.egzosn.infrastructure.database.jdbc;

import com.egzosn.infrastructure.database.jdbc.bean.Column;
import com.egzosn.infrastructure.database.splittable.TableHandler;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * 分表描述
 *
 * @author egan
 * @email egzosn@gmail.com
 * @date 2017/11/29
 */
public class SplitTableDescriptor {

    /**
     * 分表的前缀
     */
    private String prefix;

    /**
     * 分表对应的处理器
     */
    private TableHandler handler;

    /**
     * 需要处理分表的字段
     */
    private String field;


    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public TableHandler getHandler() {
        return handler;
    }

    public <X> void setHandler(TableHandler<X> handler) {
        this.handler = handler;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    /**
     * 获取分表的表名
     * @param column 分表对应的列
     * @param entity 实体
     * @return 表名
     */
    public String getTableName(Column column, Object entity) {

        try {
            Object value = column.getReadMethod().invoke(entity);
            String tableName = getHandler().handler(getPrefix(), getField(), value);
            if (!StringUtils.isEmpty(tableName)) {
                return tableName;
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return "";
    }
}
