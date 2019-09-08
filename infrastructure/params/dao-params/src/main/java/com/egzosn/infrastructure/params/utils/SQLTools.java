package com.egzosn.infrastructure.params.utils;



import com.egzosn.infrastructure.params.Field4Column;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.sql.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *  jdbc sql生成语句生产
 * @author ZaoSheng
 *Wed Nov 162 17:31:32 CST 2015
 */
public class SQLTools {
	private  static final Pattern pattern = Pattern.compile(":(\\w+)[, ]?");
	/**
	 *  获取统计的sql
	 * @param sql 原始sql
	 * @return  转化后的sql
	 */
	public static String getCountSQL(final String sql) {
		return getCountSQL(sql, null);
	}
	/**
	 *  获取统计的sql
	 * @param sql 原始sql
	 * @return  转化后的sql
	 */
	public static String getCountOracleSQL(final String sql) {
		return getCountOracleSQL(sql, null);

	}

	/**
	 * 获取统计的sql
	 * @param sql  原始sql
	 * @param countField 需要统计的字段
	 * @return 转化后的sql
	 */
	public static String getCountSQL(final String sql, String countField) {

		String countSql = String.format("SELECT  COUNT(%s) ", null == countField ? "*" : countField);
		String upperSql = sql.toUpperCase();
		int start = upperSql.indexOf("FROM ");
		int end = upperSql.lastIndexOf("ORDER BY ");
		countSql += sql.substring(start, end == -1 ? sql.length() : end);
		return countSql;
	}
	/**
	 * 获取统计的sql
	 * @param sql  原始sql
	 * @param countField 需要统计的字段
	 * @return 转化后的sql
	 */
	public static String getCountOracleSQL(final String sql, String countField) {

		String countSql = String.format("SELECT  COUNT(%s) ", null == countField ? "*" : countField);
		String upperSql = sql.toUpperCase().replace("SELECT * FROM ( ", "").replace(" ) WHERE ROWNUM BETWEEN %s AND %s", "");
		int start = upperSql.indexOf("FROM ");
		int end = upperSql.lastIndexOf("ORDER BY ");
		countSql += sql.substring(start, end == -1 ? sql.length() : end);
		return countSql;
	}

	/**
	 * 移除sql的group部分
	 * @param sql 原始sql
	 * @return 转化后的sql
	 */
	public static String removeGROUP(final String sql) {

		String upperSql = sql.toUpperCase();
		int end = upperSql.indexOf(" GROUP ");
		return -1 == end ? sql : sql.substring(0, end);
	}

	/**
	 *  获取sql
	 * @param select  需要查询的字段
	 * @return sql
	 */
	public static String getSelectSQL(String select, String tableName){
		if (null == select || "".equals(select)){
			select = "*";
		}
		return String.format("select %s from %s ", select, tableName);

	}



	/**
	 * 获取sql
	 * @param select  需要查询的字段
	 * @param tableName 表名
	 * @param alias 别名
	 * @param where 条件开始的语句
	 * @return
	 */
	public static String getSQL(String select, String tableName, String alias, String where) {
		return String.format("%s %s %s", getSelectSQL(select, tableName), alias, where);
	}




