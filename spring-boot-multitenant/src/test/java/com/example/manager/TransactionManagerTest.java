package com.example.manager;

import com.example.TradeAbstractTest;
import com.example.common.util.DateUtil;
import com.example.constant.CompanyType;
import com.example.constant.DatePreset;
import com.example.controller.manager.InvTransactionManager;
import com.example.entity.dto.order.CompanyDto;
import com.example.entity.dto.order.InvTransactionDto;
import com.example.entity.order.InvTransaction;
import com.example.entity.rs.order.TransactionSearchRequest;
import com.example.util.AccountTestUtil;
import com.example.util.CompanyTestUtil;
import com.example.util.TestDataUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class TransactionManagerTest extends TradeAbstractTest {

    @Autowired
    AccountTestUtil accountTestUtil;

    @Autowired
    CompanyTestUtil companyTestUtil;

    @Autowired
    TestDataUtil testDataUtil;

    @Autowired
    InvTransactionManager transactionManager;

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }



    //---------------------------------------------------------- Save --------------------------------------------------------------
    @Test
    public void testSaveJournal(){
        InvTransactionDto transactionDto = new InvTransactionDto();
        try {
            transactionDto = accountTestUtil.getLegalExpJournal();
            InvTransactionDto resultTranDto = transactionManager.saveTransaction(transactionDto);
            Assert.assertNotNull(resultTranDto.getId());
            Assert.assertEquals(resultTranDto.getJournals().size(), 2);
        }catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }

    }

    @Test
    public void testEditJournal(){
        InvTransactionDto transactionDto = new InvTransactionDto();
        try {
//            transactionDto = accountTestUtil.getLegalExpJournal();
//            InvTransactionDto resultTranDto = transactionManager.saveTransaction(transactionDto);
//            Assert.assertNotNull(resultTranDto.getId());
//            Assert.assertEquals(resultTranDto.getJournals().size(), 2);
        }catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }

    }

    @Test
    public void testSavePayment(){
        InvTransactionDto transactionDto = new InvTransactionDto();
        try {
//            transactionDto = accountTestUtil.getLegalExpJournal();
//            InvTransactionDto resultTranDto = transactionManager.saveTransaction(transactionDto);
//            Assert.assertNotNull(resultTranDto.getId());
//            Assert.assertEquals(resultTranDto.getJournals().size(), 2);
        }catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }

    }

    @Test
    public void testEditPayment(){
        InvTransactionDto transactionDto = new InvTransactionDto();
        try {
//            transactionDto = accountTestUtil.getLegalExpJournal();
//            InvTransactionDto resultTranDto = transactionManager.saveTransaction(transactionDto);
//            Assert.assertNotNull(resultTranDto.getId());
//            Assert.assertEquals(resultTranDto.getJournals().size(), 2);
        }catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }

    }

    @Test
    public void testSaveReceipt(){
        InvTransactionDto transactionDto = new InvTransactionDto();
        try {
//            transactionDto = accountTestUtil.getLegalExpJournal();
//            InvTransactionDto resultTranDto = transactionManager.saveTransaction(transactionDto);
//            Assert.assertNotNull(resultTranDto.getId());
//            Assert.assertEquals(resultTranDto.getJournals().size(), 2);
        }catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }

    }
    @Test // todo
    public void testEditReceipt(){
        InvTransactionDto transactionDto = new InvTransactionDto();
        try {
//            transactionDto = accountTestUtil.getLegalExpJournal();
//            InvTransactionDto resultTranDto = transactionManager.saveTransaction(transactionDto);
//            Assert.assertNotNull(resultTranDto.getId());
//            Assert.assertEquals(resultTranDto.getJournals().size(), 2);
        }catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }

    }

    //---------------------------------------------------------- Search --------------------------------------------------------------

    //@Test todo fix the test
     @Commit
    public void testSearch(){
        List<InvTransactionDto> searchResult = new ArrayList<>();
        try {

            CompanyDto selfCompany = companyTestUtil.getCompanyByType(CompanyType.SELF);
            CompanyDto supplierCompany = companyTestUtil.getCompanyByType(CompanyType.SUPPLIER);

            //Set Up
            testDataUtil.deleteAllData();
            testDataUtil.createTestDataForDateRange(LocalDateTime.now().minusMonths(4), LocalDateTime.now());

            // Test1
            TransactionSearchRequest request1 = new TransactionSearchRequest(
                    selfCompany.getId(), DatePreset.LAST_1_MONTH, CompanyType.SUPPLIER, supplierCompany.getId(), null, null);
            searchResult =  transactionManager.search(request1);
            // find min date
            Long minDate = searchResult.stream().map(InvTransactionDto::getDate).min(Long::compareTo).get();
            Long date32DaysEarlier = DateUtil.getEpochTime( LocalDateTime.now().minusDays(32));
            // Assert one month old
            Assert.assertTrue(minDate.compareTo(date32DaysEarlier) > 0);



            // Test2
//            TransactionSearchRequest request2 = new TransactionSearchRequest();
//            request2.setOrderno(3678l);
//            searchResult =  transactionManager.search(request2);
//            Assert.assertTrue(searchResult.size() > 0);





        }catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }

    }

}
