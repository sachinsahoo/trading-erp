package com.example.entity.rs;

import com.example.entity.rs.base.BaseResponse;
import com.example.entity.dto.TenantDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TenantResponse extends BaseResponse {

private List<TenantDto> tenants;

    public List<TenantDto> getTenants() {
        return tenants;
    }

    public void setTenants(List<TenantDto> tenants) {
        this.tenants = tenants;
    }
}
