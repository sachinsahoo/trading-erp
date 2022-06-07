package com.example.controller;

import com.example.common.exception.InputValidationException;
import com.example.common.util.DateUtil;
import com.example.constant.InvoiceStatus;
import com.example.constant.RsStatusType;
import com.example.controller.manager.PurchaseOrderManager;
import com.example.controller.service.InvoiceService;
import com.example.controller.manager.InvoiceManager;
import com.example.controller.manager.ProductManager;
import com.example.entity.dto.order.InvoiceDto;
import com.example.entity.dto.order.PurchaseOrderDto;
import com.example.entity.rs.EntityRequest;
import com.example.entity.rs.order.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestController
@RequestMapping("api/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private PurchaseOrderManager purchaseManager;

    @Autowired
    private InvoiceManager invoiceManager;

    @Autowired
    ProductManager productManager;

    @RequestMapping(value = "/save.ws", method = RequestMethod.POST)
    public ResponseEntity<?> save(@RequestBody InvoiceRequest invocieRequest, HttpServletRequest request) {

        InvoiceResponse response = new InvoiceResponse();
        response.setStatus(RsStatusType.SUCCESS);
        PurchaseOrderDto purchaseOrderDto;
        InvoiceDto invoiceDto;


        try {
            invocieRequest.validate();
            invoiceDto = invoiceManager.saveInvoice(invocieRequest.getInvoice());
            purchaseOrderDto = purchaseManager.getOrderById(invoiceDto.getOid());

            response.setInvoice(invoiceDto);
            response.setPurchaseOrder(purchaseOrderDto);

        } catch (InputValidationException e) {
            response.setErrorMessage(e.getError());
            response.setStatus(RsStatusType.FAILURE);

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(RsStatusType.FAILURE);
            response.setErrorMessage(e.getMessage());

        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/updatestatus.ws", method = RequestMethod.POST)
    public ResponseEntity<?> updateStatus(@RequestBody InvoiceStatusRequest invStatusRequest, HttpServletRequest request) {

        InvoiceResponse response = new InvoiceResponse();
        response.setStatus(RsStatusType.SUCCESS);
        InvoiceDto invoiceDto = null;


        try {
            invStatusRequest.validate();
            InvoiceStatus status = InvoiceStatus.getType(invStatusRequest.getStatus());
            LocalDateTime dateTime = DateUtil.toLocalDateTime(invStatusRequest.getDate());
            invoiceDto = invoiceService.updateStatus(invStatusRequest.getInvid(), status  , dateTime);
            response.setInvoice(invoiceDto);
            response.setPurchaseOrder(purchaseManager.getOrderById(invoiceDto.getOid()));

        } catch (InputValidationException e) {
            response.setErrorMessage(e.getError());
            response.setStatus(RsStatusType.FAILURE);

        } catch (Exception e) {
            response.setStatus(RsStatusType.FAILURE);
            response.setErrorMessage(e.getMessage());

        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/get.ws", method = RequestMethod.POST)
    public ResponseEntity<?> get(@RequestBody EntityRequest entityRequest, HttpServletRequest request) {

        InvoiceResponse response = new InvoiceResponse();
        response.setStatus(RsStatusType.SUCCESS);
        PurchaseOrderDto purchaseOrderDto = null;
        InvoiceDto invoiceDto = null;
        try {
            invoiceDto = invoiceManager.getInvoice(entityRequest.getId());
            purchaseOrderDto= purchaseManager.getOrderById(invoiceDto.getOid());
            response.setInvoice(invoiceDto);
            response.setPurchaseOrder(purchaseOrderDto);
        } catch (InputValidationException e) {
            e.printStackTrace();
            response.setErrorMessage(e.getError());
            response.setStatus(RsStatusType.FAILURE);

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(RsStatusType.FAILURE);
            response.setErrorMessage(e.getMessage());

        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



}
