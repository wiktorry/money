package com.example.money.services;

import com.example.money.entity.Currency;
import com.example.money.repositories.CurrencyRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

@Service
public class CurrenciesServiceImpl implements CurrenciesService {
    private final CurrencyRepository currencyRepository;
    private List<Currency> values;
    public CurrenciesServiceImpl(CurrencyRepository currencyRepository){
        this.currencyRepository = currencyRepository;
    }
    @Override
    @Scheduled(fixedRate = 60000)
    public void updateValues(){
        WebClient webClient = WebClient.create();
        WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec = webClient.method(HttpMethod.GET);
        WebClient.RequestBodySpec bodySpec = uriSpec.uri("http://api.nbp.pl/api/exchangerates/tables/a/");
        WebClient.ResponseSpec responseSpec = bodySpec
                .header(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(StandardCharsets.UTF_8)
                .ifNoneMatch("*")
                .ifModifiedSince(ZonedDateTime.now())
                .retrieve();
        Mono<String> res = bodySpec.exchangeToMono(response -> {
            if (response.statusCode().equals(HttpStatus.OK)) {
                return response.bodyToMono(String.class);
            }
            else if (response.statusCode().is4xxClientError()) {
                return Mono.just("Error response");
            }
            else {
                return response.createException().flatMap(Mono::error);
            }
        });
        this.values = new ArrayList<>();
        values.add(new Currency("euro", 4.31F));
        values.add(new Currency("dollar", 3.97F));
        values.add(new Currency("zloty", 1F));
        values.forEach(currencyRepository::save);
        System.out.println("saved");
    }
    @Override
    public Float getValue(String currency){
        Currency cur = currencyRepository.findById(currency).orElseThrow(() -> new RuntimeException("We don't have this value"));
        return cur.getValue();
    }
}
