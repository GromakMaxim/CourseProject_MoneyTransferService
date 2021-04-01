package com.example.mtservice1.repository;


import com.example.mtservice1.model.Payment;
import com.example.mtservice1.model.TransactionStatus;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TransactionRepository implements Repo {
    private static final Logger log = Logger.getLogger(TransactionRepository.class);
    private Map<String, Payment> repository = new ConcurrentHashMap<>();

    @Override
    public void save(Payment payment) {
        log.info("Saved: " + payment.getCode() + " " + payment.toString());
        repository.put(payment.getCode(), payment);
    }

    @Override
    public void show() {

    }

    public void updatePaymentStatus(String code) {
        Payment payment = repository.get(code);
        payment.setStatus(TransactionStatus.ACCEPTED);
        log.info("Status changed for #" + code + " " + payment.toString());
    }

    public boolean isRegistered(String code) {
        return repository.get(code) != null;
    }
}
