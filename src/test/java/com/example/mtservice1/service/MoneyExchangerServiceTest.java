package com.example.mtservice1.service;

import com.example.mtservice1.model.card.Amount;
import com.example.mtservice1.model.transaction.Transaction;
import com.example.mtservice1.model.transaction.TransactionStatus;
import com.example.mtservice1.repository.CardsDB;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.*;

class MoneyExchangerServiceTest {
    private CardsDB db;
    private MoneyExchangerService mes;

    @Test
    void process() {
        db = new CardsDB();
        mes = new MoneyExchangerService(db);

        Transaction tr = new Transaction("5610591081018250",
                "5019717010103742",
                "123",
                "11/25",
                new Amount("RUR", 10_000L));
        tr.setStatus(TransactionStatus.ACCEPTED);
        AtomicLong actualBalance = db.getCardByNumber("5019717010103742").getBalance();
        mes.process(tr);
        assertEquals(20_000, actualBalance.intValue());
    }

    @Test
    void process_expectedFailExchange() {
        db = new CardsDB();
        mes = new MoneyExchangerService(db);

        Transaction tr = new Transaction("5610591081018250",
                "5019717010103742",
                "123",
                "11/25",
                new Amount("RUR", 10_000L));
        //tr.setStatus(TransactionStatus.ACCEPTED);
        AtomicLong actualBalance = db.getCardByNumber("5019717010103742").getBalance();
        mes.process(tr);

        //exchange wasn't success because of transaction status
        assertEquals(10_000, actualBalance.intValue());
    }
}