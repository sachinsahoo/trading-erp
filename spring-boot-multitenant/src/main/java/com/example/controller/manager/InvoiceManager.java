package com.example.controller.manager;


import com.example.constant.InvoiceStatus;
import com.example.controller.service.InvoiceService;
import com.example.entity.dto.order.InvoiceDto;
import com.example.entity.mapper.InvoiceMapper;
import com.example.entity.order.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;


@Service
public class InvoiceManager extends BaseManager {

    @Autowired
    HttpSession session;

    @Autowired
    private InvoiceService invoiceService;

    //------------------
    // Save Invoice
    //------------------
    public InvoiceDto saveInvoice(InvoiceDto invoiceDto) throws Exception {

        InvoiceDto invoiceDto1 = InvoiceMapper.mapperEntityToDto.apply(invoiceService.saveInvoice(invoiceDto));

        return invoiceDto1;
    }

    //------------------
    // Update Invoice Status
    //------------------
    public InvoiceDto updateStatus(Long invid, InvoiceStatus status, LocalDateTime dateTime) throws Exception {

        InvoiceDto invoiceDto1 = invoiceService.updateStatus(invid, status, dateTime);
        // TODO : call update balance

        return invoiceDto1;
    }

    //------------------
    // get Invoice By Id
    //------------------
    public InvoiceDto getInvoice(Long invid) throws Exception {

        Invoice invoice = invoiceService.getInvoice(invid);
        if(invoice != null) {
            return InvoiceMapper.mapperEntityToDtoWithPOP.apply(invoice);
        } else {
            return null;
        }
    }




}
