package com.example.entity.dto.order;

import com.example.common.util.MathUtil;
import com.example.entity.dto.acct.JournalDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InvTransactionDto implements Serializable {

    private Long id;
    private Long tenantid;
    private Long date;
    private String notes;
    private Long custCompanyId;
    private Long myCompanyId;
    private String type;
    private Long invoiceId;
    private Long orderId;
    private String invRef;
    private BigDecimal amount= BigDecimal.ZERO;

    private CompanyDto custCompany;
    private CompanyDto myCompany;

    private PurchaseOrderDto order;

    private String custCompanyName;
    private String myCompanyName;

    private List<JournalDto> journals;

    public InvTransactionDto() {
    }

    public InvTransactionDto(Long tenantid, Long transdate, String type) {
        this.tenantid = tenantid;
        this.date = transdate;
        this.type = type;
    }



    public InvTransactionDto(Long date, String notes, Long custCompanyId, Long myCompanyId, String type, Long invoiceId, Long orderId, BigDecimal amount) {
        this.date = date;
        this.notes = notes;
        this.custCompanyId = custCompanyId;
        this.myCompanyId = myCompanyId;
        this.type = type;
        this.invoiceId = invoiceId;
        this.orderId = orderId;
        this.amount = amount;
    }

    public String getRupAmount(){
        return MathUtil.rupeesFormat(amount);
    }

    public Long getCustCompanyId() {
        return custCompanyId;
    }

    public void setCustCompanyId(Long custCompanyId) {
        this.custCompanyId = custCompanyId;
    }

    public Long getMyCompanyId() {
        return myCompanyId;
    }

    public void setMyCompanyId(Long myCompanyId) {
        this.myCompanyId = myCompanyId;
    }

    public CompanyDto getCustCompany() {
        return custCompany;
    }

    public void setCustCompany(CompanyDto custCompany) {
        this.custCompany = custCompany;
    }

    public CompanyDto getMyCompany() {
        return myCompany;
    }

    public void setMyCompany(CompanyDto myCompany) {
        this.myCompany = myCompany;
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

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public PurchaseOrderDto getOrder() {
        return order;
    }

    public void setOrder(PurchaseOrderDto order) {
        this.order = order;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getInvRef() {
        return invRef;
    }

    public void setInvRef(String invRef) {
        this.invRef = invRef;
    }

    public String getCustCompanyName() {
        return custCompanyName;
    }

    public void setCustCompanyName(String custCompanyName) {
        this.custCompanyName = custCompanyName;
    }

    public String getMyCompanyName() {
        return myCompanyName;
    }

    public void setMyCompanyName(String myCompanyName) {
        this.myCompanyName = myCompanyName;
    }

    public List<JournalDto> getJournals() {
        return journals;
    }

    public void setJournals(List<JournalDto> journals) {
        this.journals = journals;
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
