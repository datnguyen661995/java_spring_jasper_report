package com.datnguyen.jasper.report.domain.model.report.dynamic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DynamicReportProperties {
    List<String> columnHeaders;
    List<Integer> indexesOfColumnTypeNumber;
    List<List<String>> rows;
    List<String> summary;


}
