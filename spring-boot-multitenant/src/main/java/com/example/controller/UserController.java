package com.example.controller;

import com.example.common.exception.InputValidationException;
import com.example.constant.RsStatusType;
import com.example.controller.service.UserService;
import com.example.controller.manager.UserManager;
import com.example.entity.rs.LoginRequest;
import com.example.entity.rs.LoginResponse;
import com.example.entity.rs.TenantRequest;
import com.example.entity.rs.TenantResponse;
import com.example.entity.dto.TenantDto;
import com.example.entity.rs.base.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserManager userManager;

    @Autowired
    private PasswordEncoder passwordEncoder;



    @RequestMapping(value = "/login.ws", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {

        LoginResponse response = new LoginResponse();
        response.setStatus(RsStatusType.SUCCESS);

        try {
            loginRequest.validate();
            userManager.login(loginRequest.getUser());

        }catch (InputValidationException e){
            response.setErrorMessage(e.getError());
            response.setStatus(RsStatusType.FAILURE);

        }catch (Exception e){
            response.setStatus(RsStatusType.FAILURE);
            response.setErrorMessage(e.getMessage());

        }
        return new ResponseEntity<LoginResponse>(response, HttpStatus.OK);
    }


    @RequestMapping(value = "/register.ws", method = RequestMethod.POST)
    public ResponseEntity<LoginResponse> register(@RequestBody LoginRequest request) {

        LoginResponse response = new LoginResponse();
        response.setStatus(RsStatusType.SUCCESS);

        try {
            request.validate();
            userManager.register(request.getUser());

        }catch (InputValidationException e){
            response.setErrorMessage(e.getError());
            response.setStatus(RsStatusType.FAILURE);

        }catch (Exception e){
            response.setStatus(RsStatusType.FAILURE);
            response.setErrorMessage(e.getMessage());
        }
        return new ResponseEntity<LoginResponse>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/savetenant.ws", method = RequestMethod.POST)
    public ResponseEntity<TenantResponse> saveTenant(@RequestBody TenantRequest request) {

        TenantResponse response = new TenantResponse();
        response.setStatus(RsStatusType.SUCCESS);

        try {
            request.validate();
           userService.saveTenant(request.getTenant());
            List<TenantDto> tenants = userService.getAllTenants();
            response.setTenants(tenants);

        }catch (InputValidationException e){
            response.setErrorMessage(e.getError());
            response.setStatus(RsStatusType.FAILURE);

        }catch (Exception e){
            response.setStatus(RsStatusType.FAILURE);
            response.setErrorMessage(e.getMessage());
        }
        return new ResponseEntity<TenantResponse>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/listtenant.ws", method = RequestMethod.POST)
    public ResponseEntity<TenantResponse> listTenant(@RequestBody TenantRequest request) {

        TenantResponse response = new TenantResponse();
        response.setStatus(RsStatusType.SUCCESS);

        try {

            List<TenantDto> tenants = userService.getAllTenants();
            response.setTenants(tenants);

        }catch (InputValidationException e){
            response.setErrorMessage(e.getError());
            response.setStatus(RsStatusType.FAILURE);

        }catch (Exception e){
            response.setStatus(RsStatusType.FAILURE);
            response.setErrorMessage(e.getMessage());
        }
        return new ResponseEntity<TenantResponse>(response, HttpStatus.OK);
    }


    @RequestMapping(value = "/keepalive.ws", method = RequestMethod.GET)
    public ResponseEntity alive() {

        BaseResponse response = new BaseResponse();
        response.setStatus(RsStatusType.SUCCESS);
        return new ResponseEntity(response, HttpStatus.OK);
    }



}
