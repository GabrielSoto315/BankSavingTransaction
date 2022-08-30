package com.Bank.BankSavingTransaction.Controllers;

import com.Bank.BankSavingTransaction.Models.Documents.SavingTransaction;
import com.Bank.BankSavingTransaction.Models.Entities.ResponseHandler;
import com.Bank.BankSavingTransaction.Service.SavingTransactionService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/SavingTransaction/")
public class SavingTransactionController {

    @Autowired
    private SavingTransactionService savingTransactionService;


    private static final Logger log = LoggerFactory.getLogger(SavingTransactionController.class);

    /**
     * Lista todos las transacciones encontradas
     * @return
     */
    @GetMapping()
    public Mono<ResponseHandler> getAll(){
        return savingTransactionService.findAll();
    }

    /**
     * Obtener resultado por id de transaccion
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Mono<ResponseHandler> findbyId(@PathVariable("id") String id){
        return savingTransactionService.find(id);
    }

    /**
     * Obtener resultado por id de credito
     * @param idCredit
     * @return
     */
    @GetMapping("/Saving/{idSaving}")
    public Mono<ResponseHandler> findbySaving(@PathVariable("idSaving") String idCredit){
        return savingTransactionService.findAllbySaving(idCredit);
    }

    /**
     * Actualizar datos de transaccion
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public Mono<ResponseHandler> update(@PathVariable("id") String id, @RequestBody SavingTransaction savingTransaction) {
        return savingTransactionService.update(id, savingTransaction);
    }

    /**
     * Borrar datos por id
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseHandler>  deletebyId(@PathVariable("id") String id){
        return savingTransactionService.delete(id);
    }

    /**
     * Guardar transaccion de deposito
     * @param oSavingTransaction
     * @return
     */
    @PostMapping("Add/")
    public Mono<ResponseHandler> saveAddFunds(@RequestBody SavingTransaction oSavingTransaction){
        return savingTransactionService.RegisterAddFunds(oSavingTransaction);
    }

    /**
     * Guardar transaccion de retiro
     * @param oSavingTransaction
     * @return
     */
    @PostMapping("Retire/")
    public Mono<ResponseHandler> saveRetireFunds(@RequestBody SavingTransaction oSavingTransaction){
         return savingTransactionService.RegisterRetireFunds(oSavingTransaction);
    }
    
}