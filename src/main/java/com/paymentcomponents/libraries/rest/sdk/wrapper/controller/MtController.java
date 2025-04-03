package com.paymentcomponents.libraries.rest.sdk.wrapper.controller;

import com.paymentcomponents.libraries.rest.sdk.wrapper.SwaggerConstants;
import com.paymentcomponents.libraries.rest.sdk.wrapper.model.CustomSwiftMessage;
import com.paymentcomponents.libraries.rest.sdk.wrapper.service.MtService;
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

@RestController
@RequestMapping("/mt")
public class MtController {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    MtService mtService;

    @Autowired
    MtController(MtService mtService) {
        this.mtService = mtService;
    }

    @Operation(summary = MT_PARSE_SUMMARY,
            description = MT_PARSE_DESCRIPTION,
            tags = MT_TAG,
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = {
                            @Content(mediaType = "text/plain", examples = @ExampleObject(value = MT_PARSE_REQUEST_EXAMPLE_VALID))
                    }),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = VALID_MESSAGE_DESCRIPTION,
                            headers = @Header(name = REQUEST_LOG_ID, description = REQUEST_LOG_ID_DESCRIPTION),
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = MT_PARSE_RESPONSE_EXAMPLE_200), schema = @Schema(implementation = CustomSwiftMessage.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = INVALID_MESSAGE_DESCRIPTION,
                            headers = @Header(name = REQUEST_LOG_ID, description = REQUEST_LOG_ID_DESCRIPTION),
                            content = @Content(mediaType = "text/plain", examples = @ExampleObject(value = MT_PARSE_RESPONSE_EXAMPLE_400))),
                    @ApiResponse(
                            responseCode = "500",
                            description = UNHANDLED_ERROR_DESCRIPTION,
                            headers = @Header(name = REQUEST_LOG_ID, description = REQUEST_LOG_ID_DESCRIPTION),
                            content = @Content(mediaType = "*/*"))
            }
    )
    @PostMapping(value = "/parse", consumes = "text/plain", produces = "application/json")
    public CustomSwiftMessage parseMt(@RequestBody String mtMessage, HttpServletRequest req) throws Exception {
        logger.info("LogID=" + req.getAttribute(REQUEST_LOG_ID) + " mt message=" + mtMessage);
        return mtService.parseMt(mtMessage);
    }

    @Operation(summary = MT_VALIDATE_SUMMARY,
            description = MT_VALIDATE_DESCRIPTION,
            tags = MT_TAG,
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = {
                            @Content(mediaType = "text/plain", examples = @ExampleObject(value = MT_VALIDATE_REQUEST_EXAMPLE_VALID))
                    }),
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = VALID_MESSAGE_DESCRIPTION,
                            headers = @Header(name = REQUEST_LOG_ID, description = REQUEST_LOG_ID_DESCRIPTION),
                            content = @Content),
                    @ApiResponse(
                            responseCode = "400",
                            description = INVALID_MESSAGE_DESCRIPTION,
                            headers = @Header(name = REQUEST_LOG_ID, description = REQUEST_LOG_ID_DESCRIPTION),
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = MT_VALIDATE_RESPONSE_EXAMPLE_400), array = @ArraySchema(schema = @Schema(implementation = SwaggerConstants.SwaggerMtValidationErrorWrapper.class)))),
                    @ApiResponse(
                            responseCode = "500",
                            description = UNHANDLED_ERROR_DESCRIPTION,
                            headers = @Header(name = REQUEST_LOG_ID, description = REQUEST_LOG_ID_DESCRIPTION),
                            content = @Content(mediaType = "*/*"))
            }
    )
    @PostMapping(value = "/validate", consumes = "text/plain")
    public ResponseEntity<?> validateMt(@RequestBody String mtMessage, HttpServletRequest req) throws Exception {
        logger.info("LogID=" + req.getAttribute(REQUEST_LOG_ID) + " mt message=" + mtMessage);
        mtService.validateMt(mtMessage);
        return ResponseEntity.noContent().build();
    }

}
