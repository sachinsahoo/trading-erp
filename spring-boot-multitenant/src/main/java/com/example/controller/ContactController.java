package com.example.controller;

import com.example.constant.RsStatusType;
import com.example.controller.service.ContactService;
import com.example.controller.manager.ContactManager;
import com.example.entity.rs.order.ContactResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/contact")

public class ContactController{

    @Autowired
    private ContactService contactService;
    @Autowired
    ContactManager contactManager;


    @RequestMapping(value = "/list.ws", method = RequestMethod.POST)
    public ResponseEntity<?> list(HttpServletRequest request) {

        ContactResponse response = new ContactResponse();
        response.setStatus(RsStatusType.SUCCESS);

        try {
            response.setContacts(contactService.getAllContacts());
        } catch (Exception e) {
            response.setStatus(RsStatusType.FAILURE);
            response.setErrorMessage(e.getMessage());
        }
        return new ResponseEntity<ContactResponse>(response, HttpStatus.OK);
    }








}