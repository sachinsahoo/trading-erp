package com.example.entity.dto.acct;
import com.example.constant.AcCategory;
import com.example.entity.acct.AccountGroup;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountCategoryDto implements Serializable {
    private Long id;
    private Integer code;
    private Integer index;
    private Long tenantid;
    private Set<AccountGroupDto> groups ;

    private String name;

    private Integer type;

    public AccountCategoryDto() {
    }

    public AccountCategoryDto(AcCategory acCategory) {
        this.code = acCategory.getCode();
        this.index = acCategory.getCode();
        this.name = acCategory.getDesc();
        this.type = acCategory.getAccountType().getCode();
    }

    public AccountCategoryDto( Long tenantid,  Integer index,Integer code, String name, Integer type) {
        this.index = index;
        this.code = code;
        this.tenantid = tenantid;
        this.name = name;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getTenantid() {
        return tenantid;
    }

    public void setTenantid(Long tenantid) {
        this.tenantid = tenantid;
    }

    public Set<AccountGroupDto> getGroups() {
        return groups;
    }

    public void setGroups(Set<AccountGroupDto> groups) {
        this.groups = groups;
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
