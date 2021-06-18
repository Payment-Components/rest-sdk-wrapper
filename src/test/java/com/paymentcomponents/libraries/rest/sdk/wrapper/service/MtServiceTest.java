package com.paymentcomponents.libraries.rest.sdk.wrapper.service;

import com.paymentcomponents.libraries.rest.sdk.wrapper.TestConstants;
import com.paymentcomponents.libraries.rest.sdk.wrapper.TestUtils;
import com.paymentcomponents.libraries.rest.sdk.wrapper.exception.InvalidMessageException;
import com.paymentcomponents.libraries.rest.sdk.wrapper.model.CustomSwiftMessage;
import com.paymentcomponents.libraries.rest.sdk.wrapper.model.mt.request.MtCreate103Request;
import com.paymentcomponents.libraries.rest.sdk.wrapper.model.mt.request.MtCreateGeneralRequest;
import com.paymentcomponents.libraries.rest.sdk.wrapper.model.mt.request.MtTagGeneralRequest;
import gr.datamation.mt.common.InvalidMessageFormatException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

import static com.paymentcomponents.libraries.rest.sdk.wrapper.TestUtils.replaceLineEndings;
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
        assertEquals(replaceLineEndings(exceptionBody), replaceLineEndings(exception.getResponseBodyAsString()));
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

    @Test
    public void givenValidCreateMt103Request_whenCreateMt103_thenReturnMt103AsText() throws Exception {
        //GIVEN
        String expected = TestConstants.VALID_MT_103;

        //WHEN
        String responseText = mtService.createMt103(TestConstants.getMtCreate103RequestSample());

        //THEN
        assertEquals(replaceLineEndings(expected), replaceLineEndings(responseText));
    }

    @Test
    public void givenInvalidCreateMt103Request_whenCreateMt103_thenThrowInvalidMessageException() throws Exception {
        //GIVEN
        MtCreate103Request mtCreate103Request = TestConstants.getMtCreate103RequestSample();
        mtCreate103Request.setDetailsOfCharges("AAA"); //invalid value
        String errorResponse = "[ {\n" +
                "  \"tagName\" : \"71A\",\n" +
                "  \"description\" : \"T08 - in field 71A one of the following codes may be used : BEN, OUR, SHA. \",\n" +
                "  \"sequence\" : null,\n" +
                "  \"occurs\" : \"1\",\n" +
                "  \"line\" : null,\n" +
                "  \"messageIndex\" : null,\n" +
                "  \"errorCode\" : \"T08\"\n" +
                "} ]";

        //WHEN
        InvalidMessageException exception = assertThrows(InvalidMessageException.class, () -> {
            mtService.createMt103(mtCreate103Request);
        });

        //THEN
        assertEquals(replaceLineEndings(errorResponse), replaceLineEndings(exception.getResponseBodyAsString()));
    }

    @Test
    public void givenValidCreateMtGeneralRequest_whenCreateMtGeneral_thenReturnMtGeneralAsText() throws Exception {
        //GIVEN
        String expected = TestConstants.VALID_MT_103;

        //WHEN
        String result = mtService.createMtGeneral(TestConstants.getMtCreateGeneralRequestSample());

        //THEN
        assertEquals(replaceLineEndings(expected), replaceLineEndings(result));
    }

    @Test
    public void givenInvalidCreateMtGeneralRequest_whenCreateMtGeneral_thenThrowInvalidMessageException() throws Exception {
        //GIVEN
        MtCreateGeneralRequest mtCreateGeneralRequest = TestConstants.getMtCreateGeneralRequestSample();
        mtCreateGeneralRequest.getTags().removeIf(tag -> tag.getName().equals("71A"));
        mtCreateGeneralRequest.getTags().add(new MtTagGeneralRequest("71A", Arrays.asList("AAA"))); //invalid value
        String errorResponse = "[ {\n" +
                "  \"tagName\" : \"71A\",\n" +
                "  \"description\" : \"T08 - in field 71A one of the following codes may be used : BEN, OUR, SHA. \",\n" +
                "  \"sequence\" : null,\n" +
                "  \"occurs\" : \"1\",\n" +
                "  \"line\" : null,\n" +
                "  \"messageIndex\" : null,\n" +
                "  \"errorCode\" : \"T08\"\n" +
                "} ]";

        //WHEN
        InvalidMessageException exception = assertThrows(InvalidMessageException.class, () -> {
            mtService.createMtGeneral(mtCreateGeneralRequest);
        });

        //THEN
        assertEquals(replaceLineEndings(errorResponse), replaceLineEndings(exception.getResponseBodyAsString()));
    }

    @Test
    public void givenValidMt103Message_whenGenerateUniversalConfirmation_thenReturnUniversalConfirmation() throws Exception {
        //GIVEN
        String confirmationId = "1234";
        String statusCode = "ACCC";
        String expectedAsRegex = TestUtils.escapeSpecialRegexChars(TestConstants.VALID_MT_199)
                .replaceAll(":79:\\\\/.*\n", ":79:\\\\/.*\n");

        //WHEN
        String result = mtService.generateUniversalConfirmation(TestConstants.VALID_MT_103, confirmationId, statusCode, null, null, null, null);

        //THEN
        assertTrue(replaceLineEndings(result).matches(replaceLineEndings(expectedAsRegex)));
    }

    @Test
    public void givenValidMt103Message_whenGenerateUniversalConfirmation_thenReturnInvalidMessageException() throws Exception {
        //GIVEN
        String confirmationId = "1234";
        String statusCode = "ACCC";
        String reasonCode = "AC01";
        String errorResponse = "[ {\n" +
                "  \"tagName\" : null,\n" +
                "  \"description\" : \"D94 - In field 79, line 2, presence of subfield 2 (Reason Code) depends on subfield 1 (Status) as follows:\\n- When Status Code is ACCC, Reason Code is not allowed.\\n- When Status Code is ACSP, Reason Code must be one of the following : [G001, G002, G003, G004]\\n- When Status Code is RJCT, if Reason Code is present must be one of the following : [AC01, AC04, AC06, AM06, BE01, CUST, DUPL, FF07, FOCR, MS03, NOAS, RC01, RC08, RR03, RR05]\",\n" +
                "  \"sequence\" : null,\n" +
                "  \"occurs\" : \"1\",\n" +
                "  \"line\" : null,\n" +
                "  \"messageIndex\" : null,\n" +
                "  \"errorCode\" : \"D94\"\n" +
                "} ]";

        //WHEN
        InvalidMessageException exception = assertThrows(InvalidMessageException.class, () -> {
            mtService.generateUniversalConfirmation(TestConstants.VALID_MT_103, confirmationId, statusCode, reasonCode, null, null, null);
        });

        //THEN
        assertEquals(replaceLineEndings(errorResponse), replaceLineEndings(exception.getResponseBodyAsString()));
    }

}
