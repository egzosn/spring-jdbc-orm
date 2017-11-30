package com.egzosn.infrastructure.utils.validator.constraintvalidators;

import com.egzosn.infrastructure.utils.validator.constraints.StringArray;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by egan on 2016/11/17.
 * String数组正则校验
 */
public class StringArrayValidator implements ConstraintValidator<StringArray, String[]> {
    //正则表达式
    private String regexp;


    @Override
    public boolean isValid(String[] value, ConstraintValidatorContext context) {
        //校验是否存在正则表达式
        if(regexp == null || regexp.isEmpty()){
            return false;
        }
        //校验数组是否完整
        if(value == null || value.length == 0){
            return false;
        }
        Pattern pattern = Pattern.compile(regexp);
        for(String s : value){
            Matcher matcher = pattern.matcher(s);
            //正则校验
            if(!matcher.matches()){
                return false;
            }
        }
        return true;
    }

    @Override
    public void initialize(StringArray constraintAnnotation) {
        this.regexp = constraintAnnotation.regexp();

    }
}
