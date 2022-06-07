package com.example.entity.acct;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Immutable
@Table(name = "accountbalance", schema = "inventory")

@NamedQueries({
        @NamedQuery(name = "accountbalance.list", query = "SELECT b from AccountBalance b where b.tenantid=:tenantid")
})

public class AccountBalance implements Serializable {
    public static final String Q_LIST = "accountbalance.list";

    @Id
    private Long id;
    private Long tenantid;
    private Long mycompanyid;
    Long acctid;
    BigDecimal totdramt;
    BigDecimal totcramt;

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

    public Long getAcctid() {
        return acctid;
    }

    public void setAcctid(Long acctid) {
        this.acctid = acctid;
    }

    public BigDecimal getTotdramt() {
        if(totdramt == null) {
            return BigDecimal.ZERO;
        } else
        return totdramt;
    }

    public void setTotdramt(BigDecimal totdramt) {
        this.totdramt = totdramt;
    }

    public BigDecimal getTotcramt() {
        if(totcramt == null){
            return BigDecimal.ZERO;
        } else
        return totcramt;
    }

    public void setTotcramt(BigDecimal totcramt) {
        this.totcramt = totcramt;
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
