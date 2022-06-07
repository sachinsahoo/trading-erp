package com.example.entity.rs;

import com.example.common.exception.InputValidationException;
import com.example.entity.rs.base.AbstractBaseRequest;
import com.example.entity.dto.TenantDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.StringUtils;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TenantRequest extends AbstractBaseRequest {

    private TenantDto tenant;

    public TenantDto getTenant() {
        return tenant;
    }

    public void setTenant(TenantDto tenant) {
        this.tenant = tenant;
    }

    @Override
    public void validate() throws InputValidationException {
        if(tenant == null || StringUtils.isEmpty(tenant.getCode()) || StringUtils.isEmpty(tenant.getCompanyname())) {
           throw new InputValidationException("Please provide tenant code and company");
        }

    }

}
