package com.example.entity.rs;


import com.example.common.exception.InputValidationException;
import com.example.entity.dto.CRMUserDto;
import com.example.entity.dto.order.ProductDto;
import com.example.entity.rs.order.ProductAddRequest;
import com.example.mock.dto.CRMUserDtoMock;
import com.example.mock.dto.ProductDtoMock;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.concurrent.Callable;

@SpringBootTest
public class LoginRequestTest {

    @Test
    public void testValidate_crmuserdto_null() {
        LoginRequest request = new LoginRequest();
        try {
            request.validate();
            Assert.fail();

        } catch (InputValidationException iex) {
            Assert.assertEquals(iex.getError(), "Please provide username and password");

        }
    }

    @Test
    public void testValidate_login_success() {
        LoginRequest request = new LoginRequest();
        CRMUserDto crmUserDto = CRMUserDtoMock.mock.get();
        request.setUser(crmUserDto);
        try {
            request.validate();


        } catch (InputValidationException iex) {
            Assert.fail(iex.getMessage());
        }

    }

    @Test
    public void testValidate_userNull() {
        LoginRequest request = new LoginRequest();
        CRMUserDto crmUserDto = CRMUserDtoMock.mock.get();
        crmUserDto.setUsername("");
        request.setUser(crmUserDto);
        try {
            request.validate();
            Assert.fail();

        } catch (InputValidationException iex) {
            Assert.assertEquals(iex.getError(), "Please provide username and password");

        }

    }

    @Test
    public void testValidate_passwordNull() {
        LoginRequest request = new LoginRequest();
        CRMUserDto crmUserDto = CRMUserDtoMock.mock.get();
        crmUserDto.setPassword("");
        request.setUser(crmUserDto);
        try {
            request.validate();
            Assert.fail();

        } catch (InputValidationException iex) {
            Assert.assertEquals(iex.getError(), "Please provide username and password");

        }

    }
}






