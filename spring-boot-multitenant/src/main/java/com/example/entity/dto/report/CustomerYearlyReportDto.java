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
public class CustomerYearlyReportDto extends YearlyReport implements Serializable {

    private Long tenantid;
    long cid;
    String name;
    BigDecimal saleamt;
    BigDecimal balance;

    BigDecimal profit;
    BarChart barChart = new BarChart();
    BarChart productBarChart = new BarChart();

    List<CustomerMonthlyReportDto> monthlyReport;
    List<ProductCustomerYearlyReportDto> productReport;

    public String getRupSaleAmt() {
        return MathUtil.rupeesFormat(saleamt);
    }

    public String getRupBalance() {
        return MathUtil.rupeesFormat(balance);
    }
    public String getRupProfit() {
        return MathUtil.rupeesFormat(profit);
    }
    public CustomerYearlyReportDto(Long start, Long end) {
        this.setStart(start);
        this.setEnd(end);

    }

    public Long getTenantid() {
        return tenantid;
    }

    public void setTenantid(Long tenantid) {
        this.tenantid = tenantid;
    }

    public long getCid() {
        return cid;
    }

    public void setCid(long cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getSaleamt() {
        return saleamt;
    }

    public void setSaleamt(BigDecimal saleamt) {
        this.saleamt = saleamt;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public BarChart getBarChart() {
        return barChart;
    }

    public void setBarChart(BarChart barChart) {
        this.barChart = barChart;
    }

    public List<CustomerMonthlyReportDto> getMonthlyReport() {
        return monthlyReport;
    }

    public void setMonthlyReport(List<CustomerMonthlyReportDto> monthlyReport) {
        this.monthlyReport = monthlyReport;
    }

    public List<ProductCustomerYearlyReportDto> getProductReport() {
        return productReport;
    }

    public void setProductReport(List<ProductCustomerYearlyReportDto> productReport) {
        this.productReport = productReport;
    }

    public BarChart getProductBarChart() {
        return productBarChart;
    }

    public void setProductBarChart(BarChart productBarChart) {
        this.productBarChart = productBarChart;
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
