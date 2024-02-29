package com.example.money.services;

import com.example.money.entity.Currency;

public interface CurrenciesService {
    Currency getCurrency(String code);
    void updateValues();
}
