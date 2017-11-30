/**
* THIS FILE IS PART OF egan
* Freight.java - The core part of the freight Entity layer
* @Copyright: 2017-11-21 www.egzosn.com Inc OR egan. All rights reserved.
*/




package com.egzosn.examples.entity;

/**
* 自定义freight 实体类
*
* @author egan
* @email egzosn@gmail.com
* @date 2017-11-21 11:53:45
*/ 
public class CustomFreight {

	private String pkId;
	 //名称
	private String name;
	 //国家

	private String country;
	 //国家缩写
	private String countryShortEn;

	public void setPkId(String pkId){
		this.pkId = pkId;
	}

	public String getPkId(){
		return pkId;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setCountry(String country){
		this.country = country;
	}

	public String getCountry(){
		return country;
	}

	public void setCountryShortEn(String countryShortEn){
		this.countryShortEn = countryShortEn;
	}

	public String getCountryShortEn(){
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

