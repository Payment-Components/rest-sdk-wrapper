package com.paymentcomponents.libraries.rest.sdk.wrapper.service;

import com.paymentcomponents.libraries.rest.sdk.wrapper.model.sepa.request.MsgReplyInfoRequest;
import com.paymentcomponents.libraries.rest.sdk.wrapper.model.sepa.request.SepaCreatePacs008Request;
import com.paymentcomponents.libraries.rest.sdk.wrapper.utils.SepaUtils;
import com.paymentcomponents.libraries.rest.sdk.wrapper.utils.Utils;
import gr.datamation.iso20022.sepa.epc.ct.camt.FIToFIPaymentCancellationRequest08RecallSepaEpcCt;
import gr.datamation.iso20022.sepa.epc.ct.pacs.FIToFICustomerCreditTransfer08SepaEpcCt;
import gr.datamation.iso20022.sepa.epc.ct.pacs.PaymentReturn09ReturnSepaEpcCt;
import gr.datamation.iso20022.sepa.epc.ct.replies.FIToFICustomerCreditTransferSepaEpcCtAutoReplies;
import gr.datamation.replies.common.MsgReplyInfo;
import org.springframework.stereotype.Service;
import xsd.pacs_008_001_08.ChargeBearerType1Code;
import xsd.pacs_008_001_08.SettlementMethod1Code;

import javax.xml.datatype.XMLGregorianCalendar;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collections;

import static com.paymentcomponents.libraries.rest.sdk.wrapper.model.sepa.request.SepaCreatePacs008Request.*;
import static com.paymentcomponents.libraries.rest.sdk.wrapper.utils.AutoReplyUtils.buildMsgReplyInfoAccordingToParams;
import static com.paymentcomponents.libraries.rest.sdk.wrapper.utils.DateUtils.*;
import static com.paymentcomponents.libraries.rest.sdk.wrapper.utils.SepaUtils.addElement;
import static com.paymentcomponents.libraries.rest.sdk.wrapper.utils.SepaUtils.addIntrBkSttlmAmt;

@Service
public class SepaService {

    public String validateSepaMessage(String messageText) throws Exception {

        SepaUtils.parseAndValidateSepaMessage(messageText);

        return Utils.convertXmlToJson(messageText, "Document");

    }

    public String createPacs008(SepaCreatePacs008Request request) throws Exception {
        FIToFICustomerCreditTransfer08SepaEpcCt fiToFICustomerCreditTransfer = new FIToFICustomerCreditTransfer08SepaEpcCt();

        int index = 0;
        String rootPath = String.format("CdtTrfTxInf[%d]/", index);

        XMLGregorianCalendar creationDateTime = toXMLGregorianCalendarDate(ZonedDateTime.ofInstant(Instant.now(), ZoneOffset.UTC));

        addElement(fiToFICustomerCreditTransfer, rootPath + "PmtId/InstrId", request.getTxId());
        addElement(fiToFICustomerCreditTransfer, rootPath + "PmtId/EndToEndId", request.getEndToEndId());
        addElement(fiToFICustomerCreditTransfer, rootPath + "PmtId/TxId", request.getTxId());
        addElement(fiToFICustomerCreditTransfer, rootPath + "PmtTpInf/SvcLvl/Cd", DEFAULT_SVC_LVL);
        addIntrBkSttlmAmt(fiToFICustomerCreditTransfer, rootPath + "IntrBkSttlmAmt", request.getSttlmAmt());

        addElement(fiToFICustomerCreditTransfer, rootPath + "ChrgBr", ChargeBearerType1Code.valueOf(DEFAULT_CHRG_BR));
        addElement(fiToFICustomerCreditTransfer, rootPath + "RmtInf/Ustrd", request.getRemInfo());

        //Debtor
        addElement(fiToFICustomerCreditTransfer, rootPath + "Dbtr/Nm", request.getDebtorName());
        addElement(fiToFICustomerCreditTransfer, rootPath + "Dbtr/PstlAdr/Ctry", request.getDebtorCountryCode());
        addElement(fiToFICustomerCreditTransfer, rootPath + "Dbtr/PstlAdr/AdrLine", Arrays.asList(request.getDebtorAddrLn1(), request.getDebtorAddrLn2()));
        addElement(fiToFICustomerCreditTransfer, rootPath + "DbtrAcct/Id/IBAN", request.getDebtorIban());
        addElement(fiToFICustomerCreditTransfer, rootPath + "DbtrAgt/FinInstnId/BICFI", request.getDebtorBic());

        //Creditor
        addElement(fiToFICustomerCreditTransfer, rootPath + "Cdtr/Nm", request.getCreditorName());
        addElement(fiToFICustomerCreditTransfer, rootPath + "Cdtr/PstlAdr/Ctry", request.getCreditorCountryCode());
        addElement(fiToFICustomerCreditTransfer, rootPath + "Cdtr/PstlAdr/AdrLine", Arrays.asList(request.getCreditorAddrLn1(), request.getCreditorAddrLn2()));
        addElement(fiToFICustomerCreditTransfer, rootPath + "CdtrAcct/Id/IBAN", request.getCreditorIban());
        addElement(fiToFICustomerCreditTransfer, rootPath + "CdtrAgt/FinInstnId/BICFI", request.getCreditorBic());

        //Group Header
        addElement(fiToFICustomerCreditTransfer, "GrpHdr/IntrBkSttlmDt", fromDate(request.getSttlmDt(), DATE_FORMAT));
        addElement(fiToFICustomerCreditTransfer, "GrpHdr/MsgId", createTimestamp());
        addElement(fiToFICustomerCreditTransfer, "GrpHdr/CreDtTm", creationDateTime);
        addElement(fiToFICustomerCreditTransfer, "GrpHdr/NbOfTxs", String.valueOf(index + 1));
        addIntrBkSttlmAmt(fiToFICustomerCreditTransfer, "GrpHdr/TtlIntrBkSttlmAmt", SepaUtils.calculateTtlIntrBkSttlmAmt(fiToFICustomerCreditTransfer));
        addElement(fiToFICustomerCreditTransfer, "GrpHdr/SttlmInf/SttlmMtd", SettlementMethod1Code.valueOf(DEFAULT_STTLM_MTD));
        addElement(fiToFICustomerCreditTransfer, "GrpHdr/SttlmInf/ClrSys/Prtry", DEFAULT_CLR_SYS);
        addElement(fiToFICustomerCreditTransfer, "GrpHdr/SttlmInf/ClrSys/Prtry", DEFAULT_CLR_SYS);
        addElement(fiToFICustomerCreditTransfer, "GrpHdr/InstgAgt/FinInstnId/BICFI", request.getInstgAgt());
        addElement(fiToFICustomerCreditTransfer, "GrpHdr/InstdAgt/FinInstnId/BICFI", request.getInstdAgt());

        SepaUtils.validateSepaMessage(fiToFICustomerCreditTransfer);

        return fiToFICustomerCreditTransfer.convertToXML();
    }

