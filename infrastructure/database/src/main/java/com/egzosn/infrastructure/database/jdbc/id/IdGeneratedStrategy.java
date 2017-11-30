package com.egzosn.infrastructure.database.jdbc.id;


import java.sql.PreparedStatement;

/**
 * id生成策略
 * @author egan
 * @email egzosn@gmail.com
 * @date 2017/11/27
 */
public interface IdGeneratedStrategy extends java.io.Serializable{


    /**
     *  生成id
     * @param ps  An object that represents a precompiled SQL statement.
     *            {@link PreparedStatement}
     * @param num 生成数量
     * @return 获取id
     */
     KeyHolder generation(PreparedStatement ps, int num);


}
