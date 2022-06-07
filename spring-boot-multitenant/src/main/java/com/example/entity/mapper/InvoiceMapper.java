package com.example.entity.mapper;

import com.example.common.util.DateUtil;
import com.example.constant.InvoiceStatus;
import com.example.constant.OrderType;
import com.example.entity.dto.order.*;
import com.example.entity.order.*;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class InvoiceMapper {


    public static final Function<Invoice, InvoiceDto> mapperEntityToDto = (invoice) -> {
        InvoiceDto dto = new InvoiceDto();
        if (invoice == null) {
            return null;
        }

        dto.setId(invoice.getId());
        dto.setTenantid(invoice.getTenantid());
        dto.setOid(invoice.getOid());
        dto.setType(OrderType.getTypeString(invoice.getType()));
        dto.setReferenceno(invoice.getReferenceno());
        if (invoice.getProductlist() != null) {
            dto.setProductlist(invoice.getProductlist().stream().map(InvProductMapper.mapperEntityToDto).collect(Collectors.toList()));
            dto.getProductlist().forEach(invProductDto -> {
                invProductDto.setCompletedate(DateUtil.getEpochTime(invoice.getCompletedate()));
                invProductDto.setInvref(invoice.getReferenceno());
            });
        }
        dto.setTaxAmount(invoice.getTaxAmount());
        dto.setTotalAmount(invoice.getTotalamount());
        dto.setBalance(invoice.getBalance());
        dto.setInvdate(DateUtil.getEpochTime(invoice.getInvdate()));
        dto.setCompletedate(DateUtil.getEpochTime(invoice.getCompletedate()));
        dto.setTransportername(invoice.getTransportername());
        dto.setTrackingno(invoice.getTrackingno());
        dto.setTruckno(invoice.getTruckno());
        dto.setContainerno(invoice.getContainerno());
        dto.setStatus(InvoiceStatus.getTypeString(invoice.getStatus()));
        dto.setTaxes(invoice.getTaxes());

        return dto;
    };

    public static final Function<Invoice, InvoiceDto> mapperEntityToDtoWithPOP = (invoice) -> {

        InvoiceDto dto = mapperEntityToDto.apply(invoice);
        if (invoice.getProductlist() != null) {
            dto.setProductlist(invoice.getProductlist().stream().map(InvProductMapper.mapperEntityToDtoWithPOP).collect(Collectors.toList()));
        }
        return dto;
    };


    public static final BiFunction<InvoiceDto, Invoice, Invoice> mapDtoToEntity = (invoiceDto, entity) -> {
        if (invoiceDto == null) {
            return null;
        }
        entity.setOid(invoiceDto.getOid());
        entity.setType(OrderType.getTypeCode(invoiceDto.getType()));

        entity.setInvdate(DateUtil.toLocalDateTime(invoiceDto.getInvdate()));
        entity.setCompletedate(DateUtil.toLocalDateTime(invoiceDto.getCompletedate()));
        entity.setTransportername(invoiceDto.getTransportername());
        entity.setTrackingno(invoiceDto.getTrackingno());
        entity.setTruckno(invoiceDto.getTruckno());
        entity.setContainerno(invoiceDto.getContainerno());
        entity.setTaxes(invoiceDto.getTaxes());

        return entity;
    };


}
