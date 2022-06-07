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
public class PurchaseSaleMonthlyReportDto extends MonthReport implements Serializable, Comparable<PurchaseSaleMonthlyReportDto> {

    private Long tenantid;
    BigDecimal purchaseamt;
    BigDecimal saleamt;
    BigDecimal profit;

    public String getRupPurchaseAmt() {
        return MathUtil.rupeesFormat(purchaseamt);
    }
    public String getRupSaleAmt() {
        return MathUtil.rupeesFormat(saleamt);
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

    public BigDecimal getPurchaseamt() {
        return purchaseamt;
    }

    public void setPurchaseamt(BigDecimal purchaseamt) {
        this.purchaseamt = purchaseamt;
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
    public int compareTo(PurchaseSaleMonthlyReportDto reportDto) {
        return this.getDate().compareTo(reportDto.getDate());
    }
}
