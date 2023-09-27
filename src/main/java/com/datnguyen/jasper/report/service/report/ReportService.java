package com.datnguyen.jasper.report.service.report;

import com.datnguyen.jasper.report.domain.enums.ExportType;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;

import java.io.IOException;
import java.util.Collection;

public interface ReportService {
    void downloadTransactionReport(ExportType exportType, HttpServletResponse response) throws JRException, IOException;

    void exportReport(Collection<?> beanCollection, ExportType exportType, HttpServletResponse response) throws JRException, IOException;

}
