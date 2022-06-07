package com.example.entity.report;

import org.springframework.data.annotation.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;


@Entity
@Immutable
@Table(name = "prodsupmonthlyreport", schema = "inventory")

@NamedQueries({

        @NamedQuery(name = "prodsupmonthlyreport.list", query = "SELECT p from ProductSupplierMonthlyReport p where p.tenantid=:tenantid")

})
public class ProductSupplierMonthlyReport implements Serializable {
    public static final String Q_LIST = "prodsupmonthlyreport.list";


    @Id
    private Long id;

    private Long tenantid;
    Long prodid;
    String prodname;
    Long cid;
    String name;
    int month;
    int year;
    BigDecimal purchaseqty;
    BigDecimal purchaseamt;

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

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
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
}
