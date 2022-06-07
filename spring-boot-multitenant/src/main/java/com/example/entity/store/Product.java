package com.example.entity.store;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "product", schema = "inventory")

@NamedQueries({
        @NamedQuery(name = "product.list", query = "SELECT p from Product p where p.tenantid=:tenantid"),
        @NamedQuery(name = "product.findByName", query = "SELECT p from Product p where p.name like :name ")

})

public class Product implements Serializable {
    public static final String Q_PRODUCT_LIST = "product.list";
    public static final String Q_FIND_BY_NAME = "product.findByName";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long tenantid;
    private Long refno;

    @Column(length = 40)
    private String name;
    private BigDecimal costprice = BigDecimal.ZERO;
    private BigDecimal salesprice= BigDecimal.ZERO;
    private long barcode;
    private boolean isservice;
    @Column(length = 30)
    private String internalreference;
    @Column(length = 30)
    private String pgroup;
    @Column(length = 30)
    private String category;
    @Column(length = 30)
    private String uom;
    @Column(length = 30)
    private String hsncode;

    @Column(length = 30)
    private String unit;
    @Column(length = 30)
    private String gstapplicability;
    private BigDecimal cgstrate = BigDecimal.ZERO;
    private BigDecimal sgstrate = BigDecimal.ZERO;
    private BigDecimal igstrate = BigDecimal.ZERO;
    @Column(length = 80)
    private String description;

    private BigDecimal quantity = BigDecimal.ZERO;
    private BigDecimal pendingarrival = BigDecimal.ZERO;
    private BigDecimal pendingdispatch = BigDecimal.ZERO;


    public Product() {
    }

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

    public Long getRefno() {
        return refno;
    }

    public void setRefno(Long refno) {
        this.refno = refno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCostprice() {
        return costprice;
    }

    public void setCostprice(BigDecimal costprice) {
        this.costprice = costprice;
    }

    public BigDecimal getSalesprice() {
        return salesprice;
    }

    public void setSalesprice(BigDecimal salesprice) {
        this.salesprice = salesprice;
    }

    public long getBarcode() {
        return barcode;
    }

    public void setBarcode(long barcode) {
        this.barcode = barcode;
    }

    public boolean isIsservice() {
        return isservice;
    }

    public void setIsservice(boolean isservice) {
        this.isservice = isservice;
    }

    public String getInternalreference() {
        return internalreference;
    }

    public void setInternalreference(String internalreference) {
        this.internalreference = internalreference;
    }

    public String getPgroup() {
        return pgroup;
    }

    public void setPgroup(String pgroup) {
        this.pgroup = pgroup;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public String getHsncode() {
        return hsncode;
    }

    public void setHsncode(String hsncode) {
        this.hsncode = hsncode;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getGstapplicability() {
        return gstapplicability;
    }

    public void setGstapplicability(String gstapplicability) {
        this.gstapplicability = gstapplicability;
    }

    public BigDecimal getCgstrate() {
        return cgstrate;
    }

    public void setCgstrate(BigDecimal cgstrate) {
        this.cgstrate = cgstrate;
    }

    public BigDecimal getSgstrate() {
        return sgstrate;
    }

    public void setSgstrate(BigDecimal sgstrate) {
        this.sgstrate = sgstrate;
    }

    public BigDecimal getIgstrate() {
        return igstrate;
    }

    public void setIgstrate(BigDecimal igstrate) {
        this.igstrate = igstrate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPendingarrival() {
        return pendingarrival;
    }

    public void setPendingarrival(BigDecimal pendingarrival) {
        this.pendingarrival = pendingarrival;
    }

    public BigDecimal getPendingdispatch() {
        return pendingdispatch;
    }

    public void setPendingdispatch(BigDecimal pendingdispatch) {
        this.pendingdispatch = pendingdispatch;
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
