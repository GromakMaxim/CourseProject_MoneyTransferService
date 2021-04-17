package com.example.mtservice1.repository;

import com.example.mtservice1.model.card.Amount;
import com.example.mtservice1.model.transaction.Transaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class TransactionDBTest {
    private TransactionDB transactionDB = new TransactionDB();

    @Test
    void save_expectTrue() {
        Transaction tr = new Transaction("11111", "22222", "123", "11/25", new Amount("RUR", 10_000L));
        transactionDB.save("100", tr);
        assertTrue(transactionDB.getTransactionByID("100").getCardFromNumber().equalsIgnoreCase("11111"));
    }

    @Test
    void save_expectNull() {
        Transaction tr = new Transaction("11111", "22222", "123", "11/25", new Amount("RUR", 10_000L));
        transactionDB.save("100", tr);
        assertNull(transactionDB.getTransactionByID("101"));
    }


    @Test
    void isExist_expectTrue() {
        Transaction tr = new Transaction("11111", "22222", "123", "11/25", new Amount("RUR", 10_000L));
        transactionDB.save("100", tr);
        assertTrue(transactionDB.isExist("100"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"101","102","103","104","201","srfrwe","200","300","200"})
    void isExist_expectFalse(String args) {
        Transaction tr = new Transaction("11111", "22222", "123", "11/25", new Amount("RUR", 10_000L));
        transactionDB.save("100", tr);
        assertFalse(transactionDB.isExist(args));
    }



    @Test
    void getTransactionByID_expectNotNull() {
        Transaction tr = new Transaction("11111", "22222", "123", "11/25", new Amount("RUR", 10_000L));
        transactionDB.save("100", tr);
        Transaction actual = transactionDB.getTransactionByID("100");
        assertNotNull(actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {"101","102","103","104","201","srfrwe","200","300","200"})
    void getTransactionByID_expectNull(String args) {
        Transaction tr = new Transaction("11111", "22222", "123", "11/25", new Amount("RUR", 10_000L));
        transactionDB.save("100", tr);
        Transaction actual = transactionDB.getTransactionByID(args);
        assertNull(actual);
    }
}