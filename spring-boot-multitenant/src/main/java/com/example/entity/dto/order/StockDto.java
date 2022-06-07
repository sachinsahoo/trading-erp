package com.example.entity.dto.order;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;


public class  StockDto implements Serializable {


    private Long id;


    private GodownDto godown;

    private int quantity;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GodownDto getGodown() {
        return godown;
    }

    public void setGodown(GodownDto godown) {
        this.godown = godown;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
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
