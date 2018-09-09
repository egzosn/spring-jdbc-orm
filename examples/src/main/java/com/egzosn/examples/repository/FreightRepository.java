/*** THIS FILE IS PART OF  egan
* FreightDao.java - The core part of the freight data access layer
* @Copyright: 2017-11-21 www.egzosn.com Inc OR egan. All rights reserved.
*/




package com.egzosn.examples.repository;
import com.egzosn.examples.params.FreightDaoParams;
import com.egzosn.infrastructure.database.jdbc.BaseJdbcRepository;
import com.egzosn.infrastructure.params.Params;
import com.egzosn.examples.entity.Freight;
import com.egzosn.examples.entity.CustomFreight;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
* freight 数据处理
* 
* @author egan
* @email egzosn@gmail.com
* @date 2017-11-21 11:53:45
*/ 
@Repository
public class FreightRepository extends BaseJdbcRepository<Freight> {

    public CustomFreight customFreight(Params params) {
        FreightDaoParams.Field.getSelects(params.alias());
        return uniqueQuery(getSQL("*", params), CustomFreight.class, params.getParas().toArray() );
    }

    public Map<String, Object> findMap(Params params) {

        return uniqueQuery(getSQL("*", params), params.getParas().toArray() );
    }

}

