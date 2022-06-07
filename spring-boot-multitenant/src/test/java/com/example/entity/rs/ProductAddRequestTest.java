package com.example.entity.rs;

import com.example.common.exception.InputValidationException;
import com.example.entity.dto.order.ProductDto;
import com.example.entity.rs.order.ProductAddRequest;
import com.example.mock.dto.ProductDtoMock;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class ProductAddRequestTest {

    @Test
    public void testValidate_product_null() {
        ProductAddRequest request = new ProductAddRequest();
        try {
            request.validate();
            Assert.fail();

        } catch (InputValidationException iex) {

        }


    }

    @Test
    public void testValidate_product_success() {
        ProductAddRequest request = new ProductAddRequest();
        ProductDto productDto = ProductDtoMock.mock.get();
        productDto.setName("Product1 " + LocalDateTime.now());
        request.setProduct(productDto);
        try {
            request.validate();


        } catch (InputValidationException iex) {
            Assert.fail(iex.getMessage());

        }
    }

    @Test
    public void testValidate_product_nameLength() {
        ProductAddRequest request = new ProductAddRequest();
        ProductDto productDto = ProductDtoMock.mock.get();
        productDto.setName("A");
        request.setProduct(productDto);
        try {
            request.validate();
            Assert.fail();
        } catch (InputValidationException iex) {

        }

    }
}