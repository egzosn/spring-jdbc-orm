/**
 * THIS FILE IS PART OF egan
 * Freight.java - The core part of the freight Entity layer
 *
 * @Copyright: 2017-11-21 www.egzosn.com Inc OR egan. All rights reserved.
 */


package com.egzosn.examples.entity;

import com.egzosn.infrastructure.database.jdbc.annotations.Column;
import com.egzosn.infrastructure.database.jdbc.annotations.GeneratedValue;
import com.egzosn.infrastructure.database.jdbc.annotations.Id;
import com.egzosn.infrastructure.database.jdbc.annotations.Table;
import com.egzosn.infrastructure.database.jdbc.id.GenerationType;
import com.egzosn.infrastructure.web.support.annotation.MsgCode;
import io.swagger.annotations.ApiParam;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * freight 实体类
 *
 * @author egan
 * @email egzosn@gmail.com
 * @date 2017-11-21 11:53:45
 */
@Table(name = "freight")
public class Freight {

    @ApiParam(value = "必填 32位")
    @MsgCode(1002)
    @NotEmpty(message = "必填")
    @Length(message = "id 是32位的")
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "pk_id")
    private String pkId;
    //名称
    @ApiParam(value = "名称必填")
    @MsgCode(1003)
    @NotEmpty
    @Column(name = "name")
    private String name;
    //国家
    @ApiParam(value = "国家必填")
    @MsgCode(1003)
    @NotEmpty(message = "国家必填")
    @Column(name = "country")
    private String country;
    //国家缩写
    @ApiParam(value = "国家缩写")
    //TODO 2017/11/30 21:17 author: egan  分表注解，如需测试可打开
//	@SplitTableField  //分表注解，暂未实现动态建表, 数据插入与更新可用
    @Column(name = "country_short_en")
    private String countryShortEn;

    public void setPkId(String pkId) {
        this.pkId = pkId;
    }

    public String getPkId() {
        return pkId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public void setCountryShortEn(String countryShortEn) {
        this.countryShortEn = countryShortEn;
    }

    public String getCountryShortEn() {
        return countryShortEn;
    }

    @Override
    public String toString() {
        return "Freight{" +
                "pkId='" + pkId + '\'' +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", countryShortEn='" + countryShortEn + '\'' +
                '}';
    }
}

