package com.Bank.BankSavingTransaction.Mock;

import com.Bank.BankSavingTransaction.Models.Service.Saving;
import com.Bank.BankSavingTransaction.Models.Service.Product;

import java.math.BigDecimal;

public class SavingMock {

    public static Saving randomAccount(){
        Saving saving = new Saving();
        saving.setIdSaving("10210000000001");
        saving.setIdClient("1910000000004");
        saving.setBalance(BigDecimal.valueOf(400));

        Product product = new Product();
        product.setName("Current Account");
        product.setClientType("Person");

        saving.setProduct(product);

        return saving;
    }

    public static Saving randomSaving(){
        Saving saving = new Saving();
        saving.setIdSaving("10210000000001");
        saving.setIdClient("1910000000004");
        saving.setBalance(BigDecimal.valueOf(8000));

        Product product = new Product();
        product.setName("Saving Account");
        product.setClientType("Person");

        saving.setProduct(product);

        return saving;
    }

    public static Saving randomFixedTerms(){
        Saving saving = new Saving();
        saving.setIdSaving("10210000000001");
        saving.setIdClient("1910000000004");
        saving.setBalance(BigDecimal.valueOf(8000));

        Product product = new Product();
        product.setName("Fixed Terms Account");
        product.setClientType("Person");

        saving.setProduct(product);

        return saving;
    }


}
