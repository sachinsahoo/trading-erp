package com.example.entity.rs.order;



import com.example.common.exception.InputValidationException;
import com.example.entity.rs.base.AbstractBaseRequest;
import com.example.entity.dto.order.ContactDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.StringUtils;

@JsonIgnoreProperties(ignoreUnknown = true)

public class ContactAddRequest extends AbstractBaseRequest {   //what does abstractbase request do?
    private ContactDto contact;
    private Long companyId;

    public ContactDto getContact(){
        return contact;
    }
    public void setContact(ContactDto contact){
        this.contact = contact;   //this refers to the contact outside this method

    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    @Override
    public void validate() throws InputValidationException {
        if (companyId == null)
            throw new InputValidationException("No company present");
        if(contact == null)
            throw new InputValidationException("No contact present");
        if(StringUtils.isEmpty(contact.getName()) || contact.getName().length()<3){
            throw new InputValidationException("Contact name min 3 characters");
        }


    }

}
