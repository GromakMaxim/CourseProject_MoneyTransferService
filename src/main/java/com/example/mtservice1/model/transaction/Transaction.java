package com.example.mtservice1.model.transaction;

import com.example.mtservice1.model.card.Amount;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * created from the "/transfer" endpoint in the controller. Contains information about the sender, recipient, and amount
 */
@Validated
public class Transaction {
    @CreditCardNumber
    private String cardFromNumber;

    @CreditCardNumber
    private String cardToNumber;

    @NotNull
    @NotBlank
    @Min(100)
    @Max(999)
    private String cardFromCVV;

    @Size(min = 5, max = 5)
    private String cardFromValidTill;

    @Valid
    @NotNull
    private Amount amount;

    private TransactionStatus status = TransactionStatus.WAIT;

    @JsonCreator
    public Transaction() {
    }

    public Transaction(String cardFromNumber, String cardToNumber, String cardFromCVV, String cardFromValidTill, Amount amount) {
        this.cardFromNumber = cardFromNumber;
        this.cardToNumber = cardToNumber;
        this.cardFromCVV = cardFromCVV;
        this.cardFromValidTill = cardFromValidTill;
        this.amount = amount;
    }

    public String getCardFromNumber() {
        return cardFromNumber;
    }

    public void setCardFromNumber(String cardFromNumber) {
        this.cardFromNumber = cardFromNumber;
    }

    public String getCardToNumber() {
        return cardToNumber;
    }

    public void setCardToNumber(String cardToNumber) {
        this.cardToNumber = cardToNumber;
    }

    public String getCardFromCVV() {
        return cardFromCVV;
    }

    public void setCardFromCVV(String cardFromCVV) {
        this.cardFromCVV = cardFromCVV;
    }

    public String getCardFromValidTill() {
        return cardFromValidTill;
    }

    public void setCardFromValidTill(String cardFromValidTill) {
        this.cardFromValidTill = cardFromValidTill;
    }

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "cardFromNumber='" + cardFromNumber + '\'' +
                ", cardToNumber='" + cardToNumber + '\'' +
                ", cardFromCVV='" + cardFromCVV + '\'' +
                ", cardFromValidTill='" + cardFromValidTill + '\'' +
                ", amount=" + amount +
                ", status=" + status +
                '}';
    }
}