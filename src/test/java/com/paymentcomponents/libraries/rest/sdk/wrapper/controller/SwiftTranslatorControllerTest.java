package com.paymentcomponents.libraries.rest.sdk.wrapper.controller;

import com.paymentcomponents.libraries.rest.sdk.wrapper.Constants;
import com.paymentcomponents.libraries.rest.sdk.wrapper.TestConstants;
import com.paymentcomponents.libraries.rest.sdk.wrapper.exception.InvalidMessageException;
import com.paymentcomponents.libraries.rest.sdk.wrapper.filter.RequestLogIdFilter;
import com.paymentcomponents.libraries.rest.sdk.wrapper.service.SwiftTranslatorService;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = SwiftTranslatorController.class)
public class SwiftTranslatorControllerTest {

    @TestConfiguration
    static class SwiftTranslatorControllerTestContextConfiguration {

        @Bean
        public FilterRegistrationBean<RequestLogIdFilter> requestLogIdFilter() {
            FilterRegistrationBean<RequestLogIdFilter> registrationBean = new FilterRegistrationBean<>();
            registrationBean.setFilter(new RequestLogIdFilter());

            return registrationBean;
        }
    }

    @Autowired
    private MockMvc mvc;

    @MockBean
    private SwiftTranslatorService swiftTranslatorService;

    @BeforeEach
    public void setup() {
        reset(swiftTranslatorService);
    }

    @Test
    public void givenValidMtMessage_whenTranslateMtToMx_thenReturnMxMessage() throws Exception {
        //GIVEN
        given(swiftTranslatorService.translateMtToMx(anyString())).willReturn(TestConstants.VALID_SWIFT_TRANSLATOR_MT_TO_MX_RESPONSE);

        //WHEN
        mvc.perform(post("/swift/translator/mt/to/mx")
                .content(TestConstants.VALID_SWIFT_TRANSLATOR_MT_TO_MX_REQUEST)
                .contentType(MediaType.TEXT_PLAIN))

        //THEN
                .andExpect(status().isOk())
                .andExpect(header().string(Constants.REQUEST_LOG_ID, Matchers.anything()))
                .andExpect(content().contentType(new MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_8)))
                .andExpect(content().string(TestConstants.VALID_SWIFT_TRANSLATOR_MT_TO_MX_RESPONSE));

        then(swiftTranslatorService).should(times(1)).translateMtToMx(TestConstants.VALID_SWIFT_TRANSLATOR_MT_TO_MX_REQUEST);
    }

    @Test
    public void givenInvalidMtMessage_whenTranslateMtToMx_thenReturnValidationErrors() throws Exception {
        //GIVEN
        String errorResponse = "[\n" +
                "    {\n" +
                "        \"tagName\": \"20\",\n" +
                "        \"description\": \"SV16 - Mandatory Tag is missing \",\n" +
                "        \"sequence\": null,\n" +
                "        \"occurs\": \"1\",\n" +
                "        \"line\": null,\n" +
                "        \"messageIndex\": null,\n" +
                "        \"errorCode\": \"SV16\"\n" +
                "    }\n" +
                "]";
        given(swiftTranslatorService.translateMtToMx(anyString())).willThrow(new InvalidMessageException(errorResponse));

        //WHEN
        mvc.perform(post("/swift/translator/mt/to/mx")
                .content(TestConstants.INVALID_SWIFT_TRANSLATOR_MT_TO_MX_REQUEST)
                .contentType(MediaType.TEXT_PLAIN))

        //THEN
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].tagName", is("20")))
                .andExpect(jsonPath("$[0].description", is("SV16 - Mandatory Tag is missing ")))
                .andExpect(jsonPath("$[0].sequence", IsNull.nullValue()))
                .andExpect(jsonPath("$[0].occurs", is("1")))
                .andExpect(jsonPath("$[0].line", IsNull.nullValue()))
                .andExpect(jsonPath("$[0].messageIndex", IsNull.nullValue()))
                .andExpect(jsonPath("$[0].errorCode", is("SV16")));
        then(swiftTranslatorService).should(times(1)).translateMtToMx(TestConstants.INVALID_SWIFT_TRANSLATOR_MT_TO_MX_REQUEST);
    }


}
