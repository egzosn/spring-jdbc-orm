package com.egzosn.infrastructure.params.utils;



import com.egzosn.infrastructure.params.Params;
import com.egzosn.infrastructure.params.Where;
import com.egzosn.infrastructure.params.bean.Page;
import com.egzosn.infrastructure.params.enums.Restriction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *  JDBC 基础操作
 * @author egan
 * @email egzosn@gmail.com
 * @date 2017/11/30
 */
public class BaseJdbcRepository<T> extends SupportJdbcRepository<T>  {

    public BaseJdbcRepository(Class<T> entityClass) {
        super(entityClass);
    }

    public BaseJdbcRepository() {
    }

    public BaseJdbcRepository(String table) {
        super(table);
    }

    /**
     * 获取sql
     * @param select
     * @param params
     * @return
     */
    protected String getSQL(String select, Params params) {

        return SQLTools.getSQL(select, getTable(), params.alias(), params.builderParas().getSqlString());
    }

    /**
     * 通过orm实体属性名称查询全部
     *
     * @param propertyName
     *            orm实体属性名称
     * @param value
     *            值
     * @return List
     */
    public List<T> findByProperty(String propertyName, Object value) {
        return findByProperty(propertyName, value, null);
    }

    /**
     * 通过orm实体属性名称查询全部
     *
     * @param propertyName
     *            orm实体属性名称
     * @param value
     *            值
     * @param restriction
     *            约束名称 参考 {@link Restriction}
     * @return List
     */
    @SuppressWarnings("unchecked")
    public List<T> findByProperty(String propertyName, Object value, Restriction restriction) {
        if (null == propertyName || "".equals(propertyName) || null == value){
            return new ArrayList<T>();
        }
        if (null == restriction) {
            restriction = Restriction.EQ;
        }

        Where where = new Where();
        where.setAlias("z");
        where.and(propertyName, value, restriction, "z");

        return queryEntityList(getSQL("*", where), where.getParas().toArray());

    }

    /**
     * 通过orm实体的属性名称查询单个orm实体
     *
     * @param propertyName
     *            属性名称
     * @param value
     *            值
     * @return Object
     */
    public T findUniqueByProperty(String propertyName, Object value) {
        return findUniqueByProperty(propertyName, value, null);
    }

    /**
     * 通过orm实体的属性名称查询单个orm实体
     *
     * @param propertyName
     *            属性名称
     * @param value
     *            值
     * @param restriction
     *            约束名称 参考 {@link Restriction} 的所有实现类
     * @return Object
     */
    public T findUniqueByProperty(String propertyName, Object value, Restriction restriction) {

        if (null == restriction) {
            restriction = Restriction.EQ;
        }

        Where where = new Where();
        where.setAlias("z");
        where.and(propertyName, value, restriction, "z");
        return uniqueQuery(where);

    }




    public T uniqueQuery(Params params) {

        return uniqueQueryEntity(getSQL("*", params), params.getParas().toArray());
    }


    /**
     *  分页查找
     * @param params 查询参数集
     * @param isPage 是否分页
     * @return
     */
    public Page<T> queryPage(Params params, boolean isPage) {
        String sql = getSQL("*", params);
        return  isPage ? pageQueryEntity(sql,  params.getPage().getPageIndex(), params.getPage().getPageSize(), params.getParas().toArray()) : pageQueryEntity(sql, params.getParas());
    }

    /**
     *  分页查找
     * @param params 查询参数集
     * @param isPage 是否分页
     * @return
     */
    public List<T> queryList(Params params, boolean isPage) {
        String sql = getSQL("*", params);
        return  isPage ? queryEntityList(sql,  params.getPage().getPageIndex(), params.getPage().getPageSize(), params.getParas().toArray()) : queryEntityList(sql, params.getParas());
    }

    /**
     * @param updateField 需要更新的字段
     * @param params      条件参数
     */
    public int update(Map<String, Object> updateField, Params params) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("update %s {alias} set ", getTable()));
        for (String colName : updateField.keySet()) {
            sb.append(String.format("{alias}.%s=:%s, ", colName, colName));
        }
        sb.deleteCharAt(sb.length() - 2);
        String hql = sb.toString().replace("{alias}", params.alias()) + params.builderAttrs().getSqlString();
        updateField.putAll(params.getAttrs());
        return update(hql, updateField);
    }



}
