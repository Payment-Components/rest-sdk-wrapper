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
public class MtIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void givenValidMtMessage_whenMtParse_thenReturnParsedJson() throws Exception {
        //GIVEN

        //WHEN
        mvc.perform(post("/mt/parse")
                .content(TestConstants.VALID_MT_101)
                .contentType(MediaType.TEXT_PLAIN))

        //THEN
                .andExpect(status().isOk())
                .andExpect(header().string(Constants.REQUEST_LOG_ID, Matchers.anything()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\n    \"formattedMessage\": \"Ack Applid      : F\\nAck Servid      : 01\\nAck LTaddrBlk1  : COPZBEB0AXXX\\nAck Sesno       : 0377\\nAck Osn         : 002843\\nAck 108         : MT101 006 OF 020\\nApplid          : FServid          : 01LTaddrBlk1      : COPZBEB0AXXXSesno           : 0377Osn             : 002843Inoutind        : OMsgtype         : 101Intime          : 1519Indate          : 110804LTaddrBlk2      : LRLRXXXX4A11Ssesno          : 0000Isn             : 904466Outdate         : 110804Outtime         : 1720Msgprior        : N108             : MT101 006 OF 020433             : /AOK/NO HIT DETECTED20              : 0004328D             : 1/150F             : /409074-293-45/78650F             : 1/George Philips50F             : 2/High Street 150F             : 3/GB/London30              : 01123121              : PQR-27ZZ-0132B             : USD2564,5057D             : /C/Clementine Nuggets-1842-Y57D             : MynR49R RailRoad Trust57D             : Cloudsboro ARTUI59F             : 1/Beneficiary Name-123456789123412359F             : 2/QWERT59F             : 3/US/Beneficiary Address Line 2159F             : 3/Beneficiary Name-123456789123412371A             : OURCHK             : 19DA346889CCMAC             : 00000000TNG             : \",\n    \"argOutdate\": \"110804\",\n    \"argOuttime\": \"1720\",\n    \"argServid\": \"01\",\n    \"argSesno\": \"0377\",\n    \"argSsesno\": \"0000\",\n    \"uniqueEndToEndTrxRef\": \"\",\n    \"serviceTypeIdentifier\": \"\",\n    \"block1\": [\n        {\n            \"depth\": 0,\n            \"data\": [\n                \"F\"\n            ],\n            \"rootBlockName\": null,\n            \"numberOfDataLines\": 1,\n            \"name\": \"Applid\",\n            \"empty\": false,\n            \"valid\": true,\n            \"valueAsString\": \"F\"\n        },\n        {\n            \"depth\": 0,\n            \"data\": [\n                \"01\"\n            ],\n            \"rootBlockName\": null,\n            \"numberOfDataLines\": 1,\n            \"name\": \"Servid\",\n            \"empty\": false,\n            \"valid\": true,\n            \"valueAsString\": \"01\"\n        },\n        {\n            \"depth\": 0,\n            \"data\": [\n                \"COPZBEB0AXXX\"\n            ],\n            \"rootBlockName\": null,\n            \"numberOfDataLines\": 1,\n            \"name\": \"LTaddrBlk1\",\n            \"empty\": false,\n            \"valid\": true,\n            \"valueAsString\": \"COPZBEB0AXXX\"\n        },\n        {\n            \"depth\": 0,\n            \"data\": [\n                \"0377\"\n            ],\n            \"rootBlockName\": null,\n            \"numberOfDataLines\": 1,\n            \"name\": \"Sesno\",\n            \"empty\": false,\n            \"valid\": true,\n            \"valueAsString\": \"0377\"\n        },\n        {\n            \"depth\": 0,\n            \"data\": [\n                \"002843\"\n            ],\n            \"rootBlockName\": null,\n            \"numberOfDataLines\": 1,\n            \"name\": \"Osn\",\n            \"empty\": false,\n            \"valid\": true,\n            \"valueAsString\": \"002843\"\n        }\n    ],\n    \"block2\": [\n        {\n            \"depth\": 0,\n            \"data\": [\n                \"O\"\n            ],\n            \"rootBlockName\": null,\n            \"numberOfDataLines\": 1,\n            \"name\": \"Inoutind\",\n            \"empty\": false,\n            \"valid\": true,\n            \"valueAsString\": \"O\"\n        },\n        {\n            \"depth\": 0,\n            \"data\": [\n                \"101\"\n            ],\n            \"rootBlockName\": null,\n            \"numberOfDataLines\": 1,\n            \"name\": \"Msgtype\",\n            \"empty\": false,\n            \"valid\": true,\n            \"valueAsString\": \"101\"\n        },\n        {\n            \"depth\": 0,\n            \"data\": [\n                \"1519\"\n            ],\n            \"rootBlockName\": null,\n            \"numberOfDataLines\": 1,\n            \"name\": \"Intime\",\n            \"empty\": false,\n            \"valid\": true,\n            \"valueAsString\": \"1519\"\n        },\n        {\n            \"depth\": 0,\n            \"data\": [\n                \"110804\"\n            ],\n            \"rootBlockName\": null,\n            \"numberOfDataLines\": 1,\n            \"name\": \"Indate\",\n            \"empty\": false,\n            \"valid\": true,\n            \"valueAsString\": \"110804\"\n        },\n        {\n            \"depth\": 0,\n            \"data\": [\n                \"LRLRXXXX4A11\"\n            ],\n            \"rootBlockName\": null,\n            \"numberOfDataLines\": 1,\n            \"name\": \"LTaddrBlk2\",\n            \"empty\": false,\n            \"valid\": true,\n            \"valueAsString\": \"LRLRXXXX4A11\"\n        },\n        {\n            \"depth\": 0,\n            \"data\": [\n                \"0000\"\n            ],\n            \"rootBlockName\": null,\n            \"numberOfDataLines\": 1,\n            \"name\": \"Ssesno\",\n            \"empty\": false,\n            \"valid\": true,\n            \"valueAsString\": \"0000\"\n        },\n        {\n            \"depth\": 0,\n            \"data\": [\n                \"904466\"\n            ],\n            \"rootBlockName\": null,\n            \"numberOfDataLines\": 1,\n            \"name\": \"Isn\",\n            \"empty\": false,\n            \"valid\": true,\n            \"valueAsString\": \"904466\"\n        },\n        {\n            \"depth\": 0,\n            \"data\": [\n                \"110804\"\n            ],\n            \"rootBlockName\": null,\n            \"numberOfDataLines\": 1,\n            \"name\": \"Outdate\",\n            \"empty\": false,\n            \"valid\": true,\n            \"valueAsString\": \"110804\"\n        },\n        {\n            \"depth\": 0,\n            \"data\": [\n                \"1720\"\n            ],\n            \"rootBlockName\": null,\n            \"numberOfDataLines\": 1,\n            \"name\": \"Outtime\",\n            \"empty\": false,\n            \"valid\": true,\n            \"valueAsString\": \"1720\"\n        },\n        {\n            \"depth\": 0,\n            \"data\": [\n                \"N\"\n            ],\n            \"rootBlockName\": null,\n            \"numberOfDataLines\": 1,\n            \"name\": \"Msgprior\",\n            \"empty\": false,\n            \"valid\": true,\n            \"valueAsString\": \"N\"\n        }\n    ],\n    \"block3\": [\n        {\n            \"depth\": 0,\n            \"data\": [\n                \"MT101 006 OF 020\"\n            ],\n            \"rootBlockName\": null,\n            \"numberOfDataLines\": 1,\n            \"name\": \"108\",\n            \"empty\": false,\n            \"valid\": true,\n            \"valueAsString\": \"MT101 006 OF 020\"\n        },\n        {\n            \"depth\": 0,\n            \"data\": [\n                \"/AOK/NO HIT DETECTED     \"\n            ],\n            \"rootBlockName\": null,\n            \"numberOfDataLines\": 1,\n            \"name\": \"433\",\n            \"empty\": false,\n            \"valid\": true,\n            \"valueAsString\": \"/AOK/NO HIT DETECTED     \"\n        }\n    ],\n    \"block4\": [\n        {\n            \"depth\": 0,\n            \"data\": [\n                \"00043\"\n            ],\n            \"rootBlockName\": null,\n            \"numberOfDataLines\": 1,\n            \"name\": \"20\",\n            \"empty\": false,\n            \"valid\": true,\n            \"valueAsString\": \"00043\"\n        },\n        {\n            \"depth\": 0,\n            \"data\": [\n                \"1/1\"\n            ],\n            \"rootBlockName\": null,\n            \"numberOfDataLines\": 1,\n            \"name\": \"28D\",\n            \"empty\": false,\n            \"valid\": true,\n            \"valueAsString\": \"1/1\"\n        },\n        {\n            \"depth\": 0,\n            \"data\": [\n                \"/409074-293-45/786\",\n                \"1/George Philips\",\n                \"2/High Street 1\",\n                \"3/GB/London\"\n            ],\n            \"rootBlockName\": null,\n            \"numberOfDataLines\": 4,\n            \"name\": \"50F\",\n            \"empty\": false,\n            \"valid\": true,\n            \"valueAsString\": \"/409074-293-45/786\\n1/George Philips\\n2/High Street 1\\n3/GB/London\"\n        },\n        {\n            \"depth\": 0,\n            \"data\": [\n                \"011231\"\n            ],\n            \"rootBlockName\": null,\n            \"numberOfDataLines\": 1,\n            \"name\": \"30\",\n            \"empty\": false,\n            \"valid\": true,\n            \"valueAsString\": \"011231\"\n        },\n        {\n            \"tagVec\": [\n                {\n                    \"depth\": 0,\n                    \"data\": [\n                        \"PQR-27ZZ-01\"\n                    ],\n                    \"rootBlockName\": null,\n                    \"numberOfDataLines\": 1,\n                    \"name\": \"21\",\n                    \"empty\": false,\n                    \"valid\": true,\n                    \"valueAsString\": \"PQR-27ZZ-01\"\n                },\n                {\n                    \"depth\": 0,\n                    \"data\": [\n                        \"USD2564,50\"\n                    ],\n                    \"rootBlockName\": null,\n                    \"numberOfDataLines\": 1,\n                    \"name\": \"32B\",\n                    \"empty\": false,\n                    \"valid\": true,\n                    \"valueAsString\": \"USD2564,50\"\n                },\n                {\n                    \"depth\": 0,\n                    \"data\": [\n                        \"/C/Clementine Nuggets-1842-Y\",\n                        \"MynR49R RailRoad Trust\",\n                        \"Cloudsboro ARTUI\"\n                    ],\n                    \"rootBlockName\": null,\n                    \"numberOfDataLines\": 3,\n                    \"name\": \"57D\",\n                    \"empty\": false,\n                    \"valid\": true,\n                    \"valueAsString\": \"/C/Clementine Nuggets-1842-Y\\nMynR49R RailRoad Trust\\nCloudsboro ARTUI\"\n                },\n                {\n                    \"depth\": 0,\n                    \"data\": [\n                        \"1/Beneficiary Name-1234567891234123\",\n                        \"2/QWERT\",\n                        \"3/US/Beneficiary Address Line 21\",\n                        \"3/Beneficiary Name-1234567891234123\"\n                    ],\n                    \"rootBlockName\": null,\n                    \"numberOfDataLines\": 4,\n                    \"name\": \"59F\",\n                    \"empty\": false,\n                    \"valid\": true,\n                    \"valueAsString\": \"1/Beneficiary Name-1234567891234123\\n2/QWERT\\n3/US/Beneficiary Address Line 21\\n3/Beneficiary Name-1234567891234123\"\n                },\n                {\n                    \"depth\": 0,\n                    \"data\": [\n                        \"OUR\"\n                    ],\n                    \"rootBlockName\": null,\n                    \"numberOfDataLines\": 1,\n                    \"name\": \"71A\",\n                    \"empty\": false,\n                    \"valid\": true,\n                    \"valueAsString\": \"OUR\"\n                }\n            ],\n            \"empty\": false\n        }\n    ],\n    \"block5\": [\n        {\n            \"depth\": 0,\n            \"data\": [\n                \"19DA346889CC\"\n            ],\n            \"rootBlockName\": null,\n            \"numberOfDataLines\": 1,\n            \"name\": \"CHK\",\n            \"empty\": false,\n            \"valid\": true,\n            \"valueAsString\": \"19DA346889CC\"\n        },\n        {\n            \"depth\": 0,\n            \"data\": [\n                \"00000000\"\n            ],\n            \"rootBlockName\": null,\n            \"numberOfDataLines\": 1,\n            \"name\": \"MAC\",\n            \"empty\": false,\n            \"valid\": true,\n            \"valueAsString\": \"00000000\"\n        },\n        {\n            \"depth\": 0,\n            \"data\": [\n                \"\"\n            ],\n            \"rootBlockName\": null,\n            \"numberOfDataLines\": 1,\n            \"name\": \"TNG\",\n            \"empty\": false,\n            \"valid\": true,\n            \"valueAsString\": \"\"\n        }\n    ],\n    \"messageAsVector\": null,\n    \"envelope\": false,\n    \"argOsn\": \"002843\",\n    \"ackApplid\": \"F\",\n    \"ackLTaddrBlk1\": \"COPZBEB0AXXX\",\n    \"ackOsn\": \"002843\",\n    \"ackServid\": \"01\",\n    \"ackSesno\": \"0377\",\n    \"argApplid\": \"F\",\n    \"argDelMon\": \"\",\n    \"argIndate\": \"110804\",\n    \"argInoutind\": \"O\",\n    \"argIntime\": \"1519\",\n    \"argIsn\": \"904466\",\n    \"argLTaddrBlk1\": \"COPZBEB0AXXX\",\n    \"argLTaddrBlk2\": \"LRLRXXXX4A11\",\n    \"argMsgprior\": \"N\",\n    \"argMsgtype\": \"101\",\n    \"argObsPer\": \"\",\n    \"ack108\": \"MT101 006 OF 020\",\n    \"ack177\": \"\",\n    \"ack405\": \"\",\n    \"ack451\": \"\",\n    \"paymentControlsInformation\": \"\"\n}"));
    }

    @Test
    public void givenInvalidFormatMtMessage_whenMtParse_thenReturnErrorText() throws Exception {
        //GIVEN
        String mtMessage = TestConstants.VALID_MT_101.replace("{1:", "");

        //WHEN
        mvc.perform(post("/mt/parse")
                .content(mtMessage)
                .contentType(MediaType.TEXT_PLAIN))

        //THEN
                .andExpect(status().isBadRequest())
                .andExpect(header().string(Constants.REQUEST_LOG_ID, Matchers.anything()))
                .andExpect(content().contentType(new MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_8)))
                .andExpect(content().string("Basic Header Block is missing"));

    }

    @Test
    public void givenValidMtMessage_whenMtValidate_thenReturnNoContent() throws Exception {
        //GIVEN

        //WHEN
        mvc.perform(post("/mt/validate")
                .content(TestConstants.VALID_MT_101)
                .contentType(MediaType.TEXT_PLAIN))

        //THEN
                .andExpect(status().isNoContent())
                .andExpect(header().string(Constants.REQUEST_LOG_ID, Matchers.anything()));

    }

    @Test
    public void givenInValidMtMessage_whenMtValidate_thenReturnValidationErrors() throws Exception {
        //GIVEN

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

    }

    @Test
    public void givenInValidFormatMtMessage_whenMtValidate_thenReturnErrorMessage() throws Exception {
        //GIVEN
        String mtMessage = TestConstants.VALID_MT_101.replace("{1:", "");

        //WHEN
        mvc.perform(post("/mt/validate")
                .content(mtMessage)
                .contentType(MediaType.TEXT_PLAIN))

        //THEN
                .andExpect(status().isBadRequest())
                .andExpect(header().string(Constants.REQUEST_LOG_ID, Matchers.anything()))
                .andExpect(content().contentType(new MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_8)))
                .andExpect(content().string("Basic Header Block is missing"));

    }

}