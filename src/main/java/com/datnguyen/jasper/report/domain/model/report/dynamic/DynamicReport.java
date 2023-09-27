package com.datnguyen.jasper.report.domain.model.report.dynamic;

import com.datnguyen.jasper.report.domain.enums.ExportType;
import com.datnguyen.jasper.report.domain.model.report.Report;
import jakarta.servlet.http.HttpServletResponse;

import java.io.InputStream;

public class DynamicReport extends Report {
    private final DynamicReportProperties dynamicReportProperties;

    public DynamicReport(
            InputStream inputStream,
            String title,
            ExportType exportType,
            HttpServletResponse response,
            DynamicReportProperties dynamicReportProperties) {
        super(inputStream, title, null, exportType, response);
        this.dynamicReportProperties = dynamicReportProperties;
    }

    public DynamicReportProperties getDynamicReportProperties() {
        return dynamicReportProperties;
    }
}
