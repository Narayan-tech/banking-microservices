package com.example.fundtransferservice.Controller;


import com.example.fundtransferservice.DTO.TransferRequest;
import com.example.fundtransferservice.DTO.TransferResponse;
import com.example.fundtransferservice.Service.FundTransferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fund-transfer")
@RequiredArgsConstructor
public class FundTransferController {

    private final FundTransferService fundTransferService;


    @PostMapping
    public TransferResponse transferResponse(@Valid @RequestBody TransferRequest transferRequest)
    {
        return fundTransferService.transferResponse(transferRequest);
    }
}
