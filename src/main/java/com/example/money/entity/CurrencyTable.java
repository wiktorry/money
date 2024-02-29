package com.example.money.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Getter
@Setter
public class CurrencyTable {
    private String table;
    private String no;
    private String effectiveDate;
    private Currency[] rates;

}
