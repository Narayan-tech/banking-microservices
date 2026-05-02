package com.example.fundtransferservice.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class TransactionRequest {

    private String transactionType;
    private String sourceAccountNumber;
    private String targetAccountNumber;
    private BigDecimal amount;
    private String rewards;
}
