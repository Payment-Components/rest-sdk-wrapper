package com.paymentcomponents.libraries.rest.sdk.wrapper.controller;

import com.paymentcomponents.libraries.rest.sdk.wrapper.Constants;
import com.paymentcomponents.libraries.rest.sdk.wrapper.TestConstants;
import com.paymentcomponents.libraries.rest.sdk.wrapper.exception.InvalidMessageException;
import com.paymentcomponents.libraries.rest.sdk.wrapper.filter.RequestLogIdFilter;
import com.paymentcomponents.libraries.rest.sdk.wrapper.service.MxService;
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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = MxController.class)
public class MxControllerTest {

    @TestConfiguration
    static class MxControllerTestContextConfiguration {

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
    private MxService mxService;

    @BeforeEach
    public void setup() {
        reset(mxService);
    }

    @Test
    public void givenValidMxMessage_whenMxValidate_thenReturnMxAsJson() throws Exception {
        //GIVEN
        given(mxService.validateMx(anyString())).willReturn(TestConstants.VALID_JSON_MX_PAIN001);

        //WHEN
        mvc.perform(post("/mx/validate")
                .content(TestConstants.VALID_MX_PAIN_001)
                .contentType(MediaType.APPLICATION_XML))

        //THEN
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(TestConstants.VALID_JSON_MX_PAIN001))
                .andExpect(header().string(Constants.REQUEST_LOG_ID, Matchers.anything()));

        then(mxService).should(times(1)).validateMx(TestConstants.VALID_MX_PAIN_001);
    }

    @Test
    public void givenInValidMxMessage_whenMxValidate_thenReturnValidationErrors() throws Exception {
        //GIVEN
        String errorReponse = "[\n" +
                "    {\n" +
                "        \"severity\": \"ERROR\",\n" +
                "        \"errorCode\": null,\n" +
                "        \"fieldPath\": null,\n" +
                "        \"description\": \"cvc-complex-type.2.4.a: Invalid content was found starting with element 'CreDtTm'. One of '{\\\"urn:iso:std:iso:20022:tech:xsd:pain.001.001.09\\\":MsgId}' is expected.\",\n" +
                "        \"erroneousValue\": null,\n" +
                "        \"line\": 0,\n" +
                "        \"column\": 0\n" +
                "    }\n" +
                "]";
        willThrow(new InvalidMessageException(errorReponse)).given(mxService).validateMx(anyString());

        //WHEN
        mvc.perform(post("/mx/validate")
                .content(TestConstants.INVALID_MX_PAIN_001)
                .contentType(MediaType.APPLICATION_XML))

        //THEN
                .andExpect(status().isBadRequest())
                .andExpect(header().string(Constants.REQUEST_LOG_ID, Matchers.anything()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].severity", is("ERROR")))
                .andExpect(jsonPath("$[0].errorCode", IsNull.nullValue()))
                .andExpect(jsonPath("$[0].fieldPath", IsNull.nullValue()))
                .andExpect(jsonPath("$[0].description", is("cvc-complex-type.2.4.a: Invalid content was found starting with element 'CreDtTm'. One of '{\"urn:iso:std:iso:20022:tech:xsd:pain.001.001.09\":MsgId}' is expected.")))
                .andExpect(jsonPath("$[0].erroneousValue", IsNull.nullValue()))
                .andExpect(jsonPath("$[0].line", is(0)))
                .andExpect(jsonPath("$[0].column", is(0)));

        then(mxService).should(times(1)).validateMx(TestConstants.INVALID_MX_PAIN_001);
    }

}
