package com.example.entity.dto.report;

import com.example.common.util.MathUtil;
import com.example.entity.dto.report.base.MonthReport;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductSupplierMonthlyReportDto extends MonthReport implements Serializable, Comparable<ProductSupplierMonthlyReportDto> {

    private Long tenantid;
    Long prodid;
    String prodname;
    Long cid;
    String name;
    BigDecimal purchaseqty;
    BigDecimal purchaseamt;
    BigDecimal avgCostPrice;

    public String getRupPurchaseAmt() {
        return MathUtil.rupeesFormat(purchaseamt);
    }

    public String getRupAvgCostPrice() {
        return MathUtil.rupeesFormat(avgCostPrice);
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
    public int compareTo(ProductSupplierMonthlyReportDto reportDto) {
        return this.getDate().compareTo(reportDto.getDate());
    }
}
