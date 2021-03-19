package com.paymentcomponents.libraries.rest.sdk.wrapper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymentcomponents.libraries.rest.sdk.wrapper.TestConstants;
import com.paymentcomponents.libraries.rest.sdk.wrapper.TestUtils;
import com.paymentcomponents.libraries.rest.sdk.wrapper.exception.InvalidMessageException;
import com.paymentcomponents.libraries.rest.sdk.wrapper.filter.RequestLogIdFilter;
import com.paymentcomponents.libraries.rest.sdk.wrapper.model.CustomSwiftMessage;
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
        given(mtService.parseMt(anyString())).willReturn(new CustomSwiftMessage(TestUtils.constructSwiftMessage(TestConstants.VALID_MT_101)));

        //WHEN
        mvc.perform(post("/mt/parse")
                .content(TestConstants.VALID_MT_101)
                .contentType(MediaType.TEXT_PLAIN))

        //THEN
                .andExpect(status().isOk())
                .andExpect(header().string(Constants.REQUEST_LOG_ID, Matchers.anything()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\n    \"formattedMessage\": \"Ack Applid      : F\\nAck Servid      : 01\\nAck LTaddrBlk1  : COPZBEB0AXXX\\nAck Sesno       : 0377\\nAck Osn         : 002843\\nAck 108         : MT101 006 OF 020\\nApplid          : FServid          : 01LTaddrBlk1      : COPZBEB0AXXXSesno           : 0377Osn             : 002843Inoutind        : OMsgtype         : 101Intime          : 1519Indate          : 110804LTaddrBlk2      : LRLRXXXX4A11Ssesno          : 0000Isn             : 904466Outdate         : 110804Outtime         : 1720Msgprior        : N108             : MT101 006 OF 02020              : 0004328D             : 1/150F             : /409074-293-45/78650F             : 1/George Philips50F             : 2/High Street 150F             : 3/GB/London30              : 01123121              : PQR-27ZZ-0132B             : USD2564,5057D             : /C/Clementine Nuggets-1842-Y57D             : MynR49R RailRoad Trust57D             : Cloudsboro ARTUI59F             : 1/Beneficiary Name-123456789123412359F             : 2/QWERT59F             : 3/US/Beneficiary Address Line 2159F             : 3/Beneficiary Name-123456789123412371A             : OURCHK             : 19DA346889CCMAC             : 00000000TNG             : \",\n    \"ackSesno\": \"0377\",\n    \"ack405\": \"\",\n    \"ackLTaddrBlk1\": \"COPZBEB0AXXX\",\n    \"argInoutind\": \"O\",\n    \"ack108\": \"MT101 006 OF 020\",\n    \"ackApplid\": \"F\",\n    \"argLTaddrBlk1\": \"COPZBEB0AXXX\",\n    \"argObsPer\": \"\",\n    \"argMsgtype\": \"101\",\n    \"argOsn\": \"002843\",\n    \"argIndate\": \"110804\",\n    \"ack177\": \"\",\n    \"ackServid\": \"01\",\n    \"ackOsn\": \"002843\",\n    \"argApplid\": \"F\",\n    \"argLTaddrBlk2\": \"LRLRXXXX4A11\",\n    \"argMsgprior\": \"N\",\n    \"argIntime\": \"1519\",\n    \"argDelMon\": \"\",\n    \"argIsn\": \"904466\",\n    \"ack451\": \"\",\n    \"argSesno\": \"0377\",\n    \"block4\": [\n        {\n            \"depth\": 0,\n            \"name\": \"20\",\n            \"empty\": false,\n            \"valid\": true,\n            \"valueAsString\": \"00043\",\n            \"data\": [\n                \"00043\"\n            ],\n            \"numberOfDataLines\": 1,\n            \"rootBlockName\": null\n        },\n        {\n            \"depth\": 0,\n            \"name\": \"28D\",\n            \"empty\": false,\n            \"valid\": true,\n            \"valueAsString\": \"1/1\",\n            \"data\": [\n                \"1/1\"\n            ],\n            \"numberOfDataLines\": 1,\n            \"rootBlockName\": null\n        },\n        {\n            \"depth\": 0,\n            \"name\": \"50F\",\n            \"empty\": false,\n            \"valid\": true,\n            \"valueAsString\": \"/409074-293-45/786\\n1/George Philips\\n2/High Street 1\\n3/GB/London\",\n            \"data\": [\n                \"/409074-293-45/786\",\n                \"1/George Philips\",\n                \"2/High Street 1\",\n                \"3/GB/London\"\n            ],\n            \"numberOfDataLines\": 4,\n            \"rootBlockName\": null\n        },\n        {\n            \"depth\": 0,\n            \"name\": \"30\",\n            \"empty\": false,\n            \"valid\": true,\n            \"valueAsString\": \"011231\",\n            \"data\": [\n                \"011231\"\n            ],\n            \"numberOfDataLines\": 1,\n            \"rootBlockName\": null\n        },\n        {\n            \"tagVec\": [\n                {\n                    \"depth\": 0,\n                    \"name\": \"21\",\n                    \"empty\": false,\n                    \"valid\": true,\n                    \"valueAsString\": \"PQR-27ZZ-01\",\n                    \"data\": [\n                        \"PQR-27ZZ-01\"\n                    ],\n                    \"numberOfDataLines\": 1,\n                    \"rootBlockName\": null\n                },\n                {\n                    \"depth\": 0,\n                    \"name\": \"32B\",\n                    \"empty\": false,\n                    \"valid\": true,\n                    \"valueAsString\": \"USD2564,50\",\n                    \"data\": [\n                        \"USD2564,50\"\n                    ],\n                    \"numberOfDataLines\": 1,\n                    \"rootBlockName\": null\n                },\n                {\n                    \"depth\": 0,\n                    \"name\": \"57D\",\n                    \"empty\": false,\n                    \"valid\": true,\n                    \"valueAsString\": \"/C/Clementine Nuggets-1842-Y\\nMynR49R RailRoad Trust\\nCloudsboro ARTUI\",\n                    \"data\": [\n                        \"/C/Clementine Nuggets-1842-Y\",\n                        \"MynR49R RailRoad Trust\",\n                        \"Cloudsboro ARTUI\"\n                    ],\n                    \"numberOfDataLines\": 3,\n                    \"rootBlockName\": null\n                },\n                {\n                    \"depth\": 0,\n                    \"name\": \"59F\",\n                    \"empty\": false,\n                    \"valid\": true,\n                    \"valueAsString\": \"1/Beneficiary Name-1234567891234123\\n2/QWERT\\n3/US/Beneficiary Address Line 21\\n3/Beneficiary Name-1234567891234123\",\n                    \"data\": [\n                        \"1/Beneficiary Name-1234567891234123\",\n                        \"2/QWERT\",\n                        \"3/US/Beneficiary Address Line 21\",\n                        \"3/Beneficiary Name-1234567891234123\"\n                    ],\n                    \"numberOfDataLines\": 4,\n                    \"rootBlockName\": null\n                },\n                {\n                    \"depth\": 0,\n                    \"name\": \"71A\",\n                    \"empty\": false,\n                    \"valid\": true,\n                    \"valueAsString\": \"OUR\",\n                    \"data\": [\n                        \"OUR\"\n                    ],\n                    \"numberOfDataLines\": 1,\n                    \"rootBlockName\": null\n                }\n            ],\n            \"empty\": false\n        }\n    ],\n    \"argOuttime\": \"1720\",\n    \"envelope\": false,\n    \"block3\": [\n        {\n            \"depth\": 0,\n            \"name\": \"108\",\n            \"empty\": false,\n            \"valid\": true,\n            \"valueAsString\": \"MT101 006 OF 020\",\n            \"data\": [\n                \"MT101 006 OF 020\"\n            ],\n            \"numberOfDataLines\": 1,\n            \"rootBlockName\": null\n        }\n    ],\n    \"block5\": [\n        {\n            \"depth\": 0,\n            \"name\": \"CHK\",\n            \"empty\": false,\n            \"valid\": true,\n            \"valueAsString\": \"19DA346889CC\",\n            \"data\": [\n                \"19DA346889CC\"\n            ],\n            \"numberOfDataLines\": 1,\n            \"rootBlockName\": null\n        },\n        {\n            \"depth\": 0,\n            \"name\": \"MAC\",\n            \"empty\": false,\n            \"valid\": true,\n            \"valueAsString\": \"00000000\",\n            \"data\": [\n                \"00000000\"\n            ],\n            \"numberOfDataLines\": 1,\n            \"rootBlockName\": null\n        },\n        {\n            \"depth\": 0,\n            \"name\": \"TNG\",\n            \"empty\": false,\n            \"valid\": true,\n            \"valueAsString\": \"\",\n            \"data\": [\n                \"\"\n            ],\n            \"numberOfDataLines\": 1,\n            \"rootBlockName\": null\n        }\n    ],\n    \"argServid\": \"01\",\n    \"block2\": [\n        {\n            \"depth\": 0,\n            \"name\": \"Inoutind\",\n            \"empty\": false,\n            \"valid\": true,\n            \"valueAsString\": \"O\",\n            \"data\": [\n                \"O\"\n            ],\n            \"numberOfDataLines\": 1,\n            \"rootBlockName\": null\n        },\n        {\n            \"depth\": 0,\n            \"name\": \"Msgtype\",\n            \"empty\": false,\n            \"valid\": true,\n            \"valueAsString\": \"101\",\n            \"data\": [\n                \"101\"\n            ],\n            \"numberOfDataLines\": 1,\n            \"rootBlockName\": null\n        },\n        {\n            \"depth\": 0,\n            \"name\": \"Intime\",\n            \"empty\": false,\n            \"valid\": true,\n            \"valueAsString\": \"1519\",\n            \"data\": [\n                \"1519\"\n            ],\n            \"numberOfDataLines\": 1,\n            \"rootBlockName\": null\n        },\n        {\n            \"depth\": 0,\n            \"name\": \"Indate\",\n            \"empty\": false,\n            \"valid\": true,\n            \"valueAsString\": \"110804\",\n            \"data\": [\n                \"110804\"\n            ],\n            \"numberOfDataLines\": 1,\n            \"rootBlockName\": null\n        },\n        {\n            \"depth\": 0,\n            \"name\": \"LTaddrBlk2\",\n            \"empty\": false,\n            \"valid\": true,\n            \"valueAsString\": \"LRLRXXXX4A11\",\n            \"data\": [\n                \"LRLRXXXX4A11\"\n            ],\n            \"numberOfDataLines\": 1,\n            \"rootBlockName\": null\n        },\n        {\n            \"depth\": 0,\n            \"name\": \"Ssesno\",\n            \"empty\": false,\n            \"valid\": true,\n            \"valueAsString\": \"0000\",\n            \"data\": [\n                \"0000\"\n            ],\n            \"numberOfDataLines\": 1,\n            \"rootBlockName\": null\n        },\n        {\n            \"depth\": 0,\n            \"name\": \"Isn\",\n            \"empty\": false,\n            \"valid\": true,\n            \"valueAsString\": \"904466\",\n            \"data\": [\n                \"904466\"\n            ],\n            \"numberOfDataLines\": 1,\n            \"rootBlockName\": null\n        },\n        {\n            \"depth\": 0,\n            \"name\": \"Outdate\",\n            \"empty\": false,\n            \"valid\": true,\n            \"valueAsString\": \"110804\",\n            \"data\": [\n                \"110804\"\n            ],\n            \"numberOfDataLines\": 1,\n            \"rootBlockName\": null\n        },\n        {\n            \"depth\": 0,\n            \"name\": \"Outtime\",\n            \"empty\": false,\n            \"valid\": true,\n            \"valueAsString\": \"1720\",\n            \"data\": [\n                \"1720\"\n            ],\n            \"numberOfDataLines\": 1,\n            \"rootBlockName\": null\n        },\n        {\n            \"depth\": 0,\n            \"name\": \"Msgprior\",\n            \"empty\": false,\n            \"valid\": true,\n            \"valueAsString\": \"N\",\n            \"data\": [\n                \"N\"\n            ],\n            \"numberOfDataLines\": 1,\n            \"rootBlockName\": null\n        }\n    ],\n    \"argOutdate\": \"110804\",\n    \"argSsesno\": \"0000\",\n    \"block1\": [\n        {\n            \"depth\": 0,\n            \"name\": \"Applid\",\n            \"empty\": false,\n            \"valid\": true,\n            \"valueAsString\": \"F\",\n            \"data\": [\n                \"F\"\n            ],\n            \"numberOfDataLines\": 1,\n            \"rootBlockName\": null\n        },\n        {\n            \"depth\": 0,\n            \"name\": \"Servid\",\n            \"empty\": false,\n            \"valid\": true,\n            \"valueAsString\": \"01\",\n            \"data\": [\n                \"01\"\n            ],\n            \"numberOfDataLines\": 1,\n            \"rootBlockName\": null\n        },\n        {\n            \"depth\": 0,\n            \"name\": \"LTaddrBlk1\",\n            \"empty\": false,\n            \"valid\": true,\n            \"valueAsString\": \"COPZBEB0AXXX\",\n            \"data\": [\n                \"COPZBEB0AXXX\"\n            ],\n            \"numberOfDataLines\": 1,\n            \"rootBlockName\": null\n        },\n        {\n            \"depth\": 0,\n            \"name\": \"Sesno\",\n            \"empty\": false,\n            \"valid\": true,\n            \"valueAsString\": \"0377\",\n            \"data\": [\n                \"0377\"\n            ],\n            \"numberOfDataLines\": 1,\n            \"rootBlockName\": null\n        },\n        {\n            \"depth\": 0,\n            \"name\": \"Osn\",\n            \"empty\": false,\n            \"valid\": true,\n            \"valueAsString\": \"002843\",\n            \"data\": [\n                \"002843\"\n            ],\n            \"numberOfDataLines\": 1,\n            \"rootBlockName\": null\n        }\n    ],\n    \"serviceTypeIdentifier\": \"\",\n    \"paymentControlsInformation\": \"\",\n    \"uniqueEndToEndTrxRef\": \"\",\n    \"messageAsVector\": null\n}"));

        then(mtService).should(times(1)).parseMt(TestConstants.VALID_MT_101);
    }

    @Test
    public void givenInvalidFormatMtMessage_whenMtParse_thenReturnErrorText() throws Exception {
        //GIVEN
        String errorResponse = "Application Header Block is missing";
        given(mtService.parseMt(anyString())).willThrow(new InvalidMessageFormatException(errorResponse));

        //WHEN
        mvc.perform(post("/mt/parse")
                .content(TestConstants.VALID_MT_101)
                .contentType(MediaType.TEXT_PLAIN))

        //THEN
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(new MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_8)))
                .andExpect(content().string(errorResponse));

        then(mtService).should(times(1)).parseMt(TestConstants.VALID_MT_101);
    }

    @Test
    public void givenValidMtMessage_whenMtValidate_thenReturnNoContent() throws Exception {
        //GIVEN
        willDoNothing().given(mtService).validateMt(anyString());

        //WHEN
        mvc.perform(post("/mt/validate")
                .content(TestConstants.VALID_MT_101)
                .contentType(MediaType.TEXT_PLAIN))

        //THEN
                .andExpect(status().isNoContent())
                .andExpect(header().string(Constants.REQUEST_LOG_ID, Matchers.anything()));

        then(mtService).should(times(1)).validateMt(TestConstants.VALID_MT_101);
    }

    @Test
    public void givenInValidMtMessage_whenMtValidate_thenReturnValidationErrors() throws Exception {
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
        willThrow(new InvalidMessageException(errorResponse)).given(mtService).validateMt(anyString());

        //WHEN
        mvc.perform(post("/mt/validate")
                .content(TestConstants.INVALID_MT_101)
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

        then(mtService).should(times(1)).validateMt(TestConstants.INVALID_MT_101);
    }

    @Test
    public void givenInValidFormatMtMessage_whenMtValidate_thenReturnErrorMessage() throws Exception {
        //GIVEN
        String errorResponse = "Application Header Block is missing";
        willThrow(new InvalidMessageFormatException(errorResponse)).given(mtService).validateMt(anyString());

        //WHEN
        mvc.perform(post("/mt/validate")
                .content(TestConstants.VALID_MT_101)
                .contentType(MediaType.TEXT_PLAIN))

        //THEN
                .andExpect(status().isBadRequest())
                .andExpect(header().string(Constants.REQUEST_LOG_ID, Matchers.anything()))
                .andExpect(content().contentType(new MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_8)))
                .andExpect(content().string(errorResponse));

        then(mtService).should(times(1)).validateMt(TestConstants.VALID_MT_101);
    }

}
