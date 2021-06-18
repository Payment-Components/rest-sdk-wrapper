package com.paymentcomponents.libraries.rest.sdk.wrapper.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymentcomponents.libraries.rest.sdk.wrapper.exception.InvalidMessageException;
import com.paymentcomponents.libraries.rest.sdk.wrapper.model.sepa.request.MsgReplyInfoRequest;
import gr.datamation.mx.CoreMessage;
import gr.datamation.mx.MXUtils;
import gr.datamation.mx.Message;
import gr.datamation.mx.XSDSchema;
import gr.datamation.mx.message.pacs.FIToFICustomerCreditTransfer08;
import gr.datamation.util.Utilities;
import gr.datamation.validation.error.ValidationErrorList;
import org.springframework.util.ObjectUtils;
import xsd.pacs_008_001_08.ActiveCurrencyAndAmount;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.Collection;

public class SepaUtils {

    public static final String MESSASE_TYPE_ERROR = "Cannot retrieve message type";

    private static final String SEPA_EPC_CT_PACKAGE_NAME = "gr.datamation.iso20022.sepa.epc.ct";
    private static final String SEPA_EPC_CT_MESSAGE_SUFFIX = ".sepa.epc.ct";

    private static final Collection<Class<?>> sepaEpcCTClasses = Utilities.getAnnotatedClasses(SEPA_EPC_CT_PACKAGE_NAME, XSDSchema.class);

    public static Message parseAndValidateSepaMessage(String sepaXml) throws Exception {
        String messageNamespace = MXUtils.extractNamespaceFromXML(sepaXml);

        if (messageNamespace.split("\\.").length != 4)
            throw new InvalidMessageException(MESSASE_TYPE_ERROR);

        Class<?> messageClass = MXUtils.determineClassByNamespace(messageNamespace, sepaEpcCTClasses, SEPA_EPC_CT_MESSAGE_SUFFIX);

        Message<?, ?, ?> sepaMessage = (CoreMessage<?, ?, ?>) messageClass.getDeclaredConstructor().newInstance();

        ValidationErrorList validationErrorList = sepaMessage.validateXML(new ByteArrayInputStream(sepaXml.getBytes()));
        MxUtils.throwMxValidationError(validationErrorList);

        sepaMessage.parseXML(sepaXml);

        validationErrorList.addAll(sepaMessage.validate());
        MxUtils.throwMxValidationError(validationErrorList);

        return sepaMessage;
    }

    public static void validateSepaMessage(CoreMessage<?, ?, ?> coreMessage) throws Exception {
        ValidationErrorList validationErrorList = coreMessage.validate();
        MxUtils.throwMxValidationError(validationErrorList);
    }

    public static BigDecimal calculateTtlIntrBkSttlmAmt(FIToFICustomerCreditTransfer08 fiToFICustomerCreditTransfer) {
        return fiToFICustomerCreditTransfer.getMessage().getCdtTrfTxInf().stream()
                .map(it -> it.getIntrBkSttlmAmt().getValue())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static void addIntrBkSttlmAmt(CoreMessage coreMessage, String elementPath, BigDecimal value) throws Exception {
        if (ObjectUtils.isEmpty(value))
            return;

        ActiveCurrencyAndAmount intrBkSttlmAmt = new ActiveCurrencyAndAmount();
        intrBkSttlmAmt.setCcy("EUR");
        intrBkSttlmAmt.setValue(value);

        coreMessage.setElement(elementPath, intrBkSttlmAmt);
    }

    public static void addElement(CoreMessage coreMessage, String elementPath, Object element) throws Exception {
        if (ObjectUtils.isEmpty(element))
            return;

        coreMessage.setElement(elementPath, element);
    }

    public static void validateMsgReplyInfoRequest(MsgReplyInfoRequest msgReplyInfoRequest) throws JsonProcessingException {
        boolean rsnCdExists = !ObjectUtils.isEmpty(msgReplyInfoRequest.getReasonCode());
        boolean rsnPrtryExists = !ObjectUtils.isEmpty(msgReplyInfoRequest.getReasonPrtry());

        if (rsnCdExists && rsnPrtryExists)
            throw new InvalidMessageException(
                    new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString("The reason is CD or PRTRY based"));

        if (!rsnCdExists && !rsnPrtryExists)
            throw new InvalidMessageException(
                    new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString("Either CD or PRTRY is required"));

    }

}