package com.paymentcomponents.libraries.rest.sdk.wrapper.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymentcomponents.libraries.rest.sdk.wrapper.exception.InvalidMessageException;
import gr.datamation.mx.CoreMessage;
import gr.datamation.mx.MXUtils;
import gr.datamation.validation.error.ValidationErrorList;

import java.io.ByteArrayInputStream;

public class MxUtils {

    public static CoreMessage parseAndValidateMxMessage(String mxMessage) throws Exception {
        ValidationErrorList validationErrorList = MXUtils.autoValidateXML(new ByteArrayInputStream(mxMessage.getBytes()));
        throwMxValidationError(validationErrorList);

        CoreMessage coreMessage = (CoreMessage) MXUtils.autoParseXML(mxMessage);
        validationErrorList = coreMessage.validate();
        throwMxValidationError(validationErrorList);

        return coreMessage;
    }

    public static void throwMxValidationError(ValidationErrorList validationErrorList) throws JsonProcessingException, InvalidMessageException {
        if (!validationErrorList.isEmpty()) {
            throw new InvalidMessageException(
                    new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(validationErrorList));
        }
    }

}
