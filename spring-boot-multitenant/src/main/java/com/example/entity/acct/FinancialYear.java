package com.example.entity.acct;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


@Entity
@Table(name = "finyear", schema = "inventory")

@NamedQueries({
        @NamedQuery(name = "FinancialYear.all", query = "SELECT f from FinancialYear f where tenantid=:tenantid"),
        @NamedQuery(name = "FinancialYear.current", query = "SELECT f from FinancialYear f where f.enddate > :date")
})
public class FinancialYear implements Serializable {

    public static final String Q_ALL = "account.all";
    public static final String Q_BY_CODE = "account.bycode";
    public static final String Q_BY_GROUP = "account.bygroup";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long tenantid;

    private LocalDateTime startdate;
    private LocalDateTime enddate;

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

    public LocalDateTime getStartdate() {
        return startdate;
    }

    public void setStartdate(LocalDateTime startdate) {
        this.startdate = startdate;
    }

    public LocalDateTime getEnddate() {
        return enddate;
    }

    public void setEnddate(LocalDateTime enddate) {
        this.enddate = enddate;
    }

    public FinancialYear() {
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
