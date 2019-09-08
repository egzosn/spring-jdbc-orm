/**
 * THIS FILE IS PART OF egan
 * FreightDaoParams.java - The core part of the freight The data access parameters layer
 *
 * @Copyright: 2017-11-21 www.egzosn.com Inc OR egan. All rights reserved.
 */


package com.egzosn.infrastructure.params.bean;


import com.egzosn.infrastructure.params.Where;
import com.egzosn.infrastructure.params.enums.Restriction;

import java.util.Arrays;
import java.util.List;

/**
 * freight 请求参数封装类
 *
 * @author egan
 * @email egzosn@gmail.com
 * @date 2017-11-21 11:53:45
 */


public class FreightDaoParams extends Where {
    public static final String ALIAS = "f";
    public static final String TABLE = "freight";

    public FreightDaoParams() {
        alias = ALIAS;
        where();
    }

    public enum Field {
        pkId("pk_id"), name("name"), country("country"), countryShortEn("country_short_en");
        private String column;

        private Field(String column) {
            this.column = column;
        }

        public String getColumn() {
            return column;

        }

        public static String getSelects(Field... ignoreFields) {
            return getSelects(ALIAS, ignoreFields);
        }

        public static String getSelects(String prefix, Field... ignoreFields) {
            StringBuilder sb = new StringBuilder();
            if (null != ignoreFields) {
                Arrays.sort(ignoreFields);
            }
            for (Field field : Field.values()) {
                if (null != ignoreFields && Arrays.binarySearch(ignoreFields, field) >= 0) {
                    continue;
                }
                sb.append(String.format(" %s.`%s` %s, ", prefix, field.getColumn(), field.name()));
            }
            sb.deleteCharAt(sb.length() - 2);
            return sb.toString();
        }

        public String getSelect() {
            return getSelect(ALIAS);
        }

        public String getSelect(String prefix) {
            return String.format(" %s.`%s` %s ", prefix, this.getColumn(), this.name());
        }

        public static String getSelect(Field... fields) {
            return getSelect(ALIAS, fields);
        }

        public static String getSelect(String prefix, Field... fields) {
            if (null == fields || fields.length == 0) {
                return "";
            }
            StringBuilder sb = new StringBuilder();
            for (Field field : fields) {
                sb.append(String.format(" %s.`%s` %s, ", prefix, field.getColumn(), field.name()));
            }
            sb.deleteCharAt(sb.length() - 2);
            return sb.toString();
        }
    }

    public FreightDaoParams setPkId(String pkId, boolean isHQL) {
        and(isHQL ? Field.pkId.name() : Field.pkId.getColumn(), pkId, alias);
        return this;
    }

    public String getPkId() {
        return (String) attrs.get(Field.pkId.name());
    }

    public FreightDaoParams setPkIdLLk(String pkId, boolean isHQL) {
        and(isHQL ? Field.pkId.name() : Field.pkId.getColumn(), pkId, Restriction.LLK, alias);
        return this;
    }

    public FreightDaoParams setPkIdRLK(String pkId, boolean isHQL) {
        and(isHQL ? Field.pkId.name() : Field.pkId.getColumn(), pkId, Restriction.RLK, alias);
        return this;
    }

    public FreightDaoParams setPkIdLK(String pkId, boolean isHQL) {
        and(isHQL ? Field.pkId.name() : Field.pkId.getColumn(), pkId, Restriction.LK, alias);
        return this;
    }

    public FreightDaoParams setPkIdIn(List<Object> pkId, boolean isHQL) {
        and(isHQL ? Field.pkId.name() : Field.pkId.getColumn(), pkId, Restriction.IN, alias);
        return this;
    }

    public FreightDaoParams setPkIdNIn(List<Object> pkId, boolean isHQL) {
        and(isHQL ? Field.pkId.name() : Field.pkId.getColumn(), pkId, Restriction.NIN, alias);
        return this;
    }

    public FreightDaoParams setPkIdNul(boolean isHQL) {
        and(isHQL ? Field.pkId.name() : Field.pkId.getColumn(), null, Restriction.NUL, alias);
        return this;
    }

    public FreightDaoParams setPkIdNNul(boolean isHQL) {
        and(isHQL ? Field.pkId.name() : Field.pkId.getColumn(), null, Restriction.NNUL, alias);
        return this;
    }

    public FreightDaoParams setName(String name, boolean isHQL) {
        and(isHQL ? Field.name.name() : Field.name.getColumn(), name, alias);
        return this;
    }

    public String getName() {
        return (String) attrs.get(Field.name.name());
    }

    public FreightDaoParams setNameLLk(String name, boolean isHQL) {
        and(isHQL ? Field.name.name() : Field.name.getColumn(), name, Restriction.LLK, alias);
        return this;
    }

    public FreightDaoParams setNameRLK(String name, boolean isHQL) {
        and(isHQL ? Field.name.name() : Field.name.getColumn(), name, Restriction.RLK, alias);
        return this;
    }

