package com.egzosn.infrastructure.params.bean;

import com.egzosn.infrastructure.params.Field4Column;
import com.egzosn.infrastructure.params.enums.Restriction;
import com.egzosn.infrastructure.params.Where;
import com.egzosn.infrastructure.params.utils.SQLTools;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 订单查询参数
 * Created by egan on 2018/11/2.
 * <a href="mailto:egzosn@gmail.com">郑灶生</a>
 * <br/>
 * email: egzosn@gmail.com
 */

public class OdOrderDaoParams extends Where {
    public static final String ALIAS = "o";
    public static final String TABLE = "od_order";
    public static final Map<String, Field4Column> FIELD_4_COLUMNS = new HashMap<String, Field4Column>(Field.values().length);
    static {
        for (Field4Column field4Column : Field.values()){
            FIELD_4_COLUMNS.put(field4Column.getField(), field4Column);
        }
    }
    public OdOrderDaoParams() {
        alias = ALIAS;
        where();
    }
    public enum Field implements Field4Column {
        pkId("pk_id"), name("name"), orderCreateTime("order_create_time"),shipType("ship_type");
        public String column;
        Field(String column) {
            this.column = column;
        }
        @Override
        public String getField(){
            return name();
        }
        @Override
        public String getColumn(){
            return column;
        }

        /**
         * 获取select 字段  列名 别名(字段名)
         *
         * @return 获取select
         */
        @Override
        public String getSelect() {
            return SQLTools.getSelect(ALIAS, this);
        }

        /**
         * 获取select 字段  列名 别名(字段名)
         *
         * @param prefix 表别名
         * @return 获取select
         */
        @Override
        public String getSelect(String prefix) {
            return SQLTools.getSelect(prefix, this);
        }


        public static String getSelects(Field... ignoreFields) {
            return getSelects(ALIAS, ignoreFields);
        }

        public static String getSelects(String prefix, Field... ignoreFields) {
            return SQLTools.getSelects(prefix, Field.values(), ignoreFields);
        }

        public static String getSelect(Field... fields) {
            return getSelect(ALIAS, fields);
        }

        public static String getSelect(String prefix, Field... fields) {
            return SQLTools.getSelects(prefix, fields);
        }



    }


    public OdOrderDaoParams setPkId(String pkId, boolean isHQL) {
        and(isHQL ? Field.pkId.name() : Field.pkId.getColumn(), pkId, alias);
        return this;
    }

    public String getPkId() {
        return (String) attrs.get(Field.pkId.name());
    }

    public OdOrderDaoParams setPkIdLLk(String pkId, boolean isHQL) {
        and(isHQL ? Field.pkId.name() : Field.pkId.getColumn(), pkId, Restriction.LLK, alias);
        return this;
    }

    public OdOrderDaoParams setPkIdRLK(String pkId, boolean isHQL) {
        and(isHQL ? Field.pkId.name() : Field.pkId.getColumn(), pkId, Restriction.RLK, alias);
        return this;
    }

    public OdOrderDaoParams setPkIdLK(String pkId, boolean isHQL) {
        and(isHQL ? Field.pkId.name() : Field.pkId.getColumn(), pkId, Restriction.LK, alias);
        return this;
    }

    public OdOrderDaoParams setPkIdIn(List<Object> pkId, boolean isHQL) {
        and(isHQL ? Field.pkId.name() : Field.pkId.getColumn(), pkId, Restriction.IN, alias);
        return this;
    }

    public OdOrderDaoParams setPkIdNIn(List<Object> pkId, boolean isHQL) {
        and(isHQL ? Field.pkId.name() : Field.pkId.getColumn(), pkId, Restriction.NIN, alias);
        return this;
    }

    public OdOrderDaoParams setPkIdNul(boolean isHQL) {
        and(isHQL ? Field.pkId.name() : Field.pkId.getColumn(), null, Restriction.NUL, alias);
        return this;
    }

    public OdOrderDaoParams setPkIdNNul(boolean isHQL) {
        and(isHQL ? Field.pkId.name() : Field.pkId.getColumn(), null, Restriction.NNUL, alias);
        return this;
    }

    public OdOrderDaoParams setName(String name, boolean isHQL) {
        and(isHQL ? Field.name.name() : Field.name.getColumn(), name, alias);
        return this;
    }

    public String getName() {
        return (String) attrs.get(Field.name.name());
    }

    public OdOrderDaoParams setNameLLk(String name, boolean isHQL) {
        and(isHQL ? Field.name.name() : Field.name.getColumn(), name, Restriction.LLK, alias);
        return this;
    }

    public OdOrderDaoParams setNameRLK(String name, boolean isHQL) {
        and(isHQL ? Field.name.name() : Field.name.getColumn(), name, Restriction.RLK, alias);
        return this;
    }

