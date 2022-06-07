package com.example.entity.report;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Immutable
@Table(name = "productmonthlyreport", schema = "inventory")

@NamedQueries({

        @NamedQuery(name = "productmonthlyreport.list", query = "SELECT p from ProductMonthlyReport p where p.tenantid=:tenantid")

})

public class ProductMonthlyReport implements Serializable {
    public static final String Q_PRODUCTMONTHLYREPORT_LIST = "productmonthlyreport.list";


    @Id
    private Long id;

    private Long tenantid;
    Long prodid;
    int month;
    int year;
    BigDecimal saleqty;
    BigDecimal saleamt;
    BigDecimal profit;
    BigDecimal purchaseqty;
    BigDecimal purchaseamt;

    public Long getTenantid() {
        return tenantid;
    }

    public void setTenantid(Long tenantid) {
        this.tenantid = tenantid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProdid() {
        return prodid;
    }

    public void setProdid(Long prodid) {
        this.prodid = prodid;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
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
