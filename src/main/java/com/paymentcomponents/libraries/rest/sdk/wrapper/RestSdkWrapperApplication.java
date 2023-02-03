package com.paymentcomponents.libraries.rest.sdk.wrapper;

import com.paymentcomponents.libraries.rest.sdk.wrapper.controller.*;
import com.paymentcomponents.libraries.rest.sdk.wrapper.service.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@SpringBootApplication
@Import({
        MxController.class,
        CbprTranslatorController.class,
        CbprController.class,
        MtController.class,
        SepaController.class,
        RtgsTranslatorController.class,
        RtgsController.class,
        CbprService.class,
        CbprTranslatorService.class,
        MtService.class,
        MxService.class,
        RtgsService.class,
        RtgsTranslatorService.class,
        SepaService.class,
})
public class RestSdkWrapperApplication {

    /*
     * Create required HandlerMapping, to avoid several default HandlerMapping instances being created
     */
    @Bean
    public HandlerMapping handlerMapping() {
        return new RequestMappingHandlerMapping();
    }

    /*
     * Create required HandlerAdapter, to avoid several default HandlerAdapter instances being created
     */
    @Bean
    public HandlerAdapter handlerAdapter() {
        return new RequestMappingHandlerAdapter();
    }

    public static void main(String[] args) {
        SpringApplication.run(RestSdkWrapperApplication.class, args);
    }

}
