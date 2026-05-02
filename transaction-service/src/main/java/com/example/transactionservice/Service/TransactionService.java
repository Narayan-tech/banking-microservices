package com.example.transactionservice.Service;

import com.example.transactionservice.DTO.TransactionRequest;
import com.example.transactionservice.Model.Transaction;
import com.example.transactionservice.Repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionService {



    private final TransactionRepository transactionRepository;

    public Transaction saveTransaction(TransactionRequest transactionRequest)
    {

        String type = transactionRequest.getTransactionType().toUpperCase();


        //Basic validation based on type
      if("TRANSFER".equals(type))
        {
            if(transactionRequest.getSourceAccountNumber()==null ||
            transactionRequest.getTargetAccountNumber()==null ||
            transactionRequest.getSourceAccountNumber().isBlank()||
            transactionRequest.getTargetAccountNumber().isBlank())
            {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Source and Target account numbers are required for transfer");
            }
            if(transactionRequest.getSourceAccountNumber()
                    .equals(transactionRequest.getTargetAccountNumber()))
            {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Source and Target account number cannot be the same!");
            }
        }




        if("DEPOSIT".equals(type) || "WITHDRAW".equals(type))
        {
            if(transactionRequest.getSourceAccountNumber() == null||
            transactionRequest.getSourceAccountNumber().isBlank())
            {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Source account number is required");
            }
        }


        return transactionRepository.save(

                Transaction.builder()
                        .transactionType(type)
                        .amount(transactionRequest.getAmount())
                        .sourceAccountNumber(transactionRequest.getSourceAccountNumber())
                        .targetAccountNumber(transactionRequest.getTargetAccountNumber())
                        .timestamp(LocalDateTime.now())
                        .build()

        );






    }
}
