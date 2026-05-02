package com.example.transactionservice.Controller;

import com.example.transactionservice.DTO.TransactionRequest;
import com.example.transactionservice.Model.Transaction;
import com.example.transactionservice.Service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public Transaction saveTransaction(@RequestBody TransactionRequest transactionRequest)
    {
        return transactionService.saveTransaction(transactionRequest);
    }


}
