package com.paymentcomponents.libraries.rest.sdk.wrapper.controller;

import com.paymentcomponents.libraries.rest.sdk.wrapper.Constants;
import com.paymentcomponents.libraries.rest.sdk.wrapper.SwaggerConstants;
import com.paymentcomponents.libraries.rest.sdk.wrapper.model.sepa.request.MsgReplyInfoRequest;
import com.paymentcomponents.libraries.rest.sdk.wrapper.model.sepa.request.SepaCreatePacs008Request;
import com.paymentcomponents.libraries.rest.sdk.wrapper.service.SepaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/sepa")
public class SepaController {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    SepaService sepaService;

    @Autowired
    SepaController(SepaService sepaService) {
        this.sepaService = sepaService;
    }

    @Operation(summary = SwaggerConstants.SEPA_VALIDATE_SUMMARY,
            description = SwaggerConstants.SEPA_VALIDATE_DESCRIPTION,
            tags = SwaggerConstants.SEPA_TAG,
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = {
                            @Content(mediaType = "application/xml", examples = @ExampleObject(value = SwaggerConstants.SEPA_VALIDATE_REQUEST_EXAMPLE_VALID), schema = @Schema(implementation = SwaggerConstants.SwaggerSepaPacs002Wrapper.class))
                    }),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = SwaggerConstants.VALID_MESSAGE_DESCRIPTION,
                            headers = @Header(name = Constants.REQUEST_LOG_ID, description = SwaggerConstants.REQUEST_LOG_ID_DESCRIPTION),
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = SwaggerConstants.SEPA_VALIDATE_RESPONSE_EXAMPLE_200))),
                    @ApiResponse(
                            responseCode = "400",
                            description = SwaggerConstants.INVALID_MESSAGE_DESCRIPTION,
                            headers = @Header(name = Constants.REQUEST_LOG_ID, description = SwaggerConstants.REQUEST_LOG_ID_DESCRIPTION),
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = SwaggerConstants.SEPA_VALIDATE_RESPONSE_EXAMPLE_400), array = @ArraySchema(schema = @Schema(implementation = String.class)))),
                    @ApiResponse(
                            responseCode = "500",
                            description = SwaggerConstants.UNHANDLED_ERROR_DESCRIPTION,
                            headers = @Header(name = Constants.REQUEST_LOG_ID, description = SwaggerConstants.REQUEST_LOG_ID_DESCRIPTION),
                            content = @Content(mediaType = "*/*"))
            }
    )
    @PostMapping(value = "/validate", consumes = "application/xml", produces = "application/json")
    public String validateSepa(@RequestBody String sepaMessage, HttpServletRequest req) throws Exception {
        logger.info("LogID=" + req.getAttribute(Constants.REQUEST_LOG_ID) + " sepa message=" + sepaMessage);
        return sepaService.validateSepaMessage(sepaMessage);
    }

    @Operation(summary = SwaggerConstants.SEPA_CREATE_PACS08_SUMMARY,
            description = SwaggerConstants.SEPA_CREATE_PACS08_DESCRIPTION,
            tags = SwaggerConstants.SEPA_TAG,
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = SwaggerConstants.VALID_MESSAGE_DESCRIPTION,
                            headers = @Header(name = Constants.REQUEST_LOG_ID, description = SwaggerConstants.REQUEST_LOG_ID_DESCRIPTION),
                            content = @Content(mediaType = "application/xml", schema = @Schema(implementation = SwaggerConstants.SwaggerSepaPacs008Wrapper.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = SwaggerConstants.INVALID_MESSAGE_DESCRIPTION,
                            headers = @Header(name = Constants.REQUEST_LOG_ID, description = SwaggerConstants.REQUEST_LOG_ID_DESCRIPTION),
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = SwaggerConstants.SEPA_CREATE_PACS08_RESPONSE_EXAMPLE_400), array = @ArraySchema(schema = @Schema(implementation = String.class)))),
                    @ApiResponse(
                            responseCode = "500",
                            description = SwaggerConstants.UNHANDLED_ERROR_DESCRIPTION,
                            headers = @Header(name = Constants.REQUEST_LOG_ID, description = SwaggerConstants.REQUEST_LOG_ID_DESCRIPTION),
                            content = @Content(mediaType = "*/*"))
            }
    )
    @PostMapping(value = "/create/pacs008", consumes = "application/json", produces = "application/xml")
    public String createSepaPacs008(@RequestBody SepaCreatePacs008Request sepaCreatePacs008Request, HttpServletRequest req) throws Exception {
        logger.info("LogID=" + req.getAttribute(Constants.REQUEST_LOG_ID) + " sepa pacs008 request=" + sepaCreatePacs008Request);
        return sepaService.createPacs008(sepaCreatePacs008Request);
    }

    @Operation(summary = SwaggerConstants.SEPA_PAYMENT_RETURN_SUMMARY,
            description = SwaggerConstants.SEPA_PAYMENT_RETURN_DESCRIPTION,
            tags = SwaggerConstants.SEPA_TAG,
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = {
                            @Content(mediaType = "application/xml", examples = @ExampleObject(value = SwaggerConstants.SEPA_PAYMENT_RETURN_EXAMPLE_VALID), schema = @Schema(implementation = SwaggerConstants.SwaggerSepaPacs008Wrapper.class))
                    }),
            parameters = {
                    @Parameter(in = ParameterIn.QUERY, name = "reasonCode", description = SwaggerConstants.REASON_CD_PRTRY_PRESENCE, content = @Content(examples = @ExampleObject(value = "AC04"))),
                    @Parameter(in = ParameterIn.QUERY, name = "reasonPrtry", description = SwaggerConstants.REASON_CD_PRTRY_PRESENCE, content = @Content(examples = @ExampleObject(value = "Not trusted"))),
                    @Parameter(in = ParameterIn.QUERY, name = "indicator", description = SwaggerConstants.REASON_INDICATOR, content = @Content(examples = @ExampleObject(value = "1234"))),
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = SwaggerConstants.VALID_MESSAGE_DESCRIPTION,
                            headers = @Header(name = Constants.REQUEST_LOG_ID, description = SwaggerConstants.REQUEST_LOG_ID_DESCRIPTION),
                            content = @Content(mediaType = "application/xml", schema = @Schema(implementation = SwaggerConstants.SwaggerSepaPacs004Wrapper.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = SwaggerConstants.INVALID_MESSAGE_DESCRIPTION,
                            headers = @Header(name = Constants.REQUEST_LOG_ID, description = SwaggerConstants.REQUEST_LOG_ID_DESCRIPTION),
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = SwaggerConstants.SEPA_PAYMENT_RETURN_RESPONSE_EXAMPLE_400), array = @ArraySchema(schema = @Schema(implementation = String.class)))),
                    @ApiResponse(
                            responseCode = "500",
                            description = SwaggerConstants.UNHANDLED_ERROR_DESCRIPTION,
                            headers = @Header(name = Constants.REQUEST_LOG_ID, description = SwaggerConstants.REQUEST_LOG_ID_DESCRIPTION),
                            content = @Content(mediaType = "*/*"))
            }
    )
    @PostMapping(value = "/payment/return", consumes = "application/xml", produces = "application/xml")
    public String generatePaymentReturn(@Parameter(hidden = true) MsgReplyInfoRequest msgReplyInfoRequest,
                                        @RequestBody String pacs008Message,
                                        HttpServletRequest req) throws Exception {
        logger.info("LogID=" + req.getAttribute(Constants.REQUEST_LOG_ID) + "pacs008 message=" + pacs008Message);
        return sepaService.generatePaymentReturn(pacs008Message, msgReplyInfoRequest);
    }

    @Operation(summary = SwaggerConstants.SEPA_CANCEL_REQUEST_SUMMARY,
            description = SwaggerConstants.SEPA_CANCEL_REQUEST_DESCRIPTION,
            tags = SwaggerConstants.SEPA_TAG,
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = {
                            @Content(mediaType = "application/xml", examples = @ExampleObject(value = SwaggerConstants.SEPA_CANCEL_REQUEST_EXAMPLE_VALID), schema = @Schema(implementation = SwaggerConstants.SwaggerSepaPacs008Wrapper.class))
                    }),
            parameters = {
                    @Parameter(in = ParameterIn.QUERY, name = "reasonCode", description = SwaggerConstants.REASON_CD_PRTRY_PRESENCE, content = @Content(examples = @ExampleObject(value = "DUPL"))),
                    @Parameter(in = ParameterIn.QUERY, name = "reasonPrtry", description = SwaggerConstants.REASON_CD_PRTRY_PRESENCE, content = @Content(examples = @ExampleObject(value = "Not trusted"))),
                    @Parameter(in = ParameterIn.QUERY, name = "indicator", description = SwaggerConstants.REASON_INDICATOR, content = @Content(examples = @ExampleObject(value = "1234"))),
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = SwaggerConstants.VALID_MESSAGE_DESCRIPTION,
                            headers = @Header(name = Constants.REQUEST_LOG_ID, description = SwaggerConstants.REQUEST_LOG_ID_DESCRIPTION),
                            content = @Content(mediaType = "application/xml", schema = @Schema(implementation = SwaggerConstants.SwaggerSepaCamt056Wrapper.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = SwaggerConstants.INVALID_MESSAGE_DESCRIPTION,
                            headers = @Header(name = Constants.REQUEST_LOG_ID, description = SwaggerConstants.REQUEST_LOG_ID_DESCRIPTION),
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = SwaggerConstants.SEPA_CANCEL_REQUEST_RESPONSE_EXAMPLE_400), array = @ArraySchema(schema = @Schema(implementation = String.class)))),
                    @ApiResponse(
                            responseCode = "500",
                            description = SwaggerConstants.UNHANDLED_ERROR_DESCRIPTION,
                            headers = @Header(name = Constants.REQUEST_LOG_ID, description = SwaggerConstants.REQUEST_LOG_ID_DESCRIPTION),
                            content = @Content(mediaType = "*/*"))
            }
    )
    @PostMapping(value = "/cancellation/request", consumes = "application/xml", produces = "application/xml")
    public String generateRecallReturn(@Parameter(hidden = true) MsgReplyInfoRequest msgReplyInfoRequest,
                                       @RequestBody String pacs008Message,
                                       HttpServletRequest req) throws Exception {
        logger.info("LogID=" + req.getAttribute(Constants.REQUEST_LOG_ID) + "pacs008 message=" + pacs008Message);
        return sepaService.generateCancellationRequest(pacs008Message, msgReplyInfoRequest);
    }

    @Operation(summary = SwaggerConstants.SEPA_RES_OF_INVEST_SUMMARY,
            description = SwaggerConstants.SEPA_RES_OF_INVEST_DESCRIPTION,
            tags = SwaggerConstants.SEPA_TAG,
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = {
                            @Content(mediaType = "application/xml", examples = @ExampleObject(value = SwaggerConstants.SEPA_RES_OF_INVEST_EXAMPLE_VALID), schema = @Schema(implementation = SwaggerConstants.SwaggerSepaPacs008Wrapper.class))
                    }),
            parameters = {
                    @Parameter(in = ParameterIn.QUERY, name = "reasonCode", description = SwaggerConstants.REASON_CD_PRTRY_PRESENCE, content = @Content(examples = @ExampleObject(value = "DUPL"))),
                    @Parameter(in = ParameterIn.QUERY, name = "reasonPrtry", description = SwaggerConstants.REASON_CD_PRTRY_PRESENCE, content = @Content(examples = @ExampleObject(value = "Not trusted"))),
                    @Parameter(in = ParameterIn.QUERY, name = "indicator", description = SwaggerConstants.REASON_INDICATOR, content = @Content(examples = @ExampleObject(value = "1234"))),
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = SwaggerConstants.VALID_MESSAGE_DESCRIPTION,
                            headers = @Header(name = Constants.REQUEST_LOG_ID, description = SwaggerConstants.REQUEST_LOG_ID_DESCRIPTION),
                            content = @Content(mediaType = "application/xml", schema = @Schema(implementation = SwaggerConstants.SwaggerSepaCamt029Wrapper.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = SwaggerConstants.INVALID_MESSAGE_DESCRIPTION,
                            headers = @Header(name = Constants.REQUEST_LOG_ID, description = SwaggerConstants.REQUEST_LOG_ID_DESCRIPTION),
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = SwaggerConstants.SEPA_RES_OF_INVEST_RESPONSE_EXAMPLE_400), array = @ArraySchema(schema = @Schema(implementation = String.class)))),
                    @ApiResponse(
                            responseCode = "500",
                            description = SwaggerConstants.UNHANDLED_ERROR_DESCRIPTION,
                            headers = @Header(name = Constants.REQUEST_LOG_ID, description = SwaggerConstants.REQUEST_LOG_ID_DESCRIPTION),
                            content = @Content(mediaType = "*/*"))
            }
    )
    @PostMapping(value = "/resolution/of/investigation", consumes = "application/xml", produces = "application/xml")
    public String generateResolutionOfInvestigation(@Parameter(hidden = true) MsgReplyInfoRequest msgReplyInfoRequest,
                                                    @RequestBody String pacs008Message,
                                                    HttpServletRequest req) throws Exception {
        logger.info("LogID=" + req.getAttribute(Constants.REQUEST_LOG_ID) + "pacs008 message=" + pacs008Message);
        return sepaService.generateResolutionOfInvestigation(pacs008Message, msgReplyInfoRequest);
    }

}