    public OdOrderDaoParams setNameLK(String name, boolean isHQL) {
        and(isHQL ? Field.name.name() : Field.name.getColumn(), name, Restriction.LK, alias);
        return this;
    }

    public OdOrderDaoParams setNameIn(List<Object> name, boolean isHQL) {
        and(isHQL ? Field.name.name() : Field.name.getColumn(), name, Restriction.IN, alias);
        return this;
    }

    public OdOrderDaoParams setNameNIn(List<Object> name, boolean isHQL) {
        and(isHQL ? Field.name.name() : Field.name.getColumn(), name, Restriction.NIN, alias);
        return this;
    }

    public OdOrderDaoParams setNameNul(boolean isHQL) {
        and(isHQL ? Field.name.name() : Field.name.getColumn(), null, Restriction.NUL, alias);
        return this;
    }

    public OdOrderDaoParams setNameNNul(boolean isHQL) {
        and(isHQL ? Field.name.name() : Field.name.getColumn(), null, Restriction.NNUL, alias);
        return this;
    }

    public OdOrderDaoParams setOrderCreateTime(Date orderCreateTime, boolean isHQL) {
        and(isHQL ? Field.orderCreateTime.name() : Field.orderCreateTime.getColumn(), orderCreateTime, alias);
        return this;
    }

    public Date getOrderCreateTime() {
        return (Date) attrs.get(Field.orderCreateTime.name());
    }

    public OdOrderDaoParams setOrderCreateTimeBetween(Date[] orderCreateTime, boolean isHQL) {
        and(isHQL ? Field.orderCreateTime.name() : Field.orderCreateTime.getColumn(), orderCreateTime, Restriction.BW, alias);
        return this;
    }


    public OdOrderDaoParams setOrderCreateTimeIn(List<Object> orderCreateTime, boolean isHQL) {
        and(isHQL ? Field.orderCreateTime.name() : Field.orderCreateTime.getColumn(), orderCreateTime, Restriction.IN, alias);
        return this;
    }

    public OdOrderDaoParams setOrderCreateTimeNIn(List<Object> orderCreateTime, boolean isHQL) {
        and(isHQL ? Field.orderCreateTime.name() : Field.orderCreateTime.getColumn(), orderCreateTime, Restriction.NIN, alias);
        return this;
    }

    public OdOrderDaoParams setOrderCreateTimeNul(boolean isHQL) {
        and(isHQL ? Field.orderCreateTime.name() : Field.orderCreateTime.getColumn(), null, Restriction.NUL, alias);
        return this;
    }

    public OdOrderDaoParams setOrderCreateTimeNNul(boolean isHQL) {
        and(isHQL ? Field.orderCreateTime.name() : Field.orderCreateTime.getColumn(), null, Restriction.NNUL, alias);
        return this;
    }

    public OdOrderDaoParams setShipType(String shipType, boolean isHQL) {
        and(isHQL ? Field.shipType.name() : Field.shipType.getColumn(), shipType, alias);
        return this;
    }

    public String getShipType() {
        return (String) attrs.get(Field.shipType.name());
    }

    public OdOrderDaoParams setShipTypeLLk(String shipType, boolean isHQL) {
        and(isHQL ? Field.shipType.name() : Field.shipType.getColumn(), shipType, Restriction.LLK, alias);
        return this;
    }

    public OdOrderDaoParams setShipTypeRLK(String shipType, boolean isHQL) {
        and(isHQL ? Field.shipType.name() : Field.shipType.getColumn(), shipType, Restriction.RLK, alias);
        return this;
    }

    public OdOrderDaoParams setShipTypeLK(String shipType, boolean isHQL) {
        and(isHQL ? Field.shipType.name() : Field.shipType.getColumn(), shipType, Restriction.LK, alias);
        return this;
    }

    public OdOrderDaoParams setShipTypeIn(List<Object> shipType, boolean isHQL) {
        and(isHQL ? Field.shipType.name() : Field.shipType.getColumn(), shipType, Restriction.IN, alias);
        return this;
    }

    public OdOrderDaoParams setShipTypeNIn(List<Object> shipType, boolean isHQL) {
        and(isHQL ? Field.shipType.name() : Field.shipType.getColumn(), shipType, Restriction.NIN, alias);
        return this;
    }

    public OdOrderDaoParams setShipTypeNul(boolean isHQL) {
        and(isHQL ? Field.shipType.name() : Field.shipType.getColumn(), null, Restriction.NUL, alias);
        return this;
    }

    public OdOrderDaoParams setShipTypeNNul(boolean isHQL) {
        and(isHQL ? Field.shipType.name() : Field.shipType.getColumn(), null, Restriction.NNUL, alias);
        return this;
    }
}

