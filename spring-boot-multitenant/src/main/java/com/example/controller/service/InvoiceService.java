package com.example.controller.service;

import com.example.common.exception.InputValidationException;
import com.example.common.util.InventoryUtil;
import com.example.constant.InvoiceStatus;
import com.example.constant.OrderType;
import com.example.controller.service.base.BaseDaoService;
import com.example.entity.dto.order.InvProductDto;
import com.example.entity.dto.order.InvoiceDto;
import com.example.entity.mapper.InvoiceMapper;
import com.example.entity.order.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class InvoiceService extends BaseDaoService {

    @Autowired
    HttpSession session;

    @Autowired
    InvTransactionService invTransactionService;

    @Autowired
    UserService userService;

    public InvoiceService() {
    }


    @Transactional
    public Invoice saveInvoice(InvoiceDto invoiceDto) throws Exception {

        // todo write validation Service
        if (invoiceDto.getOid() == null) {
            throw new InputValidationException("Purchase Order missing");
        }

        PurchaseOrder order = (PurchaseOrder) find(PurchaseOrder.class, invoiceDto.getOid());
        if (order == null) {
            throw new InputValidationException("Purchase Order not found ");
        }

        Invoice invoice = null;

        if (invoiceDto.getId() != null) {
            invoice = (Invoice) find(Invoice.class, invoiceDto.getId());
            // Todo if invoice is received or shipped cannot be deleted
        } else {
            invoice = new Invoice();
            Preference preference = userService.getPreferencePoNo();
            String refNo = OrderType.getInvPrefix(order.getType()) + "-" + preference.getPono(); //+ "/" + preference.getFinyear();
            invoice.setInvoiceno(preference.getPono());
            invoice.setReferenceno(refNo);

        }

        invoice = InvoiceMapper.mapDtoToEntity.apply(invoiceDto, invoice);

        invoice.setOid(order.getId());
        invoice.setTenantid(getTenantId());
        invoice.setType(order.getType());


        if (invoice.getId() == null) {
            persist(invoice);
        } else {
            merge(invoice);
        }

        Set<Long> invpids = new HashSet<>();
        Set<Long> invpDtoIds = new HashSet<>();


        // Inv Product
        if (invoice.getProductlist() != null) {
            invpids = invoice.getProductlist().stream().map(InvProduct::getId).collect(Collectors.toSet());
        }
        if (invoiceDto.getProductlist() != null) {
            invpDtoIds = invoiceDto.getProductlist().stream().map(InvProductDto::getId).collect(Collectors.toSet());
        }

        // Delete Inv products not in DTO
        for (Long id : invpids) {
            if (!invpDtoIds.contains(id)) {
                InvProduct invProduct = (InvProduct) find(InvProduct.class, id);
                invoice.getProductlist().remove(invProduct);
                remove(invProduct);
            }
        }

        BigDecimal totalInvoiceAmount = BigDecimal.ZERO;
        BigDecimal totalTaxAmount = BigDecimal.ZERO;
        if (invoiceDto.getProductlist() != null) {
            for (InvProductDto invProductDto : invoiceDto.getProductlist()) {
                ;
                POProduct poProduct = null;
                InvProduct invProduct = null;
                if (invProductDto.getId() != null) {
                    invProduct = (InvProduct) find(InvProduct.class, invProductDto.getId());
                } else {
                    invProduct = new InvProduct();

                }

                // Todo move Validation to Validation service
                if (invProductDto.getPopid() == null) {
                    throw new InputValidationException("Purchase order product missing");
                }
                poProduct = (POProduct) find(POProduct.class, invProductDto.getPopid());

                if (poProduct == null) throw new InputValidationException(" Purchase order product not found");

                invProduct.setQuantity(invProductDto.getQuantity());
                invProduct.setPoproduct(poProduct);
                invProduct.setTenantid(getTenantId());
                invProduct.setInvid(invoice.getId());


                if (invProduct.getId() != null) merge(invProduct); else persist(invProduct);

                BigDecimal taxAmt = BigDecimal.ZERO;
                List<POPTax> taxes = poProduct.getTaxes();
                for (POPTax tax : taxes) {
                    BigDecimal poTax = InventoryUtil.calcTaxOnOrder(poProduct.getPrice(), invProduct.getQuantity(), tax.getPercent()) ;
                    taxAmt = taxAmt.add(poTax);
                }
                totalInvoiceAmount = totalInvoiceAmount.add(poProduct.getPrice().multiply(invProduct.getQuantity())).add(taxAmt);
                totalTaxAmount = totalTaxAmount.add(taxAmt);
            }

            invoice.setTotalamount(totalInvoiceAmount);
            invoice.setTaxAmount(totalTaxAmount);
            merge(invoice);
            refresh(invoice);
            refresh(order);

        }
        return invoice;
    }



    @Transactional
    public InvoiceDto updateStatus(Long invid, InvoiceStatus status, LocalDateTime dateTime) throws Exception {

        Invoice invoice = (Invoice) find(Invoice.class, invid);

        //validate
        if(invoice == null ) throw new  InputValidationException("Invoice Id is missing ");
        if(invoice.getOid() == null ) throw new  InputValidationException("Invoice has no linked Order");

        PurchaseOrder purchaseOrder = (PurchaseOrder) find(PurchaseOrder.class, invoice.getOid());
        // todo order status should be confirm
        //
        switch (status) {


            case COMPLETE:
                invoice.setStatus(InvoiceStatus.COMPLETE.getCode());
                invoice.setCompletedate(dateTime);
                invTransactionService.enterInvoiceBalance(invoice);
                break;

        }
        merge(invoice);

        return InvoiceMapper.mapperEntityToDto.apply(invoice);
    }


    public Invoice getInvoice(Long id) {
        Invoice invoice = (Invoice) find(Invoice.class, id);
        return invoice;
    }




}
