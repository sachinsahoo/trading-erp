package com.example.entity.acct;

import com.example.constant.AcGroupNode;
import com.example.entity.dto.acct.AccountGroupDto;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

@Entity
@Table(name = "accountgroup", schema = "inventory")

@NamedQueries({
        @NamedQuery(name = "accountgroup.list", query = "SELECT c from AccountGroup c where tenantid=:tenantid"),
        @NamedQuery(name = "accountgroup.bycode", query = "SELECT c from AccountGroup c where tenantid=:tenantid and internalcode=:code "),
        @NamedQuery(name = "accountgroup.findByName", query = "SELECT c from AccountGroup c where tenantid=:tenantid and c.name like :name")


})

public class AccountGroup {
    public static final String Q_LIST = "accountgroup.list";
    public static final String Q_BY_CODE = "accountgroup.bycode";
    public static final String Q_Find_BY_NAME = "accountgroup.findByName";
   

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long tenantid;
    private Long parentid;

    @Column(length = 60)
    private String name;

    private int actype;
    private int internalcode;
    private int index;
    private int level;

    public AccountGroup() {
    }


    public AccountGroup(AcGroupNode group) {
        this.actype = group.equals(AcGroupNode.ROOT) ? 0 : group.getAccountType().getCode();
        this.level = group.getLevel();
        this.internalcode = group.getCode();
        this.index = group.getCode();
        this.name = group.getDesc();
    }

    public AccountGroup(String groupName, AccountGroupDto parent) {
        this.setTenantid(parent.getTenantid());
        this.name = groupName;
        this.setParentid(parent.getId());
        this.level = parent.getLevel() + 1;
        this.internalcode = 100000; // Todo Dynamic internal code
        this.index = 100000; // Todo Dynamic index
    }


    public Long getParentid() {
        return parentid;
    }

    public void setParentid(Long parentid) {
        this.parentid = parentid;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTenantid() {
        return tenantid;
    }

    public void setTenantid(Long tenantid) {
        this.tenantid = tenantid;
    }

    public int getActype() {
        return actype;
    }

    public void setActype(int actype) {
        this.actype = actype;
    }

    public int getInternalcode() {
        return internalcode;
    }

    public void setInternalcode(int internalcode) {
        this.internalcode = internalcode;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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
