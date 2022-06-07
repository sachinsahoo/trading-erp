package com.example.controller.manager;

import com.example.controller.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class ContactManager extends BaseManager{
    @Autowired
    HttpSession httpSession;

    @Autowired
    ContactService contactService;

}
