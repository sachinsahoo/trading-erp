package com.example.entity.rs.order;

import com.example.entity.dto.order.ProductDto;
import com.example.entity.dto.report.PurchaseSaleYearlyReportDto;
import com.example.entity.rs.base.BaseResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportResponse extends BaseResponse {

private PurchaseSaleYearlyReportDto report;

    public PurchaseSaleYearlyReportDto getReport() {
        return report;
    }

    public void setReport(PurchaseSaleYearlyReportDto report) {
        this.report = report;
    }
}
