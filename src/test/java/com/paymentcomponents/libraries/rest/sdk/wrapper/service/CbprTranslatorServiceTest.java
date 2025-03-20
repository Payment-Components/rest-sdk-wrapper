package com.paymentcomponents.libraries.rest.sdk.wrapper.service;

import com.paymentcomponents.libraries.rest.sdk.wrapper.TestConstants;
import com.paymentcomponents.libraries.rest.sdk.wrapper.TestUtils;
import com.paymentcomponents.libraries.rest.sdk.wrapper.exception.InvalidMessageException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.paymentcomponents.libraries.rest.sdk.wrapper.TestUtils.replaceLineEndings;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class CbprTranslatorServiceTest {

    @TestConfiguration
    static class CbprTranslatorServiceTestContextConfiguration {

        @Bean
        public CbprTranslatorService cbprTranslatorService() {
            return new CbprTranslatorService();
        }
    }

    @Autowired
    CbprTranslatorService cbprTranslatorService;

    @Test
    public void givenValidMtMessage_whenTranslateMtToMx_thenReturnMxMessage() throws Exception {
        //GIVEN
        String expectedAsRegex = TestUtils.escapeSpecialRegexChars(TestConstants.VALID_CBPR_TRANSLATOR_MT_TO_MX_RESPONSE)
                .replaceAll("<CreDt>.*<\\\\/CreDt>", "<CreDt>.*<\\\\/CreDt>")
                .replaceAll("<CreDtTm>.*<\\\\/CreDtTm>", "<CreDtTm>.*<\\\\/CreDtTm>")
                .replaceAll("<UETR>.*<\\\\/UETR>", "<UETR>.*<\\\\/UETR>");

        //WHEN
        String result = cbprTranslatorService.translateMtToMx(TestConstants.VALID_CBPR_TRANSLATOR_MT_TO_MX_REQUEST);

        //THEN
        assertTrue(replaceLineEndings(result).matches(replaceLineEndings(expectedAsRegex)));
    }

    @Test
    public void givenInvalidMtMessage_whenTranslateMtToMx_thenThrowInvalidMessageException() {
        //GIVEN
        String exceptionBody = "[ {\n" +
                "  \"tagName\" : \"20\",\n" +
                "  \"description\" : \"SV16 - Mandatory Tag is missing \",\n" +
                "  \"sequence\" : null,\n" +
                "  \"occurs\" : \"1\",\n" +
                "  \"line\" : null,\n" +
                "  \"messageIndex\" : null,\n" +
                "  \"errorCode\" : \"SV16\"\n" +
                "} ]";

        //WHEN
        InvalidMessageException exception = assertThrows(InvalidMessageException.class, () -> {
            cbprTranslatorService.translateMtToMx(TestConstants.INVALID_CBPR_TRANSLATOR_MT_TO_MX_REQUEST);
        });

        //THEN
        assertEquals(replaceLineEndings(exceptionBody), replaceLineEndings(exception.getResponseBodyAsString()));
    }

    @Test
    public void givenValidMxMessage_whenTranslateMxToMt_thenReturnMtMessage() throws Exception {
        //GIVEN
        String expectedAsRegex = TestUtils.escapeSpecialRegexChars(TestConstants.VALID_CBPR_TRANSLATOR_MX_TO_MT_RESPONSE)
                .replaceAll("1108210602", ".*")
                .replaceAll("2106021108", ".*");

        //WHEN
        String result = cbprTranslatorService.translateMxToMt(TestConstants.VALID_CBPR_TRANSLATOR_MX_TO_MT_REQUEST, "O");

        //THEN
        assertTrue(replaceLineEndings(result).matches(replaceLineEndings(expectedAsRegex)));
    }

    @Test
    public void givenInvalidMxMessage_whenTranslateMxToMt_thenThrowInvalidMessageException() {
        //GIVEN
        String exceptionBody = "[ {\n" +
                "  \"severity\" : \"ERROR\",\n" +
                "  \"errorCode\" : null,\n" +
                "  \"fieldPath\" : \"/Document/FICdtTrf/GrpHdr/CreDtTm\",\n" +
                "  \"description\" : \"cvc-complex-type.2.4.a: Invalid content was found starting with element 'CreDtTm'. One of '{\\\"urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08\\\":MsgId}' is expected.\",\n" +
                "  \"erroneousValue\" : null,\n" +
                "  \"line\" : 4,\n" +
                "  \"column\" : 22\n" +
                "} ]";

        //WHEN
        InvalidMessageException exception = assertThrows(InvalidMessageException.class, () -> {
            cbprTranslatorService.translateMxToMt(TestConstants.INVALID_CBPR_TRANSLATOR_MX_TO_MT_REQUEST, "O");
        });

        //THEN
        assertEquals(replaceLineEndings(exceptionBody), replaceLineEndings(exception.getResponseBodyAsString()));
    }

}
