package com.paymentcomponents.libraries.rest.sdk.wrapper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymentcomponents.libraries.rest.sdk.wrapper.Constants;
import com.paymentcomponents.libraries.rest.sdk.wrapper.TestConstants;
import com.paymentcomponents.libraries.rest.sdk.wrapper.exception.InvalidMessageException;
import com.paymentcomponents.libraries.rest.sdk.wrapper.filter.RequestLogIdFilter;
import com.paymentcomponents.libraries.rest.sdk.wrapper.model.sepa.request.MsgReplyInfoRequest;
import com.paymentcomponents.libraries.rest.sdk.wrapper.model.sepa.request.SepaCreatePacs008Request;
import com.paymentcomponents.libraries.rest.sdk.wrapper.service.SepaService;
import org.hamcrest.Matchers;
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

@WebMvcTest(value = SepaController.class)
public class SepaControllerTest {

    @TestConfiguration
    static class SepaControllerTestContextConfiguration {

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
    private SepaService sepaService;

    @BeforeEach
    public void setup() {
        reset(sepaService);
    }

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void givenValidSepaMessage_whenSepaValidate_thenReturnSepaAsJson() throws Exception {
        //GIVEN
        given(sepaService.validateSepaMessage(anyString())).willReturn(TestConstants.VALID_JSON_SEPA_EPC_PACS_002);

        //WHEN
        mvc.perform(post("/sepa/validate")
                .content(TestConstants.VALID_SEPA_EPC_PACS_002)
                .contentType(MediaType.APPLICATION_XML))

        //THEN
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(TestConstants.VALID_JSON_SEPA_EPC_PACS_002))
                .andExpect(header().string(Constants.REQUEST_LOG_ID, Matchers.anything()));

        then(sepaService).should(times(1)).validateSepaMessage(TestConstants.VALID_SEPA_EPC_PACS_002);
    }

    @Test
    public void givenInValidSepaMessage_whenSepaValidate_thenReturnValidationErrors() throws Exception {
        //GIVEN
        String errorResponse = "[ \"Line: 5 -- cvc-complex-type.2.4.a: Invalid content was found starting with element 'CreDtTm'. One of '{\\\"urn:iso:std:iso:20022:tech:xsd:pacs.002.001.03\\\":MsgId}' is expected.\" ]";
        given(sepaService.validateSepaMessage(anyString())).willThrow(new InvalidMessageException(errorResponse));

        //WHEN
        mvc.perform(post("/sepa/validate")
                .content(TestConstants.INVALID_SEPA_EPC_PACS_002)
                .contentType(MediaType.APPLICATION_XML))

                //THEN
                .andExpect(status().isBadRequest())
                .andExpect(header().string(Constants.REQUEST_LOG_ID, Matchers.anything()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0]", is("Line: 5 -- cvc-complex-type.2.4.a: Invalid content was found starting with element 'CreDtTm'. One of '{\"urn:iso:std:iso:20022:tech:xsd:pacs.002.001.03\":MsgId}' is expected.")));

        then(sepaService).should(times(1)).validateSepaMessage(TestConstants.INVALID_SEPA_EPC_PACS_002);
    }

    @Test
    public void givenValidPacs008Json_whenCreatePacs008_thenReturnXml() throws Exception {
        //GIVEN
        given(sepaService.createPacs008(isA(SepaCreatePacs008Request.class))).willReturn(TestConstants.VALID_SEPA_PACS_008);
        String requestJson = objectMapper.writeValueAsString(TestConstants.getSepaCreatePacs008RequestSample());

        //WHEN
        mvc.perform(post("/sepa/create/pacs008")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))

                //THEN
                .andExpect(status().isOk())
                .andExpect(header().string(Constants.REQUEST_LOG_ID, Matchers.anything()))
                .andExpect(content().contentType(new MediaType(MediaType.APPLICATION_XML, StandardCharsets.UTF_8)))
                .andExpect(content().xml(TestConstants.VALID_SEPA_PACS_008));

        then(sepaService).should(times(1)).createPacs008(isA(SepaCreatePacs008Request.class));
    }

    @Test
    public void givenInvalidPacs008Json_whenCreatePacs008_thenReturnValidationErrors() throws Exception {
        //GIVEN
        String errorResponse = "[\n" +
                "    \"Line: 53 -- cvc-pattern-valid: Value 'AAAAAAAAAAAA' is not facet-valid with respect to pattern '[A-Z]{6,6}[A-Z2-9][A-NP-Z0-9]([A-Z0-9]{3,3}){0,1}' for type 'BICIdentifier'.\"\n" +
                "]";
        given(sepaService.createPacs008(isA(SepaCreatePacs008Request.class))).willThrow(new InvalidMessageException(errorResponse));
        SepaCreatePacs008Request sepaCreatePacs008Request = TestConstants.getSepaCreatePacs008RequestSample();
        sepaCreatePacs008Request.setDebtorBic("AAAAAAAAAAAA"); //invalid value, 12 chars in bic
        String requestJson = objectMapper.writeValueAsString(sepaCreatePacs008Request);

        //WHEN
        mvc.perform(post("/sepa/create/pacs008")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))

