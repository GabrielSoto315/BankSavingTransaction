package com.Bank.BankSavingTransaction.Models.Service;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@Data
public class Saving {
    @Id
    private String idSaving;
    private String type;
    private BigDecimal balance;
    private String idClient;
    private Product product;

}
