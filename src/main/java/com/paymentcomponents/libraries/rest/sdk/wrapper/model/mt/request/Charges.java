package com.paymentcomponents.libraries.rest.sdk.wrapper.model.mt.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public class Charges {
    @Schema(description = "Currency",
            example = "EUR", required = true)
    private String currency;
    @Schema(description = "Amount",
            example = "0.40", required = true)
    private BigDecimal amount;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}