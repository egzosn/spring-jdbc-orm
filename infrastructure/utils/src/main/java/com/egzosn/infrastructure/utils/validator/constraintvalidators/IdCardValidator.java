package com.egzosn.infrastructure.utils.validator.constraintvalidators;




import com.egzosn.infrastructure.utils.validator.constraints.IdCard;
import com.egzosn.infrastructure.utils.validator.plug.IdCardUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;

/**
 * Created by ZaoSheng on 2015/6/25.
 */
public class IdCardValidator implements ConstraintValidator<IdCard, String> {
    @Override
    public void initialize(IdCard constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.length() == 0) {
            return true;
        }

        try {
            if ("true".equals(IdCardUtil.IDCardValidate(value))) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();

        }
        return false;
    }
}
