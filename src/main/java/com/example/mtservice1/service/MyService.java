package com.example.mtservice1.service;

import com.example.mtservice1.model.Payment;
import com.example.mtservice1.repository.TransactionRepository;

public class MyService {
    private TransactionRepository repository;

    public MyService(TransactionRepository repository) {
        this.repository = repository;
    }

    public void createPaymentOperationAndSave(String code, String cardFrom, String cardTo, String svv, String period, String currency, Long pay) {
        Payment payment = new Payment(code, cardFrom, cardTo, svv, period, currency, pay);
        repository.save(payment);

    }

    public void updatePaymentStatus(String code) {
        repository.updatePaymentStatus(code);
    }
    public boolean isRegistered(String code){
        return repository.isRegistered(code);
    }
}
