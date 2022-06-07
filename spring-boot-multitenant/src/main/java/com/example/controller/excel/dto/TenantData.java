package com.example.controller.excel.dto;

import com.example.entity.dto.TenantDto;
import com.example.entity.dto.order.GodownDto;
import com.example.entity.dto.order.ProductDto;
import com.example.entity.dto.order.StockDto;

import java.io.Serializable;
import java.util.List;

public class TenantData implements Serializable {

    private static final long serialVersionUID = 1L;

    private TenantDto tenantDto;
    private List<ProductDto> productDtoList;
    private List<StockDto> stockDtoList;
    private List<GodownDto> godownDtoList;

    public TenantDto getTenantDto() {
        return tenantDto;
    }

    public void setTenantDto(TenantDto tenantDto) {
        this.tenantDto = tenantDto;
    }

    public List<ProductDto> getProductDtoList() {
        return productDtoList;
    }

    public void setProductDtoList(List<ProductDto> productDtoList) {
        this.productDtoList = productDtoList;
    }

    public List<StockDto> getStockDtoList() {
        return stockDtoList;
    }

    public void setStockDtoList(List<StockDto> stockDtoList) {
        this.stockDtoList = stockDtoList;
    }

    public List<GodownDto> getGodownDtoList() {
        return godownDtoList;
    }

    public void setGodownDtoList(List<GodownDto> godownDtoList) {
        this.godownDtoList = godownDtoList;
    }
}
