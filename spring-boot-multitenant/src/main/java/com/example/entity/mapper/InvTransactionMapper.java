package com.example.entity.mapper;

import com.example.common.util.DateUtil;
import com.example.constant.InvTransactionType;
import com.example.entity.order.Company;
import com.example.entity.order.InvTransaction;
import com.example.entity.dto.order.InvTransactionDto;
import com.example.entity.order.Invoice;
import com.example.entity.order.PurchaseOrder;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class InvTransactionMapper {

    public static final Function<InvTransaction, InvTransactionDto> mapperEntityToDto = (transaction) -> {
        InvTransactionDto dto = new InvTransactionDto();
        if (transaction == null) {
            return null;
        }
        dto.setId(transaction.getId());
        dto.setTenantid(transaction.getTenantid());
        dto.setDate(DateUtil.getEpochTime(transaction.getTransdate()));
        dto.setAmount(transaction.getAmount());
        dto.setType(InvTransactionType.getTypeString(transaction.getType()));
        dto.setNotes(transaction.getNotes());
        if(transaction.getClient() != null) {
            dto.setCustCompanyId(transaction.getClient().getId());
            dto.setCustCompanyName(transaction.getClient().getName());
        }
        if(transaction.getMycompany() != null) {
            dto.setMyCompanyId(transaction.getMycompany().getId());
            dto.setMyCompanyName(transaction.getMycompany().getName());
        }
        dto.setInvoiceId(transaction.getInvoice() != null ? transaction.getInvoice().getId() : null );
        dto.setOrderId(transaction.getOrder() != null ? transaction.getOrder().getId(): null);
        dto.setInvRef(transaction.getInvoice() != null ? transaction.getInvoice().getReferenceno() : null );
        dto.setJournals(transaction.getJournals().stream().map(JournalMapper.mapperEntityToDto).collect(Collectors.toList()));
        return dto;
    };



    public static final BiFunction<InvTransactionDto, InvTransaction, InvTransaction> mapDtoToEntity = (dto, entity) -> {

        if (dto == null) {
            return null;
        }
        if(dto.getOrderId() != null) {
            entity.setOrder(new PurchaseOrder(dto.getOrderId()));
        }
        if(dto.getInvoiceId() != null) {
            entity.setInvoice(new Invoice(dto.getInvoiceId()));
        }
        if(dto.getMyCompanyId() != null) {
            entity.setMycompany(new Company(dto.getMyCompanyId()));
        }
        if(dto.getCustCompanyId() != null) {
            entity.setClient(new Company(dto.getCustCompanyId()));
        }
        entity.setAmount(dto.getAmount());
        entity.setTransdate(DateUtil.toLocalDateTime(dto.getDate()));
        entity.setType(InvTransactionType.getTypeCode(dto.getType()));
        entity.setNotes(dto.getNotes());

        return entity;
    };
}

