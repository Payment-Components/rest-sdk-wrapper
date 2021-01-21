package com.paymentcomponents.libraries.rest.sdk.wrapper.controller;

import com.paymentcomponents.libraries.rest.sdk.wrapper.service.SwiftTranslatorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static com.paymentcomponents.libraries.rest.sdk.wrapper.Constants.REQUEST_LOG_ID;
import static com.paymentcomponents.libraries.rest.sdk.wrapper.SwaggerConstants.*;


@RestController
@RequestMapping("/swift/translator")
public class SwiftTranslatorController {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    SwiftTranslatorService swiftTranslatorService;

    @Autowired
    SwiftTranslatorController(SwiftTranslatorService swiftTranslatorService) {
        this.swiftTranslatorService = swiftTranslatorService;
    }

    @Operation(summary = SWIFT_TRANSLATOR_MT_TO_MX_SUMMARY,
            description = SWIFT_TRANSLATOR_MT_TO_MX_DESCRIPTION,
            tags = SWIFT_TRANSLATOR_TAG,
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = {
                            @Content(mediaType = "text/plain", examples = @ExampleObject(value = SWIFT_TRANSLATOR_MT_TO_MX_REQUEST_EXAMPLE_VALID))
                    }),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = VALID_MESSAGE_DESCRIPTION,
                            headers = @Header(name = REQUEST_LOG_ID, description = REQUEST_LOG_ID_DESCRIPTION),
                            content = @Content(mediaType = "text/plain", examples = @ExampleObject(value = SWIFT_TRANSLATOR_MT_TO_MX_RESPONSE_EXAMPLE_200))),
                    @ApiResponse(
                            responseCode = "400",
                            description = INVALID_MESSAGE_DESCRIPTION,
                            headers = @Header(name = REQUEST_LOG_ID, description = REQUEST_LOG_ID_DESCRIPTION),
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = SWIFT_TRANSLATOR_MT_TO_MX_RESPONSE_EXAMPLE_400))),
                    @ApiResponse(
                            responseCode = "500",
                            description = UNHANDLED_ERROR_DESCRIPTION,
                            headers = @Header(name = REQUEST_LOG_ID, description = REQUEST_LOG_ID_DESCRIPTION),
                            content = @Content(mediaType = "*/*"))
            }
    )
    @PostMapping(value = "/mt/to/mx", consumes = "text/plain", produces = "text/plain")
    public String translateMtToMx(@RequestBody String mtMessage, HttpServletRequest req) throws Exception {
        logger.info("LogID=" + req.getAttribute(REQUEST_LOG_ID) + " mt message=" + mtMessage);
        return swiftTranslatorService.translateMtToMx(mtMessage);
    }

    @Operation(summary = SWIFT_TRANSLATOR_MX_TO_MT_SUMMARY,
            description = SWIFT_TRANSLATOR_MX_TO_MT_DESCRIPTION,
            tags = SWIFT_TRANSLATOR_TAG,
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = {
                            @Content(mediaType = "text/plain", examples = @ExampleObject(value = SWIFT_TRANSLATOR_MX_TO_MT_REQUEST_EXAMPLE_VALID))
                    }),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = VALID_MESSAGE_DESCRIPTION,
                            headers = @Header(name = REQUEST_LOG_ID, description = REQUEST_LOG_ID_DESCRIPTION),
                            content = @Content(mediaType = "text/plain", examples = @ExampleObject(value = SWIFT_TRANSLATOR_MX_TO_MT_RESPONSE_EXAMPLE_200))),
                    @ApiResponse(
                            responseCode = "400",
                            description = INVALID_MESSAGE_DESCRIPTION,
                            headers = @Header(name = REQUEST_LOG_ID, description = REQUEST_LOG_ID_DESCRIPTION),
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = SWIFT_TRANSLATOR_MX_TO_MT_RESPONSE_EXAMPLE_400))),
                    @ApiResponse(
                            responseCode = "500",
                            description = UNHANDLED_ERROR_DESCRIPTION,
                            headers = @Header(name = REQUEST_LOG_ID, description = REQUEST_LOG_ID_DESCRIPTION),
                            content = @Content(mediaType = "*/*"))
            }
    )
    @PostMapping(value = "/mx/to/mt", consumes = "text/plain", produces = "text/plain")
    public String translateMxToMt(@RequestBody String mtMessage, HttpServletRequest req) throws Exception {
        logger.info("LogID=" + req.getAttribute(REQUEST_LOG_ID) + " mt message=" + mtMessage);
        return swiftTranslatorService.translateMxToMt(mtMessage);
    }

}
