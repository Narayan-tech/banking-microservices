package com.example.fundtransferservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class AccountOperationRequest {

    private String accountNumber;
    private BigDecimal amount;
}
