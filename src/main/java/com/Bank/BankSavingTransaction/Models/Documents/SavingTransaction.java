package com.Bank.BankSavingTransaction.Models.Documents;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "SavingTransaction")
@Data
public class SavingTransaction {
    @Id
    private String idTransaction;
    private String idSaving;
    private Date transactionDate;
    private BigDecimal amount;
    private String type;
    private Boolean active;
    private BigDecimal oldBalance;
    private BigDecimal newBalance;
    private String idClient;
}
