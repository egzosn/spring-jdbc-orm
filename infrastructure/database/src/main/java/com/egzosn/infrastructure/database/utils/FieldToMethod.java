package com.egzosn.infrastructure.database.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static java.util.Locale.ENGLISH;

/**
 * 根据类字段获取对应的属性方法
 * @author egan
 * @email egzosn@gmail.com
 * @date 2017/11/29
 */
public class FieldToMethod {

    static final String GET_PREFIX = "get";
    static final String SET_PREFIX = "set";
    static final String IS_PREFIX = "is";



    /**
     *  Gets the method that should be used to read the property value.
     * @param clazz class
     * @param field Fields corresponding to the class
     * @return The method that should be used to read the property value.
     * May return null if the property can't be read.
     * @throws NoSuchMethodException
     */
    public static synchronized Method getReadMethod(Class clazz, Field field) throws NoSuchMethodException {

        String readMethod = null;
        Class type = field.getType();

        if (type == boolean.class || type == null) {
            readMethod = IS_PREFIX ;
        }else {
            readMethod = GET_PREFIX;
        }
        return clazz.getMethod(readMethod + capitalize(field.getName()), null);

    }

    /**
     * Gets the method that should be used to write the property value.
     * @param clazz class
     * @param field Fields corresponding to the class
     * @return The method that should be used to write the property value.
     * May return null if the property can't be written.
     * @throws NoSuchMethodException
     */
    public static synchronized Method getWriteMethod(Class clazz, Field field) throws NoSuchMethodException {

        Class type = field.getType();
        return clazz.getMethod( SET_PREFIX + capitalize(field.getName()), new Class[]{type});

    }


    /**
     * Returns a String which capitalizes the first letter of the string.
     */
    static String capitalize(String name) {
        if (name == null || name.length() == 0) {
            return name;
        }
        return name.substring(0, 1).toUpperCase(ENGLISH) + name.substring(1);
    }

}
