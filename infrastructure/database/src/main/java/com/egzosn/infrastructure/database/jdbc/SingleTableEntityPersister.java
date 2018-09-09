package com.egzosn.infrastructure.database.jdbc;

import com.egzosn.infrastructure.database.jdbc.annotations.GeneratedValue;
import com.egzosn.infrastructure.database.jdbc.annotations.Id;
import com.egzosn.infrastructure.database.jdbc.annotations.Ignore;
import com.egzosn.infrastructure.database.jdbc.annotations.Table;
import com.egzosn.infrastructure.database.jdbc.bean.Column;
import com.egzosn.infrastructure.database.jdbc.id.GenerationType;
import com.egzosn.infrastructure.database.jdbc.id.IdField;
import com.egzosn.infrastructure.database.splittable.SplitTable;
import com.egzosn.infrastructure.database.splittable.SplitTableField;
import com.egzosn.infrastructure.database.splittable.TableHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.util.*;

import static com.egzosn.infrastructure.database.utils.FieldToMethod.getReadMethod;
import static com.egzosn.infrastructure.database.utils.FieldToMethod.getWriteMethod;

/**
 *  记录 ORM对应实体的信息
 * @author egan
 * @email egzosn@gmail.com
 * @date 2017/11/28
 */
public class SingleTableEntityPersister<T> {
    private static Logger logger = LoggerFactory .getLogger(SingleTableEntityPersister.class);

    /**
     * 表名
     */
    private String tableName;
    /**
     * 数据库列集
     */
    private Map<String, Column> columns;
    /**
     * 实体字段集,预防查询结果取别名问题
     */
    private Map<String, Column> fields;
    /**
     * ORM映射的实体
     */
    private Class<T> entityClass;

    /**
     * 主键字段
     */
    private IdField idField;
    /**
     * 分表描述，记录ORM对应分表的信息
     */
    private SplitTableDescriptor splitTableDescriptor;


    /**
     *    列的名字 与一下字段语句一一对应
     *   {@link #sqlInsertString}
     *   {@link #sqlUpdateByRowIdString}
     */
    private List<String>[] columnNames = new  List[2];
    /**
     * ORM对应的插入语句
     */
    private String sqlInsertString;
    /**
     * ORM对应的更新语句
     */
    private String sqlUpdateByRowIdString;

    /**
     * ORM对应的删除语句
     */
    private String sqlDeleteString;




    public SingleTableEntityPersister(Class<T> entityClass) {
        this.entityClass = entityClass;
        init();
    }

    /**
     * 初始化
     */
    public void init(){
        Table table = entityClass.getAnnotation(Table.class);

        if (null == table || StringUtils.isEmpty(table.name())){
            tableName = entityClass.getSimpleName();
        }else {
            tableName = table.name();
        }
        initSplitTable();
        try {
            setColumns(table.columnFlag());
        } catch (NoSuchMethodException e) {
            logger.error("找不到方法", e);
        }
        sqlInsertString = SQLTools.generateInsertString(String.format("$$%s$$", tableName), columnNames[0]).toString();
        sqlUpdateByRowIdString = SQLTools.generateUpdateByRowIdString(String.format("$$%s$$", tableName), columnNames[1], idField.getColumn()).toString();
        sqlDeleteString = String.format("delete from %s ", tableName);
    }


    /**
     * 初始化分表信息
     */
    private void initSplitTable(){
        SplitTable splitTable = entityClass.getAnnotation(SplitTable.class);
        if (null != splitTable && !"".equals(splitTable.field())){
            if (null == splitTableDescriptor ){
                splitTableDescriptor = new SplitTableDescriptor();
            }
            splitTableDescriptor.setField(splitTable.field());
            splitTableDescriptor.setHandler(BeanUtils.<TableHandler>instantiateClass(splitTable.handler()));
            splitTableDescriptor.setPrefix(splitTable.prefix() ?  (tableName + "_") : "" );
        }
    }
    /**
     *
     * 初始化字段分表信息
     * @param field 字段
     */
    private void initSplitTable(Field field){
        SplitTableField splitTable = field.getAnnotation(SplitTableField.class);
        if (null != splitTable){
            splitTableDescriptor = new SplitTableDescriptor();
            splitTableDescriptor.setField(field.getName());
            splitTableDescriptor.setHandler(BeanUtils.<TableHandler>instantiateClass(splitTable.handler()));
            splitTableDescriptor.setPrefix(splitTable.prefix() ?  (tableName + "_") : "" );
        }
    }


