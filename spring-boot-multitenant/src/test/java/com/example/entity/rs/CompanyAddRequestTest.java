package com.example.entity.rs;

import com.example.common.exception.InputValidationException;
import com.example.entity.dto.order.CompanyDto;
import com.example.entity.rs.order.CompanyAddRequest;
import com.example.mock.dto.CompanyDtoMock;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CompanyAddRequestTest {

    @Test
    public void testValidate_null() {
        CompanyAddRequest request = new CompanyAddRequest();
        try {
            request.validate();
            Assert.fail();


        } catch (InputValidationException iex) {
            Assert.assertEquals(iex.getError(), "No company present");
        }
    }

    @Test
    public void testValidate_success(){
        CompanyAddRequest request = new CompanyAddRequest();
        CompanyDto companyDto = CompanyDtoMock.mock.get();
        request.setCompany(companyDto);
        try {
            request.validate();

        } catch (InputValidationException iex) {
            Assert.fail(iex.getMessage());
        }

    }
    @Test
    public void testValidate_nameLength(){
        CompanyAddRequest request = new CompanyAddRequest();
        CompanyDto companyDto = CompanyDtoMock.mock.get();
        companyDto.setName("A");
        request.setCompany(companyDto);
        try{
            request.validate();
            Assert.fail();
        } catch (InputValidationException iex) {
            Assert.assertEquals(iex.getError(),"Company name min 3 characters");
        }
    }
}
