package com.example.entity.rs;

import com.example.common.exception.InputValidationException;
import com.example.entity.dto.TenantDto;
import com.example.mock.dto.TenantDtoMock;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TenantRequestTest {

    @Test
    public void testValidate_null(){
        TenantRequest request = new TenantRequest();
        try {
            request.validate();
            Assert.fail();
        } catch (InputValidationException iex) {
            Assert.assertEquals(iex.getError(), "Please provide tenant code and company");

        }
    }

    @Test
    public void testValidate_success() {
        TenantRequest request = new TenantRequest();
        TenantDto tenantDto = TenantDtoMock.mock.get();
        request.setTenant(tenantDto);
        try {
            request.validate();
        } catch (InputValidationException iex) {
            Assert.fail(iex.getMessage());

        }

    }

    @Test
    public void testValidate_noCode() {
        TenantRequest request = new TenantRequest();
        TenantDto tenantDto = TenantDtoMock.mock.get();
        tenantDto.setCode("");
        request.setTenant(tenantDto);
        try {
            request.validate();
            Assert.fail();
        } catch (InputValidationException iex) {
            Assert.assertEquals(iex.getError(), "Please provide tenant code and company");

        }

    }

    @Test
    public void testValidate_noCompany() {
        TenantRequest request = new TenantRequest();
        TenantDto tenantDto = TenantDtoMock.mock.get();
        tenantDto.setCompanyname("");
        request.setTenant(tenantDto);
        try {
            request.validate();
        } catch (InputValidationException iex) {
            Assert.assertEquals(iex.getError(), "Please provide tenant code and company");

        }

    }




}
