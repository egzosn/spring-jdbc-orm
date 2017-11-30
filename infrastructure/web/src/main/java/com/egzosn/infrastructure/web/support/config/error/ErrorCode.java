package com.egzosn.infrastructure.web.support.config.error;

import java.util.Map;

/**
 * 错误编码
 *
 * @author 肖红星
 * @create 2016/11/12
 */
public class ErrorCode{

    //通用错误编码
    private Map<Integer, String> common;
    //自定义错误编码
    private Map<Integer, String> controller;

    public Map<Integer, String> getCommon() {
        return common;
    }

    public void setCommon(Map<Integer, String> common) {
        this.common = common;
    }

    public Map<Integer, String> getController() {
        return controller;
    }

    public void setController(Map<Integer, String> controller) {
        this.controller = controller;
    }
}
