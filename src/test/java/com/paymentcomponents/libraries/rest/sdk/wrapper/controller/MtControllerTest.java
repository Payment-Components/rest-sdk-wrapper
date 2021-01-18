package com.paymentcomponents.libraries.rest.sdk.wrapper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymentcomponents.libraries.rest.sdk.wrapper.TestConstants;
import com.paymentcomponents.libraries.rest.sdk.wrapper.TestUtils;
import com.paymentcomponents.libraries.rest.sdk.wrapper.exception.InvalidMessageException;
import com.paymentcomponents.libraries.rest.sdk.wrapper.filter.RequestLogIdFilter;
import com.paymentcomponents.libraries.rest.sdk.wrapper.model.CustomSwiftMessage;
import com.paymentcomponents.libraries.rest.sdk.wrapper.model.mt.request.MtCreate103Request;
import com.paymentcomponents.libraries.rest.sdk.wrapper.model.mt.request.MtCreateGeneralRequest;
import com.paymentcomponents.libraries.rest.sdk.wrapper.model.mt.request.MtTagGeneralRequest;
import com.paymentcomponents.libraries.rest.sdk.wrapper.service.MtService;
import com.paymentcomponents.libraries.rest.sdk.wrapper.Constants;
import gr.datamation.swift.common.InvalidMessageFormatException;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = MtController.class)
public class MtControllerTest {

    @TestConfiguration
    static class MtControllerTestContextConfiguration {

