package com.egzosn.infrastructure.web.exception;

/**
 * Created by egan on 2014/10/24.
 */
public class MultipartSameValidateException extends  RuntimeException {

    public MultipartSameValidateException(String message) {
        super(message);
    }
}
