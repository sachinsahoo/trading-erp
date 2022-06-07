package com.example.controller.util;

import com.example.constant.DrCrType;
import com.example.constant.InternalAccountType;
import com.example.controller.service.AccountService;
import com.example.entity.acct.Account;
import com.example.entity.acct.Journal;
import com.example.entity.order.Company;
import com.example.entity.order.InvTransaction;
import com.example.entity.order.Invoice;
import com.example.entity.order.PurchaseOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class JournalEntryRules {

    @Autowired
    private AccountService accountService;

    public  Set<Journal> openingBalance(InvTransaction transaction, Account account) throws Exception {

        Journal je1 = null;
        je1 = new Journal("Opening Balance", transaction, account, DrCrType.getType(account.getDrcr()), account.getBalance());
        Set<Journal> journalSet = new HashSet<>();
        journalSet.add(je1);
        return journalSet;

    }


    public  Set<Journal> advancePaidOnPurchase(InvTransaction transaction, PurchaseOrder order) throws Exception {

        Journal je1 = null, je2 = null;
        String advanceTo= "Advance to  " + transaction.getClient().getName();
        je1 = new Journal("Advance paid", transaction, getAccount(transaction.getClient()), DrCrType.DEBIT, order.getAdvance());
        je2 = new Journal( advanceTo, transaction, getAccount(InternalAccountType.BANK), DrCrType.CREDIT, order.getAdvance());

        Set<Journal> journalSet = new HashSet<>();
        journalSet.add(je1);
        journalSet.add(je2);
        return journalSet;

    }

    public Set<Journal> advanceReceivedOnSale(InvTransaction transaction, PurchaseOrder order) throws Exception {

        Journal je1 = null, je2 = null;
        String advanceFrom= "Advance from  " + transaction.getClient().getName();
        je1 = new Journal(advanceFrom, transaction, getAccount(InternalAccountType.BANK), DrCrType.DEBIT, order.getAdvance());
        je2 = new Journal("Advance Received ", transaction, getAccount(transaction.getClient()), DrCrType.CREDIT, order.getAdvance());

        Set<Journal> journalSet = new HashSet<>();
        journalSet.add(je1);
        journalSet.add(je2);
        return journalSet;

    }

    @Transactional
    public Set<Journal> purchaseLocal(InvTransaction transaction, Invoice invoice) throws Exception {

        Journal je1 = null, je2 = null;

        String purchaseFrom = "Purchase from " + transaction.getClient().getName();
        je1 = new Journal(purchaseFrom, transaction, getAccount(InternalAccountType.PURCHASE_LOCAL), DrCrType.DEBIT, invoice.getBalance());
        je2 = new Journal("Purchsed Goods", transaction, getAccount(transaction.getClient()), DrCrType.CREDIT, invoice.getBalance());

        Set<Journal> journalSet = new HashSet<>();
        journalSet.add(je1);
        journalSet.add(je2);
        return journalSet;

    }

    @Transactional
    public Set<Journal> purchaseOutside(InvTransaction transaction, Invoice invoice) throws Exception {
        Journal je1 = null, je2 = null;
        String purchaseFrom = "Purchase from " + transaction.getClient().getName();
        je1 = new Journal(purchaseFrom, transaction, getAccount(InternalAccountType.PURCHASE_OUTSIDE), DrCrType.DEBIT, invoice.getBalance());
        je2 = new Journal("Purchsed Goods", transaction, getAccount(transaction.getClient()), DrCrType.CREDIT, invoice.getBalance());

        Set<Journal> journalSet = new HashSet<>();
        journalSet.add(je1);
        journalSet.add(je2);
        return journalSet;
    }

    @Transactional
    public Set<Journal> saleLocal( InvTransaction transaction, Invoice invoice) throws Exception {
        Journal je1 = null, je2 = null;

        String saleTo = "Sold to " + transaction.getClient().getName();
        je1 = new Journal(saleTo, transaction, getAccount(InternalAccountType.SALE_LOCAL), DrCrType.CREDIT, invoice.getBalance());
        je2 = new Journal("Sold Goods", transaction, getAccount(transaction.getClient()), DrCrType.DEBIT, invoice.getBalance());

        Set<Journal> journalSet = new HashSet<>();
        journalSet.add(je1);
        journalSet.add(je2);
        return journalSet;
    }

    @Transactional
    public Set<Journal> saleOutside(InvTransaction transaction, Invoice invoice) throws Exception {
        Journal je1 = null, je2 = null;
        String saleTo = "Sold to " + transaction.getClient().getName();
        je1 = new Journal(saleTo, transaction, getAccount(InternalAccountType.SALE_OUTSIDE), DrCrType.DEBIT, invoice.getBalance());
        je2 = new Journal("Sold Goods", transaction, getAccount(transaction.getClient()), DrCrType.CREDIT, invoice.getBalance());

        Set<Journal> journalSet = new HashSet<>();
        journalSet.add(je1);
        journalSet.add(je2);
        return journalSet;
    }

    @Transactional
    public Set<Journal> saleCommissionLocal(InvTransaction transaction, Invoice invoice) throws Exception {

        return null;
    }

    @Transactional
    public Set<Journal> saleCommissionOutside(InvTransaction transaction, Invoice invoice) throws Exception {
        return null;
    }

    @Transactional
    public Set<Journal> CommissionExpense(InvTransaction transaction, Invoice invoice) throws Exception {
        return null;
    }

    @Transactional
    public Set<Journal> transportingExpense(InvTransaction transaction) throws Exception {
        return null;
    }

    @Transactional
    public Set<Journal> cashDeposit(InvTransaction transaction) throws Exception {
        return null;
    }

    @Transactional
    public Set<Journal> cashWithdrawl(InvTransaction transaction) throws Exception {
        return null;
    }

    @Transactional
    public Set<Journal> paymentToParty(InvTransaction transaction) throws Exception {
        Journal je1 = null, je2 = null;
        String paymentTo = "Payment to " + transaction.getClient().getName();
        je1 = new Journal("Payment", transaction, getAccount(transaction.getClient()), DrCrType.DEBIT, transaction.getAmount());
        je2 = new Journal(paymentTo, transaction, getAccount(InternalAccountType.BANK), DrCrType.CREDIT, transaction.getAmount());

        Set<Journal> journalSet = new HashSet<>();
        journalSet.add(je1);
        journalSet.add(je2);
        return journalSet;
    }

    @Transactional
    public Set<Journal> receiptFromDebtor(InvTransaction transaction) throws Exception {
        Journal je1 = null, je2 = null;
        String receiptFrom = "Receipt from " + transaction.getClient().getName();
        je1 = new Journal(receiptFrom, transaction, getAccount(InternalAccountType.BANK), DrCrType.DEBIT, transaction.getAmount());
        je2 = new Journal("Received Money", transaction, getAccount(transaction.getClient()), DrCrType.CREDIT, transaction.getAmount());

        Set<Journal> journalSet = new HashSet<>();
        journalSet.add(je1);
        journalSet.add(je2);
        return journalSet;
    }

    @Transactional
    public Set<Journal> bankCharges() throws Exception {
        return null;
    }

    @Transactional
    public Set<Journal> indirectExpenses() throws Exception {
        return null;
    }

    @Transactional
    public Set<Journal> badDebt() throws Exception {
        return null;
    }

    @Transactional
    public Set<Journal> legalExpense() throws Exception {
        return null;
    }

    @Transactional
    public Set<Journal> interestOnLatePayment() throws Exception {
        return null;
    }


    public Account getAccount(InternalAccountType iacType) throws Exception {
        return accountService.getAccount(iacType);
    }

    public Account getAccount(Company client) throws Exception {
        return accountService.getAccount(client);
    }


}
