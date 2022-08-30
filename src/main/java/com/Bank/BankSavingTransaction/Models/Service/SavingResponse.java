package com.Bank.BankSavingTransaction.Models.Service;

import lombok.Data;

@Data
public class SavingResponse {
    private String message;
    private String status;
    private Saving data;
}
