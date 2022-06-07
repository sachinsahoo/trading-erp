package com.example.entity.order;

import com.example.constant.InvTransactionType;
import com.example.entity.acct.Journal;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name= "invtransaction", schema = "inventory")
@NamedQueries({
        @NamedQuery(name = "transaction.list", query = "SELECT t from InvTransaction t where t.tenantid=:tenantid"),
        @NamedQuery(name = "transaction.byoid", query = "SELECT t from InvTransaction t where t.order.id=:orderid"),
        @NamedQuery(name = "transaction.byinvid", query = "SELECT t from InvTransaction t where t.invoice.id=:invid"),

})
public class InvTransaction implements Serializable {
    public static final String Q_TRANSACTION_LIST = "transaction.list";
    public static final String Q_BY_OID = "transaction.byoid";
    public static final String Q_BY_INVID = "transaction.byinvid";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long tenantid;
    private LocalDateTime transdate;

    private BigDecimal amount;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "clientid", foreignKey=@ForeignKey(name="trans_client_company_fkey"))
    private Company client;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mycompanyid", foreignKey=@ForeignKey(name="trans_my_company_fkey"))
    private Company mycompany;

    private Integer type;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "orderid", foreignKey=@ForeignKey(name="trans_order_fkey"))
    private PurchaseOrder order;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "invid", foreignKey=@ForeignKey(name="trans_invoice_fkey"))
    private Invoice invoice;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "transid", foreignKey=@ForeignKey(name="trans_journal_fkey"))
    private Set<Journal> journals;

    @Column(length = 80)
    private String notes;

    public InvTransaction() {
    }

    public InvTransaction(Long tenantid, LocalDateTime transdate, int type) {
        this.tenantid = tenantid;
        this.transdate = transdate;
        this.type = type;
    }

    public InvTransaction(InvTransactionType type, PurchaseOrder order, Invoice invoice, BigDecimal amount, LocalDateTime dateTime, Long tenantid) {
        this.type = type.getCode();
        this.notes = type.getDesc();
        this.tenantid = tenantid;
        this.transdate = dateTime;
        this.amount = amount;
        this.client = order.getClient();
        this.mycompany = order.getMyCompany();
        this.order = order;
        this.invoice = invoice;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDateTime getTransdate() {
        return transdate;
    }

    public void setTransdate(LocalDateTime transdate) {
        this.transdate = transdate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Company getClient() {
        return client;
    }

    public void setClient(Company client) {
        this.client = client;
    }

    public Company getMycompany() {
        return mycompany;
    }

    public void setMycompany(Company mycompany) {
        this.mycompany = mycompany;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public PurchaseOrder getOrder() {
        return order;
    }

    public void setOrder(PurchaseOrder order) {
        this.order = order;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Set<Journal> getJournals() {
        return journals;
    }

    public void setJournals(Set<Journal> journals) {
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


