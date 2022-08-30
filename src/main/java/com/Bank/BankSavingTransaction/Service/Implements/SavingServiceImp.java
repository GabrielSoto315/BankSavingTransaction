package com.Bank.BankSavingTransaction.Service.Implements;

import com.Bank.BankSavingTransaction.Models.Service.Saving;
import com.Bank.BankSavingTransaction.Models.Service.SavingResponse;
import com.Bank.BankSavingTransaction.Service.SavingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class SavingServiceImp implements SavingService {
    private static final Logger log = LoggerFactory.getLogger(SavingServiceImp.class);

    public Mono<SavingResponse> FindSaving(String id){
        String url = "http://localhost:18084/api/Saving/"+id;
        Mono<SavingResponse> oCreditMono = WebClient.create()
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(SavingResponse.class);
        oCreditMono.subscribe(client -> log.info(client.toString()));
        return oCreditMono;
    }

    public Mono<SavingResponse> UpdateSaving(Saving oSaving){
        String url = "http://localhost:18084/api/Saving/"+ oSaving.getIdSaving();
        Mono<SavingResponse> oCreditMono = WebClient.create()
                .put()
                .uri(url)
                .body(Mono.just(oSaving), Saving.class)
                .retrieve()
                .bodyToMono(SavingResponse.class);
        oCreditMono.subscribe(client -> log.info(client.toString()));
        return oCreditMono;
    }


}
