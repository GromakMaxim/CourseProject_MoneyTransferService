package com.example.mtservice1.service;

import com.example.mtservice1.model.transaction.Transaction;
import com.example.mtservice1.repository.CardsDB;
import org.springframework.stereotype.Service;

/**
 * the class that manages working with the card pool
 */
@Service
public class CardDBService {
    private CardsDB cardsDB;

    public CardDBService(CardsDB cardsDB) {
        this.cardsDB = cardsDB;
    }

    public boolean isExist(Transaction transaction) {
        return cardsDB.isExist(transaction.getCardFromNumber()) && cardsDB.isExist(transaction.getCardToNumber());
    }

}
