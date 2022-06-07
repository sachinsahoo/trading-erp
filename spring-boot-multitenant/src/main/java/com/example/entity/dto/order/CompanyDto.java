package com.example.entity.dto.order;

import com.example.common.util.MathUtil;
import com.example.controller.excel.dto.TallyAccount;
import com.example.entity.dto.acct.AccountDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CompanyDto implements Serializable {

    private Long id;
    private Long tenantid;
    private String type;
    private String name;
    private String phone;
    private String email;
    private String tan;
    private String pan;
    private String cst;
    private String gstin1;
    private String gstRegType;

    private String bankname;
    private String accountType;
    private String bankaccno;
    private String ifsccode;
    private BigDecimal balance= BigDecimal.ZERO;

    private BigDecimal payable= BigDecimal.ZERO;
    private BigDecimal receivable= BigDecimal.ZERO;
    private BigDecimal bank= BigDecimal.ZERO;


    private Long invoiceContactId;
    private Long dispatchContactId;

    private List<ContactDto> contactList;
    private ContactDto invoiceContact = new ContactDto();
    private ContactDto dispatchContact = new ContactDto();

    private AccountDto account;

    public String getRupPayable() {
        return MathUtil.rupeesFormat(payable);
    }

    public String getRupReceivable() {
        return MathUtil.rupeesFormat(receivable);
    }

    public String getRupBank() {
        return MathUtil.rupeesFormat(bank);
    }

    public String getRupBalance() {
        return MathUtil.rupeesFormat(balance);
    }



    public CompanyDto() {
    }

    public CompanyDto(String type, String name, String phone, String email, String tan, String pan, String gstin1, String bankname, String accountType, String bankaccno, String ifsccode) {
        this.type = type;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.tan = tan;
        this.pan = pan;
        this.gstin1 = gstin1;
        this.bankname = bankname;
        this.accountType = accountType;
        this.bankaccno = bankaccno;
        this.ifsccode = ifsccode;
    }

    public CompanyDto(TallyAccount tallyAccount) {
        this.setTallyAccountInfo(tallyAccount);
    }

    public void setTallyAccountInfo(TallyAccount tallyAccount) {
        this.name = tallyAccount.getName();
        this.phone = tallyAccount.getContactNo() != null ? tallyAccount.getContactNo() : tallyAccount.getMobileNo();
        this.email = tallyAccount.getEmail();
        this.pan = tallyAccount.getPanNo();
        this.tan = tallyAccount.getTinNo();
        this.cst = tallyAccount.getCstNo();
        this.gstRegType = tallyAccount.getRegistrationType();
        this.gstin1 = tallyAccount.getGstin();
    }

    public String getCst() {
        return cst;
    }

    public void setCst(String cst) {
        this.cst = cst;
    }

    public String getGstRegType() {
        return gstRegType;
    }

    public void setGstRegType(String gstRegType) {
        this.gstRegType = gstRegType;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTan() {
        return tan;
    }

    public void setTan(String tan) {
        this.tan = tan;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getGstin1() {
        return gstin1;
    }

    public void setGstin1(String gstin1) {
        this.gstin1 = gstin1;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getBankaccno() {
        return bankaccno;
    }

    public void setBankaccno(String bankaccno) {
        this.bankaccno = bankaccno;
    }

    public String getIfsccode() {
        return ifsccode;
    }

    public void setIfsccode(String ifsccode) {
        this.ifsccode = ifsccode;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getPayable() {
        return payable;
    }

    public void setPayable(BigDecimal payable) {
        this.payable = payable;
    }

    public BigDecimal getReceivable() {
        return receivable;
    }

    public void setReceivable(BigDecimal receivable) {
        this.receivable = receivable;
    }

    public BigDecimal getBank() {
        return bank;
    }

    public void setBank(BigDecimal bank) {
        this.bank = bank;
    }

    public Long getInvoiceContactId() {
        return invoiceContactId;
    }

    public void setInvoiceContactId(Long invoiceContactId) {
        this.invoiceContactId = invoiceContactId;
    }

    public Long getDispatchContactId() {
        return dispatchContactId;
    }

    public void setDispatchContactId(Long dispatchContactId) {
        this.dispatchContactId = dispatchContactId;
    }

    public List<ContactDto> getContactList() {
        return contactList;
    }

    public void setContactList(List<ContactDto> contactList) {
        this.contactList = contactList;
    }

    public ContactDto getInvoiceContact() {
        return invoiceContact;
    }

    public void setInvoiceContact(ContactDto invoiceContact) {
        this.invoiceContact = invoiceContact;
    }

    public ContactDto getDispatchContact() {
        return dispatchContact;
    }

    public void setDispatchContact(ContactDto dispatchContact) {
        this.dispatchContact = dispatchContact;
    }

    public AccountDto getAccount() {
        return account;
    }

    public void setAccount(AccountDto account) {
        this.account = account;
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
