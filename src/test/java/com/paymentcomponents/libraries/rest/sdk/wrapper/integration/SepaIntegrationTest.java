package com.paymentcomponents.libraries.rest.sdk.wrapper.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymentcomponents.libraries.rest.sdk.wrapper.TestConstants;
import com.paymentcomponents.libraries.rest.sdk.wrapper.TestUtils;
import com.paymentcomponents.libraries.rest.sdk.wrapper.model.sepa.request.MsgReplyInfoRequest;
import com.paymentcomponents.libraries.rest.sdk.wrapper.model.sepa.request.SepaCreatePacs008Request;
import com.paymentcomponents.libraries.rest.sdk.wrapper.Constants;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SepaIntegrationTest {

    @Autowired
    private MockMvc mvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void givenValidSepaMessage_whenSepaValidate_thenReturnSepaAsJson() throws Exception {
        //GIVEN

        //WHEN
        mvc.perform(post("/sepa/validate")
                .content(TestConstants.VALID_SEPA_PACS_008)
                .contentType(MediaType.APPLICATION_XML))

        //THEN
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(TestConstants.VALID_JSON_SEPA_PACS_008))
                .andExpect(header().string(Constants.REQUEST_LOG_ID, Matchers.anything()));

    }

    @Test
    public void givenInValidSepaMessage_whenSepaValidate_thenReturnValidationErrors() throws Exception {
        //GIVEN

        //WHEN
        mvc.perform(post("/sepa/validate")
                .content(TestConstants.INVALID_SEPA_PACS_008)
                .contentType(MediaType.APPLICATION_XML))

        //THEN
                .andExpect(status().isBadRequest())
                .andExpect(header().string(Constants.REQUEST_LOG_ID, Matchers.anything()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0]", is("Line: 6 -- cvc-complex-type.2.4.a: Invalid content was found starting with element 'CreDtTm'. One of '{\"urn:iso:std:iso:20022:tech:xsd:pacs.008.001.02\":MsgId}' is expected.")));

    }

    @Test
    public void givenValidPacs008Json_whenCreatePacs008_thenReturnSepaXml() throws Exception {
        //GIVEN
        String expectedAsRegex = TestUtils.escapeSpecialRegexChars(TestConstants.VALID_SEPA_PACS_008)
                .replace("<MsgId>20210113155624246<\\/MsgId>", "<MsgId>([0-9]{17})<\\/MsgId>")
                .replace("<CreDtTm>2021-01-13T15:56:24<\\/CreDtTm>", "<CreDtTm>.*<\\/CreDtTm>");

        String requestJson = objectMapper.writeValueAsString(TestConstants.getSepaCreatePacs008RequestSample());

        //WHEN
        mvc.perform(post("/sepa/create/pacs008")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))

        //THEN
                .andExpect(status().isOk())
                .andExpect(header().string(Constants.REQUEST_LOG_ID, Matchers.anything()))
                .andExpect(content().contentType(new MediaType(MediaType.APPLICATION_XML, StandardCharsets.UTF_8)))
                .andExpect(content().string(Matchers.matchesPattern(expectedAsRegex + "\n")));

    }

    @Test
    public void givenInvalidPacs008Json_whenCreatePacs008_thenReturnValidationErrors() throws Exception {
        //GIVEN
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
                .andExpect(jsonPath("$[0]", is("Line: 56 -- cvc-pattern-valid: Value 'AAAAAAAAAAAA' is not facet-valid with respect to pattern '[A-Z]{6,6}[A-Z2-9][A-NP-Z0-9]([A-Z0-9]{3,3}){0,1}' for type 'BICIdentifier'.")));

    }

    @Test
    public void givenValidPacs008_whenPaymentReturn_thenReturnPacs004() throws Exception {
        //GIVEN
        MsgReplyInfoRequest msgReplyInfoRequest = new MsgReplyInfoRequest();
        msgReplyInfoRequest.setReasonCode("AC04");
        String expectedAsRegex = TestUtils.escapeSpecialRegexChars(TestConstants.VALID_SEPA_PACS_004)
                .replace("<MsgId>20210113155630479<\\/MsgId>", "<MsgId>([0-9]{17})<\\/MsgId>")
                .replace("<IntrBkSttlmDt>2021-01-13<\\/IntrBkSttlmDt>", "<IntrBkSttlmDt>.*<\\/IntrBkSttlmDt>")
                .replace("<CreDtTm>2021-01-13T15:56:30<\\/CreDtTm>", "<CreDtTm>.*<\\/CreDtTm>")
                .replace("<RtrId>20210113155630479<\\/RtrId>", "<RtrId>.*<\\/RtrId>");


        //WHEN
        mvc.perform(post("/sepa/payment/return")
                .content(TestConstants.VALID_SEPA_PACS_008)
                .queryParam("reasonCode", msgReplyInfoRequest.getReasonCode())
                .contentType(MediaType.APPLICATION_XML))

        //THEN
                .andExpect(status().isOk())
                .andExpect(content().contentType(new MediaType(MediaType.APPLICATION_XML, StandardCharsets.UTF_8)))
                .andExpect(content().string(Matchers.matchesPattern(expectedAsRegex + "\n")))
                .andExpect(header().string(Constants.REQUEST_LOG_ID, Matchers.anything()));

    }

    @Test
    public void givenInvalidPacs008_whenPaymentReturn_thenReturnValidationErrors() throws Exception {
        //GIVEN
        MsgReplyInfoRequest msgReplyInfoRequest = new MsgReplyInfoRequest();
        msgReplyInfoRequest.setReasonCode("AC04");

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
                .andExpect(jsonPath("$[0]", is("Line: 6 -- cvc-complex-type.2.4.a: Invalid content was found starting with element 'CreDtTm'. One of '{\"urn:iso:std:iso:20022:tech:xsd:pacs.008.001.02\":MsgId}' is expected.")));

    }

    @Test
    public void givenValidPacs008_whenCancellationRequest_thenReturnCamt056() throws Exception {
        //GIVEN
        MsgReplyInfoRequest msgReplyInfoRequest = new MsgReplyInfoRequest();
        msgReplyInfoRequest.setReasonCode("DUPL");
        String expectedAsRegex = TestUtils.escapeSpecialRegexChars(TestConstants.VALID_SEPA_CAMT_056)
                .replace("<Id>20210113171000590<\\/Id>", "<Id>([0-9]{17})<\\/Id>")
                .replace("<CreDtTm>2021-01-13T17:10:00<\\/CreDtTm>", "<CreDtTm>.*<\\/CreDtTm>")
                .replace("<CxlId>20210113171000590<\\/CxlId>", "<CxlId>.*<\\/CxlId>")
                .replace("<OrgnlIntrBkSttlmDt>2021-01-13<\\/OrgnlIntrBkSttlmDt>", "<OrgnlIntrBkSttlmDt>.*<\\/OrgnlIntrBkSttlmDt>");

        //WHEN
        mvc.perform(post("/sepa/cancellation/request")
                .content(TestConstants.VALID_SEPA_PACS_008)
                .queryParam("reasonCode", msgReplyInfoRequest.getReasonCode())
                .contentType(MediaType.APPLICATION_XML))

                //THEN
                .andExpect(status().isOk())
                .andExpect(content().contentType(new MediaType(MediaType.APPLICATION_XML, StandardCharsets.UTF_8)))
                .andExpect(content().string(Matchers.matchesPattern(expectedAsRegex + "\n")))
                .andExpect(header().string(Constants.REQUEST_LOG_ID, Matchers.anything()));

    }

    @Test
    public void givenInvalidPacs008_whenCancellationRequest_thenReturnValidationErrors() throws Exception {
        //GIVEN
        MsgReplyInfoRequest msgReplyInfoRequest = new MsgReplyInfoRequest();
        msgReplyInfoRequest.setReasonCode("DUPL");

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
                .andExpect(jsonPath("$[0]", is("Line: 6 -- cvc-complex-type.2.4.a: Invalid content was found starting with element 'CreDtTm'. One of '{\"urn:iso:std:iso:20022:tech:xsd:pacs.008.001.02\":MsgId}' is expected.")));

    }

    @Test
    public void givenValidPacs008_whenResolutionOfInvestigation_thenReturnCamt029() throws Exception {
        //GIVEN
        MsgReplyInfoRequest msgReplyInfoRequest = new MsgReplyInfoRequest();
        msgReplyInfoRequest.setReasonCode("CUST");
        String expectedAsRegex = TestUtils.escapeSpecialRegexChars(TestConstants.VALID_SEPA_CAMT_029)
                .replace("<Id>20210114115956743<\\/Id>", "<Id>([0-9]{17})<\\/Id>")
                .replace("<CreDtTm>2021-01-14T11:59:56<\\/CreDtTm>", "<CreDtTm>.*<\\/CreDtTm>")
                .replace("<CxlStsId>20210114115956743<\\/CxlStsId>", "<CxlStsId>.*<\\/CxlStsId>");

        //WHEN
        mvc.perform(post("/sepa/resolution/of/investigation")
                .content(TestConstants.VALID_SEPA_PACS_008)
                .queryParam("reasonCode", msgReplyInfoRequest.getReasonCode())
                .contentType(MediaType.APPLICATION_XML))

        //THEN
                .andExpect(status().isOk())
                .andExpect(content().contentType(new MediaType(MediaType.APPLICATION_XML, StandardCharsets.UTF_8)))
                .andExpect(content().string(Matchers.matchesPattern(expectedAsRegex + "\n")))
                .andExpect(header().string(Constants.REQUEST_LOG_ID, Matchers.anything()));

    }

    @Test
    public void givenInvalidPacs008_whenResolutionOfInvestigation_thenReturnValidationErrors() throws Exception {
        //GIVEN
        MsgReplyInfoRequest msgReplyInfoRequest = new MsgReplyInfoRequest();
        msgReplyInfoRequest.setReasonCode("CUST");

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
                .andExpect(jsonPath("$[0]", is("Line: 6 -- cvc-complex-type.2.4.a: Invalid content was found starting with element 'CreDtTm'. One of '{\"urn:iso:std:iso:20022:tech:xsd:pacs.008.001.02\":MsgId}' is expected.")));

    }

}