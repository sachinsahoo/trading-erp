package com.example.manager;

import com.example.TradeAbstractTest;
import com.example.util.TestDataUtil;
import com.ibm.icu.impl.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;

import java.time.LocalDateTime;


public class TestController extends TradeAbstractTest {

    @Autowired
    TestDataUtil testDataUtil;

    @Override
    @Before
    public void setUp() {
        super.setUp();
        super.login();

    }

    @Test
    @Commit
    public void CreateData() throws Exception  {
        try {
            testDataUtil.deleteAllData();
            testDataUtil.createTestDataForDateRange(LocalDateTime.now().minusDays(35), LocalDateTime.now());
            System.out.println("Successfull");
            Assert.assrt("Passed", true);
        } catch(Exception e) {
            e.printStackTrace();
            Assert.fail(e);
            System.out.println("Exception------------------------" + e.getMessage());
        }
    }

    @Test
    public void deleteAllData() throws Exception  {
        testDataUtil.deleteAllData();
    }





}
