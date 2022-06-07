package com.example.entity.dto.order;

import com.example.common.util.MathUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import java.io.Serializable;
import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TaxDto implements Serializable {

    private Long id;
    private Long tenantid;
    private String name;
    private BigDecimal percent = BigDecimal.ZERO;

    public TaxDto(Long tenantid, String name, BigDecimal percent) {
        this.tenantid = tenantid;
        this.name = name;
        this.percent = percent;
    }

    public TaxDto() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
