package com.paymentcomponents.libraries.rest.sdk.wrapper.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymentcomponents.libraries.rest.sdk.wrapper.exception.InvalidMessageException;
import gr.datamation.mx.CbprMessage;
import gr.datamation.validation.error.ValidationErrorList;


public class CbprUtils {

    public static CbprMessage parseAndValidateCbprMessage(String cbprXml) throws Exception {
        CbprMessage cbprMessage = new CbprMessage();

        cbprMessage.autoParseXml(cbprXml);

        ValidationErrorList validationErrorList = cbprMessage.autoValidate();

        if (!validationErrorList.isEmpty()) {
            throw new InvalidMessageException(
                    new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(validationErrorList));
        }

        return cbprMessage;
    }

}
