package com.egzosn.infrastructure.utils.validator.constraintvalidators;

import com.egzosn.infrastructure.utils.validator.constraints.SingleFileUpload;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by LinJie on 2014/10/22.
 *
 * @author Linjie
 *         文件合法验证
 */
public class SingleFileUploadValidate implements ConstraintValidator<SingleFileUpload, MultipartFile> {

    private long size;
    private String[] contentTypes;
    private boolean allowEmpty;



    public static void main(String[] args) {
        SingleFileUploadValidate p = new SingleFileUploadValidate();

    }

    @Override
    public void initialize(SingleFileUpload uploadFileValidate) {
        this.allowEmpty = uploadFileValidate.allowEmpty();
        this.size = uploadFileValidate.size();
        this.contentTypes = uploadFileValidate.contentTypes();

    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {

        System.out.println(multipartFile.getContentType());
        if (multipartFile == null || multipartFile.getSize() == 0) {
            return allowEmpty;
        }

        return (allowSize(multipartFile) && allowContentType(multipartFile));

    }

    private boolean allowSize(MultipartFile multipartFile) {

        return (size == 0 || multipartFile.getSize() <= size);
    }

    private boolean allowContentType(MultipartFile multipartFile) {
        // 不限制
        if (contentTypes.length == 0){
            return true;
        }
        for (int i = 0; i < contentTypes.length; i++) {
            if (contentTypes[i].equals(multipartFile.getContentType())) {
                return true;
            }
        }
        return false;
    }


}
