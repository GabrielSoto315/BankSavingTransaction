package com.Bank.BankSavingTransaction.Service;

import com.Bank.BankSavingTransaction.Mock.SavingMock;
import com.Bank.BankSavingTransaction.Mock.SavingTransactionMock;
import com.Bank.BankSavingTransaction.Models.Documents.SavingTransaction;
import com.Bank.BankSavingTransaction.Models.Service.Saving;
import com.Bank.BankSavingTransaction.Models.Service.SavingResponse;
import com.Bank.BankSavingTransaction.Repository.ISavingTransactionRepository;
import com.Bank.BankSavingTransaction.Service.Implements.SavingServiceImp;
import com.Bank.BankSavingTransaction.Service.Implements.SavingTransactionServiceImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;


@ExtendWith(SpringExtension.class)
public class SavingTransactionServiceTest {

    @InjectMocks
    private SavingServiceImp savingServiceImp;
    @InjectMocks
    private SavingTransactionServiceImp savingTransactionServiceImp;
    @Mock
    private ISavingTransactionRepository savingTransactionRepository;
    @Mock
    private SavingService SavingService;

    @Test
    void findAllTest() {
        SavingTransaction savingTransaction = SavingTransactionMock.randomTransactionAdd();

        Mockito
                .when(savingTransactionRepository.findAll())
                .thenReturn(Flux.just(savingTransaction));

        StepVerifier.create(savingTransactionServiceImp.findAll())
                .expectNextMatches(responseHandler -> responseHandler.getData() != null)
                .verifyComplete();
    }

    @Test
    void findByIdTest() {
        SavingTransaction savingTransaction = SavingTransactionMock.randomTransactionAdd();

        Mockito
                .when(savingTransactionRepository.existsById(savingTransaction.getIdTransaction()))
                .thenReturn(Mono.just(true));

        Mockito
                .when(savingTransactionRepository.findById(savingTransaction.getIdTransaction()))
                .thenReturn(Mono.just(savingTransaction));

        StepVerifier.create(savingTransactionServiceImp.find(savingTransaction.getIdTransaction()))
                .expectNextMatches(responseHandler -> responseHandler.getMessage().equals("Done"))
                .verifyComplete();
    }

    @Test
    void findBySavingTest() {
        SavingTransaction savingTransaction = SavingTransactionMock.randomTransactionAdd();

        Mockito
                .when(savingTransactionRepository.findAll())
                .thenReturn(Flux.just(savingTransaction));

        StepVerifier.create(savingTransactionServiceImp.findAllbySaving(savingTransaction.getIdSaving()))
                .expectNextMatches(responseHandler -> responseHandler.getMessage().equals("Done"))
                .verifyComplete();
    }

    @Test
    void updateTest() {
        SavingTransaction savingTransaction = SavingTransactionMock.randomTransactionAdd();

        Mockito
                .when(savingTransactionRepository.existsById(savingTransaction.getIdTransaction()))
                .thenReturn(Mono.just(true));

        Mockito
                .when(savingTransactionRepository.findById(savingTransaction.getIdTransaction()))
                .thenReturn(Mono.just(savingTransaction));

        Mockito
                .when(savingTransactionRepository.save(savingTransaction))
                .thenReturn(Mono.just(savingTransaction));

        StepVerifier.create(savingTransactionServiceImp.update(savingTransaction.getIdTransaction(), savingTransaction))
                .expectNextMatches(x -> x.getMessage().equals("Done"))
                .expectComplete()
                .verify();
    }

    @Test
    void updateNotFoundTest() {
        SavingTransaction savingTransaction = SavingTransactionMock.randomTransactionAdd();

        Mockito
                .when(savingTransactionRepository.existsById("630d920bf4c74f27ae62fcd4"))
                .thenReturn(Mono.just(false));

        StepVerifier.create(savingTransactionServiceImp.update(savingTransaction.getIdTransaction(), savingTransaction))
                .expectNextMatches(x -> x.getMessage().equals("Not found"))
                .expectComplete()
                .verify();

    }

    @Test
    void deleteTest() {
        SavingTransaction savingTransaction = SavingTransactionMock.randomTransactionAdd();

        Mockito
                .when(savingTransactionRepository.existsById(savingTransaction.getIdTransaction()))
                .thenReturn(Mono.just(true));

        Mockito
                .when(savingTransactionRepository.findById(savingTransaction.getIdTransaction()))
                .thenReturn(Mono.just(savingTransaction));

        Mockito
                .when(savingTransactionRepository.save(savingTransaction))
                .thenReturn(Mono.just(savingTransaction));

        StepVerifier.create(savingTransactionServiceImp.delete(savingTransaction.getIdTransaction()))
                .expectNextMatches(x -> x.getMessage().equals("Done"))
                .expectComplete()
                .verify();
    }


