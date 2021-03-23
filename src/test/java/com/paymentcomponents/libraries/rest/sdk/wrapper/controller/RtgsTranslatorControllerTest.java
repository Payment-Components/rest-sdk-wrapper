package com.paymentcomponents.libraries.rest.sdk.wrapper.controller;

import com.paymentcomponents.libraries.rest.sdk.wrapper.Constants;
import com.paymentcomponents.libraries.rest.sdk.wrapper.TestConstants;
import com.paymentcomponents.libraries.rest.sdk.wrapper.exception.InvalidMessageException;
import com.paymentcomponents.libraries.rest.sdk.wrapper.filter.RequestLogIdFilter;
import com.paymentcomponents.libraries.rest.sdk.wrapper.service.RtgsTranslatorService;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = RtgsTranslatorController.class)
public class RtgsTranslatorControllerTest {

    @TestConfiguration
    static class RtgsTranslatorControllerTestContextConfiguration {

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
    private RtgsTranslatorService rtgsTranslatorService;

    @BeforeEach
    public void setup() {
        reset(rtgsTranslatorService);
    }

    @Test
    public void givenValidMtMessage_whenTranslateMtToMx_thenReturnMxMessage() throws Exception {
        //GIVEN
        given(rtgsTranslatorService.translateMtToMx(anyString())).willReturn(TestConstants.VALID_RTGS_TRANSLATOR_MT_TO_MX_RESPONSE);

        //WHEN
        mvc.perform(post("/swift/translator/rtgs/mt/to/mx")
                .content(TestConstants.VALID_RTGS_TRANSLATOR_MT_TO_MX_REQUEST)
                .contentType(MediaType.TEXT_PLAIN))

                //THEN
                .andExpect(status().isOk())
                .andExpect(header().string(Constants.REQUEST_LOG_ID, Matchers.anything()))
                .andExpect(content().contentType(new MediaType(MediaType.APPLICATION_XML, StandardCharsets.UTF_8)))
                .andExpect(content().string(TestConstants.VALID_RTGS_TRANSLATOR_MT_TO_MX_RESPONSE));

        then(rtgsTranslatorService).should(times(1)).translateMtToMx(TestConstants.VALID_RTGS_TRANSLATOR_MT_TO_MX_REQUEST);
    }

    @Test
    public void givenInvalidMtMessage_whenTranslateMtToMx_thenReturnValidationErrors() throws Exception {
        //GIVEN
        String errorResponse = "Message could not be translated";
        given(rtgsTranslatorService.translateMtToMx(anyString())).willThrow(new InvalidMessageException(errorResponse));

        //WHEN
        mvc.perform(post("/swift/translator/rtgs/mt/to/mx")
                .content(TestConstants.INVALID_RTGS_TRANSLATOR_MT_TO_MX_REQUEST)
                .contentType(MediaType.TEXT_PLAIN))

                //THEN
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(errorResponse));
        then(rtgsTranslatorService).should(times(1)).translateMtToMx(TestConstants.INVALID_RTGS_TRANSLATOR_MT_TO_MX_REQUEST);
    }

    @Test
    public void givenValidMxMessage_whenTranslateMxToMt_thenReturnMtMessage() throws Exception {
        //GIVEN
        given(rtgsTranslatorService.translateMxToMt(anyString())).willReturn(TestConstants.VALID_RTGS_TRANSLATOR_MX_TO_MT_RESPONSE);

        //WHEN
        mvc.perform(post("/swift/translator/rtgs/mx/to/mt")
                .content(TestConstants.VALID_RTGS_TRANSLATOR_MX_TO_MT_REQUEST)
                .contentType(MediaType.APPLICATION_XML))

                //THEN
                .andExpect(status().isOk())
                .andExpect(header().string(Constants.REQUEST_LOG_ID, Matchers.anything()))
                .andExpect(content().contentType(new MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_8)))
                .andExpect(content().string(TestConstants.VALID_RTGS_TRANSLATOR_MX_TO_MT_RESPONSE));

        then(rtgsTranslatorService).should(times(1)).translateMxToMt(TestConstants.VALID_RTGS_TRANSLATOR_MX_TO_MT_REQUEST);
    }

    @Test
    public void givenInvalidMxMessage_whenTranslateMxToMt_thenReturnValidationErrors() throws Exception {
        //GIVEN
        String errorResponse = "[\n" +
                "    {\n" +
                "        \"severity\": \"ERROR\",\n" +
                "        \"errorCode\": null,\n" +
                "        \"fieldPath\": null,\n" +
                "        \"description\": \"cvc-complex-type.2.4.a: Invalid content was found starting with element 'CreDtTm'. One of '{\\\"urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08\\\":MsgId}' is expected.\",\n" +
                "        \"erroneousValue\": null,\n" +
                "        \"line\": 5,\n" +
                "        \"column\": 22\n" +
                "    }\n" +
                "]";
        given(rtgsTranslatorService.translateMxToMt(anyString())).willThrow(new InvalidMessageException(errorResponse));

        //WHEN
        mvc.perform(post("/swift/translator/rtgs/mx/to/mt")
                .content(TestConstants.INVALID_RTGS_TRANSLATOR_MX_TO_MT_REQUEST)
                .contentType(MediaType.APPLICATION_XML))

                //THEN
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].severity", is("ERROR")))
                .andExpect(jsonPath("$[0].errorCode", IsNull.nullValue()))
                .andExpect(jsonPath("$[0].fieldPath", IsNull.nullValue()))
                .andExpect(jsonPath("$[0].description", is("cvc-complex-type.2.4.a: Invalid content was found starting with element 'CreDtTm'. One of '{\"urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08\":MsgId}' is expected.")))
                .andExpect(jsonPath("$[0].erroneousValue", IsNull.nullValue()))
                .andExpect(jsonPath("$[0].line", is(5)))
                .andExpect(jsonPath("$[0].column", is(22)));

        then(rtgsTranslatorService).should(times(1)).translateMxToMt(TestConstants.INVALID_RTGS_TRANSLATOR_MX_TO_MT_REQUEST);
    }

}
