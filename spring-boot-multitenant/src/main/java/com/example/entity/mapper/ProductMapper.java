package com.example.entity.mapper;

import com.example.entity.order.Product;
import com.example.entity.dto.order.ProductDto;

import java.util.function.Function;

public class ProductMapper {


    public static final Function<Product, ProductDto> mapperEntityToDto = (product) -> {
        ProductDto dto = new ProductDto();
        if (product == null) {
            return null;
        }

        dto.setCostPrice(product.getCostprice());
        dto.setId(product.getId());
        dto.setTenantid(product.getTenantid());
        dto.setName(product.getName());
        dto.setSalesPrice(product.getSalesprice());
        dto.setBarcode(product.getBarcode());
        dto.setService(product.isIsservice());
        dto.setInternalReference(product.getInternalreference());

        dto.setGroup(product.getPgroup());
        dto.setCategory(product.getCategory());
        dto.setUom(product.getUom());
        dto.setHsnCode(product.getHsncode());
        dto.setUnit(product.getUnit());

        dto.setGstApplicability(product.getGstapplicability());
        dto.setCgstRate(product.getCgstrate());
        dto.setSgstRate(product.getSgstrate());
        dto.setIgstRate(product.getIgstrate());
        dto.setDescription(product.getDescription());
        dto.setQuantity(product.getQuantity());

        dto.setPendingarrival(product.getPendingarrival());
        dto.setPendingdispatch(product.getPendingdispatch());

        return dto;

    };

    public static final Function<ProductDto, Product> mapperDtoToEntity = (product) -> {
        Product entity = new Product();
        if (product == null) {
            return null;
        }

        entity.setCostprice(product.getCostPrice());
        entity.setId(product.getId());
        entity.setName(product.getName());
        entity.setSalesprice(product.getSalesPrice());
        entity.setBarcode(product.getBarcode());
        entity.setIsservice(product.isService());
        entity.setInternalreference(product.getInternalReference());
        entity.setPgroup(product.getGroup());
        entity.setCategory(product.getCategory());
        entity.setUom(product.getUom());
        entity.setHsncode(product.getHsnCode());
        entity.setUnit(product.getUnit());
        entity.setGstapplicability(product.getGstApplicability());
        entity.setCgstrate(product.getCgstRate());
        entity.setSgstrate(product.getSgstRate());
        entity.setIgstrate(product.getIgstRate());
        entity.setDescription(product.getDescription());
        entity.setQuantity(product.getQuantity());
        return entity;

    };


}
