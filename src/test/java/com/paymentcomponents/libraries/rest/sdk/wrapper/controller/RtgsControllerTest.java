package com.paymentcomponents.libraries.rest.sdk.wrapper.controller;

import com.paymentcomponents.libraries.rest.sdk.wrapper.Constants;
import com.paymentcomponents.libraries.rest.sdk.wrapper.TestConstants;
import com.paymentcomponents.libraries.rest.sdk.wrapper.exception.InvalidMessageException;
import com.paymentcomponents.libraries.rest.sdk.wrapper.filter.RequestLogIdFilter;
import com.paymentcomponents.libraries.rest.sdk.wrapper.service.RtgsService;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
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

@WebMvcTest(value = RtgsController.class)
public class RtgsControllerTest {

    @TestConfiguration
    static class RtgsControllerTestContextConfiguration {

        @Bean
        public FilterRegistrationBean<RequestLogIdFilter> requestLogIdFilter() {
            FilterRegistrationBean<RequestLogIdFilter> registrationBean = new FilterRegistrationBean<>();
            registrationBean.setFilter(new RequestLogIdFilter());

            return registrationBean;
        }
        
        @Bean
        public RtgsService rtgsService() {
            return mock(RtgsService.class);
        }
    }

    @Autowired
    private MockMvc mvc;

    @Autowired
    private RtgsService rtgsService;

    @BeforeEach
    public void setup() {
        reset(rtgsService);
    }

    @Test
    public void givenValidRtgsMessage_whenRtgsValidate_thenReturnRtgsAsJson() throws Exception {
        //GIVEN
        given(rtgsService.validateRtgs(anyString())).willReturn(TestConstants.VALID_JSON_RTGS_PACS_009);

        //WHEN
        mvc.perform(post("/rtgs/validate")
                .content(TestConstants.VALID_RTGS_PACS_009)
                .contentType(MediaType.APPLICATION_XML))

        //THEN
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(TestConstants.VALID_JSON_RTGS_PACS_009))
                .andExpect(header().string(Constants.REQUEST_LOG_ID, Matchers.anything()));

        then(rtgsService).should(times(1)).validateRtgs(TestConstants.VALID_RTGS_PACS_009);
    }

    @Test
    public void givenInvalidRtgsMessage_whenRtgsValidate_thenReturnValidationErrors() throws Exception {
        //GIVEN
        String errorResponse = "[\n" +
                "    {\n" +
                "        \"severity\": \"ERROR\",\n" +
                "        \"errorCode\": null,\n" +
                "        \"fieldPath\": \"/Document/FICdtTrf/GrpHdr/CreDtTm\",\n" +
                "        \"description\": \"cvc-complex-type.2.4.a: Invalid content was found starting with element 'CreDtTm'. One of '{\\\"urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08\\\":MsgId}' is expected.\",\n" +
                "        \"erroneousValue\": null,\n" +
                "        \"line\": 6,\n" +
                "        \"column\": 22\n" +
                "    }\n" +
                "]";
        given(rtgsService.validateRtgs(anyString())).willThrow(new InvalidMessageException(errorResponse));

        //WHEN
        mvc.perform(post("/rtgs/validate")
                .content(TestConstants.INVALID_RTGS_PACS_009)
                .contentType(MediaType.APPLICATION_XML))

        //THEN
                .andExpect(status().isBadRequest())
                .andExpect(header().string(Constants.REQUEST_LOG_ID, Matchers.anything()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].severity", is("ERROR")))
                .andExpect(jsonPath("$[0].errorCode", IsNull.nullValue()))
                .andExpect(jsonPath("$[0].fieldPath", is("/Document/FICdtTrf/GrpHdr/CreDtTm")))
                .andExpect(jsonPath("$[0].description", is("cvc-complex-type.2.4.a: Invalid content was found starting with element 'CreDtTm'. One of '{\"urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08\":MsgId}' is expected.")))
                .andExpect(jsonPath("$[0].erroneousValue", IsNull.nullValue()))
                .andExpect(jsonPath("$[0].line", is(6)))
                .andExpect(jsonPath("$[0].column", is(22)));

        then(rtgsService).should(times(1)).validateRtgs(TestConstants.INVALID_RTGS_PACS_009);
    }

}
