package com.example.account_service.Service;


import com.example.account_service.DTO.*;
import com.example.account_service.Model.Account;
import com.example.account_service.Repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;


@Service
public class AccountService {



    @Autowired
    AccountRepository accountRepository;

    private static final Logger log = LoggerFactory.getLogger(AccountService.class);

    public Account createAccount(AccountRequest accountRequest) {

        Account account = new Account();
        account.setAccountNumber(accountRequest.getAccountNumber());
        account.setAccountHolderName(accountRequest.getAccountHolderName());
        account.setEmail(accountRequest.getEmail());
        account.setBalance(accountRequest.getBalance());
        account.setCreatedAt(LocalDateTime.now());


        return accountRepository.save(account);

    }

    public Account retrieveAccountById(long id) {
        return accountRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,"Account is not found with the given id: " + id));
    }

    public Account retrieveAccountByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber).orElseThrow(
                () -> new RuntimeException("Account is not found with the given account number: "+accountNumber)
        );
    }

    public Account depositAmountByAccountNumber(DepositRequest depositRequest) {

        log.info("Deposit started for account {}",depositRequest.getAccountNumber());

        log.info("Deposit amount: {}",depositRequest.getAmount());


        Account account =  accountRepository.findByAccountNumber(depositRequest.getAccountNumber())
                .orElseThrow(() -> new RuntimeException("Account is not found with account number "+ depositRequest.getAccountNumber())
                );

        log.debug("Balance before deposit: {}",account.getBalance());

        account.setBalance(account.getBalance().add(depositRequest.getAmount()));

        log.debug("Balance after deposit: {}",account.getBalance());







        log.info("Deposit success for account: {}",depositRequest.getAccountNumber());
        return accountRepository.save(account);
    }

    public Account withdrawAmountByAccountNumber(WithdrawRequest withdrawRequest) {

        log.info("Withdraw started for account {}",withdrawRequest.getAccountNumber());

        log.debug("Withdraw request: account={}, amount={}",
                withdrawRequest.getAccountNumber(),withdrawRequest.getAmount());

        Account account = accountRepository.findByAccountNumber(withdrawRequest.getAccountNumber())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Account is not found with account number "+ withdrawRequest.getAccountNumber()));

        log.debug("Balance before withdraw: {}",account.getBalance());

        if(account.getBalance().compareTo(withdrawRequest.getAmount()) < 0)
        {
            log.error("Insufficient balance for account {}", withdrawRequest.getAccountNumber());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Insufficient balance");
        }



        account.setBalance(account.getBalance().subtract(withdrawRequest.getAmount()));

        log.debug("Balance after withdraw: {}",account.getBalance());



        log.info("Withdraw is successful for account {}", withdrawRequest.getAccountNumber());
        return accountRepository.save(account);

    }


    public void deleteById(Long id) {
        if(!accountRepository.existsById(id))
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Account not found with id: "+id);
        }
        accountRepository.deleteById(id);
    }
}
