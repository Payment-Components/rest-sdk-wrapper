package com.paymentcomponents.libraries.rest.sdk.wrapper.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymentcomponents.libraries.rest.sdk.wrapper.exception.InvalidMessageException;
import gr.datamation.mx.CbprMessage;
import gr.datamation.mx.MXUtils;
import gr.datamation.mx.message.head.BusinessApplicationHeader02;
import gr.datamation.validation.error.ValidationErrorList;

import javax.xml.bind.JAXBException;
import java.io.UnsupportedEncodingException;

public class CbprUtils {

    public static CbprMessage parseAndValidateCbprMessage(String cbprXml) throws Exception {
        CbprMessage cbprMessage = new CbprMessage();

        cbprMessage.autoParseXml(cbprXml);

        ValidationErrorList validationErrorList = cbprMessage.validate(extractCbprMsgType(cbprMessage));

        if (!validationErrorList.isEmpty()) {
            throw new InvalidMessageException(
                    new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(validationErrorList));
        }

        return cbprMessage;
    }

    private static CbprMessage.CbprMsgType extractCbprMsgType(CbprMessage cbprMessage) throws JAXBException, UnsupportedEncodingException, JsonProcessingException {
        String BUSINESS_SERVICE_STP = "swift.cbprplus.stp.01";
        String BUSINESS_SERVICE_COV = "swift.cbprplus.cov.01";
        String documentNamespace = MXUtils.extractNamespaceFromXML(cbprMessage.getDocument().convertToXML());

        switch (documentNamespace) {
            case "pacs.008.001.08":
                if(((BusinessApplicationHeader02)cbprMessage.getAppHdr()).getMessage().getBizSvc().equals(BUSINESS_SERVICE_STP))
                    return CbprMessage.CbprMsgType.PACS_008_STP_EU;
                else
                    return CbprMessage.CbprMsgType.PACS_008;
            case "pacs.009.001.08":
                if(((BusinessApplicationHeader02)cbprMessage.getAppHdr()).getMessage().getBizSvc().equals(BUSINESS_SERVICE_COV))
                    return CbprMessage.CbprMsgType.PACS_009_COV;
                else
                    return CbprMessage.CbprMsgType.PACS_009_CORE;
            case "pacs.004.001.09":
                return CbprMessage.CbprMsgType.PACS_004;
            case "camt.052.001.08":
                return CbprMessage.CbprMsgType.CAMT_052;
            case "camt.053.001.08":
                return CbprMessage.CbprMsgType.CAMT_053;
            case "camt.054.001.08":
                return CbprMessage.CbprMsgType.CAMT_054;
            case "pacs.002.001.10":
                return CbprMessage.CbprMsgType.PACS_002;
            case "pacs.010.001.03":
                return CbprMessage.CbprMsgType.PACS_010;
            case "camt.057.001.06":
                return CbprMessage.CbprMsgType.CAMT_057;
            case "camt.060.001.05":
                return CbprMessage.CbprMsgType.CAMT_060;
            default:
                throw new InvalidMessageException(
                        new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString("Message is not available in CBPR+"));
        }

    }

}
