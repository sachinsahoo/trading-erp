package com.example.controller.manager;

import com.example.common.exception.InputValidationException;
import com.example.controller.service.UserService;
import com.example.entity.Tenant;
import com.example.entity.dto.CRMUserDto;
import com.example.entity.dto.TenantDto;
import com.example.entity.mapper.TenantMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.io.Serializable;

@Service
public class UserManager implements Serializable {


    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession session;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountManager accountManager;


    public void login(CRMUserDto user) throws Exception {

        CRMUserDto userDto = userService.getByUsername(user.getUsername());

        if(userDto == null) {
            throw new InputValidationException("username not found");
        }

        if(passwordEncoder.matches(user.getPassword(), userDto.getPassword())){
            session.setAttribute("user", userDto);
        } else {
            throw new InputValidationException("username or password did not match");
        }

    }

    /**------------------
     * Register New User
     * ------------------
     */
    public void register(CRMUserDto reqUser) throws Exception {

        CRMUserDto userDB = userService.getByUsername(reqUser.getUsername());

        if(userDB != null) {
            throw new InputValidationException("username already exists");
        }

        if(StringUtils.isEmpty(reqUser.getPassword()) || reqUser.getPassword().length() < 3) {
            throw new InputValidationException("Password min 3 characters");
        }

        if(StringUtils.isEmpty(reqUser.getConfirmPassword()) || !reqUser.getPassword().equals(reqUser.getConfirmPassword())){
            throw new InputValidationException("Confirm Password did not match");
        }

        userService.saveUser(reqUser);

    }



    /**-----------------------------------------
     * Create New Tenant
     *  - Create Accounts and groups for Tenant
     * -----------------------------------------
     */
    public TenantDto saveTenant(TenantDto tenanatDto) throws Exception {
        tenanatDto = TenantMapper.mapperEntityToDto.apply(userService.saveTenant(tenanatDto));
        accountManager.createInternalAccountForTenant(tenanatDto.getId());
        // TODO Run a query to verify All accounts were created

        return tenanatDto;
    }








}
