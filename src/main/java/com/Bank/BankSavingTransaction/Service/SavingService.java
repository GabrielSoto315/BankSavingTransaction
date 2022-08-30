package com.Bank.BankSavingTransaction.Service;

import com.Bank.BankSavingTransaction.Models.Service.Saving;
import com.Bank.BankSavingTransaction.Models.Service.SavingResponse;
import reactor.core.publisher.Mono;


public interface SavingService {

    Mono<SavingResponse> FindSaving(String id);
    Mono<SavingResponse> UpdateSaving(Saving oSaving);

}
