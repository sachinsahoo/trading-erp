package com.example.entity.mapper;

import com.example.entity.order.POProduct;
import com.example.entity.order.Product;
import com.example.entity.dto.order.POProductDto;

import java.math.BigDecimal;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class POProductMapper {


    public static final Function<POProduct, POProductDto> mapperEntityToDto = (poProduct) -> {
        POProductDto dto = new POProductDto();
        if (poProduct == null) {
            return null;
        }

        dto.setId(poProduct.getId());
        dto.setTenantid(poProduct.getTenantid());
        dto.setOid(poProduct.getOid());
        dto.setLpoid(poProduct.getLpoid());
        dto.setPrice(poProduct.getPrice());
        dto.setCommpay(poProduct.getCommpay());
        dto.setCostPrice(poProduct.getCostprice());
        dto.setProfit(poProduct.getProfit());
        dto.setProductId(poProduct.getProduct() != null ? poProduct.getProduct().getId() : null);
        dto.setProductName(poProduct.getProduct() != null ? poProduct.getProduct().getName() : null);
        dto.setQuantity(poProduct.getQuantity());
        if (poProduct.getTaxes() != null) {
                dto.setTaxes(poProduct.getTaxes().stream().map(POPTaxMapper.mapperEntityToDto).collect(Collectors.toList()));
        }
        return dto;
    };




    public static final BiFunction<POProductDto, POProduct, POProduct> mapDtoToEntity = (poProductDto, entity) -> {

        if (poProductDto == null) {
            return null;
        }
        entity.setCommpay(poProductDto.getCommpay() != null ? poProductDto.getCommpay() :BigDecimal.ZERO);
        entity.setPrice(poProductDto.getPrice());
        Product product = new Product();
        product.setId(poProductDto.getProductId());
        entity.setProduct(product);
        entity.setQuantity(poProductDto.getQuantity());
        entity.setLpoid(poProductDto.getLpoid());

        return entity;

    };


}
