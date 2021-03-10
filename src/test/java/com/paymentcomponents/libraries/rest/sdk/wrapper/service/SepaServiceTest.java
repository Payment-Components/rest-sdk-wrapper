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

import static com.paymentcomponents.libraries.rest.sdk.wrapper.TestUtils.replaceLindEndings;
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
        String expected = TestConstants.VALID_JSON_SEPA_PACS_008;

        //WHEN
        String result = sepaService.validateSepaMessage(TestConstants.VALID_SEPA_PACS_008);

        //THEN
        assertEquals(expected, result);
    }

    @Test
    public void givenInvalidSepaMessage_whenSepaValidate_thenThrowInvalidMessageException() {
        //GIVEN
        String exceptionBody = "[ \"Line: 5 -- cvc-complex-type.2.4.a: Invalid content was found starting with element 'CreDtTm'. One of '{\\\"urn:iso:std:iso:20022:tech:xsd:pacs.008.001.02\\\":MsgId}' is expected.\" ]";

        //WHEN
        InvalidMessageException exception = assertThrows(InvalidMessageException.class, () -> {
            sepaService.validateSepaMessage(TestConstants.INVALID_SEPA_PACS_008);
        });

        //THEN
        assertEquals(replaceLindEndings(exceptionBody), replaceLindEndings(exception.getResponseBodyAsString()));
    }

    @Test
    public void validateSampleMessages() throws Exception {
        List<String> messages = TestUtils.returnResourcePathFilesContentAsList("/samples/sepa");
        for (String message : messages) {
            System.out.println("Testing :\n"+ message);
            String result = sepaService.validateSepaMessage(message);
        }
    }

}
