package com.example.mock.dto;

import com.example.entity.dto.TenantDto;

import java.util.function.Supplier;

public class TenantDtoMock {
    public static Supplier<TenantDto> mock =
            new Supplier<TenantDto>() {
                @Override
                public TenantDto get() {
                    TenantDto tenant1 = new TenantDto();
                    tenant1.setCompanyname("Miller Steel");
                    tenant1.setCode("1234");
                    return tenant1;
                }


            };

}