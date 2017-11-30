package com.egzosn.infrastructure.utils.validator.constraintvalidators;



import com.egzosn.infrastructure.utils.validator.constraints.StringEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import java.util.Arrays;


/**
 * Created by egan on 15-1-26.
 */
public class StringEnumValidate implements ConstraintValidator<StringEnum, String> {
    private String[] type;

    private boolean allowNull = false;

    @Override
    public void initialize(StringEnum stringEnumValidate) {
        this.type =  stringEnumValidate.type();
        Arrays.sort(type);
        this.allowNull = stringEnumValidate.allowNull();
    }

    @Override
    public boolean isValid(String string, ConstraintValidatorContext constraintValidatorContext) {
        if (type == null || type.length == 0 || (string == null && allowNull)) {
            return true;
        }

        return  Arrays.binarySearch(type, string) >= 0;
    }


}
