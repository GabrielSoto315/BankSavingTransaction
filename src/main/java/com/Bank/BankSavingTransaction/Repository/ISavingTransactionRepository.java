package com.Bank.BankSavingTransaction.Repository;

import com.Bank.BankSavingTransaction.Models.Documents.SavingTransaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISavingTransactionRepository extends ReactiveMongoRepository<SavingTransaction, String> {
}