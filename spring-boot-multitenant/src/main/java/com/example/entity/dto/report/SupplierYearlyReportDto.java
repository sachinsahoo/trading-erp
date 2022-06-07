package com.example.entity.dto.report;

import com.example.common.util.MathUtil;
import com.example.entity.dto.report.base.YearlyReport;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SupplierYearlyReportDto extends YearlyReport implements Serializable {

    private Long tenantid;

    Long cid;
    String name;
    BigDecimal balance;
    BigDecimal purchaseamt;

    BarChart barChart;

    List<SupplierMonthlyReportDto> monthlyReport;
    List<ProductSupplierYearlyReportDto> productReport;
    BarChart productBarchart = new BarChart();



    //supplier product yearly report

    public SupplierYearlyReportDto(Long start, Long end) {
        this.start = start;
        this.end = end;
    }

    public String getRupPurchaseAmt() {
        return MathUtil.rupeesFormat(purchaseamt);
    }
    public String getRupBalance() {
        return MathUtil.rupeesFormat(balance);
    }

    public Long getTenantid() {
        return tenantid;
    }

    public void setTenantid(Long tenantid) {
        this.tenantid = tenantid;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getPurchaseamt() {
        return purchaseamt;
    }

    public void setPurchaseamt(BigDecimal purchaseamt) {
        this.purchaseamt = purchaseamt;
    }


    public BarChart getBarChart() {
        return barChart;
    }

    public void setBarChart(BarChart barChart) {
        this.barChart = barChart;
    }

    public List<SupplierMonthlyReportDto> getMonthlyReport() {
        return monthlyReport;
    }

    public void setMonthlyReport(List<SupplierMonthlyReportDto> monthlyReport) {
        this.monthlyReport = monthlyReport;
    }

    public List<ProductSupplierYearlyReportDto> getProductReport() {
        return productReport;
    }

    public void setProductReport(List<ProductSupplierYearlyReportDto> productReport) {
        this.productReport = productReport;
    }

    public BarChart getProductBarchart() {
        return productBarchart;
    }

    public void setProductBarchart(BarChart productBarchart) {
        this.productBarchart = productBarchart;
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
