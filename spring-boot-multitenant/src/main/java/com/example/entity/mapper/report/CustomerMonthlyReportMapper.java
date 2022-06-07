package com.example.entity.mapper.report;

import com.example.common.util.DateUtil;
import com.example.entity.dto.report.CustomerMonthlyReportDto;
import com.example.entity.dto.report.ProductMonthlyReportDto;
import com.example.entity.report.CustomerMonthlyReport;
import com.example.entity.report.ProductMonthlyReport;


import java.time.Month;
import java.util.function.Function;


public class CustomerMonthlyReportMapper {


    public static final Function<CustomerMonthlyReport, CustomerMonthlyReportDto> mapperEntityToDto = (customerMonthlyReport) -> {
        CustomerMonthlyReportDto dto = new CustomerMonthlyReportDto();
        if (customerMonthlyReport == null) {
            return null;
        }

        dto.setCid(customerMonthlyReport.getCid());
        dto.setTenantid(customerMonthlyReport.getTenantid());
        dto.setMonth(customerMonthlyReport.getMonth());
        dto.setYear(customerMonthlyReport.getYear());
        dto.setDate(DateUtil.getLong(Month.of(customerMonthlyReport.getMonth()), 1, customerMonthlyReport.getYear()));

        dto.setSaleamt(customerMonthlyReport.getSaleamt());
        dto.setProfit(customerMonthlyReport.getProfit());
        return dto;
    };
}

