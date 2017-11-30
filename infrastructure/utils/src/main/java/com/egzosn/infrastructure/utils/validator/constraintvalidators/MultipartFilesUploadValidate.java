package com.egzosn.infrastructure.utils.validator.constraintvalidators;

import com.egzosn.infrastructure.utils.validator.constraints.MultipartFilesUpload;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by LinJie on 2014/10/22.
 *
 * @author Linjie
 *         多文件法验证
 */
public class MultipartFilesUploadValidate implements ConstraintValidator<MultipartFilesUpload, MultipartFile[]> {


    private long singleSize;
    private String[] contentTypes;
    private boolean allowSingleEmpty;
    private boolean allowEmpty;
    private long totalSize;




    @Override
    public void initialize(MultipartFilesUpload uploadFileValidate) {
        this.allowEmpty = uploadFileValidate.allowEmpty();
        this.allowSingleEmpty = uploadFileValidate.allowSingleEmpty();
        this.singleSize = uploadFileValidate.singleSize();
        this.totalSize = uploadFileValidate.totalSize();
        this.contentTypes = uploadFileValidate.contentTypes();
    }

    @Override
    public boolean isValid(MultipartFile[] multipartFiles, ConstraintValidatorContext constraintValidatorContext) {
        long _totalSize = 0L;
        if (multipartFiles == null || multipartFiles.length <= 0) {
            return allowEmpty;
        }
        boolean result = false;
        for (MultipartFile multipartFile : multipartFiles) {
            result = (allowSize(multipartFile) && allowContentType(multipartFile));
            if (!result) {
                return false;
            }
            _totalSize += multipartFile.getSize();
        }
        /*
          totalSize = 0 ,则不限制
          如果超出大小，则限制
         */
        return totalSize == 0 || _totalSize < totalSize;
    }

    private boolean allowSize(MultipartFile multipartFile) {
        if (multipartFile.getSize() <= 0) {
            return allowSingleEmpty;
        }
        return (singleSize == 0 || multipartFile.getSize() <= singleSize);
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
