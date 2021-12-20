package com.example.transferservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionDTO {

    private String SourceAccountId;
    private String AccountsIDToBeCredit;
    private String transactionAmount;

}
