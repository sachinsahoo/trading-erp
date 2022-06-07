package com.example.controller.manager;


import com.example.constant.OrderStatus;
import com.example.controller.service.InvTransactionService;
import com.example.controller.service.PurchaseOrderService;
import com.example.entity.dto.order.PurchaseOrderDto;
import com.example.entity.mapper.PurchaseOrderMapper;
import com.example.entity.order.PurchaseOrder;
import com.example.entity.rs.order.OrderSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class PurchaseOrderManager extends BaseManager {

    @Autowired
    HttpSession session;

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Autowired
    private InvTransactionService transactionService;


    //------------------------
    // Save or Update Order
    //------------------------
    public PurchaseOrderDto savePurchaseOrder(PurchaseOrderDto purchaseOrderDto) throws Exception {


        try {
        // Remove deleted POProduct and taxes (for edit)
        purchaseOrderService.removeDeletedEntities(purchaseOrderDto);

        // save and calculate
            purchaseOrderDto =  purchaseOrderService.savePurchaseOrder(purchaseOrderDto);
            purchaseOrderDto = purchaseOrderService.getOrderById(purchaseOrderDto.getId());


        } catch (Exception e){
            System.out.println("Order manage Exception 1" + e);
            throw e;
        }
        return purchaseOrderDto;
    }


    //------------------------
    // Update Order Status
    //------------------------
    @Transactional
    public PurchaseOrderDto updateStatus(Long poid, OrderStatus status, LocalDateTime dateTime) throws Exception {
        try {
            return purchaseOrderService.updateStatus(poid, status, dateTime);
        } catch (Exception e){
            System.out.println("Order manage Exception 2" + e);
            throw e;
        }
    }

    //------------------------
    // Get Order By Id
    //------------------------
    public PurchaseOrderDto getOrderById(Long id) throws Exception {
        return purchaseOrderService.getOrderById(id);
    }

    //------------------------
    // Get all Orders
    //------------------------
    public List<PurchaseOrderDto> getAllPurchases() throws Exception {
        return purchaseOrderService.getAllPurchases();
    }


    //------------------------
    // Search Orders
    //------------------------
    public List<PurchaseOrderDto> searchOrder(OrderSearchRequest searchRequest) throws Exception {
        return purchaseOrderService.searchOrder(searchRequest);
    }



}
