package com.example.entity.dto.report;

import com.example.common.util.MathUtil;
import com.example.entity.dto.report.base.YearlyReport;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PurchaseSaleYearlyReportDto extends YearlyReport implements Serializable {

    private Long tenantid;
    BigDecimal purchaseamt;

    BigDecimal saleamt;
    BigDecimal profit;
    BigDecimal avgMonthlyProfit;

    BarChart barChart ; // Double bar chart purchase sale per month

    BarChart allProductsBar = new BarChart();
    BarChart allSuppliersBar = new BarChart();
    BarChart allCustomersBar = new BarChart();

    List<CustomerYearlyReportDto> customerReports;
    List<SupplierYearlyReportDto> supplierReports;
    List<ProductYearlyReportDto> productReports;

    List<PurchaseSaleMonthlyReportDto> monthlyReport;

    public PurchaseSaleYearlyReportDto(Long start, Long end) {
        this.setStart(start);
        this.setEnd(end);
    }

    public String getRupPurchaseAmt() {
        return MathUtil.rupeesFormat(purchaseamt);
    }
    public String getRupSaleAmt() {
        return MathUtil.rupeesFormat(saleamt);
    }
    public String getRupProfit() {
        return MathUtil.rupeesFormat(profit);
    }
    public String getRupAvgMonthlyProfit() {
        return MathUtil.rupeesFormat(avgMonthlyProfit);
    }

    public Long getTenantid() {
        return tenantid;
    }

    public void setTenantid(Long tenantid) {
        this.tenantid = tenantid;
    }

    public BigDecimal getPurchaseamt() {
        return purchaseamt;
    }

    public void setPurchaseamt(BigDecimal purchaseamt) {
        this.purchaseamt = purchaseamt;
    }

    public BigDecimal getSaleamt() {
        return saleamt;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public void setSaleamt(BigDecimal saleamt) {
        this.saleamt = saleamt;
    }

    public BarChart getBarChart() {
        return barChart;
    }

    public void setBarChart(BarChart barChart) {
        this.barChart = barChart;
    }

    public BarChart getAllProductsBar() {
        return allProductsBar;
    }

    public void setAllProductsBar(BarChart allProductsBar) {
        this.allProductsBar = allProductsBar;
    }

    public BarChart getAllSuppliersBar() {
        return allSuppliersBar;
    }

    public void setAllSuppliersBar(BarChart allSuppliersBar) {
        this.allSuppliersBar = allSuppliersBar;
    }

    public BarChart getAllCustomersBar() {
        return allCustomersBar;
    }

    public void setAllCustomersBar(BarChart allCustomersBar) {
        this.allCustomersBar = allCustomersBar;
    }

    public List<CustomerYearlyReportDto> getCustomerReports() {
        return customerReports;
    }

    public void setCustomerReports(List<CustomerYearlyReportDto> customerReports) {
        this.customerReports = customerReports;
    }

    public List<SupplierYearlyReportDto> getSupplierReports() {
        return supplierReports;
    }

    public void setSupplierReports(List<SupplierYearlyReportDto> supplierReports) {
        this.supplierReports = supplierReports;
    }

    public List<ProductYearlyReportDto> getProductReports() {
        return productReports;
    }

    public void setProductReports(List<ProductYearlyReportDto> productReports) {
        this.productReports = productReports;
    }

    public List<PurchaseSaleMonthlyReportDto> getMonthlyReport() {
        return monthlyReport;
    }

    public void setMonthlyReport(List<PurchaseSaleMonthlyReportDto> monthlyReport) {
        this.monthlyReport = monthlyReport;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object object) {
        return EqualsBuilder.reflectionEquals(this, object, false);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }


}
