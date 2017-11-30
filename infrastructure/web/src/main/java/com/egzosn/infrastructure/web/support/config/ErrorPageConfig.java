package com.egzosn.infrastructure.web.support.config;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;

/**
 * 错误页面配置类
 *
 * @author 肖红星
 * @create 2016/10/11
 */
@Import(ErrorController.class)
public class ErrorPageConfig implements EmbeddedServletContainerCustomizer {

    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
        //404错误页面
        container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/error/notFound"));
        //500错误页面
        container.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/serverError"));
        //500错误页面
        container.addErrorPages(new ErrorPage(HttpStatus.UNAUTHORIZED, "/unauthorized"));
    }

}
