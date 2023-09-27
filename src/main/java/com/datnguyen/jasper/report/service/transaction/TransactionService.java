package com.datnguyen.jasper.report.service.transaction;

import com.datnguyen.jasper.report.domain.enums.ExportType;
import com.datnguyen.jasper.report.domain.model.transaction.Transaction;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;

import java.io.IOException;
import java.util.List;

public interface TransactionService {
    List<Transaction> getTransactionList();
     void exportTransactionReport(ExportType exportType, HttpServletResponse response) throws JRException, IOException;

}
