package com.example.entity.dto.report;

import com.example.common.util.MathUtil;
import com.example.entity.dto.report.base.YearlyReport;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductYearlyReportDto extends YearlyReport implements Serializable {

    private Long tenantid;
    long prodid;
    String prodname;
    BigDecimal stockQty;

    BigDecimal purchaseqty;
    BigDecimal purchaseamt;
    BigDecimal avgCostPrice;

    BigDecimal saleqty;
    BigDecimal saleamt;
    BigDecimal avgSalePrice;

    BigDecimal profit;


    List <ProductMonthlyReportDto> monthlyReport;

    List<ProductCustomerYearlyReportDto> customerReport = new ArrayList<>();
    List<ProductSupplierYearlyReportDto> supplierReport = new ArrayList<>();


    BarChart barChart;
    BarChart custBarchart = new BarChart();
    BarChart supBarchart = new BarChart();

    public ProductYearlyReportDto(Long start, Long end) {
        this.setStart(start);
        this.setEnd(end);
    }

    public String getRupPurchaseAmt() {
        return MathUtil.rupeesFormat(purchaseamt);
    }

    public String getRupAvgCostPrice() {
        return MathUtil.rupeesFormat(avgCostPrice);
    }
    public String getRupSaleAmt() {
        return MathUtil.rupeesFormat(saleamt);
    }
    public String getRupAvgSalePrice() {
        return MathUtil.rupeesFormat(avgSalePrice);
    }
    public String getRupProfit() {
        return MathUtil.rupeesFormat(profit);
    }


    public Long getTenantid() {
        return tenantid;
    }

    public void setTenantid(Long tenantid) {
        this.tenantid = tenantid;
    }

    public long getProdid() {
        return prodid;
    }

    public void setProdid(long prodid) {
        this.prodid = prodid;
    }

    public String getProdname() {
        return prodname;
    }

    public void setProdname(String prodname) {
        this.prodname = prodname;
    }

    public BigDecimal getStockQty() {
        return stockQty;
    }

    public void setStockQty(BigDecimal stockQty) {
        this.stockQty = stockQty;
    }

    public BigDecimal getPurchaseqty() {
        return purchaseqty;
    }

    public void setPurchaseqty(BigDecimal purchaseqty) {
        this.purchaseqty = purchaseqty;
    }

    public BigDecimal getPurchaseamt() {
        return purchaseamt;
    }

    public void setPurchaseamt(BigDecimal purchaseamt) {
        this.purchaseamt = purchaseamt;
    }

    public BigDecimal getAvgCostPrice() {
        return avgCostPrice;
    }

    public void setAvgCostPrice(BigDecimal avgCostPrice) {
        this.avgCostPrice = avgCostPrice;
    }

    public BigDecimal getSaleqty() {
        return saleqty;
    }

    public void setSaleqty(BigDecimal saleqty) {
        this.saleqty = saleqty;
    }

    public BigDecimal getSaleamt() {
        return saleamt;
    }

    public void setSaleamt(BigDecimal saleamt) {
        this.saleamt = saleamt;
    }

    public BigDecimal getAvgSalePrice() {
        return avgSalePrice;
    }

    public void setAvgSalePrice(BigDecimal avgSalePrice) {
        this.avgSalePrice = avgSalePrice;
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

    public List<ProductMonthlyReportDto> getMonthlyReport() {
        return monthlyReport;
    }

    public void setMonthlyReport(List<ProductMonthlyReportDto> monthlyReport) {
        this.monthlyReport = monthlyReport;
    }

    public List<ProductCustomerYearlyReportDto> getCustomerReport() {
        return customerReport;
    }

    public void setCustomerReport(List<ProductCustomerYearlyReportDto> customerReport) {
        this.customerReport = customerReport;
    }

    public List<ProductSupplierYearlyReportDto> getSupplierReport() {
        return supplierReport;
    }

    public void setSupplierReport(List<ProductSupplierYearlyReportDto> supplierReport) {
        this.supplierReport = supplierReport;
    }

    public BarChart getCustBarchart() {
        return custBarchart;
    }

    public void setCustBarchart(BarChart custBarchart) {
        this.custBarchart = custBarchart;
    }

    public BarChart getSupBarchart() {
        return supBarchart;
    }

    public void setSupBarchart(BarChart supBarchart) {
        this.supBarchart = supBarchart;
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
