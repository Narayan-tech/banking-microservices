package com.example.account_service.Controller;


import com.example.account_service.DTO.*;
import com.example.account_service.Model.Account;
import com.example.account_service.Service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {


    @Autowired
    private AccountService accountService;


    //create account
    @PostMapping
    public ApiResponse<Account> createAccount(@Valid @RequestBody AccountRequest accountRequest) {
        Account account = accountService.createAccount(accountRequest);
        return new ApiResponse<>("Account created successfully",account);
    }

    //get a record by an id
    @GetMapping("/{id}")
    public Account retrieveAccountById(@PathVariable long id) {
        return accountService.retrieveAccountById(id);
    }


    //get a record by account number
    @GetMapping("/number/{accountNumber}")
    public Account retrieveAccountByAccountNumber(@PathVariable String accountNumber) {
        return accountService.retrieveAccountByAccountNumber(accountNumber);
    }

    //depositing the amount
    @PostMapping("/deposit")
    public ApiResponse<Account> depositAmountByAccountNumber(@Valid  @RequestBody DepositRequest depositRequest) {
        Account account =  accountService.depositAmountByAccountNumber(depositRequest);
        return new ApiResponse<>("Deposited successfully",account);
    }

    //withdrawing the amount
    @PostMapping("/withdraw")
    public ApiResponse<Account> withdrawAmountByAccountNumber(@Valid @RequestBody WithdrawRequest withdrawRequest) {
        Account account = accountService.withdrawAmountByAccountNumber(withdrawRequest);
        return new ApiResponse<>("Withdrawal successful",account);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id)
    {
        accountService.deleteById(id);
        return ResponseEntity.ok("Account deleted successfully");
    }




}
