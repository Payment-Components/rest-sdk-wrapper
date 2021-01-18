package com.paymentcomponents.libraries.rest.sdk.wrapper.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymentcomponents.libraries.rest.sdk.wrapper.Constants;
import com.paymentcomponents.libraries.rest.sdk.wrapper.exception.InvalidMessageException;
import com.paymentcomponents.libraries.rest.sdk.wrapper.model.mt.request.*;
import gr.datamation.swift.common.InvalidMessageFormatException;
import gr.datamation.swift.common.SwiftMessage;
import gr.datamation.swift.common.Tag;
import gr.datamation.swift.processor.SwiftMsgProcessor;
import gr.datamation.swift.validator.SwiftMsgValidator;
import gr.datamation.swift.validator.SwiftValidObj;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class MtUtils {

    public static SwiftMessage parseMt(String messageText) throws InvalidMessageFormatException {
        messageText = messageText.replaceAll("\r\n", "\r");
        messageText = messageText.replaceAll("\n", "\r");
        messageText = messageText.replaceAll("\r", "\r\n");

        SwiftMsgProcessor smp = new SwiftMsgProcessor("\r\n");

        return smp.ParseMsgStringToObject(messageText);
    }

    public static void validateMt(String messageText) throws InvalidMessageFormatException, JsonProcessingException {
        SwiftMessage swiftMessage = parseMt(messageText);

        SwiftMsgValidator validator = new SwiftMsgValidator();
        SwiftValidObj swiftValidObj = validator.validateMsg(swiftMessage);
        if (swiftValidObj.hasErrors()) {
            throw new InvalidMessageException(
                    new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(swiftValidObj.getValidationErrorList()));
        }
    }

    public static SwiftMessage constructSwiftMessage(MtCreateGeneralRequest mtCreateGeneralRequest) {
        SwiftMessage swiftMessage = new SwiftMessage();
        swiftMessage.setArgApplid("F");
        swiftMessage.setArgServid("01");
        swiftMessage.setArgLTaddrBlk1(addTrailingCharacters(mtCreateGeneralRequest.getSender(), "X", 12));

        swiftMessage.setArgInoutind("I");
        swiftMessage.setArgMsgtype(mtCreateGeneralRequest.getCategory().substring(2));

        swiftMessage.setArgMsgprior("N");

        swiftMessage.setArgLTaddrBlk2(addTrailingCharacters(mtCreateGeneralRequest.getReceiver(), "X", 12));
        swiftMessage.setArgSesno("1111");
        swiftMessage.setArgOsn("111111");

        if(mtCreateGeneralRequest.getUetr() != null)
            swiftMessage.setUniqueEndToEndTrxRef(mtCreateGeneralRequest.getUetr());

        mtCreateGeneralRequest.getTags().forEach( tag ->
            swiftMessage.getBlock4().add(new Tag(tag.getName(), new Vector<String>(tag.getData())))
        );

        return swiftMessage;
    }

    public static String addTrailingCharacters(String s, String a, int length) {
        StringBuilder sBuilder = new StringBuilder(s);
        while (sBuilder.length() - length < 0) {
            sBuilder.append(a);
        }
        return sBuilder.toString();
    }

    public static List<String> createField32A(String date, String currency, BigDecimal amount) throws ParseException {
        Date requestDate = ObjectUtils.isEmpty(date) ? new Date() : new SimpleDateFormat(Constants.MT_CREATE_REQUEST_DATE_FORMAT).parse(date);
        String mtDate = convertMtCreateRequestDateToMtFormat(requestDate, Constants.MT_TAG_32_DATE_FORMAT);

        return Collections.singletonList(mtDate + constructCurrencyAmountMtField(currency, amount));
    }

    public static List<String> createFieldCustomer(Customer customer) {
        List<String> lines = new ArrayList<>();
        if(!ObjectUtils.isEmpty(customer.getAccount()))
            lines.add("/" + customer.getAccount());

        lines.add(customer.getName());
        lines.add(customer.getAddressLine1());
        lines.add(customer.getAddressLine2());
        lines.add(customer.getAddressLine3());

        return lines.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    public static List<String> createFieldInstistution(Institution institution) {
        List<String> lines = new ArrayList<>();

        if(!ObjectUtils.isEmpty(institution.getId()))
            lines.add("/" + institution.getId());

        lines.add(institution.getBic());

        return lines;
    }

    public static List<String> createFieldRemmitanceInformation(RemittanceInformation remitInfo) {
        List<String> lines = new ArrayList<>();

        lines.add(remitInfo.getLine1());
        lines.add(remitInfo.getLine2());
        lines.add(remitInfo.getLine3());
        lines.add(remitInfo.getLine4());

        return lines.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    public static List<String> createFieldSenderToReceiverInformation(SenderToReceiverInformation senderToReceiverInformation) {
        List<String> lines = new ArrayList<>();

        lines.add(senderToReceiverInformation.getLine1());
        lines.add(senderToReceiverInformation.getLine2());
        lines.add(senderToReceiverInformation.getLine3());
        lines.add(senderToReceiverInformation.getLine4());
        lines.add(senderToReceiverInformation.getLine5());
        lines.add(senderToReceiverInformation.getLine6());

        return lines.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    public static List<String> createFieldCharges(Charges charges) {
       if(charges == null)
           return Collections.EMPTY_LIST;

       return Collections.singletonList(constructCurrencyAmountMtField(charges.getCurrency(), charges.getAmount()));
    }

    public static String convertMtCreateRequestDateToMtFormat(Date requestDate, String mtFormat) {
        return new SimpleDateFormat(mtFormat).format(requestDate);
    }

    public static String constructCurrencyAmountMtField(String currency, BigDecimal amount) {
        return currency + convertBigDecimaltoMtField(amount);
    }

    public static String convertBigDecimaltoMtField(BigDecimal value) {
        return String.valueOf(value.doubleValue()).replaceAll(",", "").replaceAll("\\.", ",");
    }

}
