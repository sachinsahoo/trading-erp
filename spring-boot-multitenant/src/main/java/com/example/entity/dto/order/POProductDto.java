package com.example.entity.dto.order;

import com.example.common.util.MathUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class POProductDto implements Serializable {

    private Long id;
    private Long tenantid;
    private Long oid;
    private Long lpoid;

    private Long productId;
    private String productName;
    ProductDto product;
    private BigDecimal commpay = BigDecimal.ZERO;
    private BigDecimal commrec = BigDecimal.ZERO;
    private BigDecimal price= BigDecimal.ZERO;
    private BigDecimal costPrice= BigDecimal.ZERO;

    private BigDecimal profit= BigDecimal.ZERO;
    private BigDecimal quantity= BigDecimal.ZERO;

    private List<POPTaxDto> taxes = new ArrayList<>();

    //form Purchase order
    private String poref;
    private Long orderdate;
    private Long confirmdate;
    private String status;
    private Long clientId;
    private String clientName;


    public POProductDto() {
    }

    public POProductDto(Long productId, BigDecimal price, BigDecimal quantity) {
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
    }

    public String getRupPrice() {
        return MathUtil.rupeesFormat(price);
    }
    public String getRupCostPrice() {
        return MathUtil.rupeesFormat(costPrice);
    }
    public String getRupProfit() {
        return MathUtil.rupeesFormat(profit);
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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public List<POPTaxDto> getTaxes() {
        return taxes;
    }

    public void setTaxes(List<POPTaxDto> taxes) {
        this.taxes = taxes;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public ProductDto getProduct() {
        return product;
    }

    public void setProduct(ProductDto product) {
        this.product = product;
    }

    public BigDecimal getCommpay() {
        return commpay;
    }

    public void setCommpay(BigDecimal commpay) {
        this.commpay = commpay;
    }

    public BigDecimal getCommrec() {
        return commrec;
    }

    public void setCommrec(BigDecimal commrec) {
        this.commrec = commrec;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
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

    public String getPoref() {
        return poref;
    }

    public void setPoref(String poref) {
        this.poref = poref;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(Long orderdate) {
        this.orderdate = orderdate;
    }

    public Long getConfirmdate() {
        return confirmdate;
    }

    public void setConfirmdate(Long confirmdate) {
        this.confirmdate = confirmdate;
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
