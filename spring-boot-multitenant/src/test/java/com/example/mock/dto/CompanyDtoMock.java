package com.example.mock.dto;

import com.example.entity.dto.order.CompanyDto;

import java.util.function.Supplier;

public class CompanyDtoMock {
    public static Supplier<CompanyDto> mock =
            new Supplier<CompanyDto>() {
                @Override
                public CompanyDto get() {
                    CompanyDto company1 = new CompanyDto("self", "Tata Steel", "203-495-5943",
                            "johnson@gmail.com", "3489432", "8487589475", "4343435",
                            "Frost Bank", "Checking", "28398434", "1232444");
                    return company1;
                }
            };
}
