package com.egzosn.infrastructure.utils.validator.constraintvalidators;




import com.egzosn.infrastructure.utils.validator.constraints.StringArrayEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;


/**
 * Created by linjie on 15-1-26.
 */
public class StringArrayEnumValidate implements ConstraintValidator<StringArrayEnum, String[]> {
    private String[] type;

    private int arrLength = 0;

    private boolean allowEmpty = false;

    @Override
    public void initialize(StringArrayEnum stringEnumValidate) {
        this.type = stringEnumValidate.type();
        Arrays.sort(type);
        arrLength = stringEnumValidate.arrLength();
        allowEmpty = stringEnumValidate.allowEmpty();
    }

    @Override
    public boolean isValid(String[] arrs, ConstraintValidatorContext constraintValidatorContext) {

        // 类型不限制，长度也不限制，则直接返回
        if ((type == null || type.length == 0) && arrLength == 0) {
            return true;
        }



        // 是否允许空
        if (!allowEmpty && (arrs == null  ||  arrs.length == 0)) {
            return false;
        }
        if (arrLength > 0 && arrs.length > arrLength) {
            // 长度如果超出 false
            return false;
        }
        // 不在类别中的 false
        for (String arr : arrs) {
            if (allowEmpty){
                if (Arrays.binarySearch(type, arr) < 0) {
                    return false;
                }
            }

        }
        return true;
    }

}