	public static String handleClob(Clob clob) throws SQLException {
		if (clob == null){
			return null;
		}

		Reader reader = null;
		try {
			reader = clob.getCharacterStream();
			char[] buffer = new char[(int) clob.length()];
			reader.read(buffer);
			return new String(buffer);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public static byte[] handleBlob(Blob blob) throws SQLException {
		if (blob == null){
			return null;
		}

		InputStream is = null;
		try {
			is = blob.getBinaryStream();
			byte[] data = new byte[(int) blob.length()]; // byte[] data = new
															// byte[is.available()];
			is.read(data);
			is.close();
			return data;
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}


	/**
	 * 拼接分页部分
	 * @param pageNumber 页号
	 * @param pageSize 每页大小
	 * @return 拼装 分页sql
	 */
	public static String forPaginate(int pageNumber, int pageSize) {
		int offset = pageSize * (pageNumber - 1);
		return  String.format(" limit %s,%s", offset, pageSize);
	}


	/**
	 * 拼接分页部分
	 * @param pageNumber 页号
	 * @param pageSize 每页大小
	 * @return 拼装 分页sql
	 */
	public static String forPaginate(String sql, int pageNumber, int pageSize) {
		int offset = pageSize * (pageNumber - 1);
		String sqltmp = String.format("SELECT *  FROM (SELECT ROWNUM  RN,a.* FROM (  %s  ) a  WHERE ROWNUM <= %s) WHERE RN >%s", new Object[]{sql, Integer.valueOf(offset + pageSize), Integer.valueOf(offset)});
		return sqltmp;
	}


	/**
	 * 设置 参数
	 * @param ps 代替对象
	 * @param params 参数
	 * @throws SQLException
	 */
	public static void fillStatement(PreparedStatement ps, List<Object> params)throws SQLException {
		if (null == params || params.isEmpty()){
			return;
		}

		int i = 0;
		for (Object param: params){
			if (param instanceof List){
				SQLTools.fillStatement(ps, (List)param);
				ps.addBatch();
				continue;
			}

			if (param instanceof  Object[]){
				SQLTools.fillStatement(ps, (Object[])param);
				ps.addBatch();
				continue;
			}

			if(null == param){
				ps.setNull(++i, Types.OTHER);
				continue;
			}
			ps.setObject(++i, param);

		}

	}

	/**
	 * 设置 参数
	 * @param ps 代替对象
	 * @param params 参数
	 * @throws SQLException
	 */
	public static void fillStatement(PreparedStatement ps, Object... params)throws SQLException {


		if (null == params){
			return;
		}
		if (params instanceof Object[][]){
			for (Object[] v : (Object[][])params){
				SQLTools.fillStatement(ps, v);
				ps.addBatch();
			}
		}else {
			int i = 0;
			for (Object param : params) {
				if(null == param){
					ps.setNull(++i, Types.OTHER);
					continue;
				}
				ps.setObject(++i, param);
			}
		}


	}


	/**
	 * 生成插入语句
	 * @param tableName 表名
	 * @param keyColumnNames 数据库列集
	 * @return 插入语句
	 */
	public static StringBuilder generateInsertString(String tableName, List<String> keyColumnNames){
		StringBuilder sql = new StringBuilder();
		sql.append("insert into ").append(tableName).append("(");
		StringBuilder temp = new StringBuilder(") values(");
		boolean flag = false;
		for (String colName : keyColumnNames) {
			if (flag) {
				sql.append(", ");
				temp.append(", ");
			}
			sql.append("").append(colName).append("");
			temp.append("?");
			flag = true;
		}
		return sql.append(temp.toString()).append(")");

	}

	/**
	 * 生成更新语句
	 * @param tableName 表名
	 * @param keyColumnNames 数据库列集
	 * @param idColumn 主键列
	 * @return 更新语句
	 */
	public static StringBuilder generateUpdateByRowIdString(String tableName, List<String> keyColumnNames, String idColumn){
		StringBuilder sql = new StringBuilder();
		sql.append("update ").append(tableName).append(" set ");
		int i = 0;
		for (String colName : keyColumnNames) {
			if (i != 0) {
				sql.append(", ");
			}
			sql.append("").append(colName).append(" = ? ");
			i++;
		}
		keyColumnNames.add(idColumn);
		sql.append(" where ").append(idColumn).append(" = ?");
		return sql;
	}

	/**
	 *  通过 Map 获取保存的sql
	 * @param table 表名
	 * @param attrs Map属性集
	 * @param paras 参数集
	 * @return 生成sql
	 */
	public static StringBuilder forMapSave(String table, Map<String, Object> attrs, List<Object> paras) {
		StringBuilder sql = new StringBuilder();
		sql.append("insert into ").append(table).append("(");
		StringBuilder temp = new StringBuilder(") values(");
		for (String colName : attrs.keySet()) {
			if (null == attrs.get(colName)) {
				continue;
			}
			if (paras.size() > 0) {
				sql.append(", ");
				temp.append(", ");
			}
			sql.append("").append(colName).append("");
			temp.append("?");
			paras.add(attrs.get(colName));
		}
		sql.append(temp.toString()).append(")");
		return sql;
	}




	/**
	 *  生成更新的sql
	 * @param table 表名
	 * @param attrs 更新的属性
	 * @param where 条件部分
	 * @param whereVal 条件对应的值
	 * @param paras 参数集
	 * @return 更新的sql
	 */
	public static StringBuilder forMapUpdate(String table, Map<String, Object> attrs, String where, List<Object> whereVal, List<Object> paras) {

		StringBuilder sql = new StringBuilder();
		sql.append("update ").append(table).append(" set ");
		for (String colName : attrs.keySet()) {
			if (!paras.isEmpty()){
				sql.append(", ");
			}
			sql.append("").append(colName).append(" = ? ");
			paras.add(attrs.get(colName));
		}
		
		sql.append(where);
		paras.addAll(whereVal);
		
		return sql;
	}

	/**
	 *  生成更新的sql
	 * @param table 表名
	 * @param attrs 更新的属性
	 * @param where 条件部分 and eq 级别
	 * @param paras 参数集
	 * @return 更新的sql
	 */
	public static StringBuilder forMapUpdate(String table, Map<String, Object> attrs, Map<String, Object> where, List<Object> paras) {
		boolean flag = false;
		StringBuilder sql = new StringBuilder();
		sql.append("update ").append(table).append(" set ");
		for (String colName : attrs.keySet()) {
			if (flag){
				sql.append(", ");
			}
			sql.append("").append(colName).append(" = ? ");
			paras.add(attrs.get(colName));
			flag = true;
		}
		sql.append(" where ");
		for (String colName : where.keySet()) {
			if (!flag){
				sql.append(" and ");
			}
			sql.append("").append(colName).append(" = ? ");
			paras.add(where.get(colName));
			flag = false;
		}
		return sql;
	}


	/**
	 * 冒号形式的sql转化为问号形式
	 * @param sql 原始sql
	 * @param attrs 属性
	 * @param values 转化后的属性集
	 * @return 转化后的sql
	 */
	public static String forConverSQL(String sql, Map<String, Object> attrs, List<Object> values) {
		Matcher matcher = pattern.matcher(sql);
		String rexp = null;
		while (matcher.find()) {
			String group = matcher.group(1);
			Object ov = attrs.get(group);
			if (ov instanceof Collection) {
				StringBuilder sb = new StringBuilder();
				Collection vs = (Collection) ov;
				for (Object v : vs) {
					sb.append("?,");
					values.add(v);
				}
				sb.deleteCharAt(sb.length() - 1);
				rexp = sb.toString();
			} else {
				values.add(ov);
				rexp = "?";
			}
			sql = sql.replace(String.format(":%s", group), rexp);
		}
		return sql;
	}

	/**
	 * 根据数组对象生成对应的in部分
	 *
	 * @param num 生成"?"的数量
	 *
	 *            <code> forQuestionMarkSQL(3)  = ?,?,? </code>
	 * @return 问号，逗号隔开
	 */
	public static final String forQuestionMarkSQL(int num) {

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < num; i++) {
			sb.append("?,");
		}
		sb.deleteCharAt(sb.length() - 1);

		return sb.toString();
	}


	public static final String getSelect(String prefix, Field4Column field) {
		return String.format(" %s.%s %s ", prefix, field.getColumn(), field.getField());
	}
	
	public static final String getSelect(String prefix, Field4Column... fields) {
		if (null == fields || fields.length == 0) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (Field4Column field : fields) {
			sb.append(getSelect(prefix, field)).append(", ");
//			sb.append(String.format(" %s.%s %s, ", prefix, field.getColumn(), field.getField()));
		}
		sb.deleteCharAt(sb.length() - 2);
		return sb.toString();
	}
	
	public static final String getSelects(String prefix, Field4Column[] field4Columns, Field4Column... ignoreFields) {
		StringBuilder sb = new StringBuilder();
		if (null != ignoreFields) {
			Arrays.sort(ignoreFields);
		}
		for (Field4Column field : field4Columns) {
			if (null != ignoreFields && Arrays.binarySearch(ignoreFields, field) >= 0) {
				continue;
			}
//			sb.append(String.format(" %s.%s %s, ", prefix, field.getColumn(), field.getField()));
			sb.append(getSelect(prefix, field)).append(", ");
		}
		sb.deleteCharAt(sb.length() - 2);
		return sb.toString();
	}
}
