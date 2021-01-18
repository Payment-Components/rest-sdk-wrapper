package com.paymentcomponents.libraries.rest.sdk.wrapper.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymentcomponents.libraries.rest.sdk.wrapper.exception.InvalidMessageException;
import com.paymentcomponents.libraries.rest.sdk.wrapper.model.sepa.request.MsgReplyInfoRequest;
import gr.datamation.sepa.core.messages.CoreMessage;
import gr.datamation.sepa.core.messages.common.MessageInfo;
import gr.datamation.sepa.core.messages.epc.pacs.FIToFICustomerCreditTransfer;
import gr.datamation.sepa.types.epc.pacs008.ActiveCurrencyAndAmount;
import org.reflections.Reflections;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.*;

public class SepaUtils {

    public static final String MESSASE_TYPE_ERROR = "Cannot retrieve message type";
    public static final String CLASS_NOT_FOUND_ERROR = "Class not found";
    private static final HashMap<String, String> SEPA_MESSAGE_TYPES = new HashMap<String, String>();
    static {
        Reflections reflections = new Reflections("gr.datamation.sepa.core.messages");

        Set<Class<? extends CoreMessage>> allClasses =
                reflections.getSubTypesOf(CoreMessage.class);
        for (Class clazz : allClasses) {
            if (clazz.getName().contains(".epc.") && clazz.getAnnotation(MessageInfo.class) != null) {
                MessageInfo messageInfo = (MessageInfo) clazz.getAnnotation(MessageInfo.class);
                messageInfo.xsd();
                SEPA_MESSAGE_TYPES.put(messageInfo.xsd()
                                .replaceAll("^.*([a-z]{4}\\.[0-9]{3}\\.[0-9]{3}\\.[0-9]{2})\\.xsd.*", "$1"),
                        clazz.getName());
            }
        }
    }

    public static CoreMessage validateSepaMessage(String messageText) throws JsonProcessingException, InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException, ClassNotFoundException {
        CoreMessage sepaMessage = SepaUtils.retrieveSepaMessageTypeText(messageText);
        sepaMessage.parseAndValidateString(messageText);

        if (sepaMessage.hasValidationErrors()) {
            throw new InvalidMessageException(
                    new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(sepaMessage.getErrors()));
        }

        return sepaMessage;
    }

    public static CoreMessage validateSepaMessage(CoreMessage sepaMessage) throws JsonProcessingException {
        sepaMessage.validate();

        if (sepaMessage.hasValidationErrors()) {
            throw new InvalidMessageException(
                    new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(sepaMessage.getErrors()));
        }

        return sepaMessage;
    }

    public static CoreMessage retrieveSepaMessageTypeText(String messageText) throws JsonProcessingException, ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        String messageType = messageText.replaceAll("(?s).*<Document.*([a-z]{4}\\.[0-9]{3}\\.[0-9]{3}\\.[0-9]{2})\".*>.*</Document>.*", "$1");
        if (messageType.split("\\.").length != 4) {
            throw new InvalidMessageException(
                    new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(Collections.singletonList(MESSASE_TYPE_ERROR)));
        }

        String classFullName;
        if (SEPA_MESSAGE_TYPES.containsKey(messageType)) {
            classFullName = SEPA_MESSAGE_TYPES.get(messageType);
        } else {
            throw new ClassNotFoundException(CLASS_NOT_FOUND_ERROR);
        }

        return  (CoreMessage) Class.forName(classFullName).getDeclaredConstructor().newInstance();
    }

    public static BigDecimal calculateTtlIntrBkSttlmAmt(FIToFICustomerCreditTransfer fiToFICustomerCreditTransfer) {
        return fiToFICustomerCreditTransfer.getRootMessage().getCdtTrfTxInf().stream()
                .map(it -> it.getIntrBkSttlmAmt().getValue())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static void addIntrBkSttlmAmt(CoreMessage coreMessage, String elementPath, BigDecimal value) throws Exception {
        if(ObjectUtils.isEmpty(value))
            return;

        ActiveCurrencyAndAmount intrBkSttlmAmt = new ActiveCurrencyAndAmount();
        intrBkSttlmAmt.setCcy("EUR");
        intrBkSttlmAmt.setValue(value);

        coreMessage.setElement(elementPath, intrBkSttlmAmt);
    }

    public static void addElement(CoreMessage coreMessage, String elementPath, Object element) throws Exception {
        if(ObjectUtils.isEmpty(element))
            return;

        coreMessage.setElement(elementPath, element);
    }

    public static void validateMsgReplyInfoRequest(MsgReplyInfoRequest msgReplyInfoRequest) throws JsonProcessingException {
        boolean rsnCdExists = !ObjectUtils.isEmpty(msgReplyInfoRequest.getReasonCode());
        boolean rsnPrtryExists = !ObjectUtils.isEmpty(msgReplyInfoRequest.getReasonPrtry());

        if(rsnCdExists && rsnPrtryExists)
            throw new InvalidMessageException(
                    new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString("The reason is CD or PRTRY based"));

        if(!rsnCdExists && !rsnPrtryExists)
            throw new InvalidMessageException(
                    new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString("Either CD or PRTRY is required"));

    }

}
