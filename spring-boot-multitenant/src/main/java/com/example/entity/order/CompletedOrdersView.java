package com.example.entity.order;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Immutable
@Table(name = "completedordersview", schema = "inventory")

@NamedQueries({
        @NamedQuery(name = "completedordersview.list", query = "SELECT c from CompletedOrdersView c where c.tenantid=:tenantid")
})

public class CompletedOrdersView implements Serializable {
    public static final String Q_LIST = "completedordersview.list";

    @Id
    private Long id;
    private Long tenantid;
    Long productid;
    BigDecimal totpurqty;
    BigDecimal totsaleqty;

    public Long getTenantid() {
        return tenantid;
    }

    public void setTenantid(Long tenantid) {
        this.tenantid = tenantid;
    }

    public Long getProductid() {
        return productid;
    }

    public void setProductid(Long productid) {
        this.productid = productid;
    }

    public BigDecimal getTotpurqty() {
        return totpurqty;
    }

    public void setTotpurqty(BigDecimal totpurqty) {
        this.totpurqty = totpurqty;
    }

    public BigDecimal getTotsaleqty() {
        return totsaleqty;
    }

    public void setTotsaleqty(BigDecimal totsaleqty) {
        this.totsaleqty = totsaleqty;
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
