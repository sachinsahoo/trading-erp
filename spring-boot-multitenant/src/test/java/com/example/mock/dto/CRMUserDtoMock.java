package com.example.mock.dto;

import com.example.entity.dto.CRMUserDto;
import com.example.entity.dto.order.ProductDto;

import java.util.function.Supplier;

public class CRMUserDtoMock {

    public static Supplier<CRMUserDto> mock =
            new Supplier<CRMUserDto>(){
        @Override
                public CRMUserDto get() {
            CRMUserDto user1 = new CRMUserDto("johnsmith","12345", "johnsmith@gmail.com",
                    "203-391-9393");

            return user1;
        }

    };
}
