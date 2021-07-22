package com.paymentcomponents.libraries.rest.sdk.wrapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class RestSdkWrapperApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(RestSdkWrapperApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(RestSdkWrapperApplication.class, args);
    }

}
