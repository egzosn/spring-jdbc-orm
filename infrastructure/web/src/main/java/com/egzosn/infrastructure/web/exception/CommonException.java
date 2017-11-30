package com.egzosn.infrastructure.web.exception;

/**
 * 自定义异常
 */
public class CommonException extends RuntimeException {

	private Integer code;
	private String message;
	public CommonException(Integer code) {
		this.code = code;
	}
	
	public CommonException(Integer code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public String getMessage() {

        return message;


	}
	

}
