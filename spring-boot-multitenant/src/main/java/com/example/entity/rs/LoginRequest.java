package com.example.entity.rs;

import com.example.common.exception.InputValidationException;
import com.example.entity.rs.base.AbstractBaseRequest;
import com.example.entity.dto.CRMUserDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.StringUtils;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginRequest extends AbstractBaseRequest {

    private CRMUserDto user;

    public CRMUserDto getUser() {
        return user;
    }

    public void setUser(CRMUserDto user) {
        this.user = user;
    }

    @Override
    public void validate() throws InputValidationException {
        if(user == null || StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword())) {
           throw new InputValidationException("Please provide username and password");
        }


    }

}
