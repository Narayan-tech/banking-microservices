package com.example.fundtransferservice.Service;

import com.example.fundtransferservice.DTO.AccountOperationRequest;
import com.example.fundtransferservice.DTO.TransactionRequest;
import com.example.fundtransferservice.DTO.TransferRequest;
import com.example.fundtransferservice.DTO.TransferResponse;
import lombok.RequiredArgsConstructor;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import org.slf4j.Logger;

@Service
@RequiredArgsConstructor
public class FundTransferService {

    private static final Logger log = LoggerFactory.getLogger(FundTransferService.class);

    private final RestTemplate restTemplate;

    private final String ACCOUNT_SERVICE_URL="http://localhost:8081/api/accounts";
    private final String TRANSACTION_SERVICE_URL="http://localhost:8082/api/transactions";


    public TransferResponse transferResponse(TransferRequest transferRequest)
    {

        if(transferRequest.getSourceAccountNumber()
                .equals(transferRequest.getTargetAccountNumber()))
        {
            log.warn("Invalid transfer attempt: same source and target account {}",
                    transferRequest.getSourceAccountNumber());

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,"Source and Target account cannot be the same"
            );
        }

        log.info("Transfer initiated: {} to {} | Amount: {}",
                transferRequest.getSourceAccountNumber(),
                transferRequest.getTargetAccountNumber(),
                transferRequest.getAmount());
        //withdraw
        AccountOperationRequest withdrawRequest = new AccountOperationRequest(
                transferRequest.getSourceAccountNumber(),
                transferRequest.getAmount()
        );

        //deposit
        AccountOperationRequest depositRequest = new AccountOperationRequest(
                transferRequest.getTargetAccountNumber(),
                transferRequest.getAmount()
        );

        try {
            //1.Withdraw from sourceAccount
            restTemplate.postForObject(ACCOUNT_SERVICE_URL + "/withdraw", withdrawRequest
                    , Object.class);

            //2. Deposit to targetAccount
            restTemplate.postForObject(ACCOUNT_SERVICE_URL + "/deposit", depositRequest,
                    Object.class);

        }catch(RestClientException e){
            try{
                log.warn("Rollback triggered for source account: {}",
                        transferRequest.getSourceAccountNumber());

                restTemplate.postForObject(
                        ACCOUNT_SERVICE_URL+"/deposit",
                        withdrawRequest,
                        Object.class
                );
            }catch(RestClientException rollbackException){
                log.error("Rollback also failed for account {}",
                        transferRequest.getSourceAccountNumber());

                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                        "Transfer failed and rollback also failed. Please contact support.");
            }

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST
                    ,"Transfer failed. Amount refunded to source account");
        }
        //3.Save Transaction log
        TransactionRequest transactionRequest = new TransactionRequest(
                "TRANSFER",
                transferRequest.getSourceAccountNumber(),
                transferRequest.getTargetAccountNumber(),
                transferRequest.getAmount(),
                "Fund transffered successfully"
        );

        try {
            restTemplate.postForObject(
                    TRANSACTION_SERVICE_URL,
                    transactionRequest,
                    Object.class
            );
        }catch(RestClientException e){
            log.error("Transaction logging failed for {} -> {}",
                    transferRequest.getSourceAccountNumber(),
                    transferRequest.getTargetAccountNumber());

            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Fund transferred. but transaction logging failed."
            );
        }
        return new TransferResponse(
                transferRequest.getSourceAccountNumber(),
                transferRequest.getTargetAccountNumber(),
                transferRequest.getAmount(),
                "SUCCESS",
                "Fund transferred successfully"
        );

    }
}
