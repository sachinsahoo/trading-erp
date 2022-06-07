package com.example.util;

import com.example.common.util.DateUtil;
import com.example.constant.CompanyType;
import com.example.constant.DrCrType;
import com.example.constant.InternalAccountType;
import com.example.constant.InvTransactionType;
import com.example.controller.manager.AccountManager;
import com.example.controller.service.AccountGroupService;
import com.example.controller.service.AccountService;
import com.example.controller.service.InvTransactionService;
import com.example.entity.dto.acct.JournalDto;
import com.example.entity.dto.order.InvTransactionDto;
import com.example.entity.order.InvTransaction;
import com.example.manager.dto.TestJournalTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JournalTestDataUtil {

    @Autowired
    AccountTestUtil accountTestUtil;

    @Autowired
    AccountManager accountManager;

    @Autowired
    InvTransactionService transactionService;


    @Autowired
    AccountService accountService;

    @Autowired
    CompanyTestUtil companyTestUtil;

    @Autowired
    AccountGroupService accountGroupService;



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

    @Transactional
    public  InvTransaction createJournal(TestJournalTransaction journalTransaction) throws Exception {

        String notes = journalTransaction.getNotes();
        String amt = journalTransaction.getAmt();
        InternalAccountType creditAc = journalTransaction.getCreditAc();
        InternalAccountType debitAc = journalTransaction.getDebitAc();

        JournalDto je1 = null;
        JournalDto je2 = null;

        InvTransactionDto transaction = new InvTransactionDto(accountService.getTenantId(),
                DateUtil.getEpochTime(LocalDateTime.now()), InvTransactionType.JOURNAL.getType());
        transaction.setMyCompanyId(companyTestUtil.getCompanyByType(CompanyType.SELF).getId());
        transaction.setNotes(notes + LocalDateTime.now().toString());

        je1 = new JournalDto(transaction.getNotes(), transaction, accountService.getAccount(debitAc).
                getId(), DrCrType.DEBIT, new BigDecimal(amt) );

        je2 = new JournalDto(transaction.getNotes(), transaction, accountService.getAccount(creditAc).
                getId(), DrCrType.CREDIT, new BigDecimal(amt));

        List<JournalDto> journalSet = new ArrayList<>();
        journalSet.add(je1);
        journalSet.add(je2);
        transaction.setJournals(journalSet);

        InvTransaction transaction1 =  transactionService.saveJournal(transaction);

        return transaction1;
    }












}
