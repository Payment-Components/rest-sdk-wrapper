package com.paymentcomponents.libraries.rest.sdk.wrapper.model.sepa.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public class SepaCreatePacs008Request {

      public static final String DATE_FORMAT = "yyyy-MM-dd";
      public static final String DEFAULT_CHRG_BR = "SLEV";
      public static final String DEFAULT_SVC_LVL = "SEPA";
      public static final String DEFAULT_STTLM_MTD = "CLRG";
      public static final String DEFAULT_CLR_SYS = "ST2";

      @Schema(description = "/CdtTrfTxInf/CdtrAgt/FinInstnId/BIC", example = "TESTBICA", required = true)
      private String creditorBic;
      @Schema(description = "/CdtTrfTxInf/Cdtr/PstlAdr/Ctry", example = "GR")
      private String creditorCountryCode;
      @Schema(description = "/CdtTrfTxInf/CdtrAcct/Id/IBAN", example = "GR1601101250000000012300695", required = true)
      private String creditorIban;
      @Schema(description = "/CdtTrfTxInf/Cdtr/Nm", example = "John Doe")
      private String creditorName;
      @Schema(description = "/CdtTrfTxInf/Cdtr/PstlAdr/AdrLine", example = "Address1")
      private String creditorAddrLn1;
      @Schema(description = "/CdtTrfTxInf/Cdtr/PstlAdr/AdrLine", example = "Address2")
      private String creditorAddrLn2;
      @Schema(description = "/CdtTrfTxInf/DbtrAgt/FinInstnId/BIC", example = "TESTBICB", required = true)
      private String debtorBic;
      @Schema(description = "/CdtTrfTxInf/Dbtr/PstlAdr/Ctry", example = "GR")
      private String debtorCountryCode;
      @Schema(description = "/CdtTrfTxInf/DbtrAcct/Id/IBAN", example = "GR1601101250000000012300777", required = true)
      private String debtorIban;
      @Schema(description = "/CdtTrfTxInf/Dbtr/Nm", example = "Jim Smith")
      private String debtorName;
      @Schema(description = "/CdtTrfTxInf/Dbtr/PstlAdr/AdrLine", example = "Address1")
      private String debtorAddrLn1;
      @Schema(description = "/CdtTrfTxInf/Dbtr/PstlAdr/AdrLine", example = "Address2")
      private String debtorAddrLn2;
      @Schema(description = "/CdtTrfTxInf/PmtId/EndToEndId", example = "123456789", required = true)
      private String endToEndId;
      @Schema(description = "/CdtTrfTxInf/RmtInf/Ustrd", example = "Remit. Info")
      private String remInfo;
      @Schema(description = "/CdtTrfTxInf/IntrBkSttlmAmt", example = "89.67", required = true)
      private BigDecimal sttlmAmt;
      @Schema(description = "/CdtTrfTxInf/AccptncDtTm", example = "2020-09-22", format = "yyyy-MM-dd", required = true)
      private String sttlmDt;
      @Schema(description = "/CdtTrfTxInf/PmtId/TxId", example = "tx-id-1234", required = true)
      private String txId;
      @Schema(description = "/GrpHdr/InstgAgt/FinInstnId/BIC", example = "TESTBICF", required = true)
      private String instgAgt;
      @Schema(description = "/GrpHdr/InstdAgt/FinInstnId/BIC", example = "TESTBICD", required = true)
      private String instdAgt;

      public String getCreditorBic() {
            return creditorBic;
      }

      public void setCreditorBic(String creditorBic) {
            this.creditorBic = creditorBic;
      }

      public String getCreditorCountryCode() {
            return creditorCountryCode;
      }

      public void setCreditorCountryCode(String creditorCountryCode) {
            this.creditorCountryCode = creditorCountryCode;
      }

      public String getCreditorIban() {
            return creditorIban;
      }

      public void setCreditorIban(String creditorIban) {
            this.creditorIban = creditorIban;
      }

      public String getCreditorName() {
            return creditorName;
      }

      public void setCreditorName(String creditorName) {
            this.creditorName = creditorName;
      }

      public String getCreditorAddrLn1() {
            return creditorAddrLn1;
      }

      public void setCreditorAddrLn1(String creditorAddrLn1) {
            this.creditorAddrLn1 = creditorAddrLn1;
      }

      public String getCreditorAddrLn2() {
            return creditorAddrLn2;
      }

      public void setCreditorAddrLn2(String creditorAddrLn2) {
            this.creditorAddrLn2 = creditorAddrLn2;
      }

      public String getDebtorBic() {
            return debtorBic;
      }

      public void setDebtorBic(String debtorBic) {
            this.debtorBic = debtorBic;
      }

      public String getDebtorCountryCode() {
            return debtorCountryCode;
      }

      public void setDebtorCountryCode(String debtorCountryCode) {
            this.debtorCountryCode = debtorCountryCode;
      }

      public String getDebtorIban() {
            return debtorIban;
      }

      public void setDebtorIban(String debtorIban) {
            this.debtorIban = debtorIban;
      }

      public String getDebtorName() {
            return debtorName;
      }

      public void setDebtorName(String debtorName) {
            this.debtorName = debtorName;
      }

      public String getDebtorAddrLn1() {
            return debtorAddrLn1;
      }

      public void setDebtorAddrLn1(String debtorAddrLn1) {
            this.debtorAddrLn1 = debtorAddrLn1;
      }

      public String getDebtorAddrLn2() {
            return debtorAddrLn2;
      }

      public void setDebtorAddrLn2(String debtorAddrLn2) {
            this.debtorAddrLn2 = debtorAddrLn2;
      }

      public String getEndToEndId() {
            return endToEndId;
      }

      public void setEndToEndId(String endToEndId) {
            this.endToEndId = endToEndId;
      }

      public String getRemInfo() {
            return remInfo;
      }

      public void setRemInfo(String remInfo) {
            this.remInfo = remInfo;
      }

      public BigDecimal getSttlmAmt() {
            return sttlmAmt;
      }

      public void setSttlmAmt(BigDecimal sttlmAmt) {
            this.sttlmAmt = sttlmAmt;
      }

      public String getSttlmDt() {
            return sttlmDt;
      }

      public void setSttlmDt(String sttlmDt) {
            this.sttlmDt = sttlmDt;
      }

      public String getTxId() {
            return txId;
      }

      public void setTxId(String txId) {
            this.txId = txId;
      }

      public String getInstgAgt() {
            return instgAgt;
      }

      public void setInstgAgt(String instgAgt) {
            this.instgAgt = instgAgt;
      }

      public String getInstdAgt() {
            return instdAgt;
      }

      public void setInstdAgt(String instdAgt) {
            this.instdAgt = instdAgt;
      }
}
