package com.example.controller.service;

import com.example.common.exception.ServiceException;
import com.example.controller.service.base.BaseDaoService;
import com.example.entity.order.Company;
import com.example.entity.order.Contact;
import com.example.entity.dto.CRMUserDto;
import com.example.entity.dto.order.ContactDto;
import com.example.entity.mapper.ContactMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactService extends BaseDaoService {
    @Autowired
    HttpSession httpSession;


    @Transactional(propagation= Propagation.REQUIRED)
    public ContactDto saveContact(Long companyid, ContactDto contactDto, long tenantid) throws Exception {

        Company company =(Company) find(Company.class, companyid);
        if(company == null) {
            throw new ServiceException("Company not found for ID : " + companyid);
        }

        Contact contact = null;
        if(contactDto.getId() != null) {
            contact = (Contact) find(Contact.class, contactDto.getId());
        }else {
            contact = findContactByName(contactDto.getName(), companyid, tenantid);
            if(contact != null) {
                throw new ServiceException("Contact with name already exists");

            } else {
                contact = new Contact();
            }
        }
        contact = ContactMapper.mapDtoToEntity.apply(contactDto,contact);
        contact.setCompanyid(company.getId());
        contact.setTenantid(company.getTenantid());
        contact.setType(company.getType());

        if(contactDto.getId() == null) {

            persist(contact);
        }else {
            merge(contact);
        }

        return ContactMapper.mapperEntityToDto.apply(contact);


    }



    //why write throws Exception?
    public List<ContactDto> getAllContacts () throws Exception {
        CRMUserDto userDto = getUser();
        TypedQuery<Contact> allContacts = createNamedQuery(Contact.Q_CONTACT_LIST, Contact.class);
        allContacts.setParameter("tenantid", userDto.getTenantid());
        List <Contact> contacts = allContacts.getResultList();    //a typedQuery method
        List<ContactDto> conts = contacts.stream().map(ContactMapper.mapperEntityToDto).collect(Collectors.toList());
        return conts;


    }
    public Contact findContactByName (String name, long companyid, long tenantId) throws Exception{
        Contact contact = null;
        try {
            TypedQuery<Contact> findQry = createNamedQuery(Contact.Q_FIND_BY_NAME, Contact.class);
            findQry.setParameter("name", name);
            findQry.setParameter("tenantid", tenantId);
            findQry.setParameter("companyid", companyid);
            Contact c = findQry.getSingleResult();

        } catch (Exception e){
            e.printStackTrace();
        }
        return contact;
    }


}


