package com.example.entity.rs.order;

import com.example.entity.rs.base.BaseResponse;
import com.example.entity.dto.order.CompanyDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyListByTypeResponse extends BaseResponse {

    private List<CompanyDto> customers;
    private List<CompanyDto> suppliers;
    private List<CompanyDto> myCompanies;

    public List<CompanyDto> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CompanyDto> customers) {
        this.customers = customers;
    }

    public List<CompanyDto> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<CompanyDto> suppliers) {
        this.suppliers = suppliers;
    }

    public List<CompanyDto> getMyCompanies() {
        return myCompanies;
    }

    public void setMyCompanies(List<CompanyDto> myCompanies) {
        this.myCompanies = myCompanies;
    }
}
