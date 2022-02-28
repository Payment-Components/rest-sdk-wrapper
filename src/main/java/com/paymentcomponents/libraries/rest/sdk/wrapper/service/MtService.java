package com.paymentcomponents.libraries.rest.sdk.wrapper.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymentcomponents.libraries.rest.sdk.wrapper.exception.InvalidMessageException;
import com.paymentcomponents.libraries.rest.sdk.wrapper.model.CustomSwiftMessage;
import com.paymentcomponents.libraries.rest.sdk.wrapper.model.mt.request.MtCreate103Request;
import com.paymentcomponents.libraries.rest.sdk.wrapper.model.mt.request.MtCreateGeneralRequest;
import com.paymentcomponents.libraries.rest.sdk.wrapper.model.mt.request.MtTagGeneralRequest;
import com.paymentcomponents.libraries.rest.sdk.wrapper.utils.MtUtils;
import gr.datamation.mt.common.InvalidMessageFormatException;
import gr.datamation.mt.common.SwiftMessage;
import gr.datamation.mt.common.ValidationErrorException;
import gr.datamation.mt.processor.SwiftMsgProcessor;
import gr.datamation.mt.validator.validation.ValidationErrorList;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MtService {

    public CustomSwiftMessage parseMt(String mtMessage) throws InvalidMessageFormatException {
        SwiftMessage swiftMessage = MtUtils.parseMt(mtMessage);

        return new CustomSwiftMessage(swiftMessage);
    }

    public void validateMt(String mtMessage) throws InvalidMessageFormatException, JsonProcessingException, InvalidMessageException {
        MtUtils.validateMt(mtMessage);
    }

    public String createMt103(MtCreate103Request mt103Request) throws Exception {
        MtCreateGeneralRequest mtCreateGeneralRequest = new MtCreateGeneralRequest();
        mtCreateGeneralRequest.setReceiver(mt103Request.getReceiver());
        mtCreateGeneralRequest.setSender(mt103Request.getSender());
        mtCreateGeneralRequest.setUetr(mt103Request.getUetr());
        mtCreateGeneralRequest.setCategory("MT103");

        List<MtTagGeneralRequest> tags = new ArrayList<>();

        tags.add(new MtTagGeneralRequest("20", Collections.singletonList(mt103Request.getSendersReference())));
        tags.add(new MtTagGeneralRequest("23B", Collections.singletonList(mt103Request.getBankOperationCode())));
        tags.add(new MtTagGeneralRequest("32A", MtUtils.createField32A(mt103Request.getValueDate(), mt103Request.getCurrency(), mt103Request.getInterbankSettlementAmount())));
        tags.add(new MtTagGeneralRequest("50K", MtUtils.createFieldCustomer(mt103Request.getOrderingCustomer())));
        tags.add(new MtTagGeneralRequest("52A", MtUtils.createFieldInstistution(mt103Request.getOrderingInstitution())));
        tags.add(new MtTagGeneralRequest("53A", MtUtils.createFieldInstistution(mt103Request.getSendersCorrespondent())));
        tags.add(new MtTagGeneralRequest("56A", MtUtils.createFieldInstistution(mt103Request.getIntermediaryInstitution())));
        tags.add(new MtTagGeneralRequest("57A", MtUtils.createFieldInstistution(mt103Request.getAccountWithInstitution())));
        tags.add(new MtTagGeneralRequest("59", MtUtils.createFieldCustomer(mt103Request.getBeneficiaryCustomer())));
        tags.add(new MtTagGeneralRequest("70", MtUtils.createFieldRemmitanceInformation(mt103Request.getRemittanceInformation())));
        tags.add(new MtTagGeneralRequest("71A", Collections.singletonList(mt103Request.getDetailsOfCharges())));
        tags.add(new MtTagGeneralRequest("71F", MtUtils.createFieldCharges(mt103Request.getSenderCharges())));
        tags.add(new MtTagGeneralRequest("72", MtUtils.createFieldSenderToReceiverInformation(mt103Request.getSenderToReceiverInformation())));

        //Do not include empty lists
        mtCreateGeneralRequest.setTags(tags.stream().filter( tag -> tag.getData().size() > 0).collect(Collectors.toList()));

        return createMtGeneral(mtCreateGeneralRequest);
    }

    public String createMtGeneral(MtCreateGeneralRequest mtCreateGeneralRequest) throws Exception {

        SwiftMessage swiftMessage = MtUtils.constructSwiftMessage(mtCreateGeneralRequest);
        String swiftMessageText = new SwiftMsgProcessor().BuildMsgStringFromObject(swiftMessage);
        validateMt(swiftMessageText);

        return swiftMessageText;

    }

    public String generateUniversalConfirmation(String mtMessage, String confirmationId, String statusCode, String reasonCode, String forwardTo, String settlementCode, String clearingSystem) throws Exception {
        mtMessage = mtMessage.replaceAll("\r\n", "\r");
        mtMessage = mtMessage.replaceAll("\n", "\r");
        mtMessage = mtMessage.replaceAll("\r", "\r\n");

        SwiftMsgProcessor smp = new SwiftMsgProcessor("\r\n");
        validateMt(mtMessage);
        SwiftMessage mt103 = smp.ParseMsgStringToObject(mtMessage);
        SwiftMessage mt199;
        try {
            mt199 = mt103.autoReply(confirmationId, statusCode, reasonCode, forwardTo, settlementCode, clearingSystem);
        } catch (ValidationErrorException ex) {
            ValidationErrorList validationErrors = new ValidationErrorList();
            validationErrors.add(ex.getMessage());
            throw new InvalidMessageException(
                    new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(validationErrors));
        }

        return new SwiftMsgProcessor().BuildMsgStringFromObject(mt199);
    }

}
