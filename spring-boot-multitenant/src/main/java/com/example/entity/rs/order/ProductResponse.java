package com.example.entity.rs.order;

import com.example.entity.rs.base.BaseResponse;
import com.example.entity.dto.order.ProductDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResponse extends BaseResponse {

private ProductDto product;

    public ProductDto getProduct() {
        return product;
    }

    public void setProduct(ProductDto product) {
        this.product = product;
    }
}
