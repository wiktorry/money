package com.example.money.repositories;


import com.example.money.entity.Currency;
import com.example.money.services.CurrenciesService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, String> {
}
