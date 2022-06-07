package com.example.entity.mapper;

import com.example.common.util.DateUtil;
import com.example.constant.OrderStatus;
import com.example.constant.OrderType;
import com.example.entity.order.POProduct;
import com.example.entity.order.PurchaseOrder;
import com.example.entity.dto.order.POProductDto;
import com.example.entity.dto.order.PurchaseOrderDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PurchaseOrderMapper {


    public static final Function<PurchaseOrder, PurchaseOrderDto> mapperEntityToDto = (purchaseOrder) -> {
        PurchaseOrderDto dto = new PurchaseOrderDto();
        if (purchaseOrder == null) {
            return null;
        }
        dto.setId(purchaseOrder.getId());
        dto.setTenantid(purchaseOrder.getTenantid());
        dto.setMycompanyid(purchaseOrder.getMycompanyid());
        dto.setType(OrderType.getTypeString(purchaseOrder.getType()));
        dto.setReferenceno(purchaseOrder.getReferenceno());
        dto.setOrderno(purchaseOrder.getOrderno());

        dto.setConfirmdate(DateUtil.getEpochTime(purchaseOrder.getConfirmdate()));
        dto.setOrderdate(DateUtil.getEpochTime(purchaseOrder.getOrderdate()));

        dto.setTotalAmount(purchaseOrder.getTotalamount());
        dto.setAdvance(purchaseOrder.getAdvance());

        dto.setTotalcommpay(purchaseOrder.getTotalcommpay());
        dto.setTaxes(purchaseOrder.getTaxes());
        dto.setTaxAmount(purchaseOrder.getTaxAmount());
        dto.setProfit(purchaseOrder.getProfit());
        dto.setBalance(purchaseOrder.getBalance());
        List<POProductDto> poProductList = new ArrayList<>();
        if (purchaseOrder.getPoproductlist() != null) {
            for (POProduct poProduct : purchaseOrder.getPoproductlist()) {
                POProductDto poProductDto = POProductMapper.mapperEntityToDto.apply(poProduct);

                poProductDto.setPoref(purchaseOrder.getReferenceno());
                poProductDto.setOrderdate(DateUtil.getEpochTime(purchaseOrder.getOrderdate()));
                poProductDto.setConfirmdate(DateUtil.getEpochTime(purchaseOrder.getConfirmdate()));
                poProductDto.setStatus(OrderStatus.getTypeString(purchaseOrder.getStatus()));
                poProductDto.setClientId(purchaseOrder.getClient().getId());
                poProductDto.setClientName(purchaseOrder.getClient().getName());

                poProductList.add(poProductDto);

            }
        }
        dto.setPoproductlist(poProductList);

        // Set Client id and name in Invoice
        if (purchaseOrder.getInvoices() != null) {
            dto.setInvoices(purchaseOrder.getInvoices().stream().map(InvoiceMapper.mapperEntityToDto).collect(Collectors.toList()));
            dto.getInvoices().forEach(invoiceDto -> {
                invoiceDto.setClientId(purchaseOrder.getClient().getId());
                invoiceDto.setClientName(purchaseOrder.getClient().getName());

                invoiceDto.getProductlist().forEach(invProductDto -> {
                    invProductDto.setClientId(purchaseOrder.getClient().getId());
                    invProductDto.setClientName(purchaseOrder.getClient().getName());
                });


            });
        }


        if(dto.getInvoices() != null){
            dto.getInvoices().stream().forEach(invoiceDto -> {});

        }

        dto.setStatus(OrderStatus.getTypeString(purchaseOrder.getStatus()));
        if (purchaseOrder.getCustomer() != null) {
            dto.setCustomerid(purchaseOrder.getCustomer().getId());
            dto.setCustomerName(purchaseOrder.getCustomer().getName());
        }

        if (purchaseOrder.getSupplier() != null) {
            dto.setSupplierid(purchaseOrder.getSupplier().getId());
            dto.setSupplierName(purchaseOrder.getSupplier().getName());
        }

        if (purchaseOrder.getAgent() != null) {
            dto.setAgentid(purchaseOrder.getAgent().getId());
            dto.setAgentName(purchaseOrder.getAgent().getName());
        }

        return dto;
    };

    public static final Function<PurchaseOrder, PurchaseOrderDto> mapperEntityToDtoFULL = (purchaseOrder) -> {
        PurchaseOrderDto dto = new PurchaseOrderDto();
        if (purchaseOrder == null) {
            return null;
        }
        dto = mapperEntityToDto.apply(purchaseOrder);
        if(dto.getPoproductlist() != null){
            dto.getPoproductlist().forEach(poProductDto ->
                    poProductDto.setProduct(ProductMapper.mapperEntityToDto.apply(
                            purchaseOrder.getPoproductlist().stream().filter(poProduct ->
                                    poProduct.getId().equals(poProductDto.getId())).findFirst().get().getProduct())));
        }
        if (purchaseOrder.getCustomer() != null)
            dto.setCustomer(CompanyMapper.mapperEntityToDto.apply(purchaseOrder.getCustomer()));
        if (purchaseOrder.getSupplier() != null)
            dto.setSupplier(CompanyMapper.mapperEntityToDto.apply(purchaseOrder.getSupplier()));

        return dto;
    };


    public static final BiFunction<PurchaseOrderDto, PurchaseOrder, PurchaseOrder> mapDtoToEntity = (dto, entity) -> {
        if (dto == null) {
            return null;
        }
        entity.setType(OrderType.getTypeCode(dto.getType()));
        entity.setConfirmdate(DateUtil.toLocalDateTime(dto.getConfirmdate()));
        entity.setOrderdate(DateUtil.toLocalDateTime(dto.getOrderdate()));
        entity.setAdvance(dto.getAdvance());
        entity.setTaxes(dto.getTaxes());

        return entity;
    };


}
