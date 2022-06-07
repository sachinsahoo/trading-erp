package com.example.entity.dto.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InvProductDto implements Serializable {

    private Long id;
    private Long tenantid;
    private Long invid;
    private String invref;
    private Long popid;
    private Long prodid;

    private BigDecimal quantity= BigDecimal.ZERO;

    private POProductDto poproduct;

    private Long completedate;
    private Long receivedate;

    // From Order
    private Long clientId;
    private String clientName;

    public InvProductDto() {
    }

    public InvProductDto(Long popid, Long prodid, BigDecimal quantity) {
        this.popid = popid;
        this.prodid = prodid;
        this.quantity = quantity;
    }

    public InvProductDto(BigDecimal quantity) {

        this.quantity = quantity;
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

    public String getInvref() {
        return invref;
    }

    public void setInvref(String invref) {
        this.invref = invref;
    }

    public void setInvid(Long invid) {
        this.invid = invid;
    }

    public Long getPopid() {
        return popid;
    }

    public void setPopid(Long popid) {
        this.popid = popid;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public POProductDto getPoproduct() {
        return poproduct;
    }

    public void setPoproduct(POProductDto poproduct) {
        this.poproduct = poproduct;
    }

    public Long getProdid() {
        return prodid;
    }

    public void setProdid(Long prodid) {
        this.prodid = prodid;
    }

    public Long getCompletedate() {
        return completedate;
    }

    public void setCompletedate(Long completedate) {
        this.completedate = completedate;
    }

    public Long getReceivedate() {
        return receivedate;
    }

    public void setReceivedate(Long receivedate) {
        this.receivedate = receivedate;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
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
