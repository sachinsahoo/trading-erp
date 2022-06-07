package com.example;

import com.example.common.exception.ServiceException;
import com.example.controller.manager.UserManager;
import com.example.controller.service.ProductService;
import com.example.entity.dto.AppLog;
import com.example.entity.dto.CRMUserDto;
import com.example.entity.rs.LoginRequest;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public abstract class TradeAbstractTest {
    protected MockMvc mvc;
    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserManager userManager;

    @Autowired
    private HttpSession session;



    protected void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        login();
    }
    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
    protected <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }

//    -------------------------------------------------- Application specific ---------------------------------------------------


    public void login(){
        try {
            LoginRequest loginRequest = new LoginRequest();
            CRMUserDto user = new CRMUserDto();
//            user.setUsername("leahgold1");
//            user.setPassword("password");
            user.setUsername("test");
            user.setPassword("test");
            loginRequest.setUser(user);
            userManager.login(loginRequest.getUser());

        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void CreateDataSet(LocalDateTime start, LocalDateTime end) {

    }
    // delete all records

    public void addToSessionAppLog(String key, String msg) throws ServiceException {
        AppLog appLog = (AppLog) session.getAttribute(key);
        appLog.getLogs().add(msg);
    }

}