package com.example.mtservice1.repository;


import com.example.mtservice1.model.transaction.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 class for storing a pool of incoming transactions
 */
@Repository
public class TransactionDB {
    private static final Logger log = LoggerFactory.getLogger(TransactionDB.class);
    private Map<String, Transaction> transactionsDB = new ConcurrentHashMap<>();

    public void save(String code, Transaction transaction) {
        transactionsDB.put(code, transaction);
        log.info("Saved: " + code + " " + transaction);
    }

    public boolean isExist(String code) {
        return transactionsDB.containsKey(code);
    }

    public Transaction getTransactionByID(String id) {
        return transactionsDB.get(id);
    }
}
