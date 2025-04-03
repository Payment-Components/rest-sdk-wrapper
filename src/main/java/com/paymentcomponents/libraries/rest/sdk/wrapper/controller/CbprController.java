package com.paymentcomponents.libraries.rest.sdk.wrapper.controller;

import com.paymentcomponents.libraries.rest.sdk.wrapper.Constants;
import com.paymentcomponents.libraries.rest.sdk.wrapper.SwaggerConstants;
import com.paymentcomponents.libraries.rest.sdk.wrapper.service.CbprService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

import static com.paymentcomponents.libraries.rest.sdk.wrapper.Constants.REQUEST_LOG_ID;
import static com.paymentcomponents.libraries.rest.sdk.wrapper.SwaggerConstants.*;
import static com.paymentcomponents.libraries.rest.sdk.wrapper.SwaggerConstants.REQUEST_LOG_ID_DESCRIPTION;

@RestController
@RequestMapping("/cbpr")
public class CbprController {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    CbprService cbprService;

    @Autowired
    CbprController(CbprService cbprService) {
        this.cbprService = cbprService;
    }

    @Operation(summary = CBPR_VALIDATE_SUMMARY,
            description = CBPR_VALIDATE_DESCRIPTION,
            tags = CBPR_TAG,
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = {
                            @Content(mediaType = "text/plain", examples = @ExampleObject(value = CBPR_VALIDATE_REQUEST_EXAMPLE_VALID))
                    }),
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = VALID_MESSAGE_DESCRIPTION,
                            headers = @Header(name = REQUEST_LOG_ID, description = REQUEST_LOG_ID_DESCRIPTION),
                            content = @Content),
                    @ApiResponse(
                            responseCode = "400",
                            description = SwaggerConstants.INVALID_MESSAGE_DESCRIPTION,
                            headers = @Header(name = Constants.REQUEST_LOG_ID, description = SwaggerConstants.REQUEST_LOG_ID_DESCRIPTION),
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = SwaggerConstants.CBPR_VALIDATE_RESPONSE_EXAMPLE_400), array = @ArraySchema(schema = @Schema(implementation = SwaggerConstants.SwaggerMxValidationErrorWrapper.class)))),
                    @ApiResponse(
                            responseCode = "500",
                            description = UNHANDLED_ERROR_DESCRIPTION,
                            headers = @Header(name = REQUEST_LOG_ID, description = REQUEST_LOG_ID_DESCRIPTION),
                            content = @Content(mediaType = "*/*"))
            }
    )
    @PostMapping(value = "/validate", consumes = "text/plain")
    public ResponseEntity<?> validateCbpr(@RequestBody String cbprMessage, HttpServletRequest req) throws Exception{
        logger.info("LogID=" + req.getAttribute(Constants.REQUEST_LOG_ID) + " cbpr message=" + cbprMessage );
        cbprService.validateCbpr(cbprMessage);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = CBPR_ENVELOPE_SUMMARY,
            description = CBPR_ENVELOPE_DESCRIPTION,
            tags = CBPR_TAG,
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = {
                            @Content(mediaType = "text/plain", examples = @ExampleObject(value = CBPR_ENVELOPE_REQUEST_EXAMPLE_VALID))
                    }),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = SwaggerConstants.VALID_MESSAGE_DESCRIPTION,
                            headers = @Header(name = Constants.REQUEST_LOG_ID, description = SwaggerConstants.REQUEST_LOG_ID_DESCRIPTION),
                            content = @Content(mediaType = "text/plain", examples = @ExampleObject(value = SwaggerConstants.CBPR_ENVELOPE_RESPONSE_EXAMPLE_200))),
                    @ApiResponse(
                            responseCode = "400",
                            description = SwaggerConstants.INVALID_MESSAGE_DESCRIPTION,
                            headers = @Header(name = Constants.REQUEST_LOG_ID, description = SwaggerConstants.REQUEST_LOG_ID_DESCRIPTION),
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = SwaggerConstants.CBPR_ENVELOPE_RESPONSE_EXAMPLE_400), array = @ArraySchema(schema = @Schema(implementation = SwaggerConstants.SwaggerMxValidationErrorWrapper.class)))),
                    @ApiResponse(
                            responseCode = "500",
                            description = UNHANDLED_ERROR_DESCRIPTION,
                            headers = @Header(name = REQUEST_LOG_ID, description = REQUEST_LOG_ID_DESCRIPTION),
                            content = @Content(mediaType = "*/*"))
            }
    )
    @PostMapping(value = "/envelope", consumes = "text/plain", produces = "text/plain")
    public String envelopeCbpr(@RequestBody String cbprMessage, HttpServletRequest req) throws Exception{
        logger.info("LogID=" + req.getAttribute(Constants.REQUEST_LOG_ID) + " cbpr message=" + cbprMessage );
        return cbprService.envelopeCbpr(cbprMessage);
    }

}