    /**
     * 设置列
     * @param columnFlag  是否需要 {@link com.egzosn.infrastructure.database.jdbc.annotations.Column} 进行字段映射，默认 true 需要 {@link com.egzosn.infrastructure.database.jdbc.annotations.Column}进行字段列标识
     *   当 columnFlag 值为 false 时 没有 {@link com.egzosn.infrastructure.database.jdbc.annotations.Column} 进行标识时则用字段名称
     *  <p/>
     * @throws NoSuchMethodException 找不到方法异常
     */
    private void setColumns(boolean columnFlag) throws NoSuchMethodException {
        Field[] fields = entityClass.getDeclaredFields();
        columns = new HashMap<String, Column>(fields.length);
        this.fields = new HashMap<String, Column>(columns.size());
        columnNames[0] = new ArrayList<String>(columns.size());
        columnNames[1] = new ArrayList<String>(columns.size());
        for (Field field : fields) {
            Column column = new Column();
            com.egzosn.infrastructure.database.jdbc.annotations.Column c = field.getAnnotation( com.egzosn.infrastructure.database.jdbc.annotations.Column.class);
            if (columnFlag && null == c || field.isAnnotationPresent(Ignore.class)){
               continue;
            }


            if ( null == c || "".equals(c.name()) ){
                column.setName(field.getName());
            }else {
                column.setName(c.name());
            }

            column.setFieldName(field.getName());
            column.setWriteMethod(getWriteMethod(entityClass, field));
            column.setReadMethod(getReadMethod(entityClass, field));
            column.setType(field.getType());
            columns.put(column.getName(),  column);
            this.fields.put(column.getFieldName(),  column);

            columnNames[0].add( column.getName());

            //设置主键字段
            if (!setIdField(field, column)){
                columnNames[1].add( column.getName());
            }
            //初始化字段对应的分表信息
            initSplitTable(field);
        }
    }

    /**
     * 设置主键字段
     * @param field 字段
     * @param column 对应的列
     * @return
     */
    private boolean setIdField(Field field, Column column) {
        Id id = field.getAnnotation(Id.class);
        if (null == id){
            return false;
        }
        idField = new IdField();
        idField.setColumn(column.getName());
        idField.setName(column.getFieldName());
        idField.setType(column.getType());
        idField.setWriteMethod(column.getWriteMethod());
        idField.setReadMethod(column.getReadMethod());
        GeneratedValue generatedValue =  field.getAnnotation(GeneratedValue.class);
        if (null != generatedValue){
            idField.setStrategy(generatedValue.strategy());
            if (GenerationType.AUTO == idField.getStrategy()){
                columnNames[0].remove(columnNames[0].size() - 1);
            }
        }

        return true;
    }

    public String getTableName() {
        return tableName;
    }

    public Map<String, Column> getColumns() {
        return columns;
    }

    public Map<String, Column> getFields() {
        return fields;
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }

    public IdField getIdField() {
        return idField;
    }



    /**
     * 获取插入语句
     * @return 插入语句
     */
    public String getSqlInsertString() {
        return getSqlInsertString(this.tableName);
    }

    /**
     * 获取插入语句
     * @return 插入语句
     */
    public String getSqlInsertString(String tableName) {
        return sqlInsertString.replace(String.format("$$%s$$", this.tableName), tableName);
    }

    /**
     * 获取分表插入语句
     * @return 插入语句
     */
    public String getSqlInsertString(T entity) {
        if (null != splitTableDescriptor){
            Column column = fields.get(splitTableDescriptor.getField());
            return getSqlInsertString(splitTableDescriptor.getTableName(column, entity));
        }
        return getSqlInsertString();
    }

    /**
     * 获取更新语句来自于id
     * @return 更新语句
     */
    public String getSqlUpdateByRowIdString() {
        return getSqlUpdateByRowIdString(this.tableName);
    }

    /**
     * 获取插入语句
     * @return 插入语句
     */
    public String getSqlUpdateByRowIdString(String tableName) {
        return sqlUpdateByRowIdString.replace(String.format("$$%s$$", this.tableName), tableName);
    }



