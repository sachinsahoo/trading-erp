package com.example.mock.dto;

import com.example.entity.dto.order.ProductDto;

import java.util.function.Supplier;

public class ProductDtoMock {


    public static Supplier<ProductDto> mock  =
            new Supplier<ProductDto>() {
                @Override
                public ProductDto get() {
                    ProductDto product1 = new ProductDto("Product 1", "10000", "11000", "100",
                            2384485L, "", "Machinery", "Metalworking",
                            "UOM3434", "hsn3483948", "5", "5", "10", "Model 3454");

                    return product1;
                }
            };

    public static Supplier<ProductDto> mock1 =
            new Supplier<ProductDto>() {
                @Override
                public ProductDto get() {
                    ProductDto product1 = new ProductDto("Product 2", "10000", "11000", "100",
                            2384485L, "", "Machinery", "Metalworking",
                            "UOM3434", "hsn3483948", "5", "5", "10", "Model 3454");

                    return product1;
                }
            };






}
