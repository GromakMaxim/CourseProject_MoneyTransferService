package com.example.mtservice1.model;

import org.springframework.validation.annotation.Validated;

@Validated
public class Payment {
    private String code;
    private String cardNumberSender;
    private String cardNumberPayee;
    private String CVV;
    private String validityPeriod;
    private String currency;
    private Long paymentAmount;
    private TransactionStatus status = TransactionStatus.WAIT;

    public Payment() {
    }

    public Payment(String code, String cardNumberSender, String cardNumberPayee, String CVV, String validityPeriod, String currency, Long paymentAmount) {
        this.code = code;
        this.cardNumberSender = cardNumberSender;
        this.cardNumberPayee = cardNumberPayee;
        this.CVV = CVV;
        this.validityPeriod = validityPeriod;
        this.currency = currency;
        this.paymentAmount = paymentAmount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCardNumberSender() {
        return cardNumberSender;
    }

    public void setCardNumberSender(String cardNumberSender) {
        this.cardNumberSender = cardNumberSender;
    }

    public String getCardNumberPayee() {
        return cardNumberPayee;
    }

    public void setCardNumberPayee(String cardNumberPayee) {
        this.cardNumberPayee = cardNumberPayee;
    }

    public String getCVV() {
        return CVV;
    }

    public void setCVV(String CVV) {
        this.CVV = CVV;
    }

    public String getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(String validityPeriod) {
        this.validityPeriod = validityPeriod;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Long paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "cardNumberSender='" + cardNumberSender + '\'' +
                ", cardNumberPayee='" + cardNumberPayee + '\'' +
                ", currency='" + currency + '\'' +
                ", paymentAmount=" + paymentAmount +
                ", status=" + status +
                '}';
    }
}
