package com.paymentcomponents.libraries.rest.sdk.wrapper.integration;

import com.paymentcomponents.libraries.rest.sdk.wrapper.Constants;
import com.paymentcomponents.libraries.rest.sdk.wrapper.TestConstants;
import com.paymentcomponents.libraries.rest.sdk.wrapper.TestUtils;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static com.paymentcomponents.libraries.rest.sdk.wrapper.TestUtils.replaceLineEndings;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RtgsTranslatorIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void givenValidMtMessage_whenTranslateMtToMx_thenReturnMxMessage() throws Exception {
        //GIVEN
        String expectedAsRegex = TestUtils.escapeSpecialRegexChars(TestConstants.VALID_RTGS_TRANSLATOR_MT_TO_MX_RESPONSE)
                .replaceAll("<CreDtTm>.*<\\\\/CreDtTm>", "<CreDtTm>.*<\\\\/CreDtTm>");

        //WHEN
        MockHttpServletResponse response = mvc.perform(post("/translator/rtgs/mt/to/mx")
                        .content(TestConstants.VALID_RTGS_TRANSLATOR_MT_TO_MX_REQUEST)
                        .contentType(MediaType.TEXT_PLAIN))

                //THEN
                .andExpect(status().isOk())
                .andExpect(header().string(Constants.REQUEST_LOG_ID, Matchers.anything()))
                .andExpect(content().contentType(new MediaType(MediaType.APPLICATION_XML, StandardCharsets.UTF_8)))
                .andReturn().getResponse();

        assertTrue(replaceLineEndings(response.getContentAsString()).matches(replaceLineEndings(expectedAsRegex)));
    }

    @Test
    public void givenInvalidMtMessage_whenTranslateMtToMx_thenReturnValidationErrors() throws Exception {
        //GIVEN
        String errorResponse = "[ {\n" +
                "  \"tagName\" : \"20\",\n" +
                "  \"description\" : \"SV16 - Mandatory Tag is missing \",\n" +
                "  \"sequence\" : null,\n" +
                "  \"occurs\" : \"1\",\n" +
                "  \"line\" : null,\n" +
                "  \"messageIndex\" : null,\n" +
                "  \"errorCode\" : \"SV16\"\n" +
                "} ]";

        //WHEN
        mvc.perform(post("/translator/rtgs/mt/to/mx")
                        .content(TestConstants.INVALID_RTGS_TRANSLATOR_MT_TO_MX_REQUEST)
                        .contentType(MediaType.TEXT_PLAIN))

                //THEN
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(replaceLineEndings(errorResponse)));
    }

    @Test
    public void givenValidMxMessage_whenTranslateMxToMt_thenReturnMtMessage() throws Exception {
        //GIVEN
        String expectedAsRegex = TestUtils.escapeSpecialRegexChars(TestConstants.VALID_RTGS_TRANSLATOR_MX_TO_MT_RESPONSE)
                .replaceAll("1343210323", ".*")
                .replaceAll("2103231343", ".*");

        //WHEN
        mvc.perform(post("/translator/rtgs/mx/to/mt")
                        .content(TestConstants.VALID_RTGS_TRANSLATOR_MX_TO_MT_REQUEST)
                        .contentType(MediaType.APPLICATION_XML))

        //THEN
                .andExpect(status().isOk())
                .andExpect(header().string(Constants.REQUEST_LOG_ID, Matchers.anything()))
                .andExpect(content().contentType(new MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_8)))
                .andExpect(content().string(Matchers.matchesPattern(replaceLineEndings(expectedAsRegex, "\r\n"))));
    }

    @Test
    public void givenInvalidMxMessage_whenTranslateMxToMt_thenReturnValidationErrors() throws Exception {
        //GIVEN

        //WHEN
        mvc.perform(post("/translator/rtgs/mx/to/mt")
                        .content(TestConstants.INVALID_RTGS_TRANSLATOR_MX_TO_MT_REQUEST)
                        .contentType(MediaType.APPLICATION_XML))

                //THEN
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].severity", is("ERROR")))
                .andExpect(jsonPath("$[0].errorCode", IsNull.nullValue()))
                .andExpect(jsonPath("$[0].fieldPath", is("/Document/FICdtTrf/GrpHdr/CreDtTm")))
                .andExpect(jsonPath("$[0].description", is("cvc-complex-type.2.4.a: Invalid content was found starting with element 'CreDtTm'. One of '{\"urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08\":MsgId}' is expected.")))
                .andExpect(jsonPath("$[0].erroneousValue", IsNull.nullValue()))
                .andExpect(jsonPath("$[0].line", is(5)))
                .andExpect(jsonPath("$[0].column", is(22)));

    }

}