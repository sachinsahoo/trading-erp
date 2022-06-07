package com.example.entity.report;

import org.springframework.data.annotation.Immutable;

import javax.persistence.*;
import java.math.BigDecimal;


@Entity
@Immutable
@Table(name = "prodcustmonthlyreport", schema = "inventory")

@NamedQueries({

        @NamedQuery(name = "prodcustmonthlyreport.list", query = "SELECT p from ProductCustomerMonthlyReport p where p.tenantid=:tenantid")

})
public class ProductCustomerMonthlyReport {
    public static final String Q_LIST = "prodcustmonthlyreport.list";

    @Id
    private Long id;
    private Long tenantid;
    Long prodid;
    String prodname;
    Long cid;
    String name;
    int month;
    int year;
    BigDecimal saleqty;
    BigDecimal saleamt;
    BigDecimal profit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
