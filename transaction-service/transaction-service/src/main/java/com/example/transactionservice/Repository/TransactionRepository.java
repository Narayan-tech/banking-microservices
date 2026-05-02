package com.example.transactionservice.Repository;

import com.example.transactionservice.Model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    List<Transaction> findBySourceAccountNumber(String sourceAccountNumber);
    List<Transaction> findByTargetAccountNumber(String targetAccountNumber);
}
