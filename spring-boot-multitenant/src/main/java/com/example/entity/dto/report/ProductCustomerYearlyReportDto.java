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
public class ProductCustomerYearlyReportDto extends YearlyReport implements Serializable {

    private Long tenantid;
    long prodid;
    String prodname;
    Long cid;
    String name;

    BigDecimal saleqty;
    BigDecimal saleamt;
    BigDecimal avgSalePrice;

    BigDecimal profit;

    // monthly sales purchase activity


    public ProductCustomerYearlyReportDto(Long start, Long end) {
        this.setStart(start);
        this.setEnd(end);
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
