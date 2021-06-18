package com.paymentcomponents.libraries.rest.sdk.wrapper.service;

import com.paymentcomponents.libraries.rest.sdk.wrapper.TestConstants;
import com.paymentcomponents.libraries.rest.sdk.wrapper.TestUtils;
import com.paymentcomponents.libraries.rest.sdk.wrapper.exception.InvalidMessageException;
import com.paymentcomponents.libraries.rest.sdk.wrapper.model.sepa.request.MsgReplyInfoRequest;
import com.paymentcomponents.libraries.rest.sdk.wrapper.model.sepa.request.SepaCreatePacs008Request;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class SepaServiceTest {

    @TestConfiguration
    static class SepaServiceTestContextConfiguration {

        @Bean
        public SepaService sepaService() {
            return new SepaService();
        }
    }

    @Autowired
    SepaService sepaService;

    @Test
    public void givenValidSepaMessage_whenSepaValidate_thenReturnSepaAsJson() throws Exception {
        //GIVEN
        String expected = TestConstants.VALID_JSON_SEPA_EPC_PACS_002;

        //WHEN
        String result = sepaService.validateSepaMessage(TestConstants.VALID_SEPA_EPC_PACS_002);

        //THEN
        assertEquals(expected, result);
    }

    @Test
    public void givenInvalidSepaMessage_whenSepaValidate_thenThrowInvalidMessageException() {
        //GIVEN
        String exceptionBody = "[ \"Line: 5 -- cvc-complex-type.2.4.a: Invalid content was found starting with element 'CreDtTm'. One of '{\\\"urn:iso:std:iso:20022:tech:xsd:pacs.002.001.03\\\":MsgId}' is expected.\" ]";

        //WHEN
        InvalidMessageException exception = assertThrows(InvalidMessageException.class, () -> {
            sepaService.validateSepaMessage(TestConstants.INVALID_SEPA_EPC_PACS_002);
        });

        //THEN
        assertEquals(TestUtils.replaceLineEndings(exceptionBody), TestUtils.replaceLineEndings(exception.getResponseBodyAsString()));
    }

    @Test
    public void givenValidSepaCreatePacs008Request_whenCreatePacs008_thenReturnSepaAsXml() throws Exception {
        //GIVEN
        String expectedAsRegex = TestUtils.escapeSpecialRegexChars(TestConstants.VALID_SEPA_PACS_008)
                .replaceAll("<MsgId>.*<\\\\/MsgId>", "<MsgId>.*<\\\\/MsgId>")
                .replaceAll("<CreDtTm>.*<\\\\/CreDtTm>", "<CreDtTm>.*<\\\\/CreDtTm>");

        //WHEN
        String resultXml = sepaService.createPacs008(TestConstants.getSepaCreatePacs008RequestSample());

        //THEN
        assertTrue(resultXml.matches(expectedAsRegex + "\n"));
    }

    @Test
    public void givenInvalidCreateMt103Request_whenCreateMt103_thenThrowInvalidMessageException() throws Exception {
        //GIVEN
        SepaCreatePacs008Request sepaCreatePacs008Request = TestConstants.getSepaCreatePacs008RequestSample();
        sepaCreatePacs008Request.setDebtorBic("AAAAAAAAAAAA"); //invalid value, 12 chars in bic
        String errorResponse = "[ \"Line: 56 -- cvc-pattern-valid: Value 'AAAAAAAAAAAA' is not facet-valid with respect to pattern '[A-Z]{6,6}[A-Z2-9][A-NP-Z0-9]([A-Z0-9]{3,3}){0,1}' for type 'BICIdentifier'.\" ]";

        //WHEN
        InvalidMessageException exception = assertThrows(InvalidMessageException.class, () -> {
            sepaService.createPacs008(sepaCreatePacs008Request);
        });

        //THEN
        assertEquals(errorResponse, exception.getResponseBodyAsString());
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
        String resultXml = sepaService.generatePaymentReturn(TestConstants.VALID_SEPA_PACS_008, msgReplyInfoRequest);

        //THEN
        assertTrue(resultXml.matches(expectedAsRegex + "\n"));
    }

    @Test
    public void givenInvalidPacs008_whenPaymentReturn_thenThrowInvalidMessageException() {
        //GIVEN
        MsgReplyInfoRequest msgReplyInfoRequest = new MsgReplyInfoRequest();
        msgReplyInfoRequest.setReasonCode("AC04");
        String errorResponse = "[ \"Line: 5 -- cvc-complex-type.2.4.a: Invalid content was found starting with element 'CreDtTm'. One of '{\\\"urn:iso:std:iso:20022:tech:xsd:pacs.008.001.02\\\":MsgId}' is expected.\" ]";

        //WHEN
        InvalidMessageException exception = assertThrows(InvalidMessageException.class, () -> {
            sepaService.generatePaymentReturn(TestConstants.INVALID_SEPA_PACS_008, msgReplyInfoRequest);
        });

        //THEN
        assertEquals(errorResponse, exception.getResponseBodyAsString());
    }

    @Test
    public void givenValidPacs008WithBothRsnCdPrtry_whenPaymentReturn_thenThrowInvalidMessageException() {
        //GIVEN
        MsgReplyInfoRequest msgReplyInfoRequest = new MsgReplyInfoRequest();
        msgReplyInfoRequest.setReasonCode("AC04");
        msgReplyInfoRequest.setReasonPrtry("Just Return");
        String errorResponse = "\"The reason is CD or PRTRY based\"";

        //WHEN
        InvalidMessageException exception = assertThrows(InvalidMessageException.class, () -> {
            sepaService.generatePaymentReturn(TestConstants.INVALID_SEPA_PACS_008, msgReplyInfoRequest);
        });

        //THEN
        assertEquals(errorResponse, exception.getResponseBodyAsString());
    }

    @Test
    public void givenValidPacs008WithEmptyRsnCdPrtry_whenPaymentReturn_thenThrowInvalidMessageException() {
        //GIVEN
        MsgReplyInfoRequest msgReplyInfoRequest = new MsgReplyInfoRequest();
        msgReplyInfoRequest.setReasonCode(null);
        msgReplyInfoRequest.setReasonPrtry(null);
        String errorResponse = "\"Either CD or PRTRY is required\"";

        //WHEN
        InvalidMessageException exception = assertThrows(InvalidMessageException.class, () -> {
            sepaService.generatePaymentReturn(TestConstants.INVALID_SEPA_PACS_008, msgReplyInfoRequest);
        });

        //THEN
        assertEquals(errorResponse, exception.getResponseBodyAsString());
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
                .replaceAll("<OrgnlIntrBkSttlmDt>.*<\\\\/OrgnlIntrBkSttlmDt>", "<OrgnlIntrBkSttlmDt>.*<\\\\/OrgnlIntrBkSttlmDt>");

        //WHEN
        String resultXml = sepaService.generateCancellationRequest(TestConstants.VALID_SEPA_PACS_008, msgReplyInfoRequest);

        //THEN
        assertTrue(resultXml.matches(expectedAsRegex + "\n"));
    }

    @Test
    public void givenInvalidPacs008_whenCancellationRequest_thenThrowInvalidMessageException() {
        //GIVEN
        MsgReplyInfoRequest msgReplyInfoRequest = new MsgReplyInfoRequest();
        msgReplyInfoRequest.setReasonCode("DUPL");
        String errorResponse = "[ \"Line: 5 -- cvc-complex-type.2.4.a: Invalid content was found starting with element 'CreDtTm'. One of '{\\\"urn:iso:std:iso:20022:tech:xsd:pacs.008.001.02\\\":MsgId}' is expected.\" ]";

        //WHEN
        InvalidMessageException exception = assertThrows(InvalidMessageException.class, () -> {
            sepaService.generateCancellationRequest(TestConstants.INVALID_SEPA_PACS_008, msgReplyInfoRequest);
        });

        //THEN
        assertEquals(errorResponse, exception.getResponseBodyAsString());
    }

    @Test
    public void givenValidPacs008WithBothRsnCdPrtry_whenCancellationRequest_thenThrowInvalidMessageException() {
        //GIVEN
        MsgReplyInfoRequest msgReplyInfoRequest = new MsgReplyInfoRequest();
        msgReplyInfoRequest.setReasonCode("DUPL");
        msgReplyInfoRequest.setReasonPrtry("Just Cancel");
        String errorResponse = "\"The reason is CD or PRTRY based\"";

        //WHEN
        InvalidMessageException exception = assertThrows(InvalidMessageException.class, () -> {
            sepaService.generateCancellationRequest(TestConstants.INVALID_SEPA_PACS_008, msgReplyInfoRequest);
        });

        //THEN
        assertEquals(errorResponse, exception.getResponseBodyAsString());
    }

    @Test
    public void givenValidPacs008WithEmptyRsnCdPrtry_whenCancellationRequest_thenThrowInvalidMessageException() {
        //GIVEN
        MsgReplyInfoRequest msgReplyInfoRequest = new MsgReplyInfoRequest();
        msgReplyInfoRequest.setReasonCode(null);
        msgReplyInfoRequest.setReasonPrtry(null);
        String errorResponse = "\"Either CD or PRTRY is required\"";

        //WHEN
        InvalidMessageException exception = assertThrows(InvalidMessageException.class, () -> {
            sepaService.generateCancellationRequest(TestConstants.INVALID_SEPA_PACS_008, msgReplyInfoRequest);
        });

        //THEN
        assertEquals(errorResponse, exception.getResponseBodyAsString());
    }

    @Test
    public void givenValidPacs008_whenResolutionOfInvestigation_thenReturnCamt029() throws Exception {
        //GIVEN
        MsgReplyInfoRequest msgReplyInfoRequest = new MsgReplyInfoRequest();
        msgReplyInfoRequest.setReasonCode("CUST");
        msgReplyInfoRequest.setAdditionalInformation("ATR7reference");
        String expectedAsRegex = TestUtils.escapeSpecialRegexChars(TestConstants.VALID_SEPA_CAMT_029)
                .replaceAll("<Id>.*<\\\\/Id>", "<Id>.*<\\\\/Id>")
                .replaceAll("<CreDtTm>.*<\\\\/CreDtTm>", "<CreDtTm>.*<\\\\/CreDtTm>")
                .replaceAll("<CxlStsId>.*<\\\\/CxlStsId>", "<CxlStsId>.*<\\\\/CxlStsId>");

        //WHEN
        String resultXml = sepaService.generateResolutionOfInvestigation(TestConstants.VALID_SEPA_PACS_008, msgReplyInfoRequest);

        //THEN
        assertTrue(resultXml.matches(expectedAsRegex + "\n"));
    }

    @Test
    public void givenInvalidPacs008_whenResolutionOfInvestigation_thenThrowInvalidMessageException() {
        //GIVEN
        MsgReplyInfoRequest msgReplyInfoRequest = new MsgReplyInfoRequest();
        msgReplyInfoRequest.setReasonCode("CUST");
        String errorResponse = "[ \"Line: 5 -- cvc-complex-type.2.4.a: Invalid content was found starting with element 'CreDtTm'. One of '{\\\"urn:iso:std:iso:20022:tech:xsd:pacs.008.001.02\\\":MsgId}' is expected.\" ]";

        //WHEN
        InvalidMessageException exception = assertThrows(InvalidMessageException.class, () -> {
            sepaService.generateResolutionOfInvestigation(TestConstants.INVALID_SEPA_PACS_008, msgReplyInfoRequest);
        });

        //THEN
        assertEquals(errorResponse, exception.getResponseBodyAsString());
    }

    @Test
    public void givenValidPacs008WithBothRsnCdPrtry_whenResolutionOfInvestigation_thenThrowInvalidMessageException() {
        //GIVEN
        MsgReplyInfoRequest msgReplyInfoRequest = new MsgReplyInfoRequest();
        msgReplyInfoRequest.setReasonCode("CUST");
        msgReplyInfoRequest.setReasonPrtry("Just Cancel");
        String errorResponse = "\"The reason is CD or PRTRY based\"";

        //WHEN
        InvalidMessageException exception = assertThrows(InvalidMessageException.class, () -> {
            sepaService.generateResolutionOfInvestigation(TestConstants.INVALID_SEPA_PACS_008, msgReplyInfoRequest);
        });

        //THEN
        assertEquals(errorResponse, exception.getResponseBodyAsString());
    }

    @Test
    public void givenValidPacs008WithEmptyRsnCdPrtry_whenResolutionOfInvestigation_thenThrowInvalidMessageException() {
        //GIVEN
        MsgReplyInfoRequest msgReplyInfoRequest = new MsgReplyInfoRequest();
        msgReplyInfoRequest.setReasonCode(null);
        msgReplyInfoRequest.setReasonPrtry(null);
        String errorResponse = "\"Either CD or PRTRY is required\"";

        //WHEN
        InvalidMessageException exception = assertThrows(InvalidMessageException.class, () -> {
            sepaService.generateResolutionOfInvestigation(TestConstants.INVALID_SEPA_PACS_008, msgReplyInfoRequest);
        });

        //THEN
        assertEquals(errorResponse, exception.getResponseBodyAsString());
    }

    @Test
    public void validateSampleMessages() throws Exception {
        List<String> messages = TestUtils.returnResourcePathFilesContentAsList("/samples/sepa");
        for (String message : messages) {
            System.out.println("Testing :\n" + message);
            String result = sepaService.validateSepaMessage(message);
        }
    }

}