    @Test
    void deleteNotFoundTest() {
        SavingTransaction savingTransaction = SavingTransactionMock.randomTransactionAdd();

        Mockito
                .when(savingTransactionRepository.existsById(savingTransaction.getIdTransaction()))
                .thenReturn(Mono.just(false));

        StepVerifier.create(savingTransactionServiceImp.delete(savingTransaction.getIdTransaction()))
                .expectNextMatches(x -> x.getMessage().equals("Not found"))
                .expectComplete()
                .verify();
    }

    @Test
    void addFundsTest() {
        SavingTransaction savingTransaction = SavingTransactionMock.randomTransactionAdd();
        Saving saving = SavingMock.randomAccount();

        SavingResponse savingResponseMono = new SavingResponse();
        savingResponseMono.setMessage("Done");
        savingResponseMono.setStatus("OK");
        savingResponseMono.setData(saving);

        Saving updateSaving = new Saving();
        updateSaving.setIdSaving(saving.getIdSaving());
        updateSaving.setBalance(saving.getBalance().add(savingTransaction.getAmount()));

        Mockito.when(savingTransactionRepository.save(savingTransaction))
                .thenReturn(Mono.just(savingTransaction));

        Mockito.when(SavingService.FindSaving(saving.getIdSaving()))
                .thenReturn(Mono.just(savingResponseMono));

        Mockito.when(SavingService.UpdateSaving(updateSaving))
                .thenReturn(Mono.just(savingResponseMono));

        StepVerifier.create(savingTransactionServiceImp.RegisterAddFunds(savingTransaction))
                .expectNextMatches(x -> x.getMessage().equals("Done"))
                .expectComplete()
                .verify();
    }

    @Test
    void addFundsNotFoundTest() {
        SavingTransaction savingTransaction = SavingTransactionMock.randomTransactionAdd();

        SavingResponse savingResponseMono = new SavingResponse();
        savingResponseMono.setMessage("Not found");
        savingResponseMono.setStatus("NOT FOUND");
        savingResponseMono.setData(null);

        Mockito.when(SavingService.FindSaving(savingTransaction.getIdSaving()))
                .thenReturn(Mono.just(savingResponseMono));

        StepVerifier.create(savingTransactionServiceImp.RegisterAddFunds(savingTransaction))
                .expectNextMatches(x -> x.getMessage().equals("Account not found"))
                .expectComplete()
                .verify();
    }


    @Test
    void retireFundsTest() {
        SavingTransaction savingTransaction = SavingTransactionMock.randomTransactionRetire();
        Saving saving = SavingMock.randomSaving();

        SavingResponse savingResponseMono = new SavingResponse();
        savingResponseMono.setMessage("Done");
        savingResponseMono.setStatus("OK");
        savingResponseMono.setData(saving);

        Saving updateSaving = new Saving();
        updateSaving.setIdSaving("10210000000001");
        updateSaving.setBalance(saving.getBalance().add(savingTransaction.getAmount().negate()));

        Mockito.when(savingTransactionRepository.save(savingTransaction))
                .thenReturn(Mono.just(savingTransaction));

        Mockito.when(SavingService.FindSaving("10210000000001"))
                .thenReturn(Mono.just(savingResponseMono));

        Mockito.when(SavingService.UpdateSaving(updateSaving))
                .thenReturn(Mono.just(savingResponseMono));

        StepVerifier.create(savingTransactionServiceImp.RegisterRetireFunds(savingTransaction))
                .expectNextMatches(x -> x.getMessage().equals("Done"))
                .expectComplete()
                .verify();
    }

    @Test
    void retireFundsNotFoundTest() {
        SavingTransaction savingTransaction = SavingTransactionMock.randomTransactionRetire();

        SavingResponse savingResponseMono = new SavingResponse();
        savingResponseMono.setMessage("Not found");
        savingResponseMono.setStatus("NOT FOUND");
        savingResponseMono.setData(null);

        Mockito.when(SavingService.FindSaving(savingTransaction.getIdSaving()))
                .thenReturn(Mono.just(savingResponseMono));

        StepVerifier.create(savingTransactionServiceImp.RegisterRetireFunds(savingTransaction))
                .expectNextMatches(x -> x.getMessage().equals("Account not found"))
                .expectComplete()
                .verify();
    }

    @Test
    void retireFundsNotFundsTest() {
        SavingTransaction savingTransaction = SavingTransactionMock.randomTransactionRetire();
        Saving saving = SavingMock.randomSaving();

        savingTransaction.setAmount(saving.getBalance().add(new BigDecimal(1000)));

        SavingResponse savingResponseMono = new SavingResponse();
        savingResponseMono.setMessage("Done");
        savingResponseMono.setStatus("OK");
        savingResponseMono.setData(saving);

        Mockito.when(savingTransactionRepository.save(savingTransaction))
                .thenReturn(Mono.just(savingTransaction));

        Mockito.when(SavingService.FindSaving("10210000000001"))
                .thenReturn(Mono.just(savingResponseMono));

        StepVerifier.create(savingTransactionServiceImp.RegisterRetireFunds(savingTransaction))
                .expectNextMatches(x -> x.getMessage().equals("Don't have enough funds"))
                .expectComplete()
                .verify();
    }
}