                //THEN
                .andExpect(status().isBadRequest())
                .andExpect(header().string(Constants.REQUEST_LOG_ID, Matchers.anything()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0]", is("Line: 53 -- cvc-pattern-valid: Value 'AAAAAAAAAAAA' is not facet-valid with respect to pattern '[A-Z]{6,6}[A-Z2-9][A-NP-Z0-9]([A-Z0-9]{3,3}){0,1}' for type 'BICIdentifier'.")));

        then(sepaService).should(times(1)).createPacs008(isA(SepaCreatePacs008Request.class));
    }

    @Test
    public void givenValidPacs008_whenPaymentReturn_thenReturnPacs004() throws Exception {
        //GIVEN
        MsgReplyInfoRequest msgReplyInfoRequest = new MsgReplyInfoRequest();
        msgReplyInfoRequest.setReasonCode("AC04");
        given(sepaService.generatePaymentReturn(anyString(), isA(MsgReplyInfoRequest.class))).willReturn(TestConstants.VALID_SEPA_PACS_004);

        //WHEN
        mvc.perform(post("/sepa/payment/return")
                .content(TestConstants.VALID_SEPA_PACS_008)
                .queryParam("reasonCode", msgReplyInfoRequest.getReasonCode())
                .contentType(MediaType.APPLICATION_XML))

                //THEN
                .andExpect(status().isOk())
                .andExpect(content().contentType(new MediaType(MediaType.APPLICATION_XML, StandardCharsets.UTF_8)))
                .andExpect(content().xml(TestConstants.VALID_SEPA_PACS_004))
                .andExpect(header().string(Constants.REQUEST_LOG_ID, Matchers.anything()));

        then(sepaService).should(times(1)).generatePaymentReturn(eq(TestConstants.VALID_SEPA_PACS_008), isA(MsgReplyInfoRequest.class));
    }

