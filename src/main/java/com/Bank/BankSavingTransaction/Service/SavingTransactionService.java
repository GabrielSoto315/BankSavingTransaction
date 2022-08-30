package com.Bank.BankSavingTransaction.Service;

import com.Bank.BankSavingTransaction.Models.Documents.SavingTransaction;
import com.Bank.BankSavingTransaction.Models.Entities.ResponseHandler;
import reactor.core.publisher.Mono;

public interface SavingTransactionService {

    Mono<ResponseHandler> findAll();
    Mono<ResponseHandler> find(String id);
    Mono<ResponseHandler> findAllbySaving(String idSaving);
    Mono<ResponseHandler> update(String id, SavingTransaction savingTransaction);
    Mono<ResponseHandler> delete(String id);
    Mono<ResponseHandler> RegisterAddFunds(SavingTransaction savingTransaction);
    Mono<ResponseHandler> RegisterRetireFunds(SavingTransaction oTransaction);

}
