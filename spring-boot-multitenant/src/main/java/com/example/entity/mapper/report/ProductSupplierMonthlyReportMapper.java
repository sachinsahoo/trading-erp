package com.example.entity.mapper.report;

import com.example.common.util.DateUtil;
import com.example.entity.dto.report.ProductMonthlyReportDto;
import com.example.entity.dto.report.ProductSupplierMonthlyReportDto;
import com.example.entity.report.ProductMonthlyReport;
import com.example.entity.report.ProductSupplierMonthlyReport;

import java.math.RoundingMode;
import java.time.Month;
import java.util.function.Function;

public class ProductSupplierMonthlyReportMapper {


        public static final Function<ProductSupplierMonthlyReport, ProductSupplierMonthlyReportDto> mapperEntityToDto = (prodSupReport) -> {
                ProductSupplierMonthlyReportDto dto = new ProductSupplierMonthlyReportDto();
                if (prodSupReport == null) {
                        return null;
                }

                dto.setTenantid(prodSupReport.getTenantid());
                dto.setProdid(prodSupReport.getProdid());
                dto.setProdname(prodSupReport.getProdname());
                dto.setCid(prodSupReport.getCid());
                dto.setName(prodSupReport.getName());
                dto.setMonth(prodSupReport.getMonth());
                dto.setYear(prodSupReport.getYear());
                dto.setDate(DateUtil.getLong(Month.of(prodSupReport.getMonth()),1,  prodSupReport.getYear()));
                dto.setPurchaseamt(prodSupReport.getPurchaseamt());
                dto.setPurchaseqty(prodSupReport.getPurchaseqty());
                dto.setAvgCostPrice(prodSupReport.getPurchaseamt().divide(prodSupReport.getPurchaseqty(), 3, RoundingMode.CEILING));
                return dto;
        };

}