    public String generatePaymentReturn(String pacs008MessageText, MsgReplyInfoRequest msgReplyInfoRequest) throws Exception {
        SepaUtils.validateMsgReplyInfoRequest(msgReplyInfoRequest);
        FIToFICustomerCreditTransfer08SepaEpcCt fiToFICustomerCreditTransfer = (FIToFICustomerCreditTransfer08SepaEpcCt) SepaUtils.parseAndValidateSepaMessage(pacs008MessageText);


        FIToFICustomerCreditTransferSepaEpcCtAutoReplies<FIToFICustomerCreditTransfer08SepaEpcCt, PaymentReturn09ReturnSepaEpcCt> pacs008AutoReplies =
                new FIToFICustomerCreditTransferSepaEpcCtAutoReplies<>(fiToFICustomerCreditTransfer);

        MsgReplyInfo msgReplyInfo = buildMsgReplyInfoAccordingToParams(msgReplyInfoRequest, createTimestamp());

        PaymentReturn09ReturnSepaEpcCt paymentReturn = pacs008AutoReplies.autoReply(new PaymentReturn09ReturnSepaEpcCt(), Collections.singletonList(msgReplyInfo));

        SepaUtils.validateSepaMessage(paymentReturn);

        return paymentReturn.convertToXML();
    }

    public String generateCancellationRequest(String pacs008MessageText, MsgReplyInfoRequest msgReplyInfoRequest) throws Exception {
        SepaUtils.validateMsgReplyInfoRequest(msgReplyInfoRequest);
        FIToFICustomerCreditTransfer08SepaEpcCt fiToFICustomerCreditTransfer = (FIToFICustomerCreditTransfer08SepaEpcCt) SepaUtils.parseAndValidateSepaMessage(pacs008MessageText);

        FIToFICustomerCreditTransferSepaEpcCtAutoReplies<FIToFICustomerCreditTransfer08SepaEpcCt, FIToFIPaymentCancellationRequest08RecallSepaEpcCt> pacs008AutoReplies =
                new FIToFICustomerCreditTransferSepaEpcCtAutoReplies<>(fiToFICustomerCreditTransfer);

        MsgReplyInfo msgReplyInfo = buildMsgReplyInfoAccordingToParams(msgReplyInfoRequest, createTimestamp());

        FIToFIPaymentCancellationRequest08RecallSepaEpcCt paymenCancellationRequest = pacs008AutoReplies.autoReply(new FIToFIPaymentCancellationRequest08RecallSepaEpcCt(), Collections.singletonList(msgReplyInfo));

        SepaUtils.validateSepaMessage(paymenCancellationRequest);

        return paymenCancellationRequest.convertToXML();
    }

}