    public FreightDaoParams setNameLK(String name, boolean isHQL) {
        and(isHQL ? Field.name.name() : Field.name.getColumn(), name, Restriction.LK, alias);
        return this;
    }

    public FreightDaoParams setNameIn(List<Object> name, boolean isHQL) {
        and(isHQL ? Field.name.name() : Field.name.getColumn(), name, Restriction.IN, alias);
        return this;
    }

    public FreightDaoParams setNameNIn(List<Object> name, boolean isHQL) {
        and(isHQL ? Field.name.name() : Field.name.getColumn(), name, Restriction.NIN, alias);
        return this;
    }

    public FreightDaoParams setNameNul(boolean isHQL) {
        and(isHQL ? Field.name.name() : Field.name.getColumn(), null, Restriction.NUL, alias);
        return this;
    }

    public FreightDaoParams setNameNNul(boolean isHQL) {
        and(isHQL ? Field.name.name() : Field.name.getColumn(), null, Restriction.NNUL, alias);
        return this;
    }

    public FreightDaoParams setCountry(String country, boolean isHQL) {
        and(isHQL ? Field.country.name() : Field.country.getColumn(), country, alias);
        return this;
    }

    public String getCountry() {
        return (String) attrs.get(Field.country.name());
    }

    public FreightDaoParams setCountryLLk(String country, boolean isHQL) {
        and(isHQL ? Field.country.name() : Field.country.getColumn(), country, Restriction.LLK, alias);
        return this;
    }

    public FreightDaoParams setCountryRLK(String country, boolean isHQL) {
        and(isHQL ? Field.country.name() : Field.country.getColumn(), country, Restriction.RLK, alias);
        return this;
    }

    public FreightDaoParams setCountryLK(String country, boolean isHQL) {
        and(isHQL ? Field.country.name() : Field.country.getColumn(), country, Restriction.LK, alias);
        return this;
    }

    public FreightDaoParams setCountryIn(List<Object> country, boolean isHQL) {
        and(isHQL ? Field.country.name() : Field.country.getColumn(), country, Restriction.IN, alias);
        return this;
    }

    public FreightDaoParams setCountryNIn(List<Object> country, boolean isHQL) {
        and(isHQL ? Field.country.name() : Field.country.getColumn(), country, Restriction.NIN, alias);
        return this;
    }

    public FreightDaoParams setCountryNul(boolean isHQL) {
        and(isHQL ? Field.country.name() : Field.country.getColumn(), null, Restriction.NUL, alias);
        return this;
    }

    public FreightDaoParams setCountryNNul(boolean isHQL) {
        and(isHQL ? Field.country.name() : Field.country.getColumn(), null, Restriction.NNUL, alias);
        return this;
    }

    public FreightDaoParams setCountryShortEn(String countryShortEn, boolean isHQL) {
        and(isHQL ? Field.countryShortEn.name() : Field.countryShortEn.getColumn(), countryShortEn, alias);
        return this;
    }

    public String getCountryShortEn() {
        return (String) attrs.get(Field.countryShortEn.name());
    }

    public FreightDaoParams setCountryShortEnLLk(String countryShortEn, boolean isHQL) {
        and(isHQL ? Field.countryShortEn.name() : Field.countryShortEn.getColumn(), countryShortEn, Restriction.LLK, alias);
        return this;
    }

    public FreightDaoParams setCountryShortEnRLK(String countryShortEn, boolean isHQL) {
        and(isHQL ? Field.countryShortEn.name() : Field.countryShortEn.getColumn(), countryShortEn, Restriction.RLK, alias);
        return this;
    }

    public FreightDaoParams setCountryShortEnLK(String countryShortEn, boolean isHQL) {
        and(isHQL ? Field.countryShortEn.name() : Field.countryShortEn.getColumn(), countryShortEn, Restriction.LK, alias);
        return this;
    }

    public FreightDaoParams setCountryShortEnIn(List<Object> countryShortEn, boolean isHQL) {
        and(isHQL ? Field.countryShortEn.name() : Field.countryShortEn.getColumn(), countryShortEn, Restriction.IN, alias);
        return this;
    }

    public FreightDaoParams setCountryShortEnNIn(List<Object> countryShortEn, boolean isHQL) {
        and(isHQL ? Field.countryShortEn.name() : Field.countryShortEn.getColumn(), countryShortEn, Restriction.NIN, alias);
        return this;
    }

    public FreightDaoParams setCountryShortEnNul(boolean isHQL) {
        and(isHQL ? Field.countryShortEn.name() : Field.countryShortEn.getColumn(), null, Restriction.NUL, alias);
        return this;
    }

    public FreightDaoParams setCountryShortEnNNul(boolean isHQL) {
        and(isHQL ? Field.countryShortEn.name() : Field.countryShortEn.getColumn(), null, Restriction.NNUL, alias);
        return this;
    }
}

