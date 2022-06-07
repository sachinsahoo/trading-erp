package com.example.entity.mapper.report;

import com.example.common.util.DateUtil;
import com.example.entity.dto.report.SupplierMonthlyReportDto;
import com.example.entity.report.SupplierMonthlyReport;


import java.time.Month;
import java.util.function.Function;

public class SupplierMonthlyReportMapper {

    public static final Function<SupplierMonthlyReport, SupplierMonthlyReportDto> mapperEntityToDto = (supReport) -> {
        SupplierMonthlyReportDto dto = new SupplierMonthlyReportDto();
        if (supReport == null) {
            return null;
        }

        dto.setTenantid(supReport.getTenantid());
        dto.setCid(supReport.getCid());
        dto.setMonth(supReport.getMonth());
        dto.setYear(supReport.getYear());
        dto.setDate(DateUtil.getLong(Month.of(supReport.getMonth()), 1,  supReport.getYear()));
        dto.setPurchaseamt(supReport.getPurchaseamt());

        return dto;
    };


}
