package com.example.money.services;

import com.example.money.clients.HttpClient;
import com.example.money.entity.Currency;
import com.example.money.entity.CurrencyTable;
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
    private List<Currency> rates;

    public CurrenciesServiceImpl(CurrencyRepository currencyRepository, HttpClient httpClient){
        this.currencyRepository = currencyRepository;
        this.httpClient = httpClient;
        rates = new ArrayList<>();
    }

    @Override
    @Scheduled(fixedRate = 60000)
    public void updateValues(){
        Mono<CurrencyTable[]> response = httpClient.request(HttpMethod.GET, "http://api.nbp.pl/api/exchangerates/tables/a/");
        CurrencyTable[] table = response.block();
        if (table != null) {
            rates = Arrays.asList(table[0].getRates());
        }
        for (Currency cur : rates) {
            currencyRepository.save(cur);
        }
    }
    @Override
    public Currency getCurrency(String code){
        return currencyRepository.findById(code).orElseThrow(() -> new CurrencyNotFoundException("We don't have this rate in our database"));
    }
}
