package com.egzosn.infrastructure.utils.validator.plug;

/**
 * Created by ZaoSheng on 2015/5/12.
 */
public interface Regx {
	String PHONE = "^1(3|4|5|6|7|8)\\d{9}$";
	String TELEPHONE = "^0\\d{2,3}\\+?\\-?\\d{7,8}$";
    String QQ = "^[1-9][0-9]{4,9}$";
    String ZIPCODE = "^[1-9]\\d{5}$";
    String SAFEPASS = "^(([A-Z]*|[a-z]*|\\d*|[-_\\~!@#\\$%\\^&\\*\\.\\(\\)\\[\\]\\{\\}<>\\?\\\\\\/\\'\\\"]*)|.{0,5})$|\\s";
    String PASSWORD = "^(?!_)(?!.*?_$)[a-zA-Z0-9_]{%d,%d}$";
    String CHINESE = "^[\\u4E00-\\u9FA5]{%d,%d}$";
    String LOGINNAME = "^[\\u0391-\\uFFE5\\w]+$";
    String DECIMAL = "([1-9][0-9]{1,%d}|0)(\\.[\\d]{0,%d})?";

}
