package com.datnguyen.jasper.report.service.report.impl;

import com.datnguyen.jasper.report.domain.enums.ExportType;
import com.datnguyen.jasper.report.domain.model.transaction.Transaction;
import com.datnguyen.jasper.report.service.report.ReportService;
import com.datnguyen.jasper.report.service.transaction.TransactionService;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import static com.datnguyen.jasper.report.domain.enums.ExportType.PDF;

@Service
public class ReportServiceImpl implements ReportService {
    private final TransactionService transactionService;

    public ReportServiceImpl(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    public void downloadTransactionReport(ExportType exportType, HttpServletResponse response) throws JRException, IOException {
        List<Transaction> transactionList = transactionService.getTransactionList();
        exportReport(transactionList, exportType, response);
    }

    @Override
    public void exportReport(Collection<?> beanCollection, ExportType exportType, HttpServletResponse response) throws JRException, IOException {
        InputStream transactionReportStream =
                getClass()
                        .getResourceAsStream(
                                "/report/transaction_report_" + exportType.toString().toLowerCase() + ".jrxml");
        String titleTransactionBy = "Transactions Report";

        JasperReport jasperReport = JasperCompileManager.compileReport(transactionReportStream);
        JRBeanCollectionDataSource beanColDataSource =
                new JRBeanCollectionDataSource(beanCollection);
        HashMap<String, Object> parameters = new HashMap();
        parameters.put("title", titleTransactionBy);

        JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, parameters, beanColDataSource);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        var dateTimeNow = LocalDateTime.now().format(formatter);
        var fileName = titleTransactionBy.replace(" ", "") + dateTimeNow;

        if (exportType == PDF) {

            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
            response.setContentType(exportType.getContentType());
            response.setHeader(
                    HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName + "." + exportType.getExtension() + ";");
            exporter.exportReport();

        } else if (exportType == ExportType.EXCEL) {

            JRXlsxExporter exporter = new JRXlsxExporter();
            SimpleXlsxReportConfiguration reportConfigXLS = new SimpleXlsxReportConfiguration();
            reportConfigXLS.setSheetNames(new String[]{titleTransactionBy});
            reportConfigXLS.setDetectCellType(true);
            reportConfigXLS.setCollapseRowSpan(false);
            exporter.setConfiguration(reportConfigXLS);
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
            response.setHeader(
                    HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName + "." + exportType.getExtension() + ";");
            response.setContentType(exportType.getContentType());
            exporter.exportReport();

        } else if (exportType == ExportType.CSV) {

            JRCsvExporter exporter = new JRCsvExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            var outputStream = response.getOutputStream();
            exporter.setExporterOutput((new SimpleWriterExporterOutput(outputStream)));
            response.setHeader(
                    HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName + "." + exportType.getExtension() + ";");
            response.setContentType(exportType.getContentType());
            exporter.exportReport();

        } else if (exportType == ExportType.DOCX) {

            JRDocxExporter exporter = new JRDocxExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
            response.setHeader(
                    HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName + "." + exportType.getExtension() + ";");
            response.setContentType(exportType.getContentType());
            exporter.exportReport();

        } else {
            throw new RuntimeException("File Format isn't supported!");
        }
    }
}
