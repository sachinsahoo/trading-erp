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
@Table(name = "customermonthlyreport", schema = "inventory")

@NamedQueries({
        @NamedQuery(name = "customermonthlyreport.list", query = "SELECT c from CustomerMonthlyReport c where c.tenantid=:tenantid")
})

public class CustomerMonthlyReport implements Serializable {
    public static final String Q_CUSTOMERMONTHLYREPORT_LIST = "customermonthlyreport.list";

    @Id
    private Long id;

    private Long tenantid;
    Long cid;
    int month;
    int year;

    BigDecimal saleamt;
    BigDecimal profit;

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

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
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
}
