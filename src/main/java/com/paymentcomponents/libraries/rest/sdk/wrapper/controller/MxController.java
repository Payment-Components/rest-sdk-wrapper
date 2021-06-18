package com.paymentcomponents.libraries.rest.sdk.wrapper.controller;

import com.paymentcomponents.libraries.rest.sdk.wrapper.Constants;
import com.paymentcomponents.libraries.rest.sdk.wrapper.SwaggerConstants;
import com.paymentcomponents.libraries.rest.sdk.wrapper.service.MxService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/mx")
public class MxController {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    MxService mxService;

    @Autowired
    MxController(MxService mxService) {
        this.mxService = mxService;
    }

    @Operation(summary = SwaggerConstants.MX_VALIDATE_SUMMARY,
            description = SwaggerConstants.MX_VALIDATE_DESCRIPTION,
            tags = SwaggerConstants.MX_TAG,
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = {
                            @Content(mediaType = "application/xml", examples = @ExampleObject(value = SwaggerConstants.MX_VALIDATE_REQUEST_EXAMPLE_VALID), schema = @Schema(implementation = SwaggerConstants.SwaggerMxPain001Wrapper.class))
                    }),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = SwaggerConstants.VALID_MESSAGE_DESCRIPTION,
                            headers = @Header(name = Constants.REQUEST_LOG_ID, description = SwaggerConstants.REQUEST_LOG_ID_DESCRIPTION),
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = SwaggerConstants.MX_VALIDATE_RESPONSE_EXAMPLE_200))),
                    @ApiResponse(
                            responseCode = "400",
                            description = SwaggerConstants.INVALID_MESSAGE_DESCRIPTION,
                            headers = @Header(name = Constants.REQUEST_LOG_ID, description = SwaggerConstants.REQUEST_LOG_ID_DESCRIPTION),
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = SwaggerConstants.MX_VALIDATE_RESPONSE_EXAMPLE_400), array = @ArraySchema(schema = @Schema(implementation = SwaggerConstants.SwaggerMxValidationErrorWrapper.class)))),
                    @ApiResponse(
                            responseCode = "500",
                            description = SwaggerConstants.UNHANDLED_ERROR_DESCRIPTION,
                            headers = @Header(name = Constants.REQUEST_LOG_ID, description = SwaggerConstants.REQUEST_LOG_ID_DESCRIPTION),
                            content = @Content(mediaType = "*/*"))
            }
    )
    @PostMapping(value = "/validate", consumes = "application/xml", produces = "application/json")
    public String validateMx(@RequestBody String mxMessage, HttpServletRequest req) throws Exception{
        logger.info("LogID=" + req.getAttribute(Constants.REQUEST_LOG_ID) + " mx message=" + mxMessage );
        return mxService.validateMx(mxMessage);
    }

}
