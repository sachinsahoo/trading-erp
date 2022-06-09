package com.example.entity.order;

import com.example.entity.acct.Account;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "company", schema = "inventory")

@NamedQueries({
        @NamedQuery(name = "company.list", query = "SELECT c from Company c where tenantid=:tenantid"),
        @NamedQuery(name = "company.findByName", query = "SELECT c from Company c where c.name like :name")
})
public class Company implements Serializable {

    public static final String Q_LIST = "company.list";
    public static final String Q_FIND_BY_NAME = "company.findByName";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long tenantid;

    private int type;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "companyid", foreignKey=@ForeignKey(name="company_contact_fkey"))
    private Set<Contact> contacts;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dispcontactid", foreignKey=@ForeignKey(name="company_dispcontact_fkey"))
    private Contact dispatchContact;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "invcontactid", foreignKey=@ForeignKey(name="company_invcontact_fkey"))
    private Contact invoiceContact;

    @OneToOne
    @JoinColumn(name = "clientid", foreignKey=@ForeignKey(name="company_account_fkey"))
    private Account account;

    @Column(length = 40)
    private String name;
    @Column(length = 20)
    private String phone;
    @Column(length = 40)
    private String email;
    @Column(length = 40)
    private String tan;
    @Column(length = 40)
    private String pan;
    @Column(length = 40)
    private String gstin1;

    @Column(length = 40)
    private String bankname;
    @Column(length = 20)
    private String accounttype;
    @Column(length = 40)
    private String bankaccno;
    @Column(length = 40)
    private String ifsccode;

    private BigDecimal balance= BigDecimal.ZERO;

    private BigDecimal payable= BigDecimal.ZERO;
    private BigDecimal receivable= BigDecimal.ZERO;
    private BigDecimal bank= BigDecimal.ZERO;

    public Company() {
    }

    public Company(Long id) {
        this.id = id;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Set<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(Set<Contact> contacts) {
        this.contacts = contacts;
    }

    public Contact getDispatchContact() {
        return dispatchContact;
    }

    public void setDispatchContact(Contact dispatchContact) {
        this.dispatchContact = dispatchContact;
    }

    public Contact getInvoiceContact() {
        return invoiceContact;
    }

    public void setInvoiceContact(Contact invoiceContact) {
        this.invoiceContact = invoiceContact;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
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

    public String getAccounttype() {
        return accounttype;
    }

    public void setAccounttype(String accounttype) {
        this.accounttype = accounttype;
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
