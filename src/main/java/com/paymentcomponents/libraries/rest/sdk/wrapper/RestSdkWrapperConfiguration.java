package com.paymentcomponents.libraries.rest.sdk.wrapper;

import com.paymentcomponents.libraries.rest.sdk.wrapper.filter.RequestLogIdFilter;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestSdkWrapperConfiguration {

    @Bean
    public OpenAPI openApiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("Payment Components")
                        .description("Financial Messaging APIs")
                        .version("1.0.0")); //should be the same as maven
    }

    @Bean
    public GroupedOpenApi openApiAllApis() {
        return GroupedOpenApi.builder()
                .group("All APIs")
                .pathsToMatch(
                        "/mt/**",
                        "/sepa/**",
                        "/mx/**",
                        "/swift/translator/**",
                        "/cbpr/**"
                )
                .build();
    }

    @Bean
    public FilterRegistrationBean<RequestLogIdFilter> requestLogIdFilter() {
        FilterRegistrationBean<RequestLogIdFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new RequestLogIdFilter());

        registrationBean.addUrlPatterns("/mt/*",
                                        "/sepa/*",
                                        "/mx/*",
                                        "/swift/translator/*",
                                        "/cbpr/*");

        return registrationBean;

    }

}
