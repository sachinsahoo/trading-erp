package com.example.entity.order;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "invproduct", schema = "inventory")

@NamedQueries({
        @NamedQuery(name = "invprod.list", query = "SELECT i from InvProduct i where i.tenantid=:tenantid")


})

public class InvProduct implements Serializable {
    public static final String Q_LIST = "invprod.list";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long tenantid;
    private Long invid;



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "popid", foreignKey=@ForeignKey(name="invprod_poprod_fkey"))
    private POProduct poproduct;

    private BigDecimal quantity= BigDecimal.ZERO;

    public InvProduct() {
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

    public Long getInvid() {
        return invid;
    }

    public void setInvid(Long invid) {
        this.invid = invid;
    }

    public POProduct getPoproduct() {
        return poproduct;
    }

    public void setPoproduct(POProduct poproduct) {
        this.poproduct = poproduct;
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