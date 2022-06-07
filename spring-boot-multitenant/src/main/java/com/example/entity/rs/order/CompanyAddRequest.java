package com.example.entity.rs.order;

import com.example.common.exception.InputValidationException;
import com.example.constant.CompanyType;
import com.example.entity.rs.base.AbstractBaseRequest;
import com.example.entity.dto.order.CompanyDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.StringUtils;

@JsonIgnoreProperties (ignoreUnknown = true)
public class CompanyAddRequest extends AbstractBaseRequest {


    private CompanyDto company;
    //CompanyDto companyDto = new CompanyDto(); why not write like this?


    public CompanyDto getCompany() {
        return company;
    }

    public void setCompany(CompanyDto company) {
        this.company = company;

    }

    @Override
    public void validate () throws InputValidationException {
        if (company ==null){
            throw new InputValidationException("No company present");
        }
        if  (StringUtils.isEmpty(company.getName()) || company.getName().length()<3) {
            throw new InputValidationException("Company name min 3 characters");
        }

        if  (StringUtils.isEmpty(company.getType()) || CompanyType.getType(company.getType()).equals(CompanyType.UNKNOWN)) {
            throw new InputValidationException("Company type missing : supplier, customer or self");
        }

    }

}
