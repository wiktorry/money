package com.example.money.services;

import com.example.money.entity.Currency;

public interface CurrenciesService {
    Float getValue(String currency);
    void updateValues();
}
