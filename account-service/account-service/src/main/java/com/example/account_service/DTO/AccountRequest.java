package com.example.account_service.DTO;

//import org.springframework.boot.convert.DataSizeUnit;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountRequest {

    @NotBlank(message="Account number is required")
    private String accountNumber;

    @NotBlank(message="Account holder name is required")
    private String accountHolderName;

    @NotBlank(message="Email is required")
    @Email(message="Email must be valid")
    private String email;

    @NotNull(message="Balance is required")
    @DecimalMin(value="0.0",message="Balance cannot be negative")
    private BigDecimal balance;

}
