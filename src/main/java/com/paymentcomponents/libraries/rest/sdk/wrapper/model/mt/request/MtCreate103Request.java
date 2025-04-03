package com.paymentcomponents.libraries.rest.sdk.wrapper.model.mt.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public class MtCreate103Request {

    @Schema(description = "Field 20",
            example = "123456789", requiredMode = Schema.RequiredMode.REQUIRED)
    private String sendersReference;
    @Schema(description = "Field 121(UETR)",
            example = "2412527e-aefa-455d-8307-851118e145fe", requiredMode = Schema.RequiredMode.REQUIRED)
    private String uetr;
    @Schema(description = "Field 23B",
            requiredMode = Schema.RequiredMode.REQUIRED, allowableValues = {"CRED", "CRTS", "SPAY", "SPRI", "SSTD"})
    private String bankOperationCode;
    @Schema(description = "Date of Field 32A. If absent, current date will be used.",
            example = "2020-09-24", format = "yyyy-MM-dd")
    private String valueDate;
    @Schema(description = "Currency of Field 32A",
            example = "EUR", requiredMode = Schema.RequiredMode.REQUIRED)
    private String currency;
    @Schema(description = "Amount of Field 32A",
            example = "1.08", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal interbankSettlementAmount;
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private Customer orderingCustomer; //Field 50K
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private Customer beneficiaryCustomer; //Field 59
    private Institution orderingInstitution; //Field 52A
    private Institution intermediaryInstitution; //Field 56A
    private Institution accountWithInstitution; //Field 57A
    private Institution sendersCorrespondent; //Field 53A
    private RemittanceInformation remittanceInformation; //Field 70
    private SenderToReceiverInformation senderToReceiverInformation; //Field 72
    @Schema(description = "Field 71A", allowableValues = {"BEN", "OUR", "SHA"},
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String detailsOfCharges;
    private Charges senderCharges; //Field 71F
    @Schema(description = "To be used in Block 1",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String sender;
    @Schema(description = "To be used in Block 2",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String receiver;

    public String getSendersReference() {
        return sendersReference;
    }

    public void setSendersReference(String sendersReference) {
        this.sendersReference = sendersReference;
    }

    public String getUetr() {
        return uetr;
    }

    public void setUetr(String uetr) {
        this.uetr = uetr;
    }

    public String getBankOperationCode() {
        return bankOperationCode;
    }

    public void setBankOperationCode(String bankOperationCode) {
        this.bankOperationCode = bankOperationCode;
    }

    public String getValueDate() {
        return valueDate;
    }

    public void setValueDate(String valueDate) {
        this.valueDate = valueDate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getInterbankSettlementAmount() {
        return interbankSettlementAmount;
    }

    public void setInterbankSettlementAmount(BigDecimal interbankSettlementAmount) {
        this.interbankSettlementAmount = interbankSettlementAmount;
    }

    public Customer getOrderingCustomer() {
        return orderingCustomer;
    }

    public void setOrderingCustomer(Customer orderingCustomer) {
        this.orderingCustomer = orderingCustomer;
    }

    public Customer getBeneficiaryCustomer() {
        return beneficiaryCustomer;
    }

    public void setBeneficiaryCustomer(Customer beneficiaryCustomer) {
        this.beneficiaryCustomer = beneficiaryCustomer;
    }

    public Institution getOrderingInstitution() {
        return orderingInstitution;
    }

    public void setOrderingInstitution(Institution orderingInstitution) {
        this.orderingInstitution = orderingInstitution;
    }

    public Institution getIntermediaryInstitution() {
        return intermediaryInstitution;
    }

    public void setIntermediaryInstitution(Institution intermediaryInstitution) {
        this.intermediaryInstitution = intermediaryInstitution;
    }

    public Institution getAccountWithInstitution() {
        return accountWithInstitution;
    }

    public void setAccountWithInstitution(Institution accountWithInstitution) {
        this.accountWithInstitution = accountWithInstitution;
    }

    public Institution getSendersCorrespondent() {
        return sendersCorrespondent;
    }

    public void setSendersCorrespondent(Institution sendersCorrespondent) {
        this.sendersCorrespondent = sendersCorrespondent;
    }

    public RemittanceInformation getRemittanceInformation() {
        return remittanceInformation;
    }

    public void setRemittanceInformation(RemittanceInformation remittanceInformation) {
        this.remittanceInformation = remittanceInformation;
    }

    public SenderToReceiverInformation getSenderToReceiverInformation() {
        return senderToReceiverInformation;
    }

    public void setSenderToReceiverInformation(SenderToReceiverInformation senderToReceiverInformation) {
        this.senderToReceiverInformation = senderToReceiverInformation;
    }

    public String getDetailsOfCharges() {
        return detailsOfCharges;
    }

    public void setDetailsOfCharges(String detailsOfCharges) {
        this.detailsOfCharges = detailsOfCharges;
    }

    public Charges getSenderCharges() {
        return senderCharges;
    }

    public void setSenderCharges(Charges senderCharges) {
        this.senderCharges = senderCharges;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}



