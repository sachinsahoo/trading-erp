package com.example.entity.order;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "poptax", schema = "inventory")

@NamedQueries({
        @NamedQuery(name = "poptax.list", query = "SELECT p from POPTax p where p.tenantid=:tenantid")


})

public class POPTax implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long tenantid;
    private Long popid;
    @Column(length = 10)
    private String code;
    @Column(length = 20)
    private String description;
    private BigDecimal percent;

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

    public Long getPopid() {
        return popid;
    }

    public void setPopid(Long popid) {
        this.popid = popid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPercent() {
        return percent;
    }

    public void setPercent(BigDecimal percent) {
        this.percent = percent;
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