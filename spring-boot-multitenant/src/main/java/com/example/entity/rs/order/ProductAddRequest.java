package com.example.entity.rs.order;

import com.example.common.exception.InputValidationException;
import com.example.entity.rs.base.AbstractBaseRequest;
import com.example.entity.dto.order.ProductDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.StringUtils;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductAddRequest extends AbstractBaseRequest {


    private ProductDto product;


    public ProductDto getProduct() {
        return product;
    }

    public void setProduct(ProductDto prod) {
        this.product = prod;
    }

    @Override
    public void validate() throws InputValidationException {

        if(product == null)
            throw new InputValidationException("No product present");

        if(StringUtils.isEmpty(product.getName()) || product.getName().length() < 3){
            throw new InputValidationException("Product name min 3 characters");
        }


    }

}
