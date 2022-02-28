package com.paymentcomponents.libraries.rest.sdk.wrapper.service;

import com.paymentcomponents.libraries.rest.sdk.wrapper.TestConstants;
import com.paymentcomponents.libraries.rest.sdk.wrapper.TestUtils;
import com.paymentcomponents.libraries.rest.sdk.wrapper.exception.InvalidMessageException;
import com.paymentcomponents.libraries.rest.sdk.wrapper.model.CustomSwiftMessage;
import gr.datamation.mt.common.InvalidMessageFormatException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class MtServiceTest {

    @TestConfiguration
    static class MtServiceTestContextConfiguration {

        @Bean
        public MtService mtService() {
            return new MtService();
        }
    }

    @Autowired
    MtService mtService;

    @Test
    public void givenValidMtMessage_whenMtParse_thenReturnCustomSwiftMessageObject() throws Exception {
        //GIVEN

        //WHEN
        CustomSwiftMessage customSwiftMessage = mtService.parseMt(TestConstants.VALID_MT_101);

        //THEN
        assertNotNull(customSwiftMessage);
    }

    @Test
    public void givenInvalidFormatMtMessage_whenMtParse_thenThrowInvalidMessageFormatException() {
        //GIVEN
        String mtMessage = TestConstants.VALID_MT_101.replace("{1:", "");

        //WHEN
        Throwable exception = assertThrows(InvalidMessageFormatException.class, () -> {
            mtService.parseMt(mtMessage);
        });

        //THEN
        assertEquals("Basic Header Block is missing", exception.getMessage());
    }

    @Test
    public void givenValidMtMessage_whenMtValidate_thenDoNothing() {
        //GIVEN

        //WHEN
        assertDoesNotThrow(() -> mtService.validateMt(TestConstants.VALID_MT_101));

        //THEN
    }

    @Test
    public void givenInvalidMtMessage_whenMtValidate_thenThrowInvalidMessageException() {
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
            mtService.validateMt(TestConstants.INVALID_MT_101);
        });

        //THEN
        assertEquals(TestUtils.replaceLineEndings(exceptionBody), TestUtils.replaceLineEndings(exception.getResponseBodyAsString()));
    }

    @Test
    public void givenInvalidFormatMtMessage_whenMtValidate_thenThrowInvalidMessageFormatException() {
        //GIVEN
        String mtMessage = TestConstants.VALID_MT_101.replace("{1:", "");

        //WHEN
        InvalidMessageFormatException exception = assertThrows(InvalidMessageFormatException.class, () -> {
            mtService.validateMt(mtMessage);
        });

        //THEN
        assertEquals("Basic Header Block is missing", exception.getMessage());
    }

}
