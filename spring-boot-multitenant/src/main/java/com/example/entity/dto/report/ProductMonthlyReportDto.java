package com.example.entity.dto.report;

import com.example.common.util.MathUtil;
import com.example.entity.dto.report.base.MonthReport;
import com.example.entity.report.ProductCustomerMonthlyReport;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductMonthlyReportDto extends MonthReport implements Serializable, Comparable<ProductMonthlyReportDto> {

    private Long tenantid;
    Long prodid;
    String prodname;
    BigDecimal purchaseqty;
    BigDecimal purchaseamt;
    BigDecimal avgCostPrice;

    BigDecimal saleqty;
    BigDecimal saleamt;
    BigDecimal profit;
    BigDecimal avgSalePrice;

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

    public Long getProdid() {
        return prodid;
    }

    public void setProdid(Long prodid) {
        this.prodid = prodid;
    }

    public String getProdname() {
        return prodname;
    }

    public void setProdname(String prodname) {
        this.prodname = prodname;
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

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public BigDecimal getAvgSalePrice() {
        return avgSalePrice;
    }

    public void setAvgSalePrice(BigDecimal avgSalePrice) {
        this.avgSalePrice = avgSalePrice;
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


    @Override
    public int compareTo(ProductMonthlyReportDto reportDto) {
        return this.getDate().compareTo(reportDto.getDate());
    }
}
