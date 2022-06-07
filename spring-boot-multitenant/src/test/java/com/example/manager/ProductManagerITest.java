package com.example.manager;
import com.example.TradeAbstractTest;
import com.example.constant.OrderStatus;
import com.example.constant.OrderType;
import com.example.controller.manager.ProductManager;
import com.example.controller.manager.UserManager;
import com.example.controller.service.ProductService;
import com.example.entity.dto.order.ProductDto;
import com.example.entity.dto.order.PurchaseOrderDto;
import com.example.entity.order.Product;
import com.example.mock.dto.ProductDtoMock;
import com.example.util.OrderTestUtil;
import com.example.util.ProductTestUtil;
import com.example.util.TestDataUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class ProductManagerITest extends TradeAbstractTest {

    @Autowired
    TestDataUtil testDataUtil;

    @Autowired
    ProductManager productManager;

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
            Assert.fail(e.getMessage());
        }


    }

    @Test
    public void testSave(){
        ProductDto productDto = new ProductDto();
        try {
            productDto = ProductDtoMock.mock.get();
            productDto.setName("Product1 " + LocalDateTime.now());
            productDto = productManager.save(productDto);

            Assert.assertNotNull(productDto.getId());

        // todo test
           // Assert.assertEquals();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @Commit
    public void testAvaliableStock(){

        try {

            List<ProductDto> productDtoList = productManager.getAllProducts();
            testDataUtil.createOrdersAndTransactions(LocalDateTime.now(), 2);
            PurchaseOrderDto po1 = orderTestUtil.createOrderByType(OrderType.PURCHASE, LocalDateTime.now(), BigDecimal.ONE, OrderStatus.CONFIRM, 1);
            productManager.getAllProducts();


            // todo test
            // Assert.assertEquals();
        }catch (Exception e) {
            Assert.fail(e.getMessage());
            e.printStackTrace();
        }
    }
}
