package com.example.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "organization", schema = "public")
@NamedQueries({
        @NamedQuery(name = "organization.list", query = "SELECT t from Organization t")

})

public class Organization implements Serializable {
    public static final String Q_LIST = "organization.list";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long tenantid;

    @Column(length = 30, name = "org_code")
    private String orgCode;

    @Column(length = 30, name = "org_name")
    private String orgName;

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

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
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
