package com.example.entity.mapper.report;

import com.example.common.util.DateUtil;
import com.example.entity.dto.report.PurchaseSaleMonthlyReportDto;
import com.example.entity.report.PurchaseSaleMonthlyReport;

import java.math.BigDecimal;
import java.time.Month;
import java.util.function.Function;

public class PurchaseSaleMonthlyReportMapper {


    public static final Function<PurchaseSaleMonthlyReport, PurchaseSaleMonthlyReportDto> mapperEntityToDto = (purchaseSaleMonthlyReport) -> {
        PurchaseSaleMonthlyReportDto dto = new PurchaseSaleMonthlyReportDto();
        if (purchaseSaleMonthlyReport == null) {
            return null;
        }

        dto.setTenantid(purchaseSaleMonthlyReport.getTenantid());
        dto.setMonth(purchaseSaleMonthlyReport.getMonth());
        dto.setYear(purchaseSaleMonthlyReport.getYear());
        dto.setDate(DateUtil.getLong(Month.of(purchaseSaleMonthlyReport.getMonth()), 1, purchaseSaleMonthlyReport.getYear()));
        dto.setSaleamt(purchaseSaleMonthlyReport.getSaleamt() != null ? purchaseSaleMonthlyReport.getSaleamt() : BigDecimal.ZERO);
        dto.setProfit(purchaseSaleMonthlyReport.getProfit() != null ? purchaseSaleMonthlyReport.getProfit() : BigDecimal.ZERO);
        dto.setPurchaseamt(purchaseSaleMonthlyReport.getPurchaseamt() != null ? purchaseSaleMonthlyReport.getPurchaseamt() : BigDecimal.ZERO);

        return dto;
    };


}
