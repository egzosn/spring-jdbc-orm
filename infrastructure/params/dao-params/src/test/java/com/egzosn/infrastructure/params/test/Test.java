package com.egzosn.infrastructure.params.test;

import com.egzosn.infrastructure.params.Params;
import com.egzosn.infrastructure.params.QueryParams;
import com.egzosn.infrastructure.params.Where;
import com.egzosn.infrastructure.params.bean.OdOrderDaoParams;
import com.egzosn.infrastructure.params.enums.Restriction;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by egan on 2018/11/1.
 * <a href="mailto:egzosn@gmail.com">郑灶生</a>
 * <br/>
 * email: egzosn@gmail.com
 */

public class Test {

    public static void main(String[] args) throws ParseException {

        Params params = QueryParams.WHERE("name", "张三", "user").setAlias("user").and("age", 12).order("sex").group("name");
        System.out.println(params.builderParas().getSqlString());
        System.out.println(params.getParas());

        params  = new Where().setAlias("user").and("name", "张三").and("age", new Integer[]{1, 12}, Restriction.BW).order("sex").group("name");
        System.out.println(params.builderParas().getSqlString());
        System.out.println(params.getParas());

        System.out.println(params.builderAttrs().getSqlString());
        System.out.println(params.getAttrs());

        params  = new Where().setAlias("user").and("name", "张三").and("age", 1, Restriction.GT).and("$1$age", 12, Restriction.LE).order("sex").group("name");

        System.out.println(params.builderParas().getSqlString().replaceAll("\\.\\$[0-9]\\$", "."));
        System.out.println(params.getParas());

       DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
       params = new OdOrderDaoParams()
                .setName("张三", false)
                .setOrderCreateTimeBetween(new Date[]{df.parse("2018-11-01"), df.parse("2018-11-02")}, false)
                .order(OdOrderDaoParams.Field.shipType.getColumn())
                ;

        System.out.println(params.builderParas().getSqlString());
        System.out.println(params.getParas());

    }

}
