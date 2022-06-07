package com.example.manager;
import com.example.TradeAbstractTest;
import com.example.constant.OrderStatus;
import com.example.constant.OrderType;
import com.example.controller.manager.ProductManager;
import com.example.controller.manager.PurchaseOrderManager;
import com.example.entity.dto.order.ProductDto;
import com.example.entity.dto.order.PurchaseOrderDto;
import com.example.entity.order.PurchaseOrder;
import com.example.util.OrderTestUtil;
import com.example.util.ProductTestUtil;
import com.example.util.TestDataUtil;
import com.ibm.icu.impl.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class OrderManagerTest extends TradeAbstractTest {

    @Autowired
    TestDataUtil testDataUtil;

    @Autowired
    PurchaseOrderManager orderManager;

    @Autowired
    ProductTestUtil productTestUtil;

    @Autowired
    OrderTestUtil orderTestUtil;

    @Override
    @Before
    public void setUp(){
        try {
            super.setUp();
            testDataUtil.createBaseData();

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e);
        }


    }

    @Test
    public void testGetAllOrders(){
        ProductDto productDto = new ProductDto();
        try {
            List<PurchaseOrderDto> orders = orderManager.getAllPurchases();
            // todo test
           Assert.assrt("Orders Present" ,orders.size() > 0);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


}
