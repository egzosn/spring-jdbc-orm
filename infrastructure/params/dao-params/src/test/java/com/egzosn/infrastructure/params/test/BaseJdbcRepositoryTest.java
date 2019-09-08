package com.egzosn.infrastructure.params.test;

import com.egzosn.infrastructure.params.utils.BaseJdbcRepository;
import com.egzosn.infrastructure.params.Where;
import com.egzosn.infrastructure.params.bean.FreightDaoParams;
import com.egzosn.infrastructure.params.enums.Restriction;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by egan on 2018/10/25.
 */
public class BaseJdbcRepositoryTest {

    public static void main(String[] args) {
        BaseJdbcRepository repository = new BaseJdbcRepository("USER");
        //以某个列进行查询对应的实体
        repository.findByProperty("name", "张三");

        //以条件对象的形式
        Where where = new Where().where() ;
        where.setAlias("u");
        where.and("name", "李四")
        .and("age", new Long[]{12L, 24L}, Restriction.BW)
        .order("name")
        ;
        repository.queryList(where, false);


        //以id进行删除
        repository.delete(1);

        //id集
        List ids = Arrays.asList(1,2,3);
        repository.delete(ids);

        //更新
        Map<String, Object> updateField = new HashMap<>(2);
        updateField.put("sex", "男");
        updateField.put("age", 24);
        where = new Where() ;
        where.setAlias("u");
        where.and("name", "李四")
        ;
        repository.update(updateField, where);

        // 冒号代替形式
        Map<String, Object> values = new HashMap<>(2);
        values.put("ids", ids);
        repository.delete(" delete from USER  where id in( :ids)", values);

        //查询对象式
        FreightDaoParams params = new FreightDaoParams()
                .setCountry("中国", false)
                .setNameLK("铁塔", false)

                ;
        params.order(FreightDaoParams.Field.countryShortEn.getColumn(), params.alias());

        repository.setTable(FreightDaoParams.TABLE);
        repository.uniqueQuery(params);

    }

}
