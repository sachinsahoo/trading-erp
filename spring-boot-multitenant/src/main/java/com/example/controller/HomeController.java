package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class HomeController {

    @Autowired


    @RequestMapping(value = "/home", method = RequestMethod.POST)
    public String save() {
        return "forward:index.html";

    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String getAll()  {
        return "forward:index.html";
    }

    @RequestMapping(value = "/api/order", method = RequestMethod.GET)
    public String testAPI(HttpServletRequest request)  {
        request.getSession();
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println( authentication.getAuthorities());
        return "Hello";
    }


}
