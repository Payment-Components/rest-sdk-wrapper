package com.paymentcomponents.libraries.rest.sdk.wrapper.controller;

import com.paymentcomponents.libraries.rest.sdk.wrapper.Constants;
import com.paymentcomponents.libraries.rest.sdk.wrapper.TestConstants;
import com.paymentcomponents.libraries.rest.sdk.wrapper.exception.InvalidMessageException;
import com.paymentcomponents.libraries.rest.sdk.wrapper.filter.RequestLogIdFilter;
import com.paymentcomponents.libraries.rest.sdk.wrapper.service.CbprService;
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
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = CbprController.class)
public class CbprControllerTest {

    @TestConfiguration
    static class CbprControllerTestContextConfiguration {

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
    private CbprService cbprService;

    @BeforeEach
    public void setup() {
        reset(cbprService);
    }

    @Test
    public void givenValidCbprMessage_whenCbprValidate_thenReturnNoContent() throws Exception {
        //GIVEN
        willDoNothing().given(cbprService).validateCbpr(anyString());

        //WHEN
        mvc.perform(post("/cbpr/validate")
                .content(TestConstants.VALID_CBPR_REQUEST)
                .contentType(MediaType.TEXT_PLAIN))

        //THEN
                .andExpect(status().isNoContent())
                .andExpect(header().string(Constants.REQUEST_LOG_ID, Matchers.anything()));

        then(cbprService).should(times(1)).validateCbpr(TestConstants.VALID_CBPR_REQUEST);
    }

    @Test
    public void givenInValidCbprMessage_whenCbprValidate_thenReturnValidationErrors() throws Exception {
        //GIVEN
        String errorResponse = "[\n" +
                "    {\n" +
                "        \"severity\": \"ERROR\",\n" +
                "        \"errorCode\": null,\n" +
                "        \"fieldPath\": null,\n" +
                "        \"description\": \"cvc-complex-type.2.4.a: Invalid content was found starting with element 'CreDtTm'. One of '{\\\"urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08\\\":MsgId}' is expected.\",\n" +
                "        \"erroneousValue\": null,\n" +
                "        \"line\": 4,\n" +
                "        \"column\": 22\n" +
                "    }\n" +
                "]";
        willThrow(new InvalidMessageException(errorResponse)).given(cbprService).validateCbpr(anyString());

        //WHEN
        mvc.perform(post("/cbpr/validate")
                .content(TestConstants.INVALID_CBPR_REQUEST)
                .contentType(MediaType.TEXT_PLAIN))

        //THEN
                .andExpect(status().isBadRequest())
                .andExpect(header().string(Constants.REQUEST_LOG_ID, Matchers.anything()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].severity", is("ERROR")))
                .andExpect(jsonPath("$[0].errorCode", IsNull.nullValue()))
                .andExpect(jsonPath("$[0].fieldPath", IsNull.nullValue()))
                .andExpect(jsonPath("$[0].description", is("cvc-complex-type.2.4.a: Invalid content was found starting with element 'CreDtTm'. One of '{\"urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08\":MsgId}' is expected.")))
                .andExpect(jsonPath("$[0].erroneousValue", IsNull.nullValue()))
                .andExpect(jsonPath("$[0].line", is(4)))
                .andExpect(jsonPath("$[0].column", is(22)));

        then(cbprService).should(times(1)).validateCbpr(TestConstants.INVALID_CBPR_REQUEST);
    }

    @Test
    public void givenValidCbprMessage_whenCbprEnvelope_thenReturnEnvelopedCbpr() throws Exception {
        //GIVEN
        given(cbprService.envelopeCbpr(anyString())).willReturn(TestConstants.CBPR_ENVELOPE);

        //WHEN
        mvc.perform(post("/cbpr/envelope")
                .content(TestConstants.VALID_CBPR_REQUEST)
                .contentType(MediaType.TEXT_PLAIN))

        //THEN
                .andExpect(status().isOk())
                .andExpect(header().string(Constants.REQUEST_LOG_ID, Matchers.anything()))
                .andExpect(content().contentType(new MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_8)))
                .andExpect(content().string(TestConstants.CBPR_ENVELOPE));

        then(cbprService).should(times(1)).envelopeCbpr(TestConstants.VALID_CBPR_REQUEST);
    }

    @Test
    public void givenInValidCbprMessage_whenCbprEnvelope_thenReturnValidationErrors() throws Exception {
        //GIVEN
        String errorResponse = "[\n" +
                "    {\n" +
                "        \"severity\": \"ERROR\",\n" +
                "        \"errorCode\": null,\n" +
                "        \"fieldPath\": null,\n" +
                "        \"description\": \"cvc-complex-type.2.4.a: Invalid content was found starting with element 'CreDtTm'. One of '{\\\"urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08\\\":MsgId}' is expected.\",\n" +
                "        \"erroneousValue\": null,\n" +
                "        \"line\": 4,\n" +
                "        \"column\": 22\n" +
                "    }\n" +
                "]";
        willThrow(new InvalidMessageException(errorResponse)).given(cbprService).envelopeCbpr(anyString());

        //WHEN
        mvc.perform(post("/cbpr/envelope")
                .content(TestConstants.INVALID_CBPR_REQUEST)
                .contentType(MediaType.TEXT_PLAIN))

        //THEN
                .andExpect(status().isBadRequest())
                .andExpect(header().string(Constants.REQUEST_LOG_ID, Matchers.anything()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].severity", is("ERROR")))
                .andExpect(jsonPath("$[0].errorCode", IsNull.nullValue()))
                .andExpect(jsonPath("$[0].fieldPath", IsNull.nullValue()))
                .andExpect(jsonPath("$[0].description", is("cvc-complex-type.2.4.a: Invalid content was found starting with element 'CreDtTm'. One of '{\"urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08\":MsgId}' is expected.")))
                .andExpect(jsonPath("$[0].erroneousValue", IsNull.nullValue()))
                .andExpect(jsonPath("$[0].line", is(4)))
                .andExpect(jsonPath("$[0].column", is(22)));

        then(cbprService).should(times(1)).envelopeCbpr(TestConstants.INVALID_CBPR_REQUEST);
    }

}
