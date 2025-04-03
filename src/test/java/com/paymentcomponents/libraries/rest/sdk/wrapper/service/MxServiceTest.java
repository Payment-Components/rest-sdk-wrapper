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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        String expected = TestConstants.VALID_JSON_MX_PACS009;

        //WHEN
        String result = mxService.validateMx(TestConstants.VALID_MX_PACS_009);

        //THEN
        assertEquals(expected, result);
    }

    @Test
    public void givenInvalidMxMessage_whenMxValidate_thenThrowInvalidMessageException() {
        //GIVEN
        String exceptionBody = "[ {\n" +
                "  \"severity\" : \"ERROR\",\n" +
                "  \"errorCode\" : null,\n" +
                "  \"fieldPath\" : \"/Document/FICdtTrf/GrpHdr/CreDtTm\",\n" +
                "  \"description\" : \"cvc-complex-type.2.4.a: Invalid content was found starting with element '{\\\"urn:iso:std:iso:20022:tech:xsd:pacs.009.001.10\\\":CreDtTm}'. One of '{\\\"urn:iso:std:iso:20022:tech:xsd:pacs.009.001.10\\\":MsgId}' is expected.\",\n" +
                "  \"erroneousValue\" : null,\n" +
                "  \"line\" : 5,\n" +
                "  \"column\" : 22\n" +
                "} ]";

        //WHEN
        InvalidMessageException exception = assertThrows(InvalidMessageException.class, () -> {
            mxService.validateMx(TestConstants.INVALID_MX_PACS_009);
        });

        //THEN
        assertEquals(TestUtils.replaceLineEndings(exceptionBody), TestUtils.replaceLineEndings(exception.getResponseBodyAsString()));
    }

}
