package com.egzosn.examples;

import com.egzosn.infrastructure.web.support.config.Swagger2;
import com.egzosn.infrastructure.web.support.config.error.ErrorCodeProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 * @author egan
 * @email egzosn@gmail.com
 * @date 2017/11/21
 */
@SpringBootApplication
@Import(value = { ErrorCodeProperties.class, MethodValidationPostProcessor.class, Swagger2.class})
public class SplitTableApplication {

    public static void main(String[] args) {
        SpringApplication.run(SplitTableApplication.class, args);
    }


}
