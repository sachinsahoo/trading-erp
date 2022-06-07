package com.example.manager;

import com.example.TradeAbstractTest;
import com.example.constant.AcGroupNode;
import com.example.constant.InternalAccountType;
import com.example.controller.manager.AccountManager;
import com.example.controller.manager.InvTransactionManager;
import com.example.entity.dto.acct.AccountDto;
import com.example.entity.dto.acct.AccountGroupDto;
import com.example.entity.dto.acct.BalanceSheetDto;
import com.example.entity.dto.order.InvTransactionDto;
import com.example.manager.dto.TestJournalTransaction;
import com.example.mock.dto.AllAccountJournalEntriesMock;
import com.example.util.AccountTestUtil;
import com.example.util.JournalTestDataUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@SpringBootTest
public class AccountManagerTest extends TradeAbstractTest {


    @Autowired
    AccountTestUtil accountTestUtil;

    @Autowired
    AccountManager accountManager;

    @Autowired
    InvTransactionManager transactionManager;

    @Autowired
    JournalTestDataUtil journalTestDataUtil;



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

    //-----------------------------------------
    //  Create Joural entries for all accounts
    //-----------------------------------------
    @Test
    public void testAllAccountJournalEntries() {
        try {
            List<TestJournalTransaction> allAccountJournals =  AllAccountJournalEntriesMock.mockAllAccount.get();
            for(TestJournalTransaction journalTransaction : allAccountJournals ) {
                journalTestDataUtil.createJournal(journalTransaction);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }


    @Test
    public void testGetBalancesheet(){
        try {
            // Todo
            // Delete all journals for tenant
            // Delete all transactions for tenant
            // create Journal entries for all account
            // create custom accounts and groups
            // Verify the account balance should match the journal entries
            // Verify the account is populated under right groups
            // verify custom account and groups are in right node in group tree
            BalanceSheetDto res = accountManager.getBalanceSheet(null);




            // Check sizes of root
            Assert.assertEquals(res.getAssets().size(), 4);
            Assert.assertEquals(res.getExpenses().size(),4);
            Assert.assertEquals(res.getIncomes().size(),3);
            Assert.assertEquals(res.getLiabilities().size(),3);

            // Create List of Account list for each level
            List<AccountDto> level1Account = new ArrayList<>();
            List<AccountDto> level2Account = new ArrayList<>();
            List<AccountDto> level3Account = new ArrayList<>();

            List<List<AccountDto>> allLevelAccounts = new ArrayList<>();

            // Add All account types into one list
            level1Account.addAll(res.getAssets());
            level1Account.addAll(res.getLiabilities());
            level1Account.addAll(res.getIncomes());
            level1Account.addAll(res.getExpenses());


            for(AccountDto accountDto : level1Account){
                level2Account.addAll(accountDto.getAcctList());
            }

            for(AccountDto accountDto : level2Account) {
                if(accountDto.getAcctList() != null) {
                    level3Account.addAll(accountDto.getAcctList());
                }
            }

            allLevelAccounts.add(level1Account);
            allLevelAccounts.add(level2Account);
            allLevelAccounts.add(level3Account);

            List<AcGroupNode> allGroupNodes =  Arrays.asList(AcGroupNode.values());


                for(AcGroupNode groupNode : allGroupNodes) {
                    if(groupNode.equals(AcGroupNode.ROOT)) {
                        continue;
                    }
                    AccountDto groupAccount =  allLevelAccounts.get(groupNode.getLevel() -1).stream()
                            .filter(accountDto -> accountDto.getName().equalsIgnoreCase(groupNode.getDesc()))
                            .findFirst().orElse(null);
                    System.out.println("\n " + (groupAccount != null ? groupAccount.getName() : " -- " )+ "  --node --" +groupNode.getDesc());
                   // Assert.assertNotNull(groupAccount);
                    //Assert.assertEquals(groupAccount.getCode(), groupNode.getCode());

                }

               List<InternalAccountType>  internalAccountTypes = Arrays.asList(InternalAccountType.values());
                List<AccountDto > allAccounts = new ArrayList<>();
                allAccounts.addAll(level1Account);
                allAccounts.addAll(level2Account);
                allAccounts.addAll(level3Account);


                    for(InternalAccountType internalAccountType : internalAccountTypes) {
                        if (internalAccountType.equals(InternalAccountType.UNKNOWN)) {
                            continue;
                        }
                        AccountDto internalAccount = allAccounts.stream().filter(accountDto -> accountDto.getName().
                                equalsIgnoreCase(internalAccountType.getDesc())).findFirst().orElse(null);
                        System.out.println("\n" + (internalAccount != null ? internalAccount.getName() : "--") + "--internalAC--" +internalAccountType.getDesc());
                        Assert.assertNotNull(internalAccount);
                        Assert.assertEquals(internalAccount.getCode(), internalAccountType.getCode());




                    }



        }catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }

    }




    // @Test TODO
    public void testSaveAccountGroup(){
        AccountGroupDto groupDto,result ;
        try{
            groupDto = accountTestUtil.getAccountGroup();
            result = accountManager.saveAccountGroup(groupDto, accountManager.getUserTenant());
            Assert.assertNotNull(result.getId());
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }




}
