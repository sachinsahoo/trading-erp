package com.example.entity.rs.order;

import com.example.entity.rs.base.BaseResponse;
import com.example.entity.dto.order.ContactDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;


    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public class ContactResponse extends BaseResponse {

        private List<ContactDto> contacts;

        public List<ContactDto> getContacts() {
            return contacts;
        }

        public void setContacts(List<ContactDto> contacts) {
            this.contacts = contacts;
        }
    }

