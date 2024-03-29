package com.example.money.rest;

import com.example.money.entity.Currency;
import com.example.money.services.CurrenciesService;
import com.example.money.services.CurrenciesServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("currencies")
public class CurrencyController {
    private CurrenciesService currencyService;
    public CurrencyController(CurrenciesService currencyService){
        this.currencyService = currencyService;
    }
    @GetMapping("/{code}")
    public Currency getValue(@PathVariable String code){
        return currencyService.getCurrency(code);
    }
}