    /**
     * 获取更新语句来自于id
     * @return 更新语句
     */
    public String getSqlUpdateByRowIdString(T entity) {
        if (null != splitTableDescriptor){
            Column column = fields.get(splitTableDescriptor.getField());
            return getSqlUpdateByRowIdString(splitTableDescriptor.getTableName(column, entity));
        }
        return getSqlUpdateByRowIdString();
    }

    /**
     * 获取删除语句来自于id
     * @return 删除语句
     */
    public String getSqlDeleteByRowIdString() {
        return String.format("%s where %s = ?", sqlDeleteString, getIdField().getColumn()) ;
    }

    /**
     * 获取删除语句来自于多个id
     * @return 删除语句
     */
    public String getSqlDeleteByRowIdString(int num) {

        return String.format("%s where %s in( %s)", sqlDeleteString, getIdField().getColumn(), SQLTools.forQuestionMarkSQL(num)) ;
    }

    /**
     * 获取插入语句所对应的key
     * @return 插入语句对应的参数集
     */
    public List<String> getInsertColumnNames() {
        return columnNames[0];
    }

    /**
     * 获取更新语句对应的列集
     * @return  更新语句对应的列集
     */
    public List<String> getUpdateByRowIdKeyColumnNames() {
        return columnNames[1];
    }


    /**
     * 对应实体id字段进行设值
     * @param entity 实体
     * @param ps 数据库操作对象
     */
    public Serializable idGenerated(T entity, PreparedStatement ps){

        //判断是否有id生成策略
        boolean isIdGeneratedStrategy = null != this.getIdField() && this.getIdField().isIdGeneratedStrategy();
        if (isIdGeneratedStrategy){
            //id进行设值
            return this.getIdField().idGenerated(entity, ps);
        }
        return null;
    }
    /**
     * 对应实体id字段进行设值
     * @param entitys 实体集
     * @param ps 数据库操作对象
     */
    public Collection<Serializable> idGenerated(Collection<T> entitys, PreparedStatement ps){

        //判断是否有id生成策略
        boolean isIdGeneratedStrategy = null != this.getIdField() && this.getIdField().isIdGeneratedStrategy();
        if (isIdGeneratedStrategy){
            //id进行设值
           return this.getIdField().idGenerated(entitys, ps);
        }
        return null;
    }


    /**
     * 获取字段集对应实体的值
     * @param columnNames 字段集
     * @param entity 实体
     * @return 字段集对应的值
     */
    private Object[] getFieldValues(List<String> columnNames, T entity){

        Object[] values = new Object[columnNames.size()];

        Map<String, Column> fields = getColumns();
        for (int i = 0, size = columnNames.size(); i < size; i++){
            Column column = fields.get(columnNames.get(i));
            values[i] = column.getFieldValue(entity);
        }

        return values;
    }

    /**
     * 获取插入字段集对应的值
     * @param entity 实体
     * @return 字段集对应的值
     */
    public Object[] getInsertFieldValues(T entity){
        return getFieldValues(this.getInsertColumnNames(), entity);

    }

    /**
     * 获取更新字段集对应的值
     * @param entity 实体
     * @return 字段集对应的值
     */
    public Object[] getUpdateByRowIdKeyFieldValues(T entity){
        return getFieldValues(this.getUpdateByRowIdKeyColumnNames(), entity);

    }

    /**
     * 获取插入字段集对应的值
     * @param entitys 实体集
     * @return 字段集对应的值
     */
    public Object[][] getInsertFieldValues(Collection<T> entitys){
        Object[][] values = new Object[entitys.size()][];
        int i = 0;
        for (T entity : entitys){
            values[i++] = getInsertFieldValues(entity);
        }
        return values;

    }


    /**
     * 获取更新字段集对应的值
     * @param entitys 实体集
     * @return 字段集对应的值
     */
    public Object[][] getUpdateByRowIdKeyFieldValues(Collection<T> entitys) {
        Object[][] values = new Object[entitys.size()][];
        int i = 0;
        for (T entity : entitys){
            values[i++] = getUpdateByRowIdKeyFieldValues(entity);
        }
        return values;

    }

    /**
     * 获取分表描述
     * @return 分表描述
     */
    public SplitTableDescriptor getSplitTableDescriptor() {
        return splitTableDescriptor;
    }
}
