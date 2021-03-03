package com.paymentcomponents.libraries.rest.sdk.wrapper.service;

import com.paymentcomponents.libraries.rest.sdk.wrapper.TestConstants;
import com.paymentcomponents.libraries.rest.sdk.wrapper.exception.InvalidMessageException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.paymentcomponents.libraries.rest.sdk.wrapper.TestUtils.replaceLindEndings;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class CbprServiceTest {

    @TestConfiguration
    static class CbprServiceTestContextConfiguration {

        @Bean
        public CbprService cbprService() {
            return new CbprService();
        }
    }

    @Autowired
    CbprService cbprService;

    @Test
    public void givenValidCbprMessage_whenCbprValidate_thenReturnNoContent() throws Exception {
        //GIVEN

        //WHEN
        assertDoesNotThrow(() -> cbprService.validateCbpr(TestConstants.VALID_CBPR_REQUEST));

        //THEN
    }

    @Test
    public void givenInValidCbprMessage_whenCbprValidate_thenThrowInvalidMessageException() throws Exception {
        //GIVEN
        String exceptionBody = "[ {\n" +
                "  \"severity\" : \"ERROR\",\n" +
                "  \"errorCode\" : null,\n" +
                "  \"fieldPath\" : null,\n" +
                "  \"description\" : \"cvc-complex-type.2.4.a: Invalid content was found starting with element 'CreDtTm'. One of '{\\\"urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08\\\":MsgId}' is expected.\",\n" +
                "  \"erroneousValue\" : null,\n" +
                "  \"line\" : 4,\n" +
                "  \"column\" : 34\n" +
                "} ]";

        //WHEN
        InvalidMessageException exception = assertThrows(InvalidMessageException.class, () -> {
            cbprService.validateCbpr(TestConstants.INVALID_CBPR_REQUEST);
        });

        //THEN
        assertEquals(replaceLindEndings(exceptionBody), replaceLindEndings(exception.getResponseBodyAsString()));

    }

    @Test
    public void givenValidCbprMessage_whenCbprEnvelope_thenReturnEnvelopedCbpr() throws Exception {
        //GIVEN
        String expected = TestConstants.CBPR_ENVELOPE;

        //WHEN
        String result = cbprService.envelopeCbpr(TestConstants.VALID_CBPR_REQUEST);

        //THEN
        assertEquals(replaceLindEndings(expected), replaceLindEndings(result));
    }

    @Test
    public void givenInValidCbprMessage_whenCbprEnvelope_thenThrowInvalidMessageException() throws Exception {
        //GIVEN
        String exceptionBody = "[ {\n" +
                "  \"severity\" : \"ERROR\",\n" +
                "  \"errorCode\" : null,\n" +
                "  \"fieldPath\" : null,\n" +
                "  \"description\" : \"cvc-complex-type.2.4.a: Invalid content was found starting with element 'CreDtTm'. One of '{\\\"urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08\\\":MsgId}' is expected.\",\n" +
                "  \"erroneousValue\" : null,\n" +
                "  \"line\" : 4,\n" +
                "  \"column\" : 34\n" +
                "} ]";

        //WHEN
        InvalidMessageException exception = assertThrows(InvalidMessageException.class, () -> {
            cbprService.envelopeCbpr(TestConstants.INVALID_CBPR_REQUEST);
        });

        //THEN
        assertEquals(replaceLindEndings(exceptionBody), replaceLindEndings(exception.getResponseBodyAsString()));

    }


}
