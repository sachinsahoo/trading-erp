package com.example.entity.mapper;

import com.example.constant.CompanyType;
import com.example.entity.order.Company;
import com.example.entity.dto.order.CompanyDto;
import com.example.entity.dto.order.ContactDto;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CompanyMapper {

    public static final Function<Company, CompanyDto> mapperEntityToDto = (company) -> {
        CompanyDto dto = new CompanyDto();
        if (company == null) {
            return null;
        }

        dto.setId(company.getId());
        dto.setTenantid(company.getTenantid());
        dto.setType(CompanyType.getTypeString(company.getType()));
        dto.setName(company.getName());
        dto.setPhone(company.getPhone());
        dto.setEmail(company.getEmail());
        dto.setAccountType(company.getAccounttype());
        dto.setBankaccno(company.getBankaccno());
        dto.setBankname(company.getBankname());
        dto.setBalance(company.getBalance());
        dto.setPayable(company.getPayable());
        dto.setReceivable(company.getReceivable());
        dto.setBank(company.getBank());
        if(company.getContacts() != null ) {
            List<ContactDto> contactDtoList = company.getContacts().stream().map(ContactMapper.mapperEntityToDto).collect(Collectors.toList());
            dto.setContactList(contactDtoList);
        }
        if(company.getInvoiceContact() != null ) {
            dto.setInvoiceContact(ContactMapper.mapperEntityToDto.apply(company.getInvoiceContact()));
            dto.setInvoiceContactId(company.getInvoiceContact().getId());
        }
        if(company.getDispatchContact() != null ) {
            dto.setDispatchContact(ContactMapper.mapperEntityToDto.apply(company.getDispatchContact()));
            dto.setDispatchContactId(company.getDispatchContact().getId());
        }
        if(company.getAccount() != null) {
            dto.setAccount(AccountMapper.mapperEntityToDto.apply(company.getAccount()));
        }

        dto.setGstin1(company.getGstin1());
        dto.setIfsccode(company.getIfsccode());
        dto.setPan(company.getPan());
        dto.setTan(company.getTan());
        return dto;

    };



    public static final BiFunction<CompanyDto, Company, Company> mapDtoToEntity = (CompanyDto, company) -> {

        company.setType(CompanyType.getTypeCode(CompanyDto.getType()));
        company.setName(CompanyDto.getName());
        company.setPhone(CompanyDto.getPhone());
        company.setEmail(CompanyDto.getEmail());
        company.setAccounttype(CompanyDto.getAccountType());
        company.setBankaccno(CompanyDto.getBankaccno());
        company.setBankname(CompanyDto.getBankname());

        company.setGstin1(CompanyDto.getGstin1());
        company.setIfsccode(CompanyDto.getIfsccode());
        company.setPan(CompanyDto.getPan());
        company.setTan(CompanyDto.getTan());
        company.setBalance(CompanyDto.getBalance());
        return company;

    };


}
