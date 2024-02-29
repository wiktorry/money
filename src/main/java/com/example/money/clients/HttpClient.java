package com.example.money.clients;


import com.example.money.entity.Currency;
import com.example.money.entity.CurrencyTable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.Reader;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;

@Service
public class HttpClient {
    private WebClient webClient;
    public HttpClient(){
        webClient = WebClient.create();
    }
    public Mono<CurrencyTable[]> request(HttpMethod http, String uri){
        WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec = webClient.method(http);
        WebClient.RequestBodySpec bodySpec = uriSpec.uri(uri);
        WebClient.ResponseSpec responseSpec = bodySpec
                .header(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(StandardCharsets.UTF_8)
                .ifNoneMatch("*")
                .ifModifiedSince(ZonedDateTime.now())
                .retrieve();
        return bodySpec.exchangeToMono(response -> {
            if (response.statusCode().equals(HttpStatus.OK)) {
                return response.bodyToMono(CurrencyTable[].class);
            }
            else {
                return response.createException().flatMap(Mono::error);
            }
        });
    }
}
