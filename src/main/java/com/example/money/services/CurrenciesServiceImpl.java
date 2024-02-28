package com.example.money.services;

import com.example.money.clients.HttpClient;
import com.example.money.entity.Currency;
import com.example.money.exceptions.CurrencyNotFoundException;
import com.example.money.repositories.CurrencyRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.Reader;
import java.io.Serializable;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CurrenciesServiceImpl implements CurrenciesService {
    private final CurrencyRepository currencyRepository;
    private HttpClient httpClient;
    private List<Currency> values;

    public CurrenciesServiceImpl(CurrencyRepository currencyRepository, HttpClient httpClient){
        this.currencyRepository = currencyRepository;
        this.httpClient = httpClient;
    }

    @Override
    @Scheduled(fixedRate = 60000)
    public void updateValues(){
        Mono<String> response = httpClient.request(HttpMethod.GET, "http://api.nbp.pl/api/exchangerates/tables/a/");
        values.add(new Currency("euro", 4.31F));
        values.add(new Currency("dollar", 3.97F));
        values.add(new Currency("zloty", 1F));
        values.forEach(currencyRepository::save);
        System.out.println("saved");
    }
    @Override
    public Float getValue(String currency){
        Currency cur = currencyRepository.findById(currency).orElseThrow(() -> new CurrencyNotFoundException("We don't have this value in our database"));
        return cur.getValue();
    }
}
