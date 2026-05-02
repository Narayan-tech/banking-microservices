package com.example.account_service.DTO;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
@Data
public class WithdrawRequest {

    @NotBlank(message="Account number should not be blank")
    private String accountNumber;

    @NotNull(message="Amount cannot be null")
    @DecimalMin(value="0.01",message="Amount cannot be less than zero")
    private BigDecimal amount;
}
