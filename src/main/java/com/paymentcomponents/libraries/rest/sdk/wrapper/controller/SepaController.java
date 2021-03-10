package com.paymentcomponents.libraries.rest.sdk.wrapper.controller;

import com.paymentcomponents.libraries.rest.sdk.wrapper.model.sepa.request.MsgReplyInfoRequest;
import com.paymentcomponents.libraries.rest.sdk.wrapper.model.sepa.request.SepaCreatePacs008Request;
import com.paymentcomponents.libraries.rest.sdk.wrapper.service.SepaService;
import com.paymentcomponents.libraries.rest.sdk.wrapper.Constants;
import com.paymentcomponents.libraries.rest.sdk.wrapper.SwaggerConstants;
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
import org.springframework.web.bind.annotation.*;

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
                    required = true),
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
}
