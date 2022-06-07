package com.example.entity.mapper;

import com.example.entity.Tenant;
import com.example.entity.dto.TenantDto;

import java.util.function.Function;

public class TenantMapper {


    public static final Function<Tenant, TenantDto> mapperEntityToDto = (tenant)->{
        TenantDto dto = new TenantDto();
        if(tenant == null){
            return null;
        }
        dto.setId(tenant.getId());
        dto.setCompanyname(tenant.getCompanyname());
        dto.setCode(tenant.getCode());
        return dto;

    };

    public static final Function<TenantDto, Tenant> mapperDtoToEntity = (tenanatDto)->{
        Tenant entity = new Tenant();
        if(tenanatDto == null){
            return null;
        }

        entity.setCompanyname(tenanatDto.getCompanyname());
        entity.setCode(tenanatDto.getCode());

        return entity;

    };

}
