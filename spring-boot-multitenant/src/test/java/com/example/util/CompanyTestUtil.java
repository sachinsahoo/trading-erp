package com.example.util;

import com.example.constant.CompanyType;
import com.example.controller.manager.CompanyManager;
import com.example.controller.service.CompanyService;
import com.example.controller.service.ContactService;
import com.example.entity.dto.order.CompanyDto;
import com.example.entity.dto.order.ContactDto;
import com.example.entity.order.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyTestUtil {
    @Autowired
    CompanyManager companyManager;

    @Autowired
    CompanyService companyService;

    @Autowired
    ContactService contactService;

    // Create 4 products
    // createProduct(String name)
    @Transactional
    public List<CompanyDto> createCompanies() throws Exception {
        List<CompanyDto> companies = new ArrayList<>();
        companies.add(saveCompanyIfNotPresent(getCompanyDto1("Tata Steel", "self")));
        companies.add(saveCompanyIfNotPresent(getCompanyDto1("Bokaro Steel", "self")));
        companies.add(saveCompanyIfNotPresent(getCompanyDto1("Vishaka Steel", "self")));

        companies.add(saveCompanyIfNotPresent(getCompanyDto1("Sabre Steel", "customer")));
        companies.add(saveCompanyIfNotPresent(getCompanyDto1("National Steel", "customer")));
        companies.add(saveCompanyIfNotPresent(getCompanyDto1("Birla Steel", "customer")));
        companies.add(saveCompanyIfNotPresent(getCompanyDto1("Eagle National Steel", "customer")));
        companies.add(saveCompanyIfNotPresent(getCompanyDto1("Eastern Steel", "customer")));

        companies.add(saveCompanyIfNotPresent(getCompanyDto1("Pinnacle Steel", "supplier")));
        companies.add(saveCompanyIfNotPresent(getCompanyDto1("Zeeco Steel", "supplier")));
        companies.add(saveCompanyIfNotPresent(getCompanyDto1("Rourkela Steel", "supplier")));

        companies.add(saveCompanyIfNotPresent(getCompanyDto1("Reliance Steel", "agent")));
        return companies;
    }


    public CompanyDto getCompanyByType(CompanyType companyType) throws Exception{
        List<CompanyDto> companies = createCompanies();

        CompanyDto companyDto = companies.stream().filter(dto -> {
            return dto.getType() == companyType.getType();
        }).findFirst().get();
        return companyDto;
    }

    @Transactional
    public CompanyDto saveCompanyIfNotPresent(CompanyDto companyDto) throws Exception {
        CompanyDto dbComp  = companyService.findByCompanyName(companyDto.getName());
        if(dbComp != null) {
            companyDto =  dbComp;
        } else {
            companyDto = companyManager.saveCompany(companyDto);
        }
        return companyDto;
    }

    @Transactional
    public CompanyDto getCompanyDto1(String name, String type) throws Exception {
        CompanyDto companyDto1 = null;
        try {

             companyDto1 = new CompanyDto(type, name, "203-495-5943", "johnson@gmail.com", "3489432", "8487589475", "4343435", "Frost Bank", "Checking", "28398434", "1232444");
            companyDto1 = saveCompanyIfNotPresent(companyDto1);
            ContactDto selfContact1 = new ContactDto("Paul Jones", "2345 Bryson Ave.", "", "Austin", "Texas", "2343", "454-484-5893", "221-326-7878", "pjones@gmail.com");
            selfContact1 = contactService.saveContact(companyDto1.getId(), selfContact1, companyService.getTenantId());
            ContactDto selfContact2 = new ContactDto("Annette Diaz", "1267 Commerce Ave.", "", "Austin", "Texas", "2343", "454-484-5893", "221-326-7878", "pjones@gmail.com");
            selfContact2 = contactService.saveContact(companyDto1.getId(), selfContact2, companyService.getTenantId());
            companyDto1.setInvoiceContactId(selfContact1.getId());
            companyDto1.setDispatchContactId(selfContact2.getId());
            companyDto1 = companyManager.saveCompany(companyDto1);

        }catch (Exception e){
            System.out.println("Exception 342" + e.getMessage());
        }

        return companyDto1;

    }

    public void deleteAllCompanies() throws Exception  {
        List<Company> companyList =  companyService.getAllCompanies();
        for(Company company : companyList) {
            deleteCompanyById(company.getId());
        }
    }

    public void deleteCompanyById(Long id) throws Exception {
        Company company = (Company) companyService.find(Company.class, id);
        company.setDispatchContact(null);
        company.setInvoiceContact(null);
        companyService.merge(company);
        companyService.refresh(company);
//        for(Contact contact : company.getContacts()){
//            companyService.remove(contact);
//        }
        companyService.remove(company);
    }


    public void deleteProductById4(Long id) throws Exception  {}

    public void deleteProductById5(Long id) throws Exception  {}

    public void deleteProductById6(Long id) throws Exception  {}

}