package com.paymentcomponents.libraries.rest.sdk.wrapper.controller;

import com.paymentcomponents.libraries.rest.sdk.wrapper.SwaggerConstants;
import com.paymentcomponents.libraries.rest.sdk.wrapper.service.RtgsTranslatorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
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

import jakarta.servlet.http.HttpServletRequest;

import static com.paymentcomponents.libraries.rest.sdk.wrapper.Constants.REQUEST_LOG_ID;
import static com.paymentcomponents.libraries.rest.sdk.wrapper.SwaggerConstants.*;

@RestController
@RequestMapping("/translator/rtgs")
public class RtgsTranslatorController {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    RtgsTranslatorService rtgsTranslatorService;

    @Autowired
    RtgsTranslatorController(RtgsTranslatorService rtgsTranslatorService) {
        this.rtgsTranslatorService = rtgsTranslatorService;
    }

    @Operation(summary = RTGS_TRANSLATOR_MT_TO_MX_SUMMARY,
            description = RTGS_TRANSLATOR_MT_TO_MX_DESCRIPTION,
            tags = RTGS_TRANSLATOR_TAG,
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = {
                            @Content(mediaType = "text/plain", examples = @ExampleObject(value = RTGS_TRANSLATOR_MT_TO_MX_REQUEST_EXAMPLE_VALID))
                    }),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = VALID_MESSAGE_DESCRIPTION,
                            headers = @Header(name = REQUEST_LOG_ID, description = REQUEST_LOG_ID_DESCRIPTION),
                            content = @Content(mediaType = "application/xml", examples = @ExampleObject(value = RTGS_TRANSLATOR_MT_TO_MX_RESPONSE_EXAMPLE_200), schema = @Schema(implementation = SwaggerConstants.SwaggerRtgsPacs009Wrapper.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = INVALID_MESSAGE_DESCRIPTION,
                            headers = @Header(name = REQUEST_LOG_ID, description = REQUEST_LOG_ID_DESCRIPTION),
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = RTGS_TRANSLATOR_MT_TO_MX_RESPONSE_EXAMPLE_400))),
                    @ApiResponse(
                            responseCode = "500",
                            description = UNHANDLED_ERROR_DESCRIPTION,
                            headers = @Header(name = REQUEST_LOG_ID, description = REQUEST_LOG_ID_DESCRIPTION),
                            content = @Content(mediaType = "*/*"))
            }
    )
    @PostMapping(value = "/mt/to/mx", consumes = "text/plain", produces = "application/xml")
    public String translateMtToMx(@RequestBody String mtMessage, HttpServletRequest req) throws Exception {
        logger.info("LogID=" + req.getAttribute(REQUEST_LOG_ID) + " mt message=" + mtMessage);
        return rtgsTranslatorService.translateMtToMx(mtMessage);
    }

    @Operation(summary = RTGS_TRANSLATOR_MX_TO_MT_SUMMARY,
            description = RTGS_TRANSLATOR_MX_TO_MT_DESCRIPTION,
            tags = RTGS_TRANSLATOR_TAG,
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = {
                            @Content(mediaType = "application/xml", examples = @ExampleObject(value = RTGS_TRANSLATOR_MX_TO_MT_REQUEST_EXAMPLE_VALID), schema = @Schema(implementation = SwaggerConstants.SwaggerRtgsPacs009Wrapper.class))
                    }),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = VALID_MESSAGE_DESCRIPTION,
                            headers = @Header(name = REQUEST_LOG_ID, description = REQUEST_LOG_ID_DESCRIPTION),
                            content = @Content(mediaType = "text/plain", examples = @ExampleObject(value = RTGS_TRANSLATOR_MX_TO_MT_RESPONSE_EXAMPLE_200))),
                    @ApiResponse(
                            responseCode = "400",
                            description = INVALID_MESSAGE_DESCRIPTION,
                            headers = @Header(name = REQUEST_LOG_ID, description = REQUEST_LOG_ID_DESCRIPTION),
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = RTGS_TRANSLATOR_MX_TO_MT_RESPONSE_EXAMPLE_400))),
                    @ApiResponse(
                            responseCode = "500",
                            description = UNHANDLED_ERROR_DESCRIPTION,
                            headers = @Header(name = REQUEST_LOG_ID, description = REQUEST_LOG_ID_DESCRIPTION),
                            content = @Content(mediaType = "*/*"))
            }
    )
    @PostMapping(value = "/mx/to/mt", consumes = "application/xml", produces = "text/plain")
    public String translateMxToMt(@RequestBody String mtMessage, HttpServletRequest req) throws Exception {
        logger.info("LogID=" + req.getAttribute(REQUEST_LOG_ID) + " mx message=" + mtMessage);
        return rtgsTranslatorService.translateMxToMt(mtMessage);
    }

}
