package com.example.util;

import com.example.common.util.DateUtil;
import com.example.constant.*;
import com.example.controller.manager.AccountManager;
import com.example.controller.service.AccountGroupService;
import com.example.controller.service.AccountService;
import com.example.entity.acct.Account;
import com.example.entity.dto.acct.AccountDto;
import com.example.entity.dto.acct.AccountGroupDto;
import com.example.entity.dto.acct.JournalDto;
import com.example.entity.dto.order.InvTransactionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountTestUtil {


    @Autowired
    AccountManager accountManager;

    @Autowired
    AccountService accountService;

    @Autowired
    CompanyTestUtil companyTestUtil;

    @Autowired
    AccountGroupService accountGroupService;


    public void createInternalAccounts() throws Exception {
        accountManager.createInternalAccountForTenant(accountService.getTenantId());
    }

    public void deleteAllAccounts() throws Exception  {
        List<Account> accountList =  accountService.getAllAccounts();
        for(Account account : accountList) {
            accountService.remove(account);
        }
    }

    public AccountDto getTestAccountByGroup(AcGroupNode group) throws Exception {
        List<AccountDto> accounts = accountManager.getAccountList();
        AccountDto acct = accounts.stream().filter(accountDto -> accountDto.getAcGroup().equals(group)).findFirst().get();
        return acct;
    }

    public InvTransactionDto getLegalExpJournal() throws Exception{
        JournalDto je1 = null, je2 = null;

        InvTransactionDto transaction = new InvTransactionDto(accountService.getTenantId(),
                DateUtil.getEpochTime(LocalDateTime.now()), InvTransactionType.JOURNAL.getType());
        transaction.setMyCompanyId(companyTestUtil.getCompanyByType(CompanyType.SELF).getId());
        transaction.setNotes("Legal Expense Test " + LocalDateTime.now().toString());

        je1 = new JournalDto(transaction.getNotes(), transaction, accountService.getAccount(InternalAccountType.LEGAL_PROFESSIONAL_EXPENSES).getId(), DrCrType.DEBIT, BigDecimal.TEN);
        je2 = new JournalDto(transaction.getNotes(), transaction, accountService.getAccount(InternalAccountType.BANK).getId(), DrCrType.CREDIT, BigDecimal.TEN);

        List<JournalDto> journalSet = new ArrayList<>();
        journalSet.add(je1);
        journalSet.add(je2);
        transaction.setJournals(journalSet);
        return transaction;
    }

    public AccountGroupDto getAccountGroup() throws Exception{

        AccountGroupDto group = new AccountGroupDto();
        //AccountCategory category = accountGroupService.getCategoryByCode(AcCategory.CURRENT_ASSETS.getCode());

        // TODO
        //group.setCatid(category.getId());
        group.setName("Test"+LocalDateTime.now().toString());

        return  group;
    }




}