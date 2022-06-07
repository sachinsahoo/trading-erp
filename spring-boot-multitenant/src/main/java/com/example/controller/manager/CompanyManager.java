package com.example.controller.manager;

import com.example.common.exception.InputValidationException;
import com.example.controller.service.CompanyService;
import com.example.entity.order.Company;
import com.example.entity.dto.order.CompanyDto;
import com.example.entity.dto.order.ContactDto;
import com.example.entity.mapper.CompanyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyManager extends BaseManager {

    @Autowired
    private CompanyService companyService;

    // TODO : SaveCompany If notExist
    public CompanyDto saveCompany (CompanyDto companyDto) throws InputValidationException, Exception {
        CompanyDto respCompanyDto = null;
        if(companyDto.getId() == null  && companyService.findByCompanyName(companyDto.getName()) != null) {
            throw new InputValidationException("Company with  name: " + companyDto.getName() + "already exists");
        } else {
            Company company = companyService.saveCompany(companyDto);
            respCompanyDto = CompanyMapper.mapperEntityToDto.apply(company);
        }
        return respCompanyDto;


    }

    public CompanyDto saveCompanyContact (Long companyId, ContactDto contactDto) throws Exception {
        Company company = companyService.saveCompanyContact(companyId, contactDto);
        return CompanyMapper.mapperEntityToDto.apply(company);


    }

    public List<CompanyDto> getAllCompanies () throws Exception {

        List<Company> companies = companyService.getAllCompanies();

        List<CompanyDto> companyDtos = companies.stream().map(CompanyMapper.mapperEntityToDto).collect(Collectors.toList());
        return companyDtos;

    }


}
