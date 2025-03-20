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
                "  \"fieldPath\" : \"/Document/FICdtTrf/GrpHdr/CreDtTm\",\n" +
                "  \"description\" : \"cvc-complex-type.2.4.a: Invalid content was found starting with element 'CreDtTm'. One of '{\\\"urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08\\\":MsgId}' is expected.\",\n" +
                "  \"erroneousValue\" : null,\n" +
                "  \"line\" : 4,\n" +
                "  \"column\" : 22\n" +
                "} ]";

        //WHEN
        InvalidMessageException exception = assertThrows(InvalidMessageException.class, () -> {
            cbprService.validateCbpr(TestConstants.INVALID_CBPR_REQUEST);
        });

        //THEN
        assertEquals(TestUtils.replaceLineEndings(exceptionBody), TestUtils.replaceLineEndings(exception.getResponseBodyAsString()));

    }

    @Test
    public void givenValidCbprMessage_whenCbprEnvelope_thenReturnEnvelopedCbpr() throws Exception {
        //GIVEN
        String expected = TestConstants.CBPR_ENVELOPE;

        //WHEN
        String result = cbprService.envelopeCbpr(TestConstants.VALID_CBPR_REQUEST);

        //THEN
        assertEquals(TestUtils.replaceLineEndings(expected), TestUtils.replaceLineEndings(result));
    }

    @Test
    public void givenInValidCbprMessage_whenCbprEnvelope_thenThrowInvalidMessageException() throws Exception {
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
            cbprService.envelopeCbpr(TestConstants.INVALID_CBPR_REQUEST);
        });

        //THEN
        assertEquals(TestUtils.replaceLineEndings(exceptionBody), TestUtils.replaceLineEndings(exception.getResponseBodyAsString()));

    }


}
