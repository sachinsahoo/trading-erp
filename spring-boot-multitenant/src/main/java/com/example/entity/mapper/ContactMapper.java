package com.example.entity.mapper;

import com.example.constant.CompanyType;
import com.example.entity.order.Contact;
import com.example.entity.dto.order.ContactDto;

import java.util.function.BiFunction;
import java.util.function.Function;

public class ContactMapper {

    public static final Function<Contact, ContactDto> mapperEntityToDto = (contact) -> {
        ContactDto contactDto = new ContactDto();
        if (contact == null) {
            return null;
        }
        contactDto.setId(contact.getId());
        contact.setType(contact.getType());
        contactDto.setName(contact.getName());
        contactDto.setAddress1(contact.getAddress1());
        contactDto.setAddress2(contact.getAddress2());
        contactDto.setCity(contact.getCity());
        contactDto.setEmail(contact.getEmail());
        contactDto.setPhone1(contact.getPhone1());
        contactDto.setPhone2(contact.getPhone2());
        contactDto.setPincode(contact.getPincode());
        contactDto.setState(contact.getState());
        contactDto.setTenantid(contact.getTenantid());


        return contactDto;
    };

    public static final Function<ContactDto, Contact> mapperDtoToEntity = (contactDto) -> {
        Contact contact = new Contact();
        if (contactDto == null) {
            return null;
        }
        contact.setType(CompanyType.getTypeCode(contactDto.getType()));
        contact.setName(contactDto.getName());
        contact.setAddress1(contactDto.getAddress1());
        contact.setAddress2(contactDto.getAddress2());
        contact.setCity(contactDto.getCity());
        contact.setEmail(contactDto.getEmail());
        contact.setPhone1(contactDto.getPhone1());
        contact.setPhone2(contactDto.getPhone2());
        contact.setPincode(contactDto.getPincode());
        contact.setState(contactDto.getState());
        contact.setTenantid(contactDto.getTenantid());
        return contact;

    };


    public static final BiFunction<ContactDto,Contact, Contact> mapDtoToEntity = (contactDto, contact) -> {

        contact.setType(CompanyType.getTypeCode(contactDto.getType()));
        contact.setName(contactDto.getName());
        contact.setAddress1(contactDto.getAddress1());
        contact.setAddress2(contactDto.getAddress2());
        contact.setCity(contactDto.getCity());
        contact.setEmail(contactDto.getEmail());
        contact.setPhone1(contactDto.getPhone1());
        contact.setPhone2(contactDto.getPhone2());
        contact.setPincode(contactDto.getPincode());
        contact.setState(contactDto.getState());
        return contact;

    };


}
