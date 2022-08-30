package com.Bank.BankSavingTransaction.Service.Implements;

import com.Bank.BankSavingTransaction.Models.Documents.SavingTransaction;
import com.Bank.BankSavingTransaction.Models.Entities.ResponseHandler;
import com.Bank.BankSavingTransaction.Models.Service.Saving;
import com.Bank.BankSavingTransaction.Repository.ISavingTransactionRepository;
import com.Bank.BankSavingTransaction.Service.SavingService;
import com.Bank.BankSavingTransaction.Service.SavingTransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class SavingTransactionServiceImp implements SavingTransactionService {

    @Autowired
    private ISavingTransactionRepository creditTransactionRepository;
    @Autowired
    private SavingService savingService;

    private static final Logger log = LoggerFactory.getLogger(SavingTransactionServiceImp.class);


    @Override
    public Mono<ResponseHandler> findAll() {
        return creditTransactionRepository.findAll()
                .doOnNext(n -> log.info(n.toString()))
                .collectList()
                .map(x -> new ResponseHandler("Done", HttpStatus.OK, x))
                .onErrorResume(error -> Mono.just(new ResponseHandler(error.getMessage(), HttpStatus.BAD_REQUEST, null)));
    }

    @Override
    public Mono<ResponseHandler> find(String id) {
        return creditTransactionRepository.findById(id)
                .doOnNext(n -> log.info(n.toString()))
                .map(x -> new ResponseHandler("Done", HttpStatus.OK, x))
                .onErrorResume(error -> Mono.just(new ResponseHandler(error.getMessage(), HttpStatus.BAD_REQUEST, null)));
    }

    @Override
    public Mono<ResponseHandler> findAllbySaving(String idSaving) {
        return creditTransactionRepository.findAll()
                .doOnNext(n -> log.info(n.toString()))
                .filter(f -> f.getIdSaving().equals(idSaving))
                .collectList()
                .map(x -> new ResponseHandler("Done", HttpStatus.OK, x))
                .onErrorResume(error -> Mono.just(new ResponseHandler(error.getMessage(), HttpStatus.BAD_REQUEST, null)));
    }

    @Override
    public Mono<ResponseHandler> update(String id, SavingTransaction savingTransaction) {
        return creditTransactionRepository.existsById(id).flatMap(check -> {
            if (check) {
                return creditTransactionRepository.findById(id)
                        .flatMap(x -> {
                            x.setIdClient(savingTransaction.getIdClient());
                            return creditTransactionRepository.save(x)
                                    .map(y -> new ResponseHandler("Done", HttpStatus.OK, y))
                                    .onErrorResume(error -> Mono.just(new ResponseHandler(error.getMessage(), HttpStatus.BAD_REQUEST, null)));
                        });
            }
            else
                return Mono.just(new ResponseHandler("Not found", HttpStatus.NOT_FOUND, null));
        });
    }

    @Override
    public Mono<ResponseHandler> delete(String id) {
        return creditTransactionRepository.existsById(id).flatMap(check ->{
            if (Boolean.TRUE.equals(check)){
                return creditTransactionRepository.findById(id).flatMap(x ->{
                    x.setActive(false);
                    return creditTransactionRepository.save(x)
                            .then(Mono.just(new ResponseHandler("Done", HttpStatus.OK, null)));
                });
            }
            else {
                return Mono.just(new ResponseHandler("Not found", HttpStatus.NOT_FOUND,null));
            }
        });
    }

    @Override
    public Mono<ResponseHandler> RegisterAddFunds(SavingTransaction savingTransaction) {

        log.info("Start Add funds transaction");
        return savingService.FindSaving(savingTransaction.getIdSaving()).flatMap(credit -> {
            if (credit.getData() == null){
                return Mono.just(new ResponseHandler("Account not found", HttpStatus.NOT_FOUND, null));
            }else {
                savingTransaction.setIdClient(savingTransaction.getIdClient() == null? credit.getData().getIdClient() : savingTransaction.getIdClient());
                savingTransaction.setTransactionDate(new Date());}
                savingTransaction.setType("Add Funds");
                savingTransaction.setActive(true);
                savingTransaction.setOldBalance(credit.getData().getBalance());
                savingTransaction.setNewBalance(credit.getData().getBalance().add(savingTransaction.getAmount()));
                log.info("Transaction: " + savingTransaction);
                return creditTransactionRepository.save(savingTransaction).flatMap(trans ->{
                    Saving updateSaving = new Saving();
                    updateSaving.setIdSaving(trans.getIdSaving());
                    updateSaving.setBalance(trans.getNewBalance());
                    log.info("Credit changes: " + updateSaving);
                    return savingService.UpdateSaving(updateSaving).flatMap(up ->{
                       return Mono.just(new ResponseHandler("Done", HttpStatus.OK, up));
                    });
                });
            });
    }

    @Override
    public Mono<ResponseHandler> RegisterRetireFunds(SavingTransaction oTransaction) {
        log.info("Start retire funds transaction");
        return savingService.FindSaving(oTransaction.getIdSaving()).flatMap(creditResponse -> {
            if (creditResponse.getData() == null){
                return Mono.just(new ResponseHandler("Account not found", HttpStatus.NOT_FOUND, null));
            }else {
                    if (creditResponse.getData().getBalance().add(oTransaction.getAmount().negate()).compareTo(BigDecimal.ZERO) < 0){
                        return Mono.just(new ResponseHandler("Don't have enough funds", HttpStatus.NOT_FOUND, null));
                    }else {
                        oTransaction.setIdClient(oTransaction.getIdClient() == null? creditResponse.getData().getIdClient() : oTransaction.getIdClient());
                        oTransaction.setTransactionDate(new Date());
                        oTransaction.setType("Retire Funds");
                        oTransaction.setActive(true);
                        oTransaction.setOldBalance(creditResponse.getData().getBalance());
                        oTransaction.setNewBalance(creditResponse.getData().getBalance().add(oTransaction.getAmount().negate()));
                        return creditTransactionRepository.save(oTransaction).flatMap(tran ->{
                            log.info("Transaction saved" + oTransaction.toString());
                            Saving updateSaving = new Saving();
                            updateSaving.setIdSaving(tran.getIdSaving());
                            updateSaving.setBalance(tran.getNewBalance());
                            return savingService.UpdateSaving(updateSaving).flatMap(up ->{
                                return Mono.just(new ResponseHandler("Done", HttpStatus.OK, up));
                            });
                        });
                    }
            }
        });
    }
}
