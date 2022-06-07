package com.example.controller;

import com.example.common.exception.InputValidationException;
import com.example.common.util.DateUtil;
import com.example.constant.OrderStatus;
import com.example.constant.RsStatusType;
import com.example.controller.manager.PurchaseOrderManager;
import com.example.controller.util.PDF7Util;
import com.example.controller.manager.ProductManager;
import com.example.entity.dto.order.PurchaseOrderDto;
import com.example.entity.rs.EntityRequest;
import com.example.entity.rs.order.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;

@RestController
@RequestMapping("api/purchase")
public class PurchaseOrderController {


    @Autowired
    private PurchaseOrderManager purchaseManager;

    @Autowired
    ProductManager productManager;

    @RequestMapping(value = "/save.ws", method = RequestMethod.POST)
    public ResponseEntity<?> save(@RequestBody PurchaseOrderRequest purchaseRequest, HttpServletRequest request) {

        PurchaseOrderResponse response = new PurchaseOrderResponse();
        response.setStatus(RsStatusType.SUCCESS);


        try {
            purchaseRequest.validate();

            PurchaseOrderDto purchaseDto = purchaseManager.savePurchaseOrder(purchaseRequest.getPurchaseOrder());

            response.setPurchaseOrder(purchaseDto);

        } catch (InputValidationException e) {
            response.setErrorMessage(e.getError());
            response.setStatus(RsStatusType.FAILURE);

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(RsStatusType.FAILURE);
            response.setErrorMessage(e.getMessage());

        }
        return new ResponseEntity<PurchaseOrderResponse>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/updatestatus.ws", method = RequestMethod.POST)
    public ResponseEntity<?> updateStatus(@RequestBody PurchaseOrderStatusRequest poStatusRequest, HttpServletRequest request) {

        PurchaseOrderResponse response = new PurchaseOrderResponse();
        response.setStatus(RsStatusType.SUCCESS);
        PurchaseOrderDto purchaseOrderDto = null;
        try {
            poStatusRequest.validate();
            OrderStatus status = OrderStatus.getType(poStatusRequest.getStatus());
            LocalDateTime dateTime = DateUtil.toLocalDateTime(poStatusRequest.getDate());
            purchaseOrderDto = purchaseManager.updateStatus(poStatusRequest.getOid(), status  , dateTime);
            response.setPurchaseOrder(purchaseOrderDto);

        } catch (InputValidationException e) {
            response.setErrorMessage(e.getError());
            response.setStatus(RsStatusType.FAILURE);

        } catch (Exception e) {
            response.setStatus(RsStatusType.FAILURE);
            response.setErrorMessage(e.getMessage());

        }
        return new ResponseEntity<PurchaseOrderResponse>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/list.ws", method = RequestMethod.POST)
    public ResponseEntity<?> list(HttpServletRequest request) {

        PurchaseOrderListResponse response = new PurchaseOrderListResponse();
        response.setStatus(RsStatusType.SUCCESS);

        try {
            response.setOrders(purchaseManager.getAllPurchases());
        } catch (Exception e) {
            response.setStatus(RsStatusType.FAILURE);
            response.setErrorMessage(e.getMessage());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/search.ws", method = RequestMethod.POST)
    public ResponseEntity<?> search(@RequestBody OrderSearchRequest searchRequest, HttpServletRequest request) {

        PurchaseOrderListResponse response = new PurchaseOrderListResponse();
        response.setStatus(RsStatusType.SUCCESS);

        try {
            searchRequest.validate();
            response.setOrders(purchaseManager.searchOrder(searchRequest));
            searchRequest.setCount(response.getOrders().size());
            response.setSearchRequest(searchRequest);
        } catch (Exception e) {
            response.setStatus(RsStatusType.FAILURE);
            response.setErrorMessage(e.getMessage());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/get.ws", method = RequestMethod.POST)
    public ResponseEntity<?> get(@RequestBody EntityRequest entityRequest, HttpServletRequest request) {

        PurchaseOrderResponse response = new PurchaseOrderResponse();
        response.setStatus(RsStatusType.SUCCESS);
        PurchaseOrderDto purchaseOrderDto = null;
        try {
            PurchaseOrderDto po = purchaseManager.getOrderById(entityRequest.getId());
        } catch (InputValidationException e) {
            e.printStackTrace();
            response.setErrorMessage(e.getError());
            response.setStatus(RsStatusType.FAILURE);

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(RsStatusType.FAILURE);
            response.setErrorMessage(e.getMessage());

        }
        return new ResponseEntity<PurchaseOrderResponse>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/getpdf.ws", method = RequestMethod.POST)
    public ResponseEntity<Resource> getPdf(@RequestBody EntityRequest pdfRequest, HttpServletRequest request) {


        ByteArrayResource resource = null;
        ByteArrayOutputStream baos = null;
        HttpHeaders header = new HttpHeaders();
        PurchaseOrderDto purchaseOrderDto = null;
        try {
            PurchaseOrderDto po = purchaseManager.getOrderById(pdfRequest.getId());
            pdfRequest.validate();
            PDF7Util pdfUtil = new PDF7Util(po);
            baos = pdfUtil.getPOPDF2();
            resource = new ByteArrayResource(baos.toByteArray());



            header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=purchase_order.jpg");
            header.add("Cache-Control", "no-cache, no-store, must-revalidate");
            header.add("Pragma", "no-cache");
            header.add("Expires", "0");
        } catch (InputValidationException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  ResponseEntity.ok()
                .headers(header)
                .contentLength(baos.size())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }


    // update status for poid

}
