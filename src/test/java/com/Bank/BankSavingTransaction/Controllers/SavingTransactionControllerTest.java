package com.Bank.BankSavingTransaction.Controllers;

import com.Bank.BankSavingTransaction.Mock.SavingTransactionMock;
import com.Bank.BankSavingTransaction.Models.Documents.SavingTransaction;
import com.Bank.BankSavingTransaction.Models.Entities.ResponseHandler;
import com.Bank.BankSavingTransaction.Service.SavingTransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
@WebFluxTest(SavingTransactionController.class)
public class SavingTransactionControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private SavingTransactionService savingTransactionService;

    @Test
    void findAllTest() {

        SavingTransaction savingTransaction = SavingTransactionMock.randomTransactionAdd();

        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setMessage("Ok");
        responseHandler.setStatus(HttpStatus.OK);
        responseHandler.setData(savingTransaction);

        Mockito
                .when(savingTransactionService.findAll())
                .thenReturn(Mono.just(responseHandler));

        webClient
                .get().uri("/api/SavingTransaction/")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(ResponseHandler.class);
    }

    @Test
    void findByIdTest() {
        SavingTransaction savingTransaction = SavingTransactionMock.randomTransactionAdd();

        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setMessage("Ok");
        responseHandler.setStatus(HttpStatus.OK);
        responseHandler.setData(savingTransaction);

        Mockito
                .when(savingTransactionService.find("630d920bf4c74f27ae62fcd4"))
                .thenReturn(Mono.just(responseHandler));

        webClient.get().uri("/api/SavingTransaction/{id}", "630d920bf4c74f27ae62fcd4")
                .exchange()
                .expectStatus().isOk()
                .expectBody(ResponseHandler.class);

        Mockito.verify(savingTransactionService, times(1)).find("630d920bf4c74f27ae62fcd4");
    }

    @Test
    void findByAccountTest() {
        SavingTransaction savingTransaction = SavingTransactionMock.randomTransactionAdd();

        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setMessage("Ok");
        responseHandler.setStatus(HttpStatus.OK);
        responseHandler.setData(savingTransaction);

        Mockito
                .when(savingTransactionService.findAllbySaving("10210000000001"))
                .thenReturn(Mono.just(responseHandler));

        webClient.get().uri("/api/SavingTransaction/Saving/{id}", "10210000000001")
                .exchange()
                .expectStatus().isOk()
                .expectBody(ResponseHandler.class);

        Mockito.verify(savingTransactionService, times(1)).findAllbySaving("10210000000001");
    }


    @Test
    void createAddFundsTest() {

        SavingTransaction savingTransaction = SavingTransactionMock.randomTransactionAdd();

        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setMessage("Ok");
        responseHandler.setStatus(HttpStatus.OK);
        responseHandler.setData(savingTransaction);

        Mockito
                .when(savingTransactionService.RegisterAddFunds(savingTransaction)).thenReturn(Mono.just(responseHandler));

        webClient
                .post()
                .uri("/api/SavingTransaction/Add/")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(savingTransaction))
                .exchange()
                .expectStatus().isOk();

    }

    @Test
    void createRetireFundsTest() {

        SavingTransaction savingTransaction = SavingTransactionMock.randomTransactionRetire();

        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setMessage("Ok");
        responseHandler.setStatus(HttpStatus.OK);
        responseHandler.setData(savingTransaction);

        Mockito
                .when(savingTransactionService.RegisterAddFunds(savingTransaction)).thenReturn(Mono.just(responseHandler));

        webClient
                .post()
                .uri("/api/SavingTransaction/Retire/")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(savingTransaction))
                .exchange()
                .expectStatus().isOk();
    }


    @Test
    void updateTest() {

        SavingTransaction savingTransaction = SavingTransactionMock.randomTransactionAdd();

        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setMessage("Ok");
        responseHandler.setStatus(HttpStatus.OK);
        responseHandler.setData(savingTransaction);

        Mockito
                .when(savingTransactionService.update("630d920bf4c74f27ae62fcd4", savingTransaction)).thenReturn(Mono.just(responseHandler));

        webClient
                .put()
                .uri("/api/SavingTransaction/{id}", "630d920bf4c74f27ae62fcd4")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(savingTransaction))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void deleteTest() {

        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setMessage("Ok");
        responseHandler.setStatus(HttpStatus.OK);
        responseHandler.setData(null);

        Mockito
                .when(savingTransactionService.delete("630d920bf4c74f27ae62fcd4"))
                .thenReturn(Mono.just(responseHandler));

        webClient.delete().uri("/api/SavingTransaction/{id}", "630d920bf4c74f27ae62fcd4")
                .exchange()
                .expectStatus().isOk();
    }
    
}
