package com.example.util;

import com.example.common.util.DateUtil;
import com.example.constant.CompanyType;
import com.example.constant.InvoiceStatus;
import com.example.constant.OrderStatus;
import com.example.constant.OrderType;
import com.example.controller.manager.ProductManager;
import com.example.controller.manager.PurchaseOrderManager;
import com.example.controller.service.InvoiceService;
import com.example.controller.service.ProductService;
import com.example.controller.service.PurchaseOrderService;
import com.example.entity.dto.order.*;
import com.example.entity.mapper.InvoiceMapper;
import com.example.entity.order.*;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class OrderTestUtil {

    @Autowired
    HttpSession session;

    @Autowired
    ProductTestUtil productTestUtil;

    @Autowired
    CompanyTestUtil companyTestUtil;

    @Autowired
    PurchaseOrderManager purchaseOrderManager;

    @Autowired
    PurchaseOrderService purchaseOrderService;

    @Autowired
    InvoiceService invoiceService;


    public PurchaseOrderDto createOrderByType(OrderType type, LocalDateTime date, BigDecimal priceChangePercent, OrderStatus status, int index) throws Exception {
        PurchaseOrderDto po = null;

        try {

            index = index % 3;
            int customerIndex = index % 5;
            int supplierIndex = index % 3;

            //  Products -----------
            List<ProductDto> products = productTestUtil.createProducts();
            List<CompanyDto> companies = companyTestUtil.createCompanies();

            CompanyDto self = companies.stream().filter(dto -> {
                return dto.getType() == CompanyType.SELF.getType();
            }).collect(Collectors.toList()).get(index);
            CompanyDto supplier = companies.stream().filter(dto -> {
                return dto.getType() == CompanyType.SUPPLIER.getType();
            }).collect(Collectors.toList()).get(supplierIndex);
            CompanyDto customer = companies.stream().filter(dto -> {
                return dto.getType() == CompanyType.CUSTOMER.getType();
            }).collect(Collectors.toList()).get(customerIndex);

            CompanyDto agent = companies.stream().filter(dto -> {
                return dto.getType() == CompanyType.AGENT.getType();
            }).collect(Collectors.toList()).get(0);

            List<POProductDto> popList = new ArrayList<>();

            for (ProductDto productDto : products) {
                popList.add(new POProductDto(productDto.getId(), productDto.getCostPrice().multiply(priceChangePercent), BigDecimal.TEN));
            }
            popList.forEach(pop -> {
                List<POPTaxDto> taxes = new ArrayList<>();
                taxes.add(new POPTaxDto("CGST", "5.0"));
                taxes.add(new POPTaxDto("SGST", "5.0"));
                pop.setTaxes(taxes);
                pop.setCommpay(new BigDecimal("100"));
            });

             po = new PurchaseOrderDto();


            switch (type) {
                case PURCHASE:
                    po = new PurchaseOrderDto(type.getType(), supplier.getId(), self.getId(),
                            "PO101", DateUtil.getEpochTime(date), popList);
                    po.setAgentid(agent.getId());
                    break;
                case SALE:
                    po = new PurchaseOrderDto(type.getType(), self.getId(), customer.getId(),
                            "PO101", DateUtil.getEpochTime(date), popList);
                    po.setAgentid(agent.getId());
                    break;
                case COMMISSION:
                    po = new PurchaseOrderDto(type.getType(), supplier.getId(), customer.getId(),
                            "PO101", DateUtil.getEpochTime(date), popList);
                    po.setAgent(self);
                    break;
            }

            po.setTaxes("CGST,SGST");
            PurchaseOrderDto poSaveResult = purchaseOrderManager.savePurchaseOrder(po);
            po = poSaveResult;

            if(OrderStatus.CONFIRM.equals(status)) {
                PurchaseOrderDto poConfirmResult  = purchaseOrderManager.updateStatus(poSaveResult.getId(), OrderStatus.CONFIRM, date.plusHours(1));
                po = poConfirmResult;
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return po;
    }





    public InvoiceDto createInvoice(Long oid) throws Exception{
        PurchaseOrder order = (PurchaseOrder) purchaseOrderService.find(PurchaseOrder.class, oid);
        InvoiceDto invoiceDto = new InvoiceDto();
        invoiceDto.setOid(oid);
        invoiceDto.setTenantid(order.getTenantid());
        invoiceDto.setContainerno("CONT039842KDJS");
        invoiceDto.setTruckno("TRK9384LKS");
        invoiceDto.setType(OrderType.getTypeString(order.getType()));
        invoiceDto.setTrackingno("IUEWKDSJLSDKDL88");
        invoiceDto.setInvdate(DateUtil.getEpochTime(order.getOrderdate().plusDays(2)));
        invoiceDto.setProductlist(order.getPoproductlist().stream().map( poProduct -> {
            InvProductDto invProd = new InvProductDto();
            invProd.setQuantity(poProduct.getQuantity());
            invProd.setPopid(poProduct.getId());
            return invProd;
        }).collect(Collectors.toList()));

        Invoice invoice  = invoiceService.saveInvoice(invoiceDto);
        invoiceDto = InvoiceMapper.mapperEntityToDto.apply(invoice);
        invoiceDto = invoiceService.updateStatus(invoiceDto.getId(), InvoiceStatus.COMPLETE,DateUtil.toLocalDateTime(invoiceDto.getInvdate()));


        return invoiceDto;
    }

    public void deleteAllOrders() throws Exception  {
        List<PurchaseOrderDto> orders =  purchaseOrderService.getAllPurchases();
        for(PurchaseOrderDto orderDto : orders) {
            deleteOrderById(orderDto.getId());
        }
    }

    @Transactional
    public void deleteOrderById(Long id) throws Exception {
        PurchaseOrder purchaseOrder = (PurchaseOrder) purchaseOrderService.find(PurchaseOrder.class, id);
        purchaseOrderService.remove(purchaseOrder);
    }

    //-----------------
    // Verify
    //-----------------

    public void verifySaveOrder(PurchaseOrderDto inputOrder, PurchaseOrderDto resultOrder) {
        Assert.assertNotNull(inputOrder.getId());
        Assert.assertEquals(inputOrder.getType() , resultOrder.getType());
        Assert.assertEquals(inputOrder.getAgentid() , resultOrder.getAgentid());
        Assert.assertEquals(inputOrder.getCustomerid() , resultOrder.getCustomerid());
        Assert.assertEquals(inputOrder.getSupplierid() , resultOrder.getSupplierid());
        Assert.assertEquals(inputOrder.getStatus() , OrderStatus.DRAFT.getType());

    }

    public void deleteProductById4(Long id) throws Exception  {}

    public void deleteProductById5(Long id) throws Exception  {}

    public void deleteProductById6(Long id) throws Exception  {}

}