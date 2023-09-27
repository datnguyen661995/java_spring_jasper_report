package com.datnguyen.jasper.report.controller;

import com.datnguyen.jasper.report.domain.enums.ExportType;
import com.datnguyen.jasper.report.service.report.ReportService;
import com.datnguyen.jasper.report.service.transaction.TransactionService;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/reports")
public class ReportController {
    private final ReportService reportService;
    private final TransactionService transactionService;

    public ReportController(ReportService reportService, TransactionService transactionService) {
        this.reportService = reportService;
        this.transactionService = transactionService;
    }

    @GetMapping(value = "/transactions/download")
    ResponseEntity<Void> downloadTransactionReport(@RequestParam(value = "exportType") ExportType exportType,
                                                   HttpServletResponse response) throws IOException, JRException {
        reportService.downloadTransactionReport(exportType, response);
        return  ResponseEntity.ok().build();
    }

    // new endpoint to generate & EXPORT dynamic report
    @GetMapping(value = "/dynamic/transactions/download")
    ResponseEntity<Void> exportDynamicTransactionReport(@RequestParam(value = "exportType") ExportType exportType,
                                                        HttpServletResponse response) throws IOException, JRException {
        transactionService.exportTransactionReport(exportType, response);
        return ResponseEntity.ok().build();
    }
}
