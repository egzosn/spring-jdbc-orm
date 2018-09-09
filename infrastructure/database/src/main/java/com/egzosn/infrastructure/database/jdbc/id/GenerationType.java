package com.egzosn.infrastructure.database.jdbc.id;

import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 主键类型
 * @author egan
 * @email egzosn@gmail.com
 * @date 2017/11/27
 */
public enum  GenerationType implements IdGeneratedStrategy{
    AUTO {
        /**
         *
         * @param ps  An object that represents a precompiled SQL statement.
         *            {@link PreparedStatement}
         * @param num 生成数量
         * @return KeyHolder
         */
        @Override
        public KeyHolder generation(PreparedStatement ps, int num) {
            if (null == ps){
                return null;
            }
            ResultSet rs = null;
            try {
                 rs = ps.getGeneratedKeys();
                if (rs != null && rs.getMetaData().getColumnCount() == 1) {
                    RowMapperResultSetExtractor<Map<String, Object>> rse = new RowMapperResultSetExtractor<Map<String, Object>>( new ColumnMapRowMapper(), 1);
                    return new GeneratedKeyHolder(rse.extractData(rs));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {

                    try {
                        if (null != rs && !rs.isClosed()) {
                            rs.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                }
            }
            return null;
        }
    },UUID {
        /**
         *
         * @param ps  An object that represents a precompiled SQL statement.
         *            {@link PreparedStatement}
         * @param num 生成数量
         * @return 主键
         */
        @Override
        public KeyHolder generation(PreparedStatement ps, int num) {
            List<Map<String, Object>>  keyList = new ArrayList<Map<String, Object>>(num);
            KeyHolder keyHolder = new GeneratedKeyHolder(keyList);
            for (int i = 0; i < num; i++){
                HashMap<String, Object> keys = new HashMap<String, Object>();
                keys.put("key", java.util.UUID.randomUUID().toString().replace("-", ""));
                keyList.add(keys);
            }

            return keyHolder;
        }
    };


}
