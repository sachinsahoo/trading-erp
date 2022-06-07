package com.example.entity.dto.acct;

import com.example.constant.AcGroupNode;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountGroupDto implements Serializable {
    private Integer index;
    private Long id;
    private Integer code;
    private String name;
    private Long tenantid;
    private  Long catid;
    private int level;
    private Long parentId;

    public AccountGroupDto() {
    }

    public AccountGroupDto( Long tenantid, Long catid, Integer index, Integer code, String name ) {
        this.index = index;
        this.code = code;
        this.name = name;
        this.tenantid = tenantid;
        this.catid = catid;
    }

    public AccountGroupDto(AcGroupNode groupNode) {
        this.index = groupNode.getCode();
        this.code = groupNode.getCode();
        this.name = groupNode.getDesc();
        this.level = groupNode.getLevel();
        this.parentId = null;
    }

    public  boolean isClient() {
        if(AcGroupNode.SUNDRY_CREDITORS.getCode().equals(this.getCode()) ||
                AcGroupNode.SUNDRY_DEBTORS.getCode().equals(this.getCode())) {
            return true;
        } else {
            return false;
        }
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
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

    public Long getCatid() {
        return catid;
    }

    public void setCatid(Long catid) {
        this.catid = catid;
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