        @Bean
        public FilterRegistrationBean<RequestLogIdFilter> requestLogIdFilter() {
            FilterRegistrationBean<RequestLogIdFilter> registrationBean = new FilterRegistrationBean<>();
            registrationBean.setFilter(new RequestLogIdFilter());

            return registrationBean;
        }
    }

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MtService mtService;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        reset(mtService);
    }

    @Test
    public void givenValidMtMessage_whenMtParse_thenReturnParsedJson() throws Exception {
        //GIVEN
        given(mtService.parseMt(anyString())).willReturn(new CustomSwiftMessage(TestUtils.constructSwiftMessage(TestConstants.VALID_MT_103)));

        //WHEN
        mvc.perform(post("/mt/parse")
                .content(TestConstants.VALID_MT_103)
                .contentType(MediaType.TEXT_PLAIN))

        //THEN
                .andExpect(status().isOk())
                .andExpect(header().string(Constants.REQUEST_LOG_ID, Matchers.anything()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\n    \"formattedMessage\": \"Ack Applid      : F\\nAck Servid      : 01\\nAck LTaddrBlk1  : TESTBICFXXXX\\nAck Sesno       : 1111\\nAck Osn         : 111111\\nAck 108         : 121:2412527e-aefa-455d-8307-851118e145fe\\nApplid          : FServid          : 01LTaddrBlk1      : TESTBICFXXXXSesno           : 1111Osn             : 111111Inoutind        : IMsgtype         : 103LTaddrBlk2      : TESTBICGXXXXMsgprior        : N121             : 2412527e-aefa-455d-8307-851118e145fe20              : 12345678923B             : CRED32A             : 200924EUR23,0950K             : /12345678950K             : John Doe50K             : Chatziantoniou 1450K             : 15124, Marousi50K             : Attika, Greece52A             : /123452A             : TESTBICA53A             : /12345678953A             : TESTBICE56A             : /12345656A             : TESTBICB57A             : /1234567857A             : TESTBICD59              : /98765432159              : Peter Johnes59              : Address159              : Address259              : Address370              : /INV/abc/SDF-96//1234-234///ROC/98I70              : U8771A             : OUR72              : /INS/ABNANL2A\",\n    \"argObsPer\": \"\",\n    \"argMsgtype\": \"103\",\n    \"argOutdate\": \"\",\n    \"block2\": [\n        {\n            \"depth\": 0,\n            \"name\": \"Inoutind\",\n            \"empty\": false,\n            \"data\": [\n                \"I\"\n            ],\n            \"valid\": true,\n            \"valueAsString\": \"I\",\n            \"rootBlockName\": null,\n            \"numberOfDataLines\": 1\n        },\n        {\n            \"depth\": 0,\n            \"name\": \"Msgtype\",\n            \"empty\": false,\n            \"data\": [\n                \"103\"\n            ],\n            \"valid\": true,\n            \"valueAsString\": \"103\",\n            \"rootBlockName\": null,\n            \"numberOfDataLines\": 1\n        },\n        {\n            \"depth\": 0,\n            \"name\": \"LTaddrBlk2\",\n            \"empty\": false,\n            \"data\": [\n                \"TESTBICGXXXX\"\n            ],\n            \"valid\": true,\n            \"valueAsString\": \"TESTBICGXXXX\",\n            \"rootBlockName\": null,\n            \"numberOfDataLines\": 1\n        },\n        {\n            \"depth\": 0,\n            \"name\": \"Msgprior\",\n            \"empty\": false,\n            \"data\": [\n                \"N\"\n            ],\n            \"valid\": true,\n            \"valueAsString\": \"N\",\n            \"rootBlockName\": null,\n            \"numberOfDataLines\": 1\n        }\n    ],\n    \"block1\": [\n        {\n            \"depth\": 0,\n            \"name\": \"Applid\",\n            \"empty\": false,\n            \"data\": [\n                \"F\"\n            ],\n            \"valid\": true,\n            \"valueAsString\": \"F\",\n            \"rootBlockName\": null,\n            \"numberOfDataLines\": 1\n        },\n        {\n            \"depth\": 0,\n            \"name\": \"Servid\",\n            \"empty\": false,\n            \"data\": [\n                \"01\"\n            ],\n            \"valid\": true,\n            \"valueAsString\": \"01\",\n            \"rootBlockName\": null,\n            \"numberOfDataLines\": 1\n        },\n        {\n            \"depth\": 0,\n            \"name\": \"LTaddrBlk1\",\n            \"empty\": false,\n            \"data\": [\n                \"TESTBICFXXXX\"\n            ],\n            \"valid\": true,\n            \"valueAsString\": \"TESTBICFXXXX\",\n            \"rootBlockName\": null,\n            \"numberOfDataLines\": 1\n        },\n        {\n            \"depth\": 0,\n            \"name\": \"Sesno\",\n            \"empty\": false,\n            \"data\": [\n                \"1111\"\n            ],\n            \"valid\": true,\n            \"valueAsString\": \"1111\",\n            \"rootBlockName\": null,\n            \"numberOfDataLines\": 1\n        },\n        {\n            \"depth\": 0,\n            \"name\": \"Osn\",\n            \"empty\": false,\n            \"data\": [\n                \"111111\"\n            ],\n            \"valid\": true,\n            \"valueAsString\": \"111111\",\n            \"rootBlockName\": null,\n            \"numberOfDataLines\": 1\n        }\n    ],\n    \"block3\": [\n        {\n            \"depth\": 0,\n            \"name\": \"121\",\n            \"empty\": false,\n            \"data\": [\n                \"2412527e-aefa-455d-8307-851118e145fe\"\n            ],\n            \"valid\": true,\n            \"valueAsString\": \"2412527e-aefa-455d-8307-851118e145fe\",\n            \"rootBlockName\": null,\n            \"numberOfDataLines\": 1\n        }\n    ],\n    \"argSsesno\": \"\",\n    \"envelope\": false,\n    \"argOuttime\": \"\",\n    \"block5\": [],\n    \"messageAsVector\": null,\n    \"block4\": [\n        {\n            \"depth\": 0,\n            \"name\": \"20\",\n            \"empty\": false,\n            \"data\": [\n                \"123456789\"\n            ],\n            \"valid\": true,\n            \"valueAsString\": \"123456789\",\n            \"rootBlockName\": null,\n            \"numberOfDataLines\": 1\n        },\n        {\n            \"depth\": 0,\n            \"name\": \"23B\",\n            \"empty\": false,\n            \"data\": [\n                \"CRED\"\n            ],\n            \"valid\": true,\n            \"valueAsString\": \"CRED\",\n            \"rootBlockName\": null,\n            \"numberOfDataLines\": 1\n        },\n        {\n            \"depth\": 0,\n            \"name\": \"32A\",\n            \"empty\": false,\n            \"data\": [\n                \"200924EUR23,09\"\n            ],\n            \"valid\": true,\n            \"valueAsString\": \"200924EUR23,09\",\n            \"rootBlockName\": null,\n            \"numberOfDataLines\": 1\n        },\n        {\n            \"depth\": 0,\n            \"name\": \"50K\",\n            \"empty\": false,\n            \"data\": [\n                \"/123456789\",\n                \"John Doe\",\n                \"Chatziantoniou 14\",\n                \"15124, Marousi\",\n                \"Attika, Greece\"\n            ],\n            \"valid\": true,\n            \"valueAsString\": \"/123456789\\nJohn Doe\\nChatziantoniou 14\\n15124, Marousi\\nAttika, Greece\",\n            \"rootBlockName\": null,\n            \"numberOfDataLines\": 5\n        },\n        {\n            \"depth\": 0,\n            \"name\": \"52A\",\n            \"empty\": false,\n            \"data\": [\n                \"/1234\",\n                \"TESTBICA\"\n            ],\n            \"valid\": true,\n            \"valueAsString\": \"/1234\\nTESTBICA\",\n            \"rootBlockName\": null,\n            \"numberOfDataLines\": 2\n        },\n        {\n            \"depth\": 0,\n            \"name\": \"53A\",\n            \"empty\": false,\n            \"data\": [\n                \"/123456789\",\n                \"TESTBICE\"\n            ],\n            \"valid\": true,\n            \"valueAsString\": \"/123456789\\nTESTBICE\",\n            \"rootBlockName\": null,\n            \"numberOfDataLines\": 2\n        },\n        {\n            \"depth\": 0,\n            \"name\": \"56A\",\n            \"empty\": false,\n            \"data\": [\n                \"/123456\",\n                \"TESTBICB\"\n            ],\n            \"valid\": true,\n            \"valueAsString\": \"/123456\\nTESTBICB\",\n            \"rootBlockName\": null,\n            \"numberOfDataLines\": 2\n        },\n        {\n            \"depth\": 0,\n            \"name\": \"57A\",\n            \"empty\": false,\n            \"data\": [\n                \"/12345678\",\n                \"TESTBICD\"\n            ],\n            \"valid\": true,\n            \"valueAsString\": \"/12345678\\nTESTBICD\",\n            \"rootBlockName\": null,\n            \"numberOfDataLines\": 2\n        },\n        {\n            \"depth\": 0,\n            \"name\": \"59\",\n            \"empty\": false,\n            \"data\": [\n                \"/987654321\",\n                \"Peter Johnes\",\n                \"Address1\",\n                \"Address2\",\n                \"Address3\"\n            ],\n            \"valid\": true,\n            \"valueAsString\": \"/987654321\\nPeter Johnes\\nAddress1\\nAddress2\\nAddress3\",\n            \"rootBlockName\": null,\n            \"numberOfDataLines\": 5\n        },\n        {\n            \"depth\": 0,\n            \"name\": \"70\",\n            \"empty\": false,\n            \"data\": [\n                \"/INV/abc/SDF-96//1234-234///ROC/98I\",\n                \"U87\"\n            ],\n            \"valid\": true,\n            \"valueAsString\": \"/INV/abc/SDF-96//1234-234///ROC/98I\\nU87\",\n            \"rootBlockName\": null,\n            \"numberOfDataLines\": 2\n        },\n        {\n            \"depth\": 0,\n            \"name\": \"71A\",\n            \"empty\": false,\n            \"data\": [\n                \"OUR\"\n            ],\n            \"valid\": true,\n            \"valueAsString\": \"OUR\",\n            \"rootBlockName\": null,\n            \"numberOfDataLines\": 1\n        },\n        {\n            \"depth\": 0,\n            \"name\": \"72\",\n            \"empty\": false,\n            \"data\": [\n                \"/INS/ABNANL2A\"\n            ],\n            \"valid\": true,\n            \"valueAsString\": \"/INS/ABNANL2A\",\n            \"rootBlockName\": null,\n            \"numberOfDataLines\": 1\n        }\n    ],\n    \"argServid\": \"01\",\n    \"argOsn\": \"111111\",\n    \"argSesno\": \"1111\",\n    \"paymentControlsInformation\": \"\",\n    \"serviceTypeIdentifier\": \"\",\n    \"uniqueEndToEndTrxRef\": \"2412527e-aefa-455d-8307-851118e145fe\",\n    \"ack108\": \"121:2412527e-aefa-455d-8307-851118e145fe\",\n    \"argIntime\": \"\",\n    \"argIsn\": \"\",\n    \"ackSesno\": \"1111\",\n    \"argApplid\": \"F\",\n    \"argIndate\": \"\",\n    \"ack405\": \"\",\n    \"ack451\": \"\",\n    \"ackOsn\": \"111111\",\n    \"ackApplid\": \"F\",\n    \"argDelMon\": \"\",\n    \"ack177\": \"\",\n    \"argLTaddrBlk1\": \"TESTBICFXXXX\",\n    \"argLTaddrBlk2\": \"TESTBICGXXXX\",\n    \"ackLTaddrBlk1\": \"TESTBICFXXXX\",\n    \"ackServid\": \"01\",\n    \"argInoutind\": \"I\",\n    \"argMsgprior\": \"N\"\n}"));

        then(mtService).should(times(1)).parseMt(TestConstants.VALID_MT_103);
    }

    @Test
    public void givenInvalidFormatMtMessage_whenMtParse_thenReturnErrorText() throws Exception {
        //GIVEN
        String errorResponse = "Application Header Block is missing";
        given(mtService.parseMt(anyString())).willThrow(new InvalidMessageFormatException(errorResponse));

        //WHEN
        mvc.perform(post("/mt/parse")
                .content(TestConstants.VALID_MT_103)
                .contentType(MediaType.TEXT_PLAIN))

        //THEN
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(new MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_8)))
                .andExpect(content().string(errorResponse));

        then(mtService).should(times(1)).parseMt(TestConstants.VALID_MT_103);
    }

    @Test
    public void givenValidMtMessage_whenMtValidate_thenReturnNoContent() throws Exception {
        //GIVEN
        willDoNothing().given(mtService).validateMt(anyString());

        //WHEN
        mvc.perform(post("/mt/validate")
                .content(TestConstants.VALID_MT_103)
                .contentType(MediaType.TEXT_PLAIN))

        //THEN
                .andExpect(status().isNoContent())
                .andExpect(header().string(Constants.REQUEST_LOG_ID, Matchers.anything()));

        then(mtService).should(times(1)).validateMt(TestConstants.VALID_MT_103);
    }

    @Test
    public void givenInValidMtMessage_whenMtValidate_thenReturnValidationErrors() throws Exception {
        //GIVEN
        String errorReponse = "[ {\n" +
                "  \"tagName\" : \"20\",\n" +
                "  \"description\" : \"SV16 - Mandatory Tag is missing \",\n" +
                "  \"sequence\" : null,\n" +
                "  \"occurs\" : \"1\",\n" +
                "  \"line\" : null,\n" +
                "  \"messageIndex\" : null,\n" +
                "  \"errorCode\" : \"SV16\"\n" +
                "} ]";
        willThrow(new InvalidMessageException(errorReponse)).given(mtService).validateMt(anyString());

        //WHEN
        mvc.perform(post("/mt/validate")
                .content(TestConstants.INVALID_MT_103)
                .contentType(MediaType.TEXT_PLAIN))

        //THEN
                .andExpect(status().isBadRequest())
                .andExpect(header().string(Constants.REQUEST_LOG_ID, Matchers.anything()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].tagName", is("20")))
                .andExpect(jsonPath("$[0].description", is("SV16 - Mandatory Tag is missing ")))
                .andExpect(jsonPath("$[0].sequence", IsNull.nullValue()))
                .andExpect(jsonPath("$[0].occurs", is("1")))
                .andExpect(jsonPath("$[0].line", IsNull.nullValue()))
                .andExpect(jsonPath("$[0].messageIndex", IsNull.nullValue()))
                .andExpect(jsonPath("$[0].errorCode", is("SV16")));

        then(mtService).should(times(1)).validateMt(TestConstants.INVALID_MT_103);
    }

    @Test
    public void givenInValidFormatMtMessage_whenMtValidate_thenReturnErrorMessage() throws Exception {
        //GIVEN
        String errorReponse = "Application Header Block is missing";
        willThrow(new InvalidMessageFormatException(errorReponse)).given(mtService).validateMt(anyString());

        //WHEN
        mvc.perform(post("/mt/validate")
                .content(TestConstants.VALID_MT_103)
                .contentType(MediaType.TEXT_PLAIN))

        //THEN
                .andExpect(status().isBadRequest())
                .andExpect(header().string(Constants.REQUEST_LOG_ID, Matchers.anything()))
                .andExpect(content().contentType(new MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_8)))
                .andExpect(content().string(errorReponse));

        then(mtService).should(times(1)).validateMt(TestConstants.VALID_MT_103);
    }

    @Test
    public void givenValidMt103Json_whenCreateMt103_thenReturnMt103AsText() throws Exception {
        //GIVEN
        given(mtService.createMt103(isA(MtCreate103Request.class))).willReturn(TestConstants.VALID_MT_103);
        String requestJson = objectMapper.writeValueAsString(TestConstants.getMtCreate103RequestSample());

        //WHEN
        mvc.perform(post("/mt/create/103")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))

        //THEN
                .andExpect(status().isOk())
                .andExpect(header().string(Constants.REQUEST_LOG_ID, Matchers.anything()))
                .andExpect(content().contentType(new MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_8)))
                .andExpect(content().string(TestConstants.VALID_MT_103));

        then(mtService).should(times(1)).createMt103(isA(MtCreate103Request.class));
    }

    @Test
    public void givenInvalidMt103Json_whenCreateMt103_thenReturnValidationErrors() throws Exception {
        //GIVEN
        String errorReponse = "[\n" +
                "    {\n" +
                "        \"tagName\": \"71A\",\n" +
                "        \"description\": \"T08 - in field 71A one of the following codes may be used : BEN, OUR, SHA. \",\n" +
                "        \"sequence\": null,\n" +
                "        \"occurs\": \"1\",\n" +
                "        \"line\": null,\n" +
                "        \"messageIndex\": null,\n" +
                "        \"errorCode\": \"T08\"\n" +
                "    }\n" +
                "]";
        given(mtService.createMt103(isA(MtCreate103Request.class))).willThrow(new InvalidMessageException(errorReponse));
        MtCreate103Request mtCreate103Request = TestConstants.getMtCreate103RequestSample();
        mtCreate103Request.setDetailsOfCharges("AAA"); //invalid value
        String requestJson = objectMapper.writeValueAsString(mtCreate103Request);

        //WHEN
        mvc.perform(post("/mt/create/103")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))

        //THEN
                .andExpect(status().isBadRequest())
                .andExpect(header().string(Constants.REQUEST_LOG_ID, Matchers.anything()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].tagName", is("71A")))
                .andExpect(jsonPath("$[0].description", is("T08 - in field 71A one of the following codes may be used : BEN, OUR, SHA. ")))
                .andExpect(jsonPath("$[0].sequence", IsNull.nullValue()))
                .andExpect(jsonPath("$[0].occurs", is("1")))
                .andExpect(jsonPath("$[0].line", IsNull.nullValue()))
                .andExpect(jsonPath("$[0].messageIndex", IsNull.nullValue()))
                .andExpect(jsonPath("$[0].errorCode", is("T08")));

        then(mtService).should(times(1)).createMt103(isA(MtCreate103Request.class));
    }

    @Test
    public void givenValidMtGeneralJson_whenCreateMtGeneral_thenReturnMtGeneralAsText() throws Exception {
        //GIVEN
        given(mtService.createMtGeneral(isA(MtCreateGeneralRequest.class))).willReturn(TestConstants.VALID_MT_103);
        String requestJson = objectMapper.writeValueAsString(TestConstants.getMtCreateGeneralRequestSample());

        //WHEN
        mvc.perform(post("/mt/create/general")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))

        //THEN
                .andExpect(status().isOk())
                .andExpect(header().string(Constants.REQUEST_LOG_ID, Matchers.anything()))
                .andExpect(content().contentType(new MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_8)))
                .andExpect(content().string(TestConstants.VALID_MT_103));

        then(mtService).should(times(1)).createMtGeneral(isA(MtCreateGeneralRequest.class));
    }

    @Test
    public void givenInvalidMtGeneralJson_whenCreateMtGeneral_thenReturnValidationErrors() throws Exception {
        //GIVEN
        String errorReponse = "[\n" +
                "    {\n" +
                "        \"tagName\": \"71A\",\n" +
                "        \"description\": \"T08 - in field 71A one of the following codes may be used : BEN, OUR, SHA. \",\n" +
                "        \"sequence\": null,\n" +
                "        \"occurs\": \"1\",\n" +
                "        \"line\": null,\n" +
                "        \"messageIndex\": null,\n" +
                "        \"errorCode\": \"T08\"\n" +
                "    }\n" +
                "]";
        given(mtService.createMtGeneral(isA(MtCreateGeneralRequest.class))).willThrow(new InvalidMessageException(errorReponse));
        MtCreateGeneralRequest mtCreateGeneralRequest = TestConstants.getMtCreateGeneralRequestSample();
        mtCreateGeneralRequest.getTags().removeIf( tag -> tag.getName().equals("71A"));
        mtCreateGeneralRequest.getTags().add(new MtTagGeneralRequest("71A", Arrays.asList("AAA"))); //invalid value
        String requestJson = objectMapper.writeValueAsString(mtCreateGeneralRequest);

        //WHEN
        mvc.perform(post("/mt/create/general")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))

        //THEN
                .andExpect(status().isBadRequest())
                .andExpect(header().string(Constants.REQUEST_LOG_ID, Matchers.anything()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].tagName", is("71A")))
                .andExpect(jsonPath("$[0].description", is("T08 - in field 71A one of the following codes may be used : BEN, OUR, SHA. ")))
                .andExpect(jsonPath("$[0].sequence", IsNull.nullValue()))
                .andExpect(jsonPath("$[0].occurs", is("1")))
                .andExpect(jsonPath("$[0].line", IsNull.nullValue()))
                .andExpect(jsonPath("$[0].messageIndex", IsNull.nullValue()))
                .andExpect(jsonPath("$[0].errorCode", is("T08")));

        then(mtService).should(times(1)).createMtGeneral(isA(MtCreateGeneralRequest.class));
    }

    @Test
    public void givenValidMt103Message_whenUniversalConfirmation_thenReturnUniversalConfirmation() throws Exception {
        //GIVEN
        given(mtService.generateUniversalConfirmation(eq(TestConstants.VALID_MT_103), eq("1234"), eq("ACCC"), isNull(), isNull(), isNull(), isNull())).willReturn(TestConstants.VALID_MT_199);

        //WHEN
        mvc.perform(post("/mt/generate/universal/confirmation")
                .queryParam("confirmationId", "1234")
                .queryParam("statusCode", "ACCC")
                .content(TestConstants.VALID_MT_103)
                .contentType(MediaType.TEXT_PLAIN))

        //THEN
                .andExpect(status().isOk())
                .andExpect(header().string(Constants.REQUEST_LOG_ID, Matchers.anything()))
                .andExpect(content().contentType(new MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_8)))
                .andExpect(content().string(TestConstants.VALID_MT_199));

        then(mtService).should(times(1)).generateUniversalConfirmation(eq(TestConstants.VALID_MT_103), eq("1234"), eq("ACCC"), isNull(), isNull(), isNull(), isNull());
    }

    @Test
    public void givenInvalidUniversalConfirmationParams_whenUniversalConfirmation_thenReturnInvalidMessageException() throws Exception {
        //GIVEN
        String errorReponse = "[\n" +
                "    {\n" +
                "        \"tagName\": null,\n" +
                "        \"description\": \"D94 - In field 79, line 2, presence of subfield 2 (Reason Code) depends on subfield 1 (Status) as follows:\\n- When Status Code is ACCC, Reason Code is not allowed.\\n- When Status Code is ACSP, Reason Code must be one of the following : [G001, G002, G003, G004]\\n- When Status Code is RJCT, if Reason Code is present must be one of the following : [AC01, AC04, AC06, AM06, BE01, CUST, DUPL, FF07, FOCR, MS03, NOAS, RC01, RC08, RR03, RR05]\",\n" +
                "        \"sequence\": null,\n" +
                "        \"occurs\": \"1\",\n" +
                "        \"line\": null,\n" +
                "        \"messageIndex\": null,\n" +
                "        \"errorCode\": \"D94\"\n" +
                "    }\n" +
                "]";

        given(mtService.generateUniversalConfirmation(eq(TestConstants.VALID_MT_103), eq("1234"), eq("ACCC"), eq("AC01"), isNull(), isNull(), isNull()))
                .willThrow(new InvalidMessageException(errorReponse));


        //WHEN
        mvc.perform(post("/mt/generate/universal/confirmation")
                .queryParam("confirmationId", "1234")
                .queryParam("statusCode", "ACCC")
                .queryParam("reasonCode", "AC01")
                .content(TestConstants.VALID_MT_103)
                .contentType(MediaType.TEXT_PLAIN))

        //THEN
                .andExpect(status().isBadRequest())
                .andExpect(header().string(Constants.REQUEST_LOG_ID, Matchers.anything()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].tagName", IsNull.nullValue()))
                .andExpect(jsonPath("$[0].description", is("D94 - In field 79, line 2, presence of subfield 2 (Reason Code) depends on subfield 1 (Status) as follows:\n- When Status Code is ACCC, Reason Code is not allowed.\n- When Status Code is ACSP, Reason Code must be one of the following : [G001, G002, G003, G004]\n- When Status Code is RJCT, if Reason Code is present must be one of the following : [AC01, AC04, AC06, AM06, BE01, CUST, DUPL, FF07, FOCR, MS03, NOAS, RC01, RC08, RR03, RR05]")))
                .andExpect(jsonPath("$[0].sequence", IsNull.nullValue()))
                .andExpect(jsonPath("$[0].occurs", is("1")))
                .andExpect(jsonPath("$[0].line", IsNull.nullValue()))
                .andExpect(jsonPath("$[0].messageIndex", IsNull.nullValue()))
                .andExpect(jsonPath("$[0].errorCode", is("D94")));

        then(mtService).should(times(1)).generateUniversalConfirmation(eq(TestConstants.VALID_MT_103), eq("1234"), eq("ACCC"), eq("AC01"), isNull(), isNull(), isNull());
    }

}
