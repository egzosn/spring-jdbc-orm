package com.egzosn.infrastructure.web.support.config.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * 错误码配置
 *
 * @author 肖红星
 * @create 2016/10/11
 */
@Configuration
@ConfigurationProperties(prefix = "egzosn.error", locations = "classpath:error_code.yml")
public class ErrorCodeProperties {

    //日志
    private static final Logger logger = LoggerFactory.getLogger(ErrorCodeProperties.class);

    //系统名称
    private String name;
    //系统错误码前缀/系统序号
    private int seq;
    //成功编码
    private Map<Integer, String> success;
    //错误编码
    private ErrorCode error;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public Map<Integer, String> getSuccess() {
        return success;
    }

    public void setSuccess(Map<Integer, String> success) {
        this.success = success;
    }

    public ErrorCode getError() {
        return error;
    }

    public void setError(ErrorCode error) {
        this.error = error;
    }

    /**
     * 拼合完整的错误码
     *
     * @param errorCode 错误码
     * @return
     */
    public Integer getCode(Integer errorCode) {
        String temp = String.valueOf(errorCode);
        //通用错误不需要加前缀
        if(!error.getCommon().containsKey(errorCode)){
            temp = String.valueOf(seq) + temp;
        }
        return Integer.parseInt(temp);
    }

    /**
     * 获取错误码信息
     *
     * @param errorCode 错误码
     * @return 错误提示信息
     */
    public String getMessage(Integer errorCode, String... values) {
        String message = error.getCommon().get(errorCode);
        if (StringUtils.isEmpty(message)) {
            message = error.getController().get(errorCode);
        }
        if(StringUtils.isEmpty(message)){
            logger.error("无效的错误码:" + errorCode + "，请正确添加到error.code.yml配置文件！");
        }
        if(values != null && values.length > 0){
            message = String.format(message, values);
        }
        return message;
    }
}
