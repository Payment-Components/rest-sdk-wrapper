package com.paymentcomponents.libraries.rest.sdk.wrapper.service;

import com.paymentcomponents.libraries.rest.sdk.wrapper.model.sepa.request.MsgReplyInfoRequest;
import com.paymentcomponents.libraries.rest.sdk.wrapper.model.sepa.request.SepaCreatePacs008Request;
import com.paymentcomponents.libraries.rest.sdk.wrapper.utils.SepaUtils;
import com.paymentcomponents.libraries.rest.sdk.wrapper.utils.Utils;
import gr.datamation.sepa.core.messages.common.MsgReplyInfo;
import gr.datamation.sepa.core.messages.common.ReasonCode;
import gr.datamation.sepa.core.messages.epc.camt.FIToFIPaymentCancellationRequest;
import gr.datamation.sepa.core.messages.epc.camt.ResolutionOfInvestigation;
import gr.datamation.sepa.core.messages.epc.pacs.FIToFICustomerCreditTransfer;
import gr.datamation.sepa.core.messages.epc.pacs.PaymentReturn;
import gr.datamation.sepa.types.epc.pacs008.CreditTransferTransactionInformation11;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import static com.paymentcomponents.libraries.rest.sdk.wrapper.model.sepa.request.SepaCreatePacs008Request.*;
import static com.paymentcomponents.libraries.rest.sdk.wrapper.utils.SepaUtils.addElement;
import static com.paymentcomponents.libraries.rest.sdk.wrapper.utils.SepaUtils.addIntrBkSttlmAmt;

@Service
public class SepaService {

    public String validateSepaMessage(String messageText) throws Exception {

        SepaUtils.parseAndValidateSepaMessage(messageText);

        return Utils.convertXmlToJson(messageText, "Document");

    }

    public String createPacs008(SepaCreatePacs008Request request) throws Exception {
        FIToFICustomerCreditTransfer fiToFICustomerCreditTransfer = new FIToFICustomerCreditTransfer();
        Date sttlmDt = new SimpleDateFormat(DATE_FORMAT).parse(request.getSttlmDt());

        int index = 1;
        String rootPath = String.format("/CdtTrfTxInf[%d]/", index);

        addElement(fiToFICustomerCreditTransfer, rootPath + "PmtId/InstrId", request.getTxId());
        addElement(fiToFICustomerCreditTransfer, rootPath + "PmtId/EndToEndId", request.getEndToEndId());
        addElement(fiToFICustomerCreditTransfer, rootPath + "PmtId/TxId", request.getTxId());
        addElement(fiToFICustomerCreditTransfer, rootPath + "PmtTpInf/SvcLvl/Cd", DEFAULT_SVC_LVL);
        addIntrBkSttlmAmt(fiToFICustomerCreditTransfer, rootPath + "IntrBkSttlmAmt", request.getSttlmAmt());
        addElement(fiToFICustomerCreditTransfer, rootPath + "AccptncDtTm", Utils.convertDateToCalendar(sttlmDt));
        addElement(fiToFICustomerCreditTransfer, rootPath + "ChrgBr", DEFAULT_CHRG_BR);
        addElement(fiToFICustomerCreditTransfer, rootPath + "RmtInf/Ustrd", request.getRemInfo());

        //Debtor
        addElement(fiToFICustomerCreditTransfer, rootPath + "Dbtr/Nm", request.getDebtorName());
        addElement(fiToFICustomerCreditTransfer, rootPath + "Dbtr/PstlAdr/Ctry", request.getDebtorCountryCode());
        addElement(fiToFICustomerCreditTransfer, rootPath + "Dbtr/PstlAdr/AdrLine[0]", request.getDebtorAddrLn1());
        addElement(fiToFICustomerCreditTransfer, rootPath + "Dbtr/PstlAdr/AdrLine[1]", request.getDebtorAddrLn2());
        addElement(fiToFICustomerCreditTransfer, rootPath + "DbtrAcct/Id/IBAN", request.getDebtorIban());
        addElement(fiToFICustomerCreditTransfer, rootPath + "DbtrAgt/FinInstnId/BIC", request.getDebtorBic());

        //Creditor
        addElement(fiToFICustomerCreditTransfer, rootPath + "Cdtr/Nm", request.getCreditorName());
        addElement(fiToFICustomerCreditTransfer, rootPath + "Cdtr/PstlAdr/Ctry", request.getCreditorCountryCode());
        addElement(fiToFICustomerCreditTransfer, rootPath + "Cdtr/PstlAdr/AdrLine[0]", request.getCreditorAddrLn1());
        addElement(fiToFICustomerCreditTransfer, rootPath + "Cdtr/PstlAdr/AdrLine[1]", request.getCreditorAddrLn2());
        addElement(fiToFICustomerCreditTransfer, rootPath + "CdtrAcct/Id/IBAN", request.getCreditorIban());
        addElement(fiToFICustomerCreditTransfer, rootPath + "CdtrAgt/FinInstnId/BIC", request.getCreditorBic());

        //Group Header
        addElement(fiToFICustomerCreditTransfer, "/GrpHdr/IntrBkSttlmDt", Utils.convertDateToCalendar(sttlmDt));
        addElement(fiToFICustomerCreditTransfer, "/GrpHdr/MsgId", Utils.createTimestamp());
        addElement(fiToFICustomerCreditTransfer, "/GrpHdr/NbOfTxs", String.valueOf(index));
        addIntrBkSttlmAmt(fiToFICustomerCreditTransfer, "/GrpHdr/TtlIntrBkSttlmAmt", SepaUtils.calculateTtlIntrBkSttlmAmt(fiToFICustomerCreditTransfer));
        addElement(fiToFICustomerCreditTransfer, "/GrpHdr/SttlmInf/SttlmMtd", DEFAULT_STTLM_MTD);
        addElement(fiToFICustomerCreditTransfer, "/GrpHdr/SttlmInf/ClrSys/Prtry", DEFAULT_CLR_SYS);
        addElement(fiToFICustomerCreditTransfer, "/GrpHdr/InstgAgt/FinInstnId/BIC", request.getInstgAgt());
        addElement(fiToFICustomerCreditTransfer, "/GrpHdr/InstdAgt/FinInstnId/BIC", request.getInstdAgt());

        SepaUtils.parseAndValidateSepaMessage(fiToFICustomerCreditTransfer.toString());

        return fiToFICustomerCreditTransfer.toString();
    }

