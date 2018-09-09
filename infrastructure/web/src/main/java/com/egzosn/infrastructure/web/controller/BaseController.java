package com.egzosn.infrastructure.web.controller;


import com.egzosn.infrastructure.utils.common.Page;
import com.egzosn.infrastructure.web.exception.CommonException;
import com.egzosn.infrastructure.web.support.annotation.MsgCode;
import com.egzosn.infrastructure.web.support.config.error.ErrorCodeProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

public class BaseController {

    private final static Map<String, MsgCode> errorFields = new WeakHashMap();
    public static final String CODE_KEY = "code";
    public static final String MESSAGE_KEY = "message";
    public static final String DATA_KEY = "data";

    @Autowired
    private ErrorCodeProperties errorCode;

    /**
     * @author ZaoSheng
     * Wed Nov 25 10:20:23 CST 2015 ZaoSheng
     */
    public Map<String, Object> successData() {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put(CODE_KEY, 0);
        return data;
    }

    /**
     * @author ZaoSheng
     * Wed Nov 25 10:20:23 CST 2015 ZaoSheng
     */
    public Map<String, Object> successData(String key, Object result) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put(CODE_KEY, 0);
        data.put(key, result);
        return data;
    }

    /**
     * @author ZaoSheng
     * Wed Nov 25 10:20:23 CST 2015 ZaoSheng
     */
    public Map<String, Object> successData(String key, Object result, String message) {
        Map<String, Object> data = successData(key, result);
        data.put(MESSAGE_KEY, message);
        return data;
    }

    /**
     * @author ZaoSheng
     * Wed Nov 25 10:20:23 CST 2015 ZaoSheng
     */
    public Map<String, Object> newData(Integer code, Object message) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put(CODE_KEY, code);
        data.put(MESSAGE_KEY, message);
        return data;
    }

    public Map<String, Object> newData(Integer code, Object message, Object result) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put(CODE_KEY, code);
        data.put(MESSAGE_KEY, message);
        data.put(DATA_KEY, result);
        return data;
    }

    /**
     * @author ZaoSheng
     * Wed Nov 25 10:20:23 CST 2015 ZaoSheng
     */
    public Map<String, Object> throwException(Integer code) {
        return this.throwException(code, null);
    }

    /**
     * @author ZaoSheng
     * Wed Nov 25 10:20:23 CST 2015 ZaoSheng
     */
    public Map<String, Object> throwException(Integer code, String... values) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put(CODE_KEY, errorCode.getCode(code));
        data.put(MESSAGE_KEY, errorCode.getMessage(code, values));
        return data;
    }

    /**
     * @author ZaoSheng
     * Wed Nov 25 10:20:23 CST 2015 ZaoSheng
     */
    public void throwExceptions(Integer code) throws Exception {
        throw new CommonException(code, errorCode.getMessage(code));
    }



    /**
     * @author ZaoSheng
     * Wed Nov 25 10:20:23 CST 2015 ZaoSheng
     */
    public Map<String, Object> assemblyPageData(Page<?> result) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put(CODE_KEY, 0);
        data.put("total", result.getTotal());
        data.put("page", result.getPage());
        data.put("pageSize", result.getRows());
        data.put("count", result.getCount());
        data.put("rows", result.getContent());
        return data;
    }

    /**
     * 异常处理器
     *
     * @param ex 对应的异常
     * @return 异常提示状态值
     * @throws Exception
     * @see BindException 字段式绑定异常
     * @see ConstraintViolationException 约束违反例外
     * @see CommonException 自定义异常
     * @see HttpMessageNotReadableException Http消息不可读的例外，主要用于@RequestBody 反序列化无法转换异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Map<String, Object> handleException(Exception ex) throws Exception {
        Map<String, Object> data = new HashMap<>();
        // TODO 2016/11/16 10:47 author: egan  判断是否为字段式绑定异常
        if(ex instanceof MethodArgumentNotValidException){
            MethodArgumentNotValidException me = (MethodArgumentNotValidException) ex;
            BindingResult bindingResult = me.getBindingResult();
            processFieldError(data, bindingResult.getTarget().getClass(), bindingResult.getFieldErrors());
        }else if (ex instanceof BindException) {
            BindException be = (BindException) ex;
            Class<?> targetClass = be.getTarget().getClass();
            //获取所有的对象错误集
            List<FieldError> errors = be.getFieldErrors();
            processFieldError(data, targetClass, errors);
            //约束违反异常 主要用于校验方法参数
        } else if (ex instanceof ConstraintViolationException) {
            ConstraintViolationException mcve = (ConstraintViolationException) ex;
            //获取校验不通过的异常集
            Set<ConstraintViolation<?>> constraintViolations = mcve.getConstraintViolations();
            for (ConstraintViolation<?> violation : constraintViolations) {
                Object[] executableParameters = violation.getExecutableParameters();
                //violation.getMessage() 主要为code(规范书写)
                if (violation.getMessage().matches("\\d+")) {
                    Integer code = Integer.valueOf(violation.getMessage());
                    data.put(CODE_KEY, errorCode.getCode(code));
                    String message = errorCode.getMessage(code);
                    if (null != message) {
                        data.put(MESSAGE_KEY, message);
                    }
                } else {
                    data.put(MESSAGE_KEY, violation.getMessage());
                }
                return data;
            }
            data.put(CODE_KEY, 1000);

        } else if (ex instanceof CommonException) {
            CommonException ce = (CommonException) ex;
            data.put(CODE_KEY, errorCode.getCode(ce.getCode()));
            data.put(MESSAGE_KEY, StringUtils.isEmpty(ce.getMessage()) ? errorCode.getMessage(ce.getCode()) : ce.getMessage());
            return data;
        } else if (ex instanceof HttpMessageNotReadableException) {
            data.put(CODE_KEY, 1000);
            data.put(MESSAGE_KEY, errorCode.getMessage(1000));
        } else if (ex instanceof MethodArgumentTypeMismatchException) {
            data.put(CODE_KEY, 1000);
            data.put(MESSAGE_KEY, errorCode.getMessage(1000));
        } else {
            throw ex;
        }
        return data;
    }

    private Map<String, Object> processFieldError(Map<String, Object> data, Class<?> targetClass, List<FieldError> errors) throws NoSuchFieldException {
        for (FieldError error : errors) {
            String field = error.getField();
            String key = field + error.getCode();
            MsgCode msgCode = null;
            if (null == errorFields.get(key)) {
                String[] pfields = field.split("\\.");
                //判断是否为双层对象并进行切割获取字段对象的字段并抓取code
                if (pfields.length >= 2) {
                    String temp = pfields[0];
                    if (temp.contains("[")) {
                        temp = temp.substring(0, temp.indexOf("["));
                    }
                    Field declaredField = targetClass.getDeclaredField(temp);
                    Class clazz = declaredField.getType();
                    if (clazz.isAssignableFrom(List.class) || clazz.isAssignableFrom(Map.class)) {
                        Type genType = declaredField.getGenericType();
                        if (!(genType instanceof ParameterizedType)) {
                            clazz = Object.class;
                        } else {
                            Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
                            try {
                                clazz = (Class) params[0];
                            } catch (Exception e) {
                                try {
                                    clazz = (Class) ((ParameterizedType) params[0]).getRawType();
                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }
                            }
                        }
                    }
                    msgCode = clazz.getDeclaredField(pfields[1]).getAnnotation(MsgCode.class);
                } else {
                    msgCode = targetClass.getDeclaredField(field).getAnnotation(MsgCode.class);
                }
                errorFields.put(key, msgCode);
            } else {
                msgCode = errorFields.get(key);
            }

            if (null != msgCode) {
                data.put(CODE_KEY, errorCode.getCode(msgCode.value()));
            }
            //根据code 在error_code.yml取值，如果未取到值则去默认的error.getDefaultMessage();
            String message = errorCode.getMessage(msgCode != null ? msgCode.value() : null);
            if (null == message) {
                message = error.getDefaultMessage();
            }
            data.put(MESSAGE_KEY, message);
            return data;
        }
        data.put(CODE_KEY, 1000);
        return data;
    }
}


