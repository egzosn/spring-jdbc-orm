package com.egzosn.infrastructure.database.jdbc.bean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 存储列的信息
 * @author  egan
 * @email egzosn@gmail.com
 * @date 2016-6-23 14:51:17
 */
public class Column {
    /**
     * 列名
     */
    private String name;
    /**
     * 字段名
     */
    private String fieldName;
    /**
     * 字段类型
     */
    private Class type;
    /**
     * 字段对应的可写方法
     */
    private Method writeMethod;
    /**
     * 字段对应的可读方法
     */
    private Method readMethod;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public Method getWriteMethod() {
        return writeMethod;
    }

    public void setWriteMethod(Method writeMethod) {
        this.writeMethod = writeMethod;
    }

    public Method getReadMethod() {
        return readMethod;
    }

    public void setReadMethod(Method readMethod) {
        this.readMethod = readMethod;
    }

    /**
     * 根据对象获取字段对应的值
     * @param object 对象实例
     * @return 字段值
     */
    public Object getFieldValue(Object object){

        try {
            return getReadMethod().invoke(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Column() {
    }

    @Override
    public String toString() {
        return "Column{" +
                "name='" + name + '\'' +
                ", fieldName='" + fieldName + '\'' +
                ", type=" + type +
                ", writeMethod=" + writeMethod +
                ", readMethod=" + readMethod +
                '}';
    }
}
