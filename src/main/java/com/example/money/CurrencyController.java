package com.example.money;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("currencies")
public class CurrencyController {
    private CurrencyImpl currency;
    public CurrencyController(CurrencyImpl currency){
        this.currency = currency;
    }
    @GetMapping("/{currency}")
    public String getValue(@PathVariable String currency){
        Float value = this.currency.getValue(currency);
        return currency+ ": " + value;
    }
}
