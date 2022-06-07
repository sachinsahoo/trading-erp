package com.example.entity.dto.order;

import com.example.common.util.MathUtil;
import com.example.constant.OrderType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceDto implements Serializable {

    private Long id;
    private Long tenantid;
    private Long oid;
    private String type; // purchase / sales
    private String referenceno;

    private List<InvProductDto> productlist;

    private BigDecimal taxAmount = BigDecimal.ZERO;
    private BigDecimal totalAmount= BigDecimal.ZERO;
    private BigDecimal totalcommpay= BigDecimal.ZERO;
    private BigDecimal balance= BigDecimal.ZERO;

    private Long invdate;
    private Long completedate;

    private String transportername;
    private String trackingno;
    private String truckno;
    private String containerno;

    private String status;
    private String taxes;

    // From Invoice @ Order
    private Long clientId;
    private String clientName;

    public InvoiceDto() {
    }

    public InvoiceDto(String type, Long oid) {
        this.type = type;

    }

    public String getRupTotalAmount(){
        return MathUtil.rupeesFormat(totalAmount);
    }
    public String getRupBalance(){
        return MathUtil.rupeesFormat(balance);
    }
    public String getRupTaxAmount(){
        return MathUtil.rupeesFormat(taxAmount);
    }

    public boolean isPurchase() throws Exception {
        OrderType type = OrderType.getType(getType());
        return type.equals(OrderType.PURCHASE);
    }

    public String getTaxes() {
        return taxes;
    }

    public void setTaxes(String taxes) {
        this.taxes = taxes;
    }

    public boolean isSale() throws Exception {
        OrderType type = OrderType.getType(getType());
        return type.equals(OrderType.SALE);
    }

    public OrderType getOrderType() throws Exception {
        return OrderType.getType(getType());
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReferenceno() {
        return referenceno;
    }

    public void setReferenceno(String referenceno) {
        this.referenceno = referenceno;
    }

    public List<InvProductDto> getProductlist() {
        return productlist;
    }

    public void setProductlist(List<InvProductDto> productlist) {
        this.productlist = productlist;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTotalcommpay() {
        return totalcommpay;
    }

    public void setTotalcommpay(BigDecimal totalcommpay) {
        this.totalcommpay = totalcommpay;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Long getInvdate() {
        return invdate;
    }

    public void setInvdate(Long invdate) {
        this.invdate = invdate;
    }

    public Long getCompletedate() {
        return completedate;
    }

    public void setCompletedate(Long completedate) {
        this.completedate = completedate;
    }

    public String getTransportername() {
        return transportername;
    }

    public void setTransportername(String transportername) {
        this.transportername = transportername;
    }

    public String getTrackingno() {
        return trackingno;
    }

    public void setTrackingno(String trackingno) {
        this.trackingno = trackingno;
    }

    public String getTruckno() {
        return truckno;
    }

    public void setTruckno(String truckno) {
        this.truckno = truckno;
    }

    public String getContainerno() {
        return containerno;
    }

    public void setContainerno(String containerno) {
        this.containerno = containerno;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
