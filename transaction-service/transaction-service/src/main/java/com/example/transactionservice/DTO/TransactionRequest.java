package com.example.transactionservice.DTO;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionRequest {

    @NotBlank
    private String transactionType;// deposit, withdraw, fund transfer


    @NotNull
    @DecimalMin(value="0.01",message="Amount cannot be less than zero")
    private BigDecimal amount;


    private String sourceAccountNumber;


    private String targetAccountNumber;

}
