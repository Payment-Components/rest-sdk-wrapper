package com.paymentcomponents.libraries.rest.sdk.wrapper.utils;

import gr.datamation.mx.CbprMessage;
import gr.datamation.validation.error.ValidationErrorList;

import java.io.ByteArrayInputStream;

public class CbprUtils {

    public static CbprMessage<?, ?> parseAndValidateCbprMessage(String cbprXml) throws Exception {
        CbprMessage<?, ?> cbprMessage = new CbprMessage<>();

        ValidationErrorList validationErrorList = cbprMessage.validateXml(new ByteArrayInputStream(cbprXml.getBytes()));
        MxUtils.throwMxValidationError(validationErrorList);

        cbprMessage.autoParseXml(cbprXml);
        validationErrorList = cbprMessage.autoValidate();
        MxUtils.throwMxValidationError(validationErrorList);

        return cbprMessage;
    }

}
