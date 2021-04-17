package com.example.mtservice1.service;

import com.example.mtservice1.model.card.ClientCard;
import com.example.mtservice1.model.transaction.Transaction;
import com.example.mtservice1.model.transaction.TransactionStatus;
import com.example.mtservice1.repository.CardsDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * a service that transfers money between accounts
 */
@Service
public class MoneyExchangerService {
    private static final Logger log = LoggerFactory.getLogger(MoneyExchangerService.class);
    private CardsDB cardsDB;

    public MoneyExchangerService(CardsDB cardsDB) {
        this.cardsDB = cardsDB;
    }

    public void process(Transaction transaction) {
        StringBuffer sb = new StringBuffer();
        if (transaction.getStatus().equals(TransactionStatus.ACCEPTED)) {//only operations with the 'accepted' status must be available for exchange
            ClientCard cardFrom = cardsDB.getCardByNumber(transaction.getCardFromNumber());
            ClientCard cardTo = cardsDB.getCardByNumber(transaction.getCardToNumber());
            long moneyToTransfer = transaction.getAmount().getValue();

            //that line is crucial! only operations that meet these criteria can be performed! be careful!
            if (!checkCVV(transaction) || !checkIfMoneyEnough(cardFrom, moneyToTransfer) || !checkDateValidTill(transaction)) {
                if (!checkCVV(transaction)) sb.append("Transaction failed. Wrong CVV provided. ");
                if (!checkIfMoneyEnough(cardFrom, moneyToTransfer)) sb.append("Transaction failed. Not enough money. ");
                if (!checkDateValidTill(transaction))
                    sb.append("Transaction failed. The validity period of the card is specified incorrectly");

                sb.append(cardFrom.getCardNumber() + "(" + cardFrom.getBalance() + ") ");

                sb.append(" --> ");
                sb.append("(" + moneyToTransfer + ") ");
                sb.append(" --> ");

                sb.append(cardTo.getCardNumber() + "(" + cardTo.getBalance() + ") ");
                transaction.setStatus(TransactionStatus.REJECTED);
            } else {
                withdrawMoney(cardFrom, moneyToTransfer);
                addMoney(cardTo, moneyToTransfer);
                transaction.setStatus(TransactionStatus.DONE);

                sb.append("Transaction completed successfully ");

                sb.append(cardFrom.getCardNumber() + "(" + cardFrom.getBalance() + ") ");

                sb.append(" --> ");
                sb.append("(" + moneyToTransfer + ") ");
                sb.append(" --> ");

                sb.append(cardTo.getCardNumber() + "(" + cardTo.getBalance() + ") ");
            }
        } else {
            sb.append("Transaction failed. Current status is " + transaction.getStatus());
        }
        log.info(sb.toString());
    }

    private void addMoney(ClientCard cardTo, long sum) {
        cardTo.getBalance().addAndGet(Math.abs(sum));
    }

    private void withdrawMoney(ClientCard cardFrom, long sum) {
        cardFrom.getBalance().addAndGet(-Math.abs(sum));
    }

    private boolean checkIfMoneyEnough(ClientCard cardFrom, long sum) {
        return cardFrom.getBalance().get() >= sum;
    }

    private boolean checkCVV(Transaction transaction) {
        String incomingCVV = transaction.getCardFromCVV();
        String cardFromNumber = transaction.getCardFromNumber();
        String expectedCVV = cardsDB.getCardByNumber(cardFromNumber).getSVV();

        return incomingCVV.equalsIgnoreCase(expectedCVV);
    }

    private boolean checkDateValidTill(Transaction transaction) {
        String incomingCardDate = transaction.getCardFromValidTill();
        String cardFromNumber = transaction.getCardFromNumber();
        String expectedDate = cardsDB.getCardByNumber(cardFromNumber).getValidityPeriod();

        return incomingCardDate.equalsIgnoreCase(expectedDate);
    }
}
