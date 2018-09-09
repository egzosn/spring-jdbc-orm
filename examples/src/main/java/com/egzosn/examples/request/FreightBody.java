/**
 * THIS FILE IS PART OF egan
 * Freight.java - The core part of the freight Entity layer
 *
 * @Copyright: 2017-11-21 www.egzosn.com Inc OR egan. All rights reserved.
 */


package com.egzosn.examples.request;

import com.egzosn.infrastructure.web.support.annotation.MsgCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * freight 请求体
 *
 * @author egan
 * @email egzosn@gmail.com
 * @date 2017-11-21 11:53:45
 */
@ApiModel
public class FreightBody {


    //名称
    @ApiParam("名称必填")
    @MsgCode(1002)
    @NotEmpty
    private String name;
    //国家
    @ApiParam(value = "国家必填")
    @MsgCode(1003)
    @NotEmpty(message = "国家必填")
    private String country;
    //国家缩写
    @ApiParam(value = "国家缩写")
    private String countryShortEn;


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


}

