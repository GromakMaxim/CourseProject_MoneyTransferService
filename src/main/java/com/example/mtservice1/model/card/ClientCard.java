package com.example.mtservice1.model.card;

import java.util.concurrent.atomic.AtomicLong;

/**
 * A typical bank card. It contains the card number, the owner's data, and the balance.
 */
public class ClientCard{
    private final String cardNumber;
    private String cardOwner;
    private String validityPeriod;
    private String SVV;
    private AtomicLong balance;

    public ClientCard(String cardNumber, String cardOwner, String validityPeriod, String SVV, AtomicLong balance) {
        this.cardNumber = cardNumber;
        this.cardOwner = cardOwner;
        this.validityPeriod = validityPeriod;
        this.SVV = SVV;
        this.balance = balance;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardOwner() {
        return cardOwner;
    }

    public String getValidityPeriod() {
        return validityPeriod;
    }

    public String getSVV() {
        return SVV;
    }

    public AtomicLong getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return "ClientCard{" +
                "cardNumber='" + cardNumber + '\'' +
                ", cardOwner='" + cardOwner + '\'' +
                ", SVV='" + SVV + '\'' +
                ", balance=" + balance +
                '}';
    }
}
