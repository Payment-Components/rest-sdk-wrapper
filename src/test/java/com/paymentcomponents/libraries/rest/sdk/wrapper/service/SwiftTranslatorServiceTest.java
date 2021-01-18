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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class SwiftTranslatorServiceTest {

    @TestConfiguration
    static class SwiftTranslatorServiceTestContextConfiguration {

        @Bean
        public SwiftTranslatorService swiftTranslatorService() {
            return new SwiftTranslatorService();
        }
    }

    @Autowired
    SwiftTranslatorService swiftTranslatorService;

    @Test
    public void givenValidMtMessage_whenTranslateMtToMx_thenReturnMxMessage() throws Exception {
        //GIVEN
        String expectedAsRegex = TestUtils.escapeSpecialRegexChars(TestConstants.VALID_SWIFT_TRANSLATOR_MT_TO_MX_RESPONSE)
                .replaceAll("<CreDt>.*<\\\\/CreDt>", "<CreDt>.*<\\\\/CreDt>")
                .replaceAll("<CreDtTm>.*<\\\\/CreDtTm>", "<CreDtTm>.*<\\\\/CreDtTm>")
                .replaceAll("<UETR>.*<\\\\/UETR>", "<UETR>.*<\\\\/UETR>");

        //WHEN
        String result = swiftTranslatorService.translateMtToMx(TestConstants.VALID_SWIFT_TRANSLATOR_MT_TO_MX_REQUEST);

        //THEN
        assertTrue(result.matches(expectedAsRegex));
    }

    @Test
    public void givenInvalidMtMessage_whenTranslateMtToMx_thenReturnMessageValidationException() {
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
            swiftTranslatorService.translateMtToMx(TestConstants.INVALID_SWIFT_TRANSLATOR_MT_TO_MX_REQUEST);
        });

        //THEN
        assertEquals(exceptionBody.replaceAll("\n", "\r\n"), exception.getResponseBodyAsString());
    }


}
