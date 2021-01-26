package com.paymentcomponents.libraries.rest.sdk.wrapper.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymentcomponents.libraries.rest.sdk.wrapper.exception.InvalidMessageException;
import gr.datamation.mx.CoreMessage;
import gr.datamation.mx.MXUtils;
import gr.datamation.validation.error.ValidationErrorList;

public class MxUtils {

    public static CoreMessage parseAndValidateMxMessage(String mxMessage) throws Exception {
        CoreMessage coreMessage = (CoreMessage) MXUtils.autoParseXML(mxMessage);

        ValidationErrorList validationErrorList = coreMessage.validate();

        if (!validationErrorList.isEmpty()) {
            throw new InvalidMessageException(
                    new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(validationErrorList));
        }

        return coreMessage;
    }

}
