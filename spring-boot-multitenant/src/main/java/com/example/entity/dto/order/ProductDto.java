package com.example.entity.dto.order;

import com.example.common.util.MathUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDto implements Serializable {

    private Long id;
    private Long tenantid;
    private Long refno;

    private String name;
    private BigDecimal costPrice= BigDecimal.ZERO;
    private BigDecimal salesPrice= BigDecimal.ZERO;
    private Long barcode;
    private boolean isService;
    private String internalReference;
    private String group;
    private String category;
    private String uom;
    private String hsnCode;
    private String unit;
    private String gstApplicability;
    private BigDecimal cgstRate = BigDecimal.ZERO;
    private BigDecimal sgstRate = BigDecimal.ZERO;
    private BigDecimal igstRate = BigDecimal.ZERO;
    private String description;
    private BigDecimal quantity= BigDecimal.ZERO;

    private BigDecimal pendingarrival = BigDecimal.ZERO;
    private BigDecimal pendingdispatch = BigDecimal.ZERO;

    public String getRupCostPrice() {
        return MathUtil.rupeesFormat(costPrice);
    }
    public String getRupSalesPrice() {
        return MathUtil.rupeesFormat(salesPrice);
    }


    public ProductDto() {
    }

    public ProductDto(String name, String costPrice, String salesPrice, String quantity, Long barcode, String internalReference,
                      String group, String category, String uom, String hsnCode, String cgstRate, String sgstRate, String igstRate, String description) {
        this.name = name;
        this.costPrice = new BigDecimal(costPrice);
        this.salesPrice = new BigDecimal(salesPrice);
        this.quantity = new BigDecimal(quantity);
        this.barcode = barcode;
        this.internalReference = internalReference;
        this.group = group;
        this.category = category;
        this.uom = uom;
        this.hsnCode = hsnCode;
        this.cgstRate = new BigDecimal(cgstRate);
        this.sgstRate = new BigDecimal(sgstRate);
        this.igstRate = new BigDecimal(igstRate);
        this.description = description;
        this.unit = "Units";
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

    public BigDecimal getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(BigDecimal salesPrice) {
        this.salesPrice = salesPrice;
    }

    public Long getBarcode() {
        return barcode;
    }

    public void setBarcode(Long barcode) {
        this.barcode = barcode;
    }

    public boolean isService() {
        return isService;
    }

    public void setService(boolean service) {
        isService = service;
    }

    public String getInternalReference() {
        return internalReference;
    }

    public void setInternalReference(String internalReference) {
        this.internalReference = internalReference;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
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

    public String getHsnCode() {
        return hsnCode;
    }

    public void setHsnCode(String hsnCode) {
        this.hsnCode = hsnCode;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getGstApplicability() {
        return gstApplicability;
    }

    public void setGstApplicability(String gstApplicability) {
        this.gstApplicability = gstApplicability;
    }

    public BigDecimal getCgstRate() {
        return cgstRate;
    }

    public void setCgstRate(BigDecimal cgstRate) {
        this.cgstRate = cgstRate;
    }

    public BigDecimal getSgstRate() {
        return sgstRate;
    }

    public void setSgstRate(BigDecimal sgstRate) {
        this.sgstRate = sgstRate;
    }

    public BigDecimal getIgstRate() {
        return igstRate;
    }

    public void setIgstRate(BigDecimal igstRate) {
        this.igstRate = igstRate;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
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
