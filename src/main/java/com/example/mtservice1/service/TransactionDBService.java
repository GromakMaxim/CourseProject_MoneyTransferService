package com.example.mtservice1.service;

import com.example.mtservice1.model.transaction.Transaction;
import com.example.mtservice1.repository.TransactionDB;
import org.springframework.stereotype.Service;

/**
 * a class that manages the operation of the transaction pool
 */
@Service
public class TransactionDBService {
    private TransactionDB transactionDB;

    public TransactionDBService(TransactionDB transactionDB) {
        this.transactionDB = transactionDB;
    }

    public void save(String code, Transaction transaction) {
        transactionDB.save(code, transaction);
    }

    public boolean isExist(String code) {
        return transactionDB.isExist(code);
    }

    public Transaction getTransactionByID(String id) {
        return transactionDB.getTransactionByID(id);
    }

    public boolean checkIfCardNumbersSame(Transaction transaction) {
        return transaction.getCardFromNumber().equalsIgnoreCase(transaction.getCardToNumber());
    }
}
