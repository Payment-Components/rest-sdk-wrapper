package com.paymentcomponents.libraries.rest.sdk.wrapper.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymentcomponents.libraries.rest.sdk.wrapper.Constants;
import com.paymentcomponents.libraries.rest.sdk.wrapper.TestConstants;
import com.paymentcomponents.libraries.rest.sdk.wrapper.TestUtils;
import com.paymentcomponents.libraries.rest.sdk.wrapper.model.sepa.request.MsgReplyInfoRequest;
import com.paymentcomponents.libraries.rest.sdk.wrapper.model.sepa.request.SepaCreatePacs008Request;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsNull;
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
                .content(TestConstants.VALID_SEPA_EPC_PACS_002)
                .contentType(MediaType.APPLICATION_XML))

        //THEN
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(TestConstants.VALID_JSON_SEPA_EPC_PACS_002))
                .andExpect(header().string(Constants.REQUEST_LOG_ID, Matchers.anything()));

    }

    @Test
    public void givenInValidSepaMessage_whenSepaValidate_thenReturnValidationErrors() throws Exception {
        //GIVEN

        //WHEN
        mvc.perform(post("/sepa/validate")
                .content(TestConstants.INVALID_SEPA_EPC_PACS_002)
                .contentType(MediaType.APPLICATION_XML))

                //THEN
                .andExpect(status().isBadRequest())
                .andExpect(header().string(Constants.REQUEST_LOG_ID, Matchers.anything()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].severity", is("ERROR")))
                .andExpect(jsonPath("$[0].errorCode", IsNull.nullValue()))
                .andExpect(jsonPath("$[0].fieldPath", is("/Document/FIToFIPmtStsRpt/GrpHdr/CreDtTm")))
                .andExpect(jsonPath("$[0].description", is("cvc-complex-type.2.4.a: Invalid content was found starting with element '{\"urn:iso:std:iso:20022:tech:xsd:pacs.002.001.10\":CreDtTm}'. One of '{\"urn:iso:std:iso:20022:tech:xsd:pacs.002.001.10\":MsgId}' is expected.")))
                .andExpect(jsonPath("$[0].erroneousValue", IsNull.nullValue()))
                .andExpect(jsonPath("$[0].line", is(4)))
                .andExpect(jsonPath("$[0].column", is(22)));
    }

    @Test
    public void givenValidPacs008Json_whenCreatePacs008_thenReturnSepaXml() throws Exception {
        //GIVEN
        String expectedAsRegex = TestUtils.escapeSpecialRegexChars(TestConstants.VALID_SEPA_PACS_008)
                .replaceAll("<MsgId>.*<\\\\/MsgId>", "<MsgId>.*<\\\\/MsgId>")
                .replaceAll("<CreDtTm>.*<\\\\/CreDtTm>", "<CreDtTm>.*<\\\\/CreDtTm>");

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
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].severity", is("ERROR")))
                .andExpect(jsonPath("$[0].errorCode", IsNull.nullValue()))
                .andExpect(jsonPath("$[0].fieldPath", IsNull.nullValue()))
                .andExpect(jsonPath("$[0].description", is("cvc-pattern-valid: Value 'AAAAAAAAAAAA' is not facet-valid with respect to pattern '[A-Z0-9]{4,4}[A-Z]{2,2}[A-Z0-9]{2,2}([A-Z0-9]{3,3}){0,1}' for type 'BICFIDec2014Identifier'.")))
                .andExpect(jsonPath("$[0].erroneousValue", IsNull.nullValue()))
                .andExpect(jsonPath("$[0].line", is(0)))
                .andExpect(jsonPath("$[0].column", is(0)))
                .andExpect(jsonPath("$[1].severity", is("ERROR")))
                .andExpect(jsonPath("$[1].errorCode", IsNull.nullValue()))
                .andExpect(jsonPath("$[1].fieldPath", IsNull.nullValue()))
                .andExpect(jsonPath("$[1].description", is("cvc-type.3.1.3: The value 'AAAAAAAAAAAA' of element 'BICFI' is not valid.")))
                .andExpect(jsonPath("$[1].erroneousValue", IsNull.nullValue()))
                .andExpect(jsonPath("$[1].line", is(0)))
                .andExpect(jsonPath("$[1].column", is(0)))
                .andExpect(jsonPath("$[2].severity", is("FATAL")))
                .andExpect(jsonPath("$[2].errorCode", is("D00001")))
                .andExpect(jsonPath("$[2].fieldPath", is("/cdtTrfTxInf[0]/dbtrAgt/finInstnId/BICFI")))
                .andExpect(jsonPath("$[2].description", is("Invalid FI BIC.")))
                .andExpect(jsonPath("$[2].erroneousValue", is("AAAAAAAAAAAA")))
                .andExpect(jsonPath("$[2].line", is(0)))
                .andExpect(jsonPath("$[2].column", is(0)));
    }

    @Test
    public void givenValidPacs008_whenPaymentReturn_thenReturnPacs004() throws Exception {
        //GIVEN
        MsgReplyInfoRequest msgReplyInfoRequest = new MsgReplyInfoRequest();
        msgReplyInfoRequest.setReasonCode("AC04");
        String expectedAsRegex = TestUtils.escapeSpecialRegexChars(TestConstants.VALID_SEPA_PACS_004)
                .replaceAll("<MsgId>.*<\\\\/MsgId>", "<MsgId>.*<\\\\/MsgId>")
                .replaceAll("<IntrBkSttlmDt>.*<\\\\/IntrBkSttlmDt>", "<IntrBkSttlmDt>.*<\\\\/IntrBkSttlmDt>")
                .replaceAll("<CreDtTm>.*<\\\\/CreDtTm>", "<CreDtTm>.*<\\\\/CreDtTm>")
                .replaceAll("<RtrId>.*<\\\\/RtrId>", "<RtrId>.*<\\\\/RtrId>");


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
                .andExpect(jsonPath("$[0].severity", is("ERROR")))
                .andExpect(jsonPath("$[0].errorCode", IsNull.nullValue()))
                .andExpect(jsonPath("$[0].fieldPath", is("/Document/FIToFICstmrCdtTrf/GrpHdr/CreDtTm")))
                .andExpect(jsonPath("$[0].description", is("cvc-complex-type.2.4.a: Invalid content was found starting with element '{\"urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08\":CreDtTm}'. One of '{\"urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08\":MsgId}' is expected.")))
                .andExpect(jsonPath("$[0].erroneousValue", IsNull.nullValue()))
                .andExpect(jsonPath("$[0].line", is(5)))
                .andExpect(jsonPath("$[0].column", is(22)));
    }

    @Test
    public void givenValidPacs008_whenCancellationRequest_thenReturnCamt056() throws Exception {
        //GIVEN
        MsgReplyInfoRequest msgReplyInfoRequest = new MsgReplyInfoRequest();
        msgReplyInfoRequest.setReasonCode("DUPL");
        String expectedAsRegex = TestUtils.escapeSpecialRegexChars(TestConstants.VALID_SEPA_CAMT_056)
                .replaceAll("<Id>.*<\\\\/Id>", "<Id>.*<\\\\/Id>")
                .replaceAll("<CreDtTm>.*<\\\\/CreDtTm>", "<CreDtTm>.*<\\\\/CreDtTm>")
                .replaceAll("<CxlId>.*<\\\\/CxlId>", "<CxlId>.*<\\\\/CxlId>")
                .replaceAll("<OrgnlIntrBkSttlmDt>.*<\\\\/OrgnlIntrBkSttlmDt>", "<OrgnlIntrBkSttlmDt>.*<\\/OrgnlIntrBkSttlmDt>");

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
                .andExpect(jsonPath("$[0].severity", is("ERROR")))
                .andExpect(jsonPath("$[0].errorCode", IsNull.nullValue()))
                .andExpect(jsonPath("$[0].fieldPath", is("/Document/FIToFICstmrCdtTrf/GrpHdr/CreDtTm")))
                .andExpect(jsonPath("$[0].description", is("cvc-complex-type.2.4.a: Invalid content was found starting with element '{\"urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08\":CreDtTm}'. One of '{\"urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08\":MsgId}' is expected.")))
                .andExpect(jsonPath("$[0].erroneousValue", IsNull.nullValue()))
                .andExpect(jsonPath("$[0].line", is(5)))
                .andExpect(jsonPath("$[0].column", is(22)));
    }

}