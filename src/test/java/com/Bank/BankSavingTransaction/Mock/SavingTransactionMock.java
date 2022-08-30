package com.Bank.BankSavingTransaction.Mock;

import com.Bank.BankSavingTransaction.Models.Documents.SavingTransaction;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class SavingTransactionMock {

    public static SavingTransaction randomTransactionAdd(){

        SavingTransaction savingTransaction = new SavingTransaction();
        savingTransaction.setIdTransaction("630d920bf4c74f27ae62fcd4");
        savingTransaction.setIdSaving("10210000000001");
        savingTransaction.setIdClient("1910000000003");
        savingTransaction.setTransactionDate(new Date());
        savingTransaction.setType("Add Funds");
        savingTransaction.setActive(true);
        savingTransaction.setAmount(new BigDecimal(400));
        savingTransaction.setOldBalance(new BigDecimal(0));
        savingTransaction.setNewBalance(new BigDecimal(400));

        return savingTransaction;
    }
    public static SavingTransaction randomTransactionRetire(){

        SavingTransaction savingTransaction = new SavingTransaction();
        savingTransaction.setIdTransaction(UUID.randomUUID().toString());
        savingTransaction.setIdSaving("10210000000001");
        savingTransaction.setIdClient("1910000000005");
        savingTransaction.setTransactionDate(new Date());
        savingTransaction.setType("Retire Funds");
        savingTransaction.setActive(true);
        savingTransaction.setAmount(new BigDecimal(500));
        savingTransaction.setOldBalance(new BigDecimal(8000));
        savingTransaction.setNewBalance(new BigDecimal(7500));

        return savingTransaction;
    }
}
