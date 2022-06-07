package com.example.entity.order;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "poproduct", schema = "inventory")

@NamedQueries({
        @NamedQuery(name = "poproduct.list", query = "SELECT p from POProduct p where p.tenantid=:tenantid"),
        @NamedQuery(name = "poproduct.findByName", query = "SELECT p from Product p where p.name=:name")

})

public class POProduct implements Serializable {
    public static final String Q_PRODUCT_LIST = "product.list";
    public static final String Q_FIND_BY_NAME = "product.findByName";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long tenantid;
    private Long oid;
    private Long lpoid;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "productid")
    Product product;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "popid", foreignKey=@ForeignKey(name="poproduct_poptax_fkey"))
    private List<POPTax> taxes;

    private BigDecimal price= BigDecimal.ZERO;
    private BigDecimal commpay= BigDecimal.ZERO;
    private BigDecimal costprice= BigDecimal.ZERO;
    private BigDecimal quantity= BigDecimal.ZERO;
    private BigDecimal profit= BigDecimal.ZERO;

    public POProduct() {
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

    public Long getOid() {
        return oid;
    }

    public void setOid(Long oid) {
        this.oid = oid;
    }

    public Long getLpoid() {
        return lpoid;
    }

    public void setLpoid(Long lpoid) {
        this.lpoid = lpoid;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<POPTax> getTaxes() {
        return taxes;
    }

    public void setTaxes(List<POPTax> taxes) {
        this.taxes = taxes;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getCommpay() {
        return commpay;
    }

    public void setCommpay(BigDecimal commpay) {
        this.commpay = commpay;
    }

    public BigDecimal getCostprice() {
        return costprice;
    }

    public void setCostprice(BigDecimal costprice) {
        this.costprice = costprice;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
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