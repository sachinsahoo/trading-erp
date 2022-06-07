package com.example.controller.service;

import com.example.common.exception.DBException;
import com.example.common.exception.InputValidationException;
import com.example.common.exception.ServiceException;
import com.example.constant.CompanyType;
import com.example.controller.service.base.BaseDaoService;
import com.example.entity.order.Company;
import com.example.entity.order.Contact;
import com.example.entity.dto.CRMUserDto;
import com.example.entity.dto.order.CompanyDto;
import com.example.entity.dto.order.ContactDto;
import com.example.entity.mapper.CompanyMapper;
import com.example.entity.mapper.ContactMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CompanyService extends BaseDaoService {

    @Autowired
    ContactService contactService;

    @Autowired
    AccountService accountService;

    @Transactional
    public Company saveCompany(CompanyDto companyDto) throws Exception {      //does exception or serviceexception matter?
        Company company = null;
        CompanyDto respCompany = null;
        if (companyDto.getId() != null) {
            //finds and returns entity if it already exists
            company = (Company) find(Company.class, companyDto.getId());
        } else {
            company = new Company();
        }

        company = CompanyMapper.mapDtoToEntity.apply(companyDto, company);
        company.setTenantid(getTenantId());

        if (companyDto.getInvoiceContactId() != null) {
            Contact invContact = (Contact) find(Contact.class, companyDto.getInvoiceContactId());
            company.setInvoiceContact(invContact);
        }
        if (companyDto.getDispatchContactId() != null) {
            Contact dispContact = (Contact) find(Contact.class, companyDto.getDispatchContactId());
            company.setDispatchContact(dispContact);
        }

        if (company.getId() == null) {
            persist(company);
            if (company.getType() != CompanyType.SELF.getCode())
                accountService.createClientAccount(company);

        } else {
            merge(company);
        }

        company.getContacts();


        return company;

    }


    public List<Company> getAllCompanies() throws ServiceException, DBException {
        CRMUserDto user = getUser();
        TypedQuery<Company> allCompanysQuery = createNamedQuery(Company.Q_LIST, Company.class);
        allCompanysQuery.setParameter("tenantid", user.getTenantid());
        List<Company> companies = allCompanysQuery.getResultList();
        companies.forEach(company -> company.getContacts());
        companies.forEach(company -> company.getDispatchContact());
        companies.forEach(company -> company.getInvoiceContact());

        return companies;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public CompanyDto findByCompanyName(String name) {
        CompanyDto companyDto = null;
        try {
            TypedQuery<Company> findName = createNamedQuery(Company.Q_FIND_BY_NAME, Company.class);
            findName.setParameter("name", name);
            Company company = findName.getSingleResult();
            company.getContacts();
            companyDto = CompanyMapper.mapperEntityToDto.apply(company);

        } catch (Exception e) {

        }
        return companyDto;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Company saveCompanyContact(Long companyId, ContactDto contactDto) throws Exception {

        Company company = (Company) find(Company.class, companyId);
        if (company == null) {
            throw new InputValidationException("Company not found for Id: " + companyId);
        }
        contactService.saveContact(company.getId(), contactDto, getTenantId());

        getEntityManager().refresh(company);
        return company;

    }


    @Transactional(propagation = Propagation.REQUIRED)
    public List<ContactDto> getCompanyContacts(Long companyId) throws Exception {
        List<ContactDto> companyContacts = null;
        Company company = (Company) find(Company.class, companyId);
        if (company == null) {
            throw new InputValidationException("Company not found for Id: " + companyId);
        }
        companyContacts = company.getContacts().stream().map(ContactMapper.mapperEntityToDto).collect(Collectors.toList());
        return companyContacts;
    }

}
