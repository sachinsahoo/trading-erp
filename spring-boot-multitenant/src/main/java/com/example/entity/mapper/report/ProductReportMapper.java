package com.example.entity.mapper.report;

import com.example.common.util.DateUtil;
import com.example.entity.report.ProductMonthlyReport;
import com.example.entity.dto.report.ProductMonthlyReportDto;


import java.math.RoundingMode;
import java.time.Month;
import java.util.function.Function;

public class ProductReportMapper {


        public static final Function<ProductMonthlyReport, ProductMonthlyReportDto> mapperEntityToDto = (prodReport) -> {
                ProductMonthlyReportDto dto = new ProductMonthlyReportDto();
                if (prodReport == null) {
                        return null;
                }

                dto.setTenantid(prodReport.getTenantid());
                dto.setProdid(prodReport.getProdid());
                dto.setMonth(prodReport.getMonth());
                dto.setYear(prodReport.getYear());
                dto.setDate(DateUtil.getLong(Month.of(prodReport.getMonth()),1,  prodReport.getYear()));
                dto.setSaleqty(prodReport.getSaleqty());
                dto.setSaleamt(prodReport.getSaleamt());
                dto.setPurchaseamt(prodReport.getPurchaseamt());
                dto.setPurchaseqty(prodReport.getPurchaseqty());
                dto.setProfit(prodReport.getProfit());

                dto.setAvgCostPrice(prodReport.getPurchaseamt().divide(prodReport.getPurchaseqty(), 3, RoundingMode.CEILING));
                dto.setAvgSalePrice(prodReport.getSaleamt().divide(prodReport.getSaleqty(), 3, RoundingMode.CEILING));

                return dto;
        };

}
