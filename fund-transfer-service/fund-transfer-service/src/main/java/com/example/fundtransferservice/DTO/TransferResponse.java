package com.example.fundtransferservice.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferResponse {

    private String sourceAccount;
    private String targetAccount;
    private BigDecimal amount;
    private String status;
    private String message;
}
