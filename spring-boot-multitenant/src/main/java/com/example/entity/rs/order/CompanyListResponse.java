package com.example.entity.rs.order;

import com.example.entity.rs.base.BaseResponse;
import com.example.entity.dto.order.CompanyDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyListResponse extends BaseResponse {
    private List<CompanyDto> companies;

    public List<CompanyDto> getCompanies() {
        return companies;
    }

    public void setCompanies(List<CompanyDto> companies) {
        this.companies = companies;
    }
}