    public String generatePaymentReturn(String pacs008MessageText, MsgReplyInfoRequest msgReplyInfoRequest) throws Exception {
        SepaUtils.validateMsgReplyInfoRequest(msgReplyInfoRequest);
        FIToFICustomerCreditTransfer fiToFICustomerCreditTransfer = (FIToFICustomerCreditTransfer) SepaUtils.parseAndValidateSepaMessage(pacs008MessageText);

        CreditTransferTransactionInformation11 firstTransaction = fiToFICustomerCreditTransfer.getRootMessage().getCdtTrfTxInf().get(0);

        if (ObjectUtils.isEmpty(msgReplyInfoRequest.getIndicator())) {
            msgReplyInfoRequest.setIndicator(firstTransaction.getPmtId().getTxId());
        }

        String msgId = Utils.createTimestamp();

        ReasonCode reasonCode;
        if (!ObjectUtils.isEmpty(msgReplyInfoRequest.getReasonCode())) {
            reasonCode = new ReasonCode(ReasonCode.CD, msgReplyInfoRequest.getReasonCode(), null, null);
        } else {
            reasonCode = new ReasonCode(ReasonCode.PRTRY, msgReplyInfoRequest.getReasonPrtry(), null, null);
        }

        PaymentReturn paymentReturn = new PaymentReturn();
        MsgReplyInfo msgReplyInfo = new MsgReplyInfo(
                msgReplyInfoRequest.getIndicator(),
                reasonCode,
                MsgReplyInfo.MSGID_BASED,
                msgId,
                null,
                new Date()
        );

        fiToFICustomerCreditTransfer.autoReply(paymentReturn, Arrays.asList(msgReplyInfo));

        //Add general info to the generated message
        //message id is commonly auto-generated by each system
        addElement(paymentReturn, "/GrpHdr/MsgId", msgId);
        addElement(paymentReturn, "/GrpHdr/IntrBkSttlmDt", Utils.convertDateToCalendar(new Date()));
        addElement(paymentReturn, "/GrpHdr/SttlmInf/SttlmMtd", "CLRG");
        addElement(paymentReturn, "/GrpHdr/SttlmInf/ClrSys/Prtry", "ST2");

        SepaUtils.parseAndValidateSepaMessage(paymentReturn);

        return paymentReturn.toString();
    }

