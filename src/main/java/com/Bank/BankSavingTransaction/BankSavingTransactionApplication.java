package com.Bank.BankSavingTransaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class BankSavingTransactionApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankSavingTransactionApplication.class, args);
	}

}
