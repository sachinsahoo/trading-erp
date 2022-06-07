package com.example.entity.mapper.report;

import com.example.common.util.DateUtil;
import com.example.entity.dto.report.ProductCustomerMonthlyReportDto;
import com.example.entity.dto.report.ProductMonthlyReportDto;
import com.example.entity.report.ProductCustomerMonthlyReport;
import com.example.entity.report.ProductMonthlyReport;

import java.math.RoundingMode;
import java.time.Month;
import java.util.function.Function;

public class ProductCustMonthlyReportMapper {


        public static final Function<ProductCustomerMonthlyReport, ProductCustomerMonthlyReportDto> mapperEntityToDto = (prodCustReport) -> {
                ProductCustomerMonthlyReportDto dto = new ProductCustomerMonthlyReportDto();
                if (prodCustReport == null) {
                        return null;
                }

                dto.setTenantid(prodCustReport.getTenantid());
                dto.setProdid(prodCustReport.getProdid());
                dto.setCid(prodCustReport.getCid());
                dto.setMonth(prodCustReport.getMonth());
                dto.setYear(prodCustReport.getYear());
                dto.setDate(DateUtil.getLong(Month.of(prodCustReport.getMonth()),1,  prodCustReport.getYear()));
                dto.setSaleqty(prodCustReport.getSaleqty());
                dto.setSaleamt(prodCustReport.getSaleamt());
                dto.setProfit(prodCustReport.getProfit());

                dto.setAvgSalePrice(prodCustReport.getSaleamt().divide(prodCustReport.getSaleqty(), 3, RoundingMode.CEILING));

                return dto;
        };

}
