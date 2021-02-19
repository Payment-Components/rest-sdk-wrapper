package com.paymentcomponents.libraries.rest.sdk.wrapper.service;

import com.paymentcomponents.libraries.rest.sdk.wrapper.TestConstants;
import com.paymentcomponents.libraries.rest.sdk.wrapper.exception.InvalidMessageException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class MxServiceTest {

    @TestConfiguration
    static class MxServiceTestContextConfiguration {

        @Bean
        public MxService mxService() {
            return new MxService();
        }
    }

    @Autowired
    MxService mxService;

    @Test
    public void givenValidMxMessage_whenMxValidate_thenReturnMxAsJson() throws Exception {
        //GIVEN
        String expected = TestConstants.VALID_JSON_MX_PAIN001;

        //WHEN
        String result = mxService.validateMx(TestConstants.VALID_MX_PAIN_001);

        //THEN
        assertEquals(expected, result);
    }

    @Test
    public void givenInvalidMxMessage_whenMxValidate_thenThrowInvalidMessageException() {
        //GIVEN
        String exceptionBody = "[ {\n" +
                "  \"severity\" : \"ERROR\",\n" +
                "  \"errorCode\" : null,\n" +
                "  \"fieldPath\" : null,\n" +
                "  \"description\" : \"cvc-complex-type.2.4.a: Invalid content was found starting with element 'CreDtTm'. One of '{\\\"urn:iso:std:iso:20022:tech:xsd:pain.001.001.09\\\":MsgId}' is expected.\",\n" +
                "  \"erroneousValue\" : null,\n" +
                "  \"line\" : 5,\n" +
                "  \"column\" : 34\n" +
                "} ]";

        //WHEN
        InvalidMessageException exception = assertThrows(InvalidMessageException.class, () -> {
            mxService.validateMx(TestConstants.INVALID_MX_PAIN_001);
        });

        //THEN
        assertEquals(exceptionBody.replaceAll("\n", "\r\n"), exception.getResponseBodyAsString());
    }

}
