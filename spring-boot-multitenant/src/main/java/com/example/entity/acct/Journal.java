package com.example.entity.acct;

import com.example.constant.DrCrType;
import com.example.entity.order.InvTransaction;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "journal", schema = "inventory")

@NamedQueries({
        @NamedQuery(name = "journal.acid", query = "SELECT j from Journal j where account.id=:id"),
        @NamedQuery(name = "journal.internalAcct", query = "SELECT j from Journal j where tenantid=:tenantid and actype=:actype"),
        @NamedQuery(name = "journal.clientAcct", query = "SELECT j from Journal j where tenantid=:tenantid and actype=:actype and clientid=:clientid")
})
public class Journal implements Serializable {

    public static final String Q_BY_ACCOUNT_ID = "journal.acid";
    public static final String Q_BY_INTERNAL_ACCOUNT = "journal.internalAcct";
    public static final String Q_BY_CLIENT_ACCOUNT = "journal.clientAcct";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long tenantid;

    private Long transid;
    private Long mycompanyid;
    private LocalDateTime transdate;
    private int drcrtype;

    @OneToOne()
    @JoinColumn(name = "acctid", foreignKey=@ForeignKey(name="journal_account_fkey"))
    private Account account;
    private BigDecimal amount;
    private String description;


    public Journal() {
    }

    public Journal( String description, InvTransaction transaction, Account account, DrCrType drcr, BigDecimal amount) {
        this.description = description;
        this.tenantid = transaction.getTenantid();
        this.transid = transaction.getId();
        this.mycompanyid = transaction.getMycompany().getId();
        this.transdate = transaction.getTransdate();
        this.account = account;
        this.drcrtype = drcr.getCode();
        this.amount = amount;
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

    public Long getTransid() {
        return transid;
    }

    public void setTransid(Long transid) {
        this.transid = transid;
    }

    public Long getMycompanyid() {
        return mycompanyid;
    }

    public void setMycompanyid(Long mycompanyid) {
        this.mycompanyid = mycompanyid;
    }

    public LocalDateTime getTransdate() {
        return transdate;
    }

    public void setTransdate(LocalDateTime transdate) {
        this.transdate = transdate;
    }

    public int getDrcrtype() {
        return drcrtype;
    }

    public void setDrcrtype(int drcrtype) {
        this.drcrtype = drcrtype;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
