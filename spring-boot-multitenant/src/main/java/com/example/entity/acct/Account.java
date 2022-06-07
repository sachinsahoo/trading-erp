package com.example.entity.acct;

import com.example.constant.AcGroupNode;
import com.example.constant.InternalAccountType;
import com.example.entity.order.Company;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;


@Entity
@Table(name = "account", schema = "inventory")

@NamedQueries({
        @NamedQuery(name = "account.all", query = "SELECT a from Account a where tenantid=:tenantid"),
        @NamedQuery(name = "account.bycode", query = "SELECT a from Account a where code=:code and tenantid=:tenantid"),
        @NamedQuery(name = "account.byname", query = "SELECT a from Account a where acname=:acname and tenantid=:tenantid"),
        @NamedQuery(name = "account.client", query = "SELECT a from Account a where clientid=:clientid"),
        @NamedQuery(name = "account.bygroup", query = "SELECT a from Account a where group=:group and tenantid=:tenantid")
})
public class Account implements Serializable {

    public static final String Q_ALL = "account.all";
    public static final String Q_BY_CODE = "account.bycode";
    public static final String Q_BY_NAME = "account.byname";
    public static final String Q_CLIENT_ACCT = "account.client";
    public static final String Q_BY_GROUP = "account.bygroup";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long clientid;
    private Long tenantid;
    private Long groupid;
    private int acgroup;
    private int code;
    private String acname;
    private int drcr;
    private BigDecimal balance = BigDecimal.ZERO;

    public Account() {
    }

    public Account(Company client) {
        this.acgroup = AcGroupNode.getClientAccountGroupType(client).getCode();
        this.acname = client.getName();
        this.clientid = client.getId();
    }

    public Account(InternalAccountType iacType, Long tenantid, AccountGroup group) {
        this.tenantid = tenantid;
        this.code = iacType.getCode();
        this.acname = iacType.getDesc();
        this.acgroup = iacType.getGroup().getCode();
        this.groupid = group.getId();


    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGroupid() {
        return groupid;
    }

    public void setGroupid(Long groupid) {
        this.groupid = groupid;
    }

    public Long getClientid() {
        return clientid;
    }

    public void setClientid(Long clientid) {
        this.clientid = clientid;
    }

    public Long getTenantid() {
        return tenantid;
    }

    public void setTenantid(Long tenantid) {
        this.tenantid = tenantid;
    }

    public int getAcgroup() {
        return acgroup;
    }

    public void setAcgroup(int acgroup) {
        this.acgroup = acgroup;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getAcname() {
        return acname;
    }

    public void setAcname(String acname) {
        this.acname = acname;
    }

    public int getDrcr() {
        return drcr;
    }

    public void setDrcr(int drcr) {
        this.drcr = drcr;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
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
