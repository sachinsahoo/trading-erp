package com.example.entity.mapper;

import com.example.entity.dto.order.POPTaxDto;
import com.example.entity.order.POPTax;

import java.util.function.BiFunction;
import java.util.function.Function;

public class POPTaxMapper {


    public static final Function<POPTax, POPTaxDto> mapperEntityToDto = (poPtax)->{
           POPTaxDto dto = new POPTaxDto();
            if(poPtax == null){
                return null;
            }

            dto.setId(poPtax.getId());
            dto.setCode(poPtax.getCode());
            dto.setDesc(poPtax.getDescription());
            dto.setPercent(poPtax.getPercent());
            dto.setPopid(poPtax.getPopid());

            return dto;
    };



    public static final BiFunction<POPTaxDto, POPTax, POPTax> mapperDtoToEntity = (popTaxDto, entity) -> {

        if(entity == null) {
            entity = new POPTax();
        }
        if(popTaxDto == null){
            return null;
        }

        entity.setCode(popTaxDto.getCode());
        entity.setDescription(popTaxDto.getDesc().toUpperCase());
        entity.setPercent(popTaxDto.getPercent());
        entity.setPopid(popTaxDto.getPopid());

        return entity;

    };


}
