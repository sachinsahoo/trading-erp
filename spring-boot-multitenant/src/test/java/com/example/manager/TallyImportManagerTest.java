package com.example.manager;
import com.example.TradeAbstractTest;
import com.example.controller.excel.TallyImportManager;
import com.example.controller.excel.dto.TallyAccount;
import com.example.controller.manager.AccountManager;
import com.example.entity.dto.acct.AccountGroupDto;
import com.example.entity.dto.acct.BalanceSheetDto;
import com.example.util.AccountTestUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TallyImportManagerTest extends TradeAbstractTest {


    @Autowired
    AccountTestUtil accountTestUtil;

    @Autowired
    TallyImportManager tallyImportManager;



    @Override
    @Before
    public void setUp() {
        try {
            super.setUp();
            accountTestUtil.createInternalAccounts();
        }catch (Exception ex) {
            Assert.fail(ex.getMessage());
        }
    }



    @Test
    public void test_importExcel(){
        List<TallyAccount> tallyAccountList = null;
        try {
            tallyAccountList = tallyImportManager.importExcel(tallyImportManager.getTenantId());
        }catch (Exception e) {
            e.printStackTrace();
        }

    }




}
