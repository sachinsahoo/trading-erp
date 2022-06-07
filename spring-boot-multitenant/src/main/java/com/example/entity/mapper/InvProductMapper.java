package com.example.entity.mapper;

import com.example.entity.dto.order.InvProductDto;
import com.example.entity.dto.order.POProductDto;
import com.example.entity.order.InvProduct;
import com.example.entity.order.POProduct;
import com.example.entity.order.Product;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class InvProductMapper {


    public static final Function<InvProduct, InvProductDto> mapperEntityToDto = (invProduct) -> {
        InvProductDto dto = new InvProductDto();
        if (invProduct == null) {
            return null;
        }

        dto.setId(invProduct.getId());
        dto.setTenantid(invProduct.getTenantid());
        dto.setInvid(invProduct.getTenantid());
        dto.setQuantity(invProduct.getQuantity());
        if(invProduct.getPoproduct() != null) {
            dto.setPopid(invProduct.getPoproduct().getId());
            dto.setProdid(invProduct.getPoproduct().getProduct().getId());
        }

        return dto;

    };

    public static final Function<InvProduct, InvProductDto> mapperEntityToDtoWithPOP = (invProduct) -> {

        InvProductDto dto = mapperEntityToDto.apply(invProduct);
        if(invProduct.getPoproduct() != null) {
            dto.setPoproduct(POProductMapper.mapperEntityToDto.apply(invProduct.getPoproduct()));
        }

        return dto;

    };




}
