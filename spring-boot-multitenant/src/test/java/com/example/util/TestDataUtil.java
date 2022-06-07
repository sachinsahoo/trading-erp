package com.example.util;


import com.example.common.util.DateUtil;
import com.example.constant.InvTransactionType;
import com.example.constant.OrderStatus;
import com.example.constant.OrderType;
import com.example.controller.manager.*;
import com.example.controller.service.*;
import com.example.entity.dto.order.InvoiceDto;
import com.example.entity.dto.order.PurchaseOrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TestDataUtil {

    @Autowired
    ProductTestUtil productTestUtil;

    @Autowired
    CompanyTestUtil companyTestUtil;

    @Autowired
    OrderTestUtil orderTestUtil;

    @Autowired
    AccountTestUtil accountTestUtil;

    @Autowired
    UserTestUtil userTestUtil;

    @Autowired
    TransactionTestUtil transactionTestUtil;

    @Autowired
    protected PurchaseOrderService purchaseService;

    @Autowired
    protected PurchaseOrderManager purchaseManager;

    @Autowired
    protected InvoiceService invoiceService;

    @Autowired
    protected ProductManager productManager;

    @Autowired
    protected InvTransactionService invTransactionService;

    @Autowired
    protected CompanyService companyService;

    @Autowired
    protected CompanyManager companyManager;

    @Autowired
    protected ContactService contactService;

    @Autowired
    protected InvTransactionManager invTransactionManager;

    @Autowired
    protected AccountManager accountManager;


    public void createBaseData() throws Exception {
        productTestUtil.createProducts();
        companyTestUtil.createCompanies();
        accountTestUtil.createInternalAccounts();
    }


    public void createTestDataForDateRange(LocalDateTime start, LocalDateTime end) throws Exception{
        createBaseData();
        List<LocalDateTime> dateList = DateUtil.getDays(start, end);

        int index = 0;
        for(LocalDateTime date : dateList){
            index ++;
            createOrdersAndTransactions(date, index);
        }
    }

    public void createOrdersAndTransactions(LocalDateTime date, int index) throws Exception  {
        try {
            PurchaseOrderDto po1 = orderTestUtil.createOrderByType(OrderType.PURCHASE, date, BigDecimal.ONE, OrderStatus.CONFIRM, index);
            po1 = purchaseManager.getOrderById(po1.getId());
            PurchaseOrderDto so1 = orderTestUtil.createOrderByType(OrderType.SALE, date, BigDecimal.ONE, OrderStatus.CONFIRM, index);
            so1 = purchaseManager.getOrderById(so1.getId());
            //PurchaseOrderDto co1 = orderTestUtil.createOrderByType("comm", date);

            InvoiceDto invPO1 = orderTestUtil.createInvoice(po1.getId());
            InvoiceDto invSO1 = orderTestUtil.createInvoice(so1.getId());
            transactionTestUtil.createPayment(InvTransactionType.PAYMENT, po1.getCustomer(), po1.getSupplier(), invPO1, invPO1.getBalance(), date, "");
            transactionTestUtil.createPayment(InvTransactionType.RECEIPT, so1.getSupplier(), so1.getCustomer(), invSO1, invSO1.getBalance(), date, "");
            System.out.println("Created records for " + date);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
        //transactionTestUtil.createPayment(InvTransactionType.RECEIPT, co1.getSupplier(), co1.getCustomer(), invCO1, invCO1.getBalance(), date, "");
    }

    public void deleteAllData() throws Exception  {
        transactionTestUtil.deleteAllTransactions();
        orderTestUtil.deleteAllOrders();
        companyTestUtil.deleteAllCompanies();
        productTestUtil.deleteAllProducts();
        accountTestUtil.deleteAllAccounts();

    }
    /*



delete from poptax;
delete from invproduct;
delete from poproduct;
delete from journal;
delete from invtransaction;
delete from invoice;
delete from purchaseOrder;
delete from product;
update company set dispcontactid = null;
update company set invcontactid = null;

delete from contact;
delete from company;
delete from account




 */

    public void deleteProductById6(Long id) throws Exception  {

    }








}
