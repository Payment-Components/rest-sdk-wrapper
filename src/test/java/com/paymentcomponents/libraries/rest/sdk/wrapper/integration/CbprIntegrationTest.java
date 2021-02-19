package com.paymentcomponents.libraries.rest.sdk.wrapper.integration;

import com.paymentcomponents.libraries.rest.sdk.wrapper.Constants;
import com.paymentcomponents.libraries.rest.sdk.wrapper.TestConstants;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CbprIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void givenValidCbprMessage_whenCbprValidate_thenReturnNoContent() throws Exception {
        //GIVEN

        //WHEN
        mvc.perform(post("/cbpr/validate")
                .content(TestConstants.VALID_CBPR_REQUEST)
                .contentType(MediaType.TEXT_PLAIN))

                //THEN
                .andExpect(status().isNoContent())
                .andExpect(header().string(Constants.REQUEST_LOG_ID, Matchers.anything()));

    }

    @Test
    public void givenInValidCbprMessage_whenCbprValidate_thenReturnValidationErrors() throws Exception {
        //GIVEN

        //WHEN
        mvc.perform(post("/cbpr/validate")
                .content(TestConstants.INVALID_CBPR_REQUEST)
                .contentType(MediaType.TEXT_PLAIN))

                //THEN
                .andExpect(status().isBadRequest())
                .andExpect(header().string(Constants.REQUEST_LOG_ID, Matchers.anything()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].severity", is("ERROR")))
                .andExpect(jsonPath("$[0].errorCode", IsNull.nullValue()))
                .andExpect(jsonPath("$[0].fieldPath", IsNull.nullValue()))
                .andExpect(jsonPath("$[0].description", is("cvc-complex-type.2.4.a: Invalid content was found starting with element 'CreDtTm'. One of '{\"urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08\":MsgId}' is expected.")))
                .andExpect(jsonPath("$[0].erroneousValue", IsNull.nullValue()))
                .andExpect(jsonPath("$[0].line", is(4)))
                .andExpect(jsonPath("$[0].column", is(34)));

    }

    @Test
    public void givenValidCbprMessage_whenCbprEnvelope_thenReturnEnvelopedCbpr() throws Exception {
        //GIVEN

        //WHEN
        mvc.perform(post("/cbpr/envelope")
                .content(TestConstants.VALID_CBPR_REQUEST)
                .contentType(MediaType.TEXT_PLAIN))

        //THEN
                .andExpect(status().isOk())
                .andExpect(header().string(Constants.REQUEST_LOG_ID, Matchers.anything()))
                .andExpect(content().contentType(new MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_8)))
                .andExpect(content().string(TestConstants.CBPR_ENVELOPE.replaceAll("\n", "\r\n")));

    }

    @Test
    public void givenInValidCbprMessage_whenCbprEnvelope_thenReturnValidationErrors() throws Exception {
        //GIVEN

        //WHEN
        mvc.perform(post("/cbpr/envelope")
                .content(TestConstants.INVALID_CBPR_REQUEST)
                .contentType(MediaType.TEXT_PLAIN))

                //THEN
                .andExpect(status().isBadRequest())
                .andExpect(header().string(Constants.REQUEST_LOG_ID, Matchers.anything()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].severity", is("ERROR")))
                .andExpect(jsonPath("$[0].errorCode", IsNull.nullValue()))
                .andExpect(jsonPath("$[0].fieldPath", IsNull.nullValue()))
                .andExpect(jsonPath("$[0].description", is("cvc-complex-type.2.4.a: Invalid content was found starting with element 'CreDtTm'. One of '{\"urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08\":MsgId}' is expected.")))
                .andExpect(jsonPath("$[0].erroneousValue", IsNull.nullValue()))
                .andExpect(jsonPath("$[0].line", is(4)))
                .andExpect(jsonPath("$[0].column", is(34)));

    }

}