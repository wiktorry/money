package com.example.money;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
@Service
public class CurrencyImpl implements Currency{
    private HashMap<String, Float> values;
    public CurrencyImpl() {
        this.values = new HashMap<String, Float>();
        values.put("euro", 4.31F);
        values.put("dollar", 3.97F);
        values.put("zloty", 1F);
    }
    public Float getValue(String currency){
        return values.get(currency);
    }
}
