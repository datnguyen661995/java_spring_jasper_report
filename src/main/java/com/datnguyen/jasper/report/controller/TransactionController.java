package com.datnguyen.jasper.report.controller;

import com.datnguyen.jasper.report.domain.model.transaction.Transaction;
import com.datnguyen.jasper.report.service.transaction.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public ResponseEntity<List<Transaction>> getTransactionList() {
        return ResponseEntity.ok(transactionService.getTransactionList());
    }
}
