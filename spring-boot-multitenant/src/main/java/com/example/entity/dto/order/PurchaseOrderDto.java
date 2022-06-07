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
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PurchaseOrderDto implements Serializable {

    private Long id;
    private Long tenantid;
    private Long mycompanyid;
    private String type; // purchase / sales
    private Long supplierid;
    private String supplierName;
    private Long customerid;
    private String customerName;
    private Long agentid;
    private String agentName;
    private String referenceno;
    private Long orderno;
    private Long orderdate;
    private Long confirmdate;
    private String status; // Draft / rfq / rfqsent/ PO
    private String taxes; // Ex GST@2%
    private BigDecimal taxAmount;
    private BigDecimal advance;
    private BigDecimal totalAmount= BigDecimal.ZERO;
    private BigDecimal totalcommpay= BigDecimal.ZERO;
    private BigDecimal profit= BigDecimal.ZERO;
    private BigDecimal balance= BigDecimal.ZERO;
    private List<POProductDto> poproductlist;
    private CompanyDto supplier;
    private CompanyDto customer;
    private CompanyDto agent;
    private List<InvoiceDto> invoices;

    public PurchaseOrderDto() {
    }

    public PurchaseOrderDto(String type, Long supplierid, Long customerid, String referenceno, Long orderdate, List<POProductDto> poproductlist) {
        this.type = type;
        this.supplierid = supplierid;
        this.customerid = customerid;
        this.referenceno = referenceno;
        this.orderdate = orderdate;
        this.poproductlist = poproductlist;
    }

    public String getRupTotalAmount(){
        return MathUtil.rupeesFormat(totalAmount);
    }
    public String getRupTotalCommPay(){
        return MathUtil.rupeesFormat(totalcommpay);
    }
    public String getRupProfit(){
        return MathUtil.rupeesFormat(profit);
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

    public Long getMycompanyid() {
        return mycompanyid;
    }

    public void setMycompanyid(Long mycompanyid) {
        this.mycompanyid = mycompanyid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(Long supplierid) {
        this.supplierid = supplierid;
    }

    public Long getCustomerid() {
        return customerid;
    }

    public void setCustomerid(Long customerid) {
        this.customerid = customerid;
    }

    public Long getAgentid() {
        return agentid;
    }

    public void setAgentid(Long agentid) {
        this.agentid = agentid;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getReferenceno() {
        return referenceno;
    }

    public void setReferenceno(String referenceno) {
        this.referenceno = referenceno;
    }

    public Long getOrderno() {
        return orderno;
    }

    public void setOrderno(Long orderno) {
        this.orderno = orderno;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTaxes() {
        return taxes;
    }

    public void setTaxes(String taxes) {
        this.taxes = taxes;
    }

    public BigDecimal getAdvance() {
        return advance;
    }

    public void setAdvance(BigDecimal advance) {
        this.advance = advance;
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


    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public List<POProductDto> getPoproductlist() {
        return poproductlist;
    }

    public void setPoproductlist(List<POProductDto> poproductlist) {
        this.poproductlist = poproductlist;
    }

    public CompanyDto getSupplier() {
        return supplier;
    }

    public void setSupplier(CompanyDto supplier) {
        this.supplier = supplier;
    }

    public CompanyDto getCustomer() {
        return customer;
    }

    public void setCustomer(CompanyDto customer) {
        this.customer = customer;
    }

    public CompanyDto getAgent() {
        return agent;
    }

    public void setAgent(CompanyDto agent) {
        this.agent = agent;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<InvoiceDto> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<InvoiceDto> invoices) {
        this.invoices = invoices;
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