    @Test
    public void givenInvalidPacs008_whenPaymentReturn_thenReturnValidationErrors() throws Exception {
        //GIVEN
        MsgReplyInfoRequest msgReplyInfoRequest = new MsgReplyInfoRequest();
        msgReplyInfoRequest.setReasonCode("AC04");
        String errorResponse = "[\n" +
                "    \"Line: 5 -- cvc-complex-type.2.4.a: Invalid content was found starting with element 'CreDtTm'. One of '{\\\"urn:iso:std:iso:20022:tech:xsd:pacs.008.001.02\\\":MsgId}' is expected.\"\n" +
                "]";
        given(sepaService.generatePaymentReturn(anyString(), isA(MsgReplyInfoRequest.class))).willThrow(new InvalidMessageException(errorResponse));

        //WHEN
        mvc.perform(post("/sepa/payment/return")
                .content(TestConstants.INVALID_SEPA_PACS_008)
                .queryParam("reasonCode", msgReplyInfoRequest.getReasonCode())
                .contentType(MediaType.APPLICATION_XML))

                //THEN
                .andExpect(status().isBadRequest())
                .andExpect(header().string(Constants.REQUEST_LOG_ID, Matchers.anything()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0]", is("Line: 5 -- cvc-complex-type.2.4.a: Invalid content was found starting with element 'CreDtTm'. One of '{\"urn:iso:std:iso:20022:tech:xsd:pacs.008.001.02\":MsgId}' is expected.")));

        then(sepaService).should(times(1)).generatePaymentReturn(eq(TestConstants.INVALID_SEPA_PACS_008), isA(MsgReplyInfoRequest.class));
    }

    @Test
    public void givenValidPacs008_whenCancellationRequest_thenReturnCamt056() throws Exception {
        //GIVEN
        MsgReplyInfoRequest msgReplyInfoRequest = new MsgReplyInfoRequest();
        msgReplyInfoRequest.setReasonCode("DUPL");
        given(sepaService.generateCancellationRequest(anyString(), isA(MsgReplyInfoRequest.class))).willReturn(TestConstants.VALID_SEPA_CAMT_056);

        //WHEN
        mvc.perform(post("/sepa/cancellation/request")
                .content(TestConstants.VALID_SEPA_PACS_008)
                .queryParam("reasonCode", msgReplyInfoRequest.getReasonCode())
                .contentType(MediaType.APPLICATION_XML))

                //THEN
                .andExpect(status().isOk())
                .andExpect(content().contentType(new MediaType(MediaType.APPLICATION_XML, StandardCharsets.UTF_8)))
                .andExpect(content().xml(TestConstants.VALID_SEPA_CAMT_056))
                .andExpect(header().string(Constants.REQUEST_LOG_ID, Matchers.anything()));

        then(sepaService).should(times(1)).generateCancellationRequest(eq(TestConstants.VALID_SEPA_PACS_008), isA(MsgReplyInfoRequest.class));
    }

    @Test
    public void givenInvalidPacs008_whenCancellationRequest_thenReturnValidationErrors() throws Exception {
        //GIVEN
        MsgReplyInfoRequest msgReplyInfoRequest = new MsgReplyInfoRequest();
        msgReplyInfoRequest.setReasonCode("DUPL");
        String errorResponse = "[\n" +
                "    \"Line: 5 -- cvc-complex-type.2.4.a: Invalid content was found starting with element 'CreDtTm'. One of '{\\\"urn:iso:std:iso:20022:tech:xsd:pacs.008.001.02\\\":MsgId}' is expected.\"\n" +
                "]";
        given(sepaService.generateCancellationRequest(anyString(), isA(MsgReplyInfoRequest.class))).willThrow(new InvalidMessageException(errorResponse));

        //WHEN
        mvc.perform(post("/sepa/cancellation/request")
                .content(TestConstants.INVALID_SEPA_PACS_008)
                .queryParam("reasonCode", msgReplyInfoRequest.getReasonCode())
                .contentType(MediaType.APPLICATION_XML))

                //THEN
                .andExpect(status().isBadRequest())
                .andExpect(header().string(Constants.REQUEST_LOG_ID, Matchers.anything()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0]", is("Line: 5 -- cvc-complex-type.2.4.a: Invalid content was found starting with element 'CreDtTm'. One of '{\"urn:iso:std:iso:20022:tech:xsd:pacs.008.001.02\":MsgId}' is expected.")));

        then(sepaService).should(times(1)).generateCancellationRequest(eq(TestConstants.INVALID_SEPA_PACS_008), isA(MsgReplyInfoRequest.class));
    }

    @Test
    public void givenValidPacs008_whenResolutionOfInvestigation_thenReturnCamt029() throws Exception {
        //GIVEN
        MsgReplyInfoRequest msgReplyInfoRequest = new MsgReplyInfoRequest();
        msgReplyInfoRequest.setReasonCode("CUST");
        given(sepaService.generateResolutionOfInvestigation(anyString(), isA(MsgReplyInfoRequest.class))).willReturn(TestConstants.VALID_SEPA_CAMT_029);

        //WHEN
        mvc.perform(post("/sepa/resolution/of/investigation")
                .content(TestConstants.VALID_SEPA_PACS_008)
                .queryParam("reasonCode", msgReplyInfoRequest.getReasonCode())
                .contentType(MediaType.APPLICATION_XML))

                //THEN
                .andExpect(status().isOk())
                .andExpect(content().contentType(new MediaType(MediaType.APPLICATION_XML, StandardCharsets.UTF_8)))
                .andExpect(content().xml(TestConstants.VALID_SEPA_CAMT_029))
                .andExpect(header().string(Constants.REQUEST_LOG_ID, Matchers.anything()));

        then(sepaService).should(times(1)).generateResolutionOfInvestigation(eq(TestConstants.VALID_SEPA_PACS_008), isA(MsgReplyInfoRequest.class));
    }

    @Test
    public void givenInvalidPacs008_whenResolutionOfInvestigation_thenReturnValidationErrors() throws Exception {
        //GIVEN
        MsgReplyInfoRequest msgReplyInfoRequest = new MsgReplyInfoRequest();
        msgReplyInfoRequest.setReasonCode("CUST");
        String errorResponse = "[\n" +
                "    \"Line: 5 -- cvc-complex-type.2.4.a: Invalid content was found starting with element 'CreDtTm'. One of '{\\\"urn:iso:std:iso:20022:tech:xsd:pacs.008.001.02\\\":MsgId}' is expected.\"\n" +
                "]";
        given(sepaService.generateResolutionOfInvestigation(anyString(), isA(MsgReplyInfoRequest.class))).willThrow(new InvalidMessageException(errorResponse));

        //WHEN
        mvc.perform(post("/sepa/resolution/of/investigation")
                .content(TestConstants.INVALID_SEPA_PACS_008)
                .queryParam("reasonCode", msgReplyInfoRequest.getReasonCode())
                .contentType(MediaType.APPLICATION_XML))

                //THEN
                .andExpect(status().isBadRequest())
                .andExpect(header().string(Constants.REQUEST_LOG_ID, Matchers.anything()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0]", is("Line: 5 -- cvc-complex-type.2.4.a: Invalid content was found starting with element 'CreDtTm'. One of '{\"urn:iso:std:iso:20022:tech:xsd:pacs.008.001.02\":MsgId}' is expected.")));

        then(sepaService).should(times(1)).generateResolutionOfInvestigation(eq(TestConstants.INVALID_SEPA_PACS_008), isA(MsgReplyInfoRequest.class));
    }

}
