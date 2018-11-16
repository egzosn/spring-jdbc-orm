package com.egzosn.infrastructure.database.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 *  通过别名表达式处理处理结果集方案
 *
 * 注意 <b>因别名不能超过30个字符，所以在处理的时候尽量是简短</b>
 *
 *  使用的方式 主要分为四种
 * 方式一： bean名称#方法名$数据库字段名别名，这里数据库字段名别名用于最后展示使用
 * 这里通过bean名称去匹配对应的处理对象
 *  <p>
 *      select A.PRODUCT_TYPE as "pr#getProductType$PRODUCT_TYPE" FROM TW_SERVICE_SETTLE_CONTRACT A
 *  </p>
 * 方式二： 方法名$数据库字段名别名，这里数据库字段名别名用于最后展示使用
 *  PubRestrict pubRestrict
 * 这里通过 new HeartAliasColumnMapRowMapper(pubRestrict) 的形式进行 pubRestrict是对象实例，然后规则内的方法名就是属于本实例的方法名
 *  <p>
 *      select A.PRODUCT_TYPE as "getProductType$PRODUCT_TYPE" FROM TW_SERVICE_SETTLE_CONTRACT A
 *  </p>
 * 方式三：集合key名称$数据库字段名别名，这里数据库字段名别名用于最后展示使用
 *  这里的集合属于字典表： Map<集合key名称, Map<记录数据, 需要转换的值>> 这样类型的一个集合，主要用于对结果整体的处理
 *   Map<String, Map<String, String>> propertys = new HashMap<String, Map<String, String>>();
 * 这里通过 new HeartAliasColumnMapRowMapper(propertys) 的形式进行, propertys是所有结果列里面存储的信息
 *  <p>
 *      select A.PRODUCT_TYPE as "PRODUCT_TYPE$PRODUCT_TYPE" FROM TW_SERVICE_SETTLE_CONTRACT A
 *  </p>
 * 方式四：k$数据库字段名别名，这里数据库字段名别名用于最后展示使用
 *  这里的集合属于字典表：  Map<记录数据, 需要转换的值> 这样类型的一个集合，主要用于对结果整体的处理
 *   Map<String, String> propertys = new HashMap<String,  String>();
 * 这里通过 new HeartAliasColumnMapRowMapper(propertys) 的形式进行, propertys是所有结果列里面存储的信息
 *  <p>
 *      select A.PRODUCT_TYPE as k$PRODUCT_TYPE FROM TW_SERVICE_SETTLE_CONTRACT A
 *  </p>
 * 方式五：(数据库字段名别名=集合key名称)$，这里数据库字段名别名用于最后展示使用
 *  这里的集合属于字典表： Map<集合key名称, Map<记录数据, 需要转换的值>> 这样类型的一个集合，主要用于对结果整体的处理
 *   Map<String, Map<String, String>> propertys = new HashMap<String, Map<String, String>>();
 * 这里通过 new HeartAliasColumnMapRowMapper(propertys) 的形式进行, propertys是所有结果列里面存储的信息
 *  <p>
 *      select A.PRODUCT_TYPE as "PRODUCT_TYPE$" FROM TW_SERVICE_SETTLE_CONTRACT A
 *  </p>
 *
 * 使用方式
 *  JdbcTemplate.query(sql, new AliasElColumnMapRowMapper(propertys)
 *
 *
 * Created by egan on 2018/11/13.
 * <br/>
 * email: egzosn@gmail.com
 */
public class AliasElColumnMapRowMapper extends ColumnMapRowMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(AliasElColumnMapRowMapper.class);
    WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
    private static final Map<String, Method> METHOD_MAP = new HashMap<String, Method>();





    /**
     * 转换bean实例，用于方案二的处理
     */
    private Object bean;
    /**
     * 方式四：k$数据库字段名别名
     */
    private Map<Object, Object> property;
    /**
     *  方式三：集合key名称$数据库字段名别名
     */
    private Map<String, Map<Object, Object>> propertys;

    public AliasElColumnMapRowMapper() {
    }
    /**
     * Map属性的方式
     *
     * 方式三：集合key名称$数据库字段名别名，这里数据库字段名别名用于最后展示使用
     *  这里的集合属于字典表： Map<集合key名称, Map<记录数据, 需要转换的值>> 这样类型的一个集合，主要用于对结果整体的处理
     *   Map<String, Map<String, String>> propertys = new HashMap<String, Map<String, String>>();
     * 这里通过 new HeartAliasColumnMapRowMapper(propertys) 的形式进行, propertys是所有结果列里面存储的信息
     *  <p>
     *      select A.PRODUCT_TYPE as PRODUCT_TYPE$PRODUCT_TYPE FROM TW_SERVICE_SETTLE_CONTRACT A
     *  </p>
     * 方式四：k$数据库字段名别名，这里数据库字段名别名用于最后展示使用
     *  这里的集合属于字典表：  Map<记录数据, 需要转换的值> 这样类型的一个集合，主要用于对结果整体的处理
     *   Map<String, String> propertys = new HashMap<String,  String>();
     * 这里通过 new HeartAliasColumnMapRowMapper(propertys) 的形式进行, propertys是所有结果列里面存储的信息
     *  <p>
     *      select A.PRODUCT_TYPE as k$PRODUCT_TYPE FROM TW_SERVICE_SETTLE_CONTRACT A
     *  </p>
     */
    public AliasElColumnMapRowMapper(Object handlerObj) {
        if (null == handlerObj){
            return;
        }
        if (!(handlerObj instanceof Map)){
            this.bean = handlerObj;
            return;
        }
        Map<Object, Object> property = (Map<Object, Object>)handlerObj;
        if ( property.isEmpty()) {
            return;
        }
        if ( property.entrySet().iterator().next().getValue() instanceof Map){
            this.propertys = (Map<String, Map<Object, Object>>) handlerObj;
            return;
        }
        this.property = property;


    }





    @Override
    public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {


        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        Map<String, Object> mapOfColValues = createColumnMap(columnCount);
        for (int i = 1; i <= columnCount; i++) {
            String key = getColumnKey(JdbcUtils.lookupColumnName(rsmd, i));

            Object[] elInfo = getElInfo(key);
            Object obj = getColumnValue(rs, i);
            if (null == elInfo || null == obj){
                mapOfColValues.put(key, obj);
                continue;
            }
//            LOGGER.info(JSON.toJSONString(elInfo));
           Object value = ((ElHandler)elInfo[1]).handler(obj, elInfo);
            if (obj == value){
                mapOfColValues.put(key, obj);
            }else {
                mapOfColValues.put((String) elInfo[0], value);
            }

        }
        return mapOfColValues;
    }


    /**
     * 根据字段别名获取表达式
     * @param key 字段别名
     * @return 返回格式 {别名,处理方案, (处理方法|集合key名称)?, 处理对象}
     */
    private Object[] getElInfo(String key){
        int end = key.lastIndexOf("$");
        if (end < 1){
            return null;
        }
        if (end + 1 == key.length()){
            String alias = key.substring(0, end);
            return new Object[]{alias, ElHandler.MAP_K, alias, propertys};
        }
        String alias = key.substring(end + 1);
        String el = key.substring(0, end);
        if (ElHandler.K.name().equals(el.toUpperCase())){
          if (null == property){
              return null;
          }
            return new Object[]{alias, ElHandler.K, property};
        }
        end = el.indexOf("#");
        if (end > 1){
            return new Object[]{alias, ElHandler.BEAN, el.substring(end + 1), wac.getBean(el.substring(0, end))};
        }
        if (null != bean){
            return new Object[]{alias, ElHandler.BEAN, el, bean};
        }
        if (null != propertys){
            return new Object[]{alias, ElHandler.MAP_K, el, propertys};
        }

        return null;


    }

    enum ElHandler{


        /**
         * 方式一，方式二使用
         */
        BEAN {
            /**
             * value处理器
             *
             * @param value      数据库查询结果
             * @param el         表达式集
             * @return 处理后的结果
             */
            @Override
            public Object handler(Object value, Object[] el) {
                Object handlerObj = el[3];
                Class<?> handlerClass = handlerObj.getClass();
                String methodName = (String) el[2];
                String key = handlerClass.getName() + "#" + methodName;
                Method method = METHOD_MAP.get(key);
                if (null == method){
                    method = BeanUtils.findMethod(handlerClass, methodName, value.getClass());
                    if (null == method){
                        return value;
                    }
                    METHOD_MAP.put(key, method);
                }
                try {
                    return method.invoke(handlerObj, value);
                } catch (IllegalAccessException e) {
                    AliasElColumnMapRowMapper.LOGGER.error("handlerObj 方法调用异常", e);
                } catch (InvocationTargetException e) {
                    AliasElColumnMapRowMapper.LOGGER.error("handlerObj 方法调用异常", e);
                }
                return value;
            }
        },
        /**
         * 方式三，方式五
         */
        MAP_K {
            /**
             * value处理器
             *
             * @param value      数据库查询结果
             * @param el         表达式集
             * @return 处理后的结果
             */
            @Override
            public Object handler(Object value, Object[] el) {

                if (null ==  el[3]){
                    return value;
                }
                Map<String, Map<Object, Object>> handlerObj = (Map<String, Map<Object, Object>>)el[3];
                if ( handlerObj.isEmpty()){
                    return value;
                }
                Map<Object, Object> property = handlerObj.get(el[2]);
                if (null == property || property.isEmpty()){
                    return value;
                }
                Object o = property.get(value);
                if (o != null){
                    return o;
                }
                return value;
            }
        },
        /**
         * 方式四
         */
        K {
            /**
             * value处理器
             *
             * @param value 数据库查询结果
             * @param el    表达式集
             * @return 处理后的结果
             */
            @Override
            public Object handler(Object value, Object[] el) {
                if (null ==  el[2]){
                    return value;
                }
                Map<Object, Object> handlerObj = (Map<Object, Object>)el[2];
                if (handlerObj.isEmpty() ){
                    return value;
                }
                Object o = handlerObj.get(value);
                if (o != null){
                    return o;
                }

                return value;
            }
        };

        /**
         *  value处理器
         * @param value 数据库查询结果
         * @param el 表达式集 {别名,处理方案, (处理方法|集合key名称)?, 处理对象}
         * @return 处理后的结果
         */
        public abstract Object handler(Object value, Object[] el);
    }


}
