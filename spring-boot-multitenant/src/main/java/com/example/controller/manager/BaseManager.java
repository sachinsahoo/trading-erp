package com.example.controller.manager;


import com.example.common.exception.ServiceException;
import com.example.entity.dto.CRMUserDto;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;

public class BaseManager {

    @Autowired
    HttpSession session;

    public Long getUserTenant() throws  Exception{

        CRMUserDto user =(CRMUserDto) session.getAttribute("user");
        if(user == null) {
            throw new ServiceException("User not in session");
        }
        return user.getTenantid();

    }





}
