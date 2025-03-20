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
public class RtgsTranslatorServiceTest {

    @TestConfiguration
    static class RtgsTranslatorServiceTestContextConfiguration {

        @Bean
        public RtgsTranslatorService rtgsTranslatorService() {
            return new RtgsTranslatorService();
        }
    }

    @Autowired
    RtgsTranslatorService rtgsTranslatorService;

    @Test
    public void givenValidMtMessage_whenTranslateMtToMx_thenReturnMxMessage() throws Exception {
        //GIVEN
        String expectedAsRegex = TestUtils.escapeSpecialRegexChars(TestConstants.VALID_RTGS_TRANSLATOR_MT_TO_MX_RESPONSE)
                .replaceAll("<CreDtTm>.*<\\\\/CreDtTm>", "<CreDtTm>.*<\\\\/CreDtTm>");

        //WHEN
        String result = rtgsTranslatorService.translateMtToMx(TestConstants.VALID_RTGS_TRANSLATOR_MT_TO_MX_REQUEST);

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
            rtgsTranslatorService.translateMtToMx(TestConstants.INVALID_RTGS_TRANSLATOR_MT_TO_MX_REQUEST);
        });

        //THEN
        assertEquals(replaceLineEndings(exceptionBody), replaceLineEndings(exception.getResponseBodyAsString()));
    }

    @Test
    public void givenValidMxMessage_whenTranslateMxToMt_thenReturnMtMessage() throws Exception {
        //GIVEN
        String expectedAsRegex = TestUtils.escapeSpecialRegexChars(TestConstants.VALID_RTGS_TRANSLATOR_MX_TO_MT_RESPONSE)
                .replaceAll("1343210323", ".*")
                .replaceAll("2103231343", ".*");

        //WHEN
        String result = rtgsTranslatorService.translateMxToMt(TestConstants.VALID_RTGS_TRANSLATOR_MX_TO_MT_REQUEST);

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
                "  \"line\" : 5,\n" +
                "  \"column\" : 22\n" +
                "} ]";

        //WHEN
        InvalidMessageException exception = assertThrows(InvalidMessageException.class, () -> {
            rtgsTranslatorService.translateMxToMt(TestConstants.INVALID_RTGS_TRANSLATOR_MX_TO_MT_REQUEST);
        });

        //THEN
        assertEquals(replaceLineEndings(exceptionBody), replaceLineEndings(exception.getResponseBodyAsString()));
    }

}