    public String generateCancellationRequest(String pacs008MessageText, MsgReplyInfoRequest msgReplyInfoRequest) throws Exception {
        SepaUtils.validateMsgReplyInfoRequest(msgReplyInfoRequest);
        FIToFICustomerCreditTransfer fiToFICustomerCreditTransfer = (FIToFICustomerCreditTransfer) SepaUtils.parseAndValidateSepaMessage(pacs008MessageText);

        CreditTransferTransactionInformation11 firstTransaction = fiToFICustomerCreditTransfer.getRootMessage().getCdtTrfTxInf().get(0);

        if (ObjectUtils.isEmpty(msgReplyInfoRequest.getIndicator())) {
            msgReplyInfoRequest.setIndicator(firstTransaction.getPmtId().getTxId());
        }

        String assgnmtId = Utils.createTimestamp();

        ReasonCode reasonCode;
        if (!ObjectUtils.isEmpty(msgReplyInfoRequest.getReasonCode())) {
            reasonCode = new ReasonCode(ReasonCode.CD, msgReplyInfoRequest.getReasonCode(), null, null);
        } else {
            reasonCode = new ReasonCode(ReasonCode.PRTRY, msgReplyInfoRequest.getReasonPrtry(), null, null);
        }

        FIToFIPaymentCancellationRequest cancellationRequest = new FIToFIPaymentCancellationRequest();
        MsgReplyInfo msgReplyInfo = new MsgReplyInfo(
                msgReplyInfoRequest.getIndicator(),
                reasonCode,
                MsgReplyInfo.MSGID_BASED,
                assgnmtId,
                null,
                new Date()
        );

        fiToFICustomerCreditTransfer.autoReply(cancellationRequest, Arrays.asList(msgReplyInfo));

        addElement(cancellationRequest, "/Assgnmt/Id", assgnmtId);
        addElement(cancellationRequest, "/Assgnmt/Assgnr/Agt/FinInstnId/BIC", firstTransaction.getDbtrAgt().getFinInstnId().getBIC());
        addElement(cancellationRequest, "/Assgnmt/Assgne/Agt/FinInstnId/BIC", firstTransaction.getCdtrAgt().getFinInstnId().getBIC());
        addElement(cancellationRequest, "/Assgnmt/CreDtTm", Utils.convertDateToCalendar(new Date()));

        SepaUtils.parseAndValidateSepaMessage(cancellationRequest);

        return cancellationRequest.toString();
    }

    public String generateResolutionOfInvestigation(String pacs008MessageText, MsgReplyInfoRequest msgReplyInfoRequest) throws Exception {
        SepaUtils.validateMsgReplyInfoRequest(msgReplyInfoRequest);
        FIToFICustomerCreditTransfer fiToFICustomerCreditTransfer = (FIToFICustomerCreditTransfer) SepaUtils.parseAndValidateSepaMessage(pacs008MessageText);

        CreditTransferTransactionInformation11 firstTransaction = fiToFICustomerCreditTransfer.getRootMessage().getCdtTrfTxInf().get(0);

        if (ObjectUtils.isEmpty(msgReplyInfoRequest.getIndicator())) {
            msgReplyInfoRequest.setIndicator(firstTransaction.getPmtId().getTxId());
        }

        String assgnmtId = Utils.createTimestamp();

        ReasonCode reasonCode;
        if (!ObjectUtils.isEmpty(msgReplyInfoRequest.getReasonCode())) {
            reasonCode = new ReasonCode(ReasonCode.CD, msgReplyInfoRequest.getReasonCode(), null, null);
        } else {
            reasonCode = new ReasonCode(ReasonCode.PRTRY, msgReplyInfoRequest.getReasonPrtry(), null, null);
        }

        if (!ObjectUtils.isEmpty(msgReplyInfoRequest.getAdditionalInformation())) {
            reasonCode.setLegalReasonCode1(msgReplyInfoRequest.getAdditionalInformation());
        }

        ResolutionOfInvestigation resolutionOfInvestigation = new ResolutionOfInvestigation();
        MsgReplyInfo msgReplyInfo = new MsgReplyInfo(
                msgReplyInfoRequest.getIndicator(),
                reasonCode,
                MsgReplyInfo.MSGID_BASED,
                assgnmtId,
                null,
                new Date()
        );

        fiToFICustomerCreditTransfer.autoReply(resolutionOfInvestigation, Arrays.asList(msgReplyInfo));

        addElement(resolutionOfInvestigation, "/Assgnmt/Id", assgnmtId);
        addElement(resolutionOfInvestigation, "/Assgnmt/Assgnr/Agt/FinInstnId/BIC", firstTransaction.getDbtrAgt().getFinInstnId().getBIC());
        addElement(resolutionOfInvestigation, "/Assgnmt/Assgne/Agt/FinInstnId/BIC", firstTransaction.getCdtrAgt().getFinInstnId().getBIC());
        addElement(resolutionOfInvestigation, "/Assgnmt/CreDtTm", Utils.convertDateToCalendar(new Date()));

        SepaUtils.parseAndValidateSepaMessage(resolutionOfInvestigation);

        return resolutionOfInvestigation.toString();
    }

}
