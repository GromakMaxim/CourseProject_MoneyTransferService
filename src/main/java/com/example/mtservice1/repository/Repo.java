package com.example.mtservice1.repository;

import com.example.mtservice1.model.Payment;

public interface Repo {
    void save (Payment payment);
    void show();
}
