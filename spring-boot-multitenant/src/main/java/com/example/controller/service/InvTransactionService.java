package com.example.controller.service;

import com.example.common.exception.ServiceException;
import com.example.common.util.DateUtil;
import com.example.constant.CompanyType;
import com.example.constant.DatePreset;
import com.example.constant.InvTransactionType;
import com.example.constant.OrderType;
import com.example.controller.service.base.BaseDaoService;
import com.example.controller.util.JournalEntryRules;
import com.example.entity.acct.FinancialYear;
import com.example.entity.acct.Journal;
import com.example.entity.dto.acct.JournalDto;
import com.example.entity.mapper.JournalMapper;
import com.example.entity.order.*;
import com.example.entity.dto.order.InvTransactionDto;
import com.example.entity.mapper.InvTransactionMapper;
import com.example.entity.rs.order.TransactionSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class InvTransactionService extends BaseDaoService {


    @Autowired
    private JournalEntryRules journalEntryRules;

    @Autowired
    private AccountService accountService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private UserService userService;



    /************************
     Save Custom Journal Entries
     *************************/
    @Transactional
    public InvTransaction saveJournal(InvTransactionDto transDto) throws Exception {
        InvTransaction transaction = null;
        if (transDto.getId() != null) {
            transaction = (InvTransaction) find(InvTransaction.class, transDto.getId());
            if (transaction == null)
                throw new ServiceException("Transaction not found ID: " + transDto.getId());
        } else {
            transaction = new InvTransaction();
        }

        transaction = InvTransactionMapper.mapDtoToEntity.apply(transDto, transaction);
        transaction.setTenantid(getTenantId());
        if (transaction.getId() == null)
            persist(transaction);
        else
            merge(transaction);

        if (transDto.getJournals() == null)
            throw new ServiceException("No Journal found in Transaction" + transDto.getId());

        BigDecimal totalDr = BigDecimal.ZERO;
        BigDecimal totalCr = BigDecimal.ZERO;
        for (JournalDto journalDto : transDto.getJournals()) {
            Journal journal = null;
            if (journalDto.getId() != null) {
                journal = (Journal) find(Journal.class, journalDto.getId());
            } else {
                journal = new Journal();
            }
            journal = JournalMapper.mapperDtoToEntity.apply(journalDto, journal);
            journal.setTransid(transaction.getId());
            journal.setTransdate(transaction.getTransdate());
            journal.setDescription(transaction.getNotes());
            if (journal.getId() != null) {
                merge(journal);
            } else {
                persist(journal);
            }

            // Add to total
            if (journal.getDrcrtype() == 1)
                totalDr = totalDr.add(journal.getAmount());

            if (journal.getDrcrtype() == 2)
                totalCr = totalCr.add(journal.getAmount());
        }

        if (!totalCr.equals(totalDr))
            throw new ServiceException("Total Debit Does not match Total Cr" + transDto.getId());

        transaction.setAmount(totalDr);
        merge(transaction);
        refresh(transaction);
        return transaction;
    }

    @Transactional
    public InvTransaction removeDeletedJournals(InvTransactionDto transDto) throws Exception {
        InvTransaction transaction = null;
        if (transDto.getId() != null) {
            transaction = (InvTransaction) find(InvTransaction.class, transDto.getId());
        } else {
            return null;
        }

        Set<Long> tranids = new HashSet<>();
        Set<Long> tranDtoIds = new HashSet<>();
        //PO Product
        if (transaction.getJournals() != null) {
            tranids = transaction.getJournals().stream().map(Journal::getId).collect(Collectors.toSet());
        }
        if (transDto.getJournals() != null) {
            tranDtoIds = transDto.getJournals().stream().map(JournalDto::getId).collect(Collectors.toSet());
        }
        //Delete PO products not in DTO
        for (Long id : tranids) {
            if (!tranDtoIds.contains(id)) {
                Journal journal = (Journal) find(Journal.class, id);
                transaction.getJournals().remove(journal);
                remove(journal);
            }
        }
        merge(transaction);
        refresh(transaction);
        return transaction;

    }


    @Transactional
    public InvTransaction savePayment(InvTransactionDto transDto) throws Exception {
        InvTransaction transaction = new InvTransaction();
        Set<Journal> journalSet = new HashSet<>();
        if (transDto.getId() != null) {
            transaction = (InvTransaction) find(InvTransaction.class, transDto.getId());
            if (transaction == null)
                throw new ServiceException("Transaction not found ID: " + transDto.getId());
        }

        transaction = InvTransactionMapper.mapDtoToEntity.apply(transDto, transaction);
        transaction.setTenantid(getTenantId());
        if(transaction.getId() == null)
            persist(transaction);

        // New Transaction
        if(transDto.getId() == null) {
            InvTransactionType type = InvTransactionType.getType(transaction.getType());
            String notes = "";

            switch (type) {
                case PAYMENT:
                    notes = "Payment to " + transaction.getClient().getName();
                    journalSet = journalEntryRules.paymentToParty(transaction);
                    break;

                case RECEIPT:
                    notes = "Received from " + transaction.getClient().getName();
                    journalSet = journalEntryRules.receiptFromDebtor(transaction);
                    break;
            }

            accountService.saveJournals(journalSet);
            refresh(transaction);
            transaction.setNotes(notes);
            merge(transaction);

        // For Existing Transaction Update payment amount
        } else {
            if(transaction.getJournals() == null || transaction.getJournals().isEmpty())
                throw new ServiceException("No Journals found for Existing Transaction" + transDto.getId());
            for(Journal journal : transaction.getJournals()) {
                journal.setAmount(transaction.getAmount());
            }

        }
        merge(transaction);
        refresh(transaction);
        return transaction;
    }


    /************************
     Save System Transaction
     *************************/
    @Transactional
    public InvTransaction saveSystemTransaction(InvTransactionDto transDto) throws Exception {
        Set<Journal> journalSet = new HashSet<>();
        InvTransaction transaction = new InvTransaction();
        String notes = "";

        transaction = InvTransactionMapper.mapDtoToEntity.apply(transDto, transaction);
        transaction.setTenantid(getTenantId());
        persist(transaction);
        refresh(transaction);

        InvTransactionType type = InvTransactionType.getType(transaction.getType());

        switch (type) {
            case PAYMENT:
                notes = "Payment to " + transaction.getClient().getName();
                journalSet = journalEntryRules.paymentToParty(transaction);
                break;

            case RECEIPT:
                notes = "Received from " + transaction.getClient().getName();
                journalSet = journalEntryRules.receiptFromDebtor(transaction);
                break;
        }

        transaction.setNotes(notes);
        merge(transaction);

        accountService.saveJournals(journalSet);
        refresh(transaction);

        return transaction;

    }


    /************************
     Invoice Balance Transaction
     Journal Entries
     *************************/
    @Transactional
    public void enterInvoiceBalance(Invoice invoice) throws Exception {
        Set<Journal> journalSet = new HashSet<>();
        String notes = "";

        PurchaseOrder order = (PurchaseOrder) find(PurchaseOrder.class, invoice.getOid());
        invoice.setBalance(invoice.getTotalamount());
        merge(invoice);

        OrderType type = OrderType.getType(invoice.getType());

        InvTransactionType transactionType =
                OrderType.PURCHASE.equals(type) ? InvTransactionType.PURCHASE :
                        OrderType.SALE.equals(type) ? InvTransactionType.SALE : InvTransactionType.UNKNOWN;

        InvTransaction transaction = new InvTransaction(transactionType, order, invoice, invoice.getBalance(), invoice.getInvdate(), getTenantId());
        persist(transaction);

        switch (type) {
            case SALE:
                notes = "Sold Goods to " + transaction.getClient().getName();
                journalSet = journalEntryRules.saleLocal(transaction, invoice);
                break;

            case PURCHASE:
                notes = "Purchased Goods from " + transaction.getClient().getName();
                journalSet = journalEntryRules.purchaseLocal(transaction, invoice);
                break;
        }

        transaction.setNotes(notes);
        persist(transaction);

        accountService.saveJournals(journalSet);
        refresh(transaction);

    }


    /************************
     Order Advance Transaction
     Journal Entries
     *************************/
    @Transactional
    public void enterOrderAdvance(PurchaseOrder order) throws Exception {
        Set<Journal> journalSet = new HashSet<>();
        String notes = "";

        OrderType type = OrderType.getType(order.getType());
        InvTransactionType transactionType =
                OrderType.PURCHASE.equals(type) ? InvTransactionType.PURCHASE_ADVANCE :
                        OrderType.SALE.equals(type) ? InvTransactionType.SALE_ADVANCE : InvTransactionType.UNKNOWN;

        InvTransaction transaction = new InvTransaction(transactionType, order, null, order.getAdvance(), order.getConfirmdate(), getTenantId());
        persist(transaction);

        switch (type) {
            case SALE:
                notes = "Received Advance on Order from " + transaction.getClient().getName();
                journalSet = journalEntryRules.advanceReceivedOnSale(transaction, order);
                break;

            case PURCHASE:
                notes = "Paid Advance on Order to " + transaction.getClient().getName();
                journalSet = journalEntryRules.advancePaidOnPurchase(transaction, order);
                break;
        }
        transaction.setNotes(notes);
        persist(transaction);
        accountService.saveJournals(journalSet);
        refresh(transaction);
    }


    @Transactional
    public List<InvTransactionDto> findTransactionByOid(Long oid) throws Exception {

        TypedQuery<InvTransaction> orderTransactions = createNamedQuery(InvTransaction.Q_BY_OID, InvTransaction.class);
        orderTransactions.setParameter("orderid", oid);
        List<InvTransaction> transactions = orderTransactions.getResultList();    //a typedQuery method
        List<InvTransactionDto> invTransactionDtoList = transactions.stream().map(InvTransactionMapper.mapperEntityToDto).collect(Collectors.toList());
        return invTransactionDtoList;

    }

    @Transactional
    public InvTransaction getTransaction(Long id) throws Exception {
        InvTransaction transaction = (InvTransaction) find(InvTransaction.class, id);
        return transaction;

    }


    @Transactional
    public List<InvTransaction> getAllTransaction() throws Exception {

        TypedQuery<InvTransaction> allTransactions = createNamedQuery(InvTransaction.Q_TRANSACTION_LIST, InvTransaction.class);
        allTransactions.setParameter("tenantid", getTenantId());
        List<InvTransaction> transactions = allTransactions.getResultList();    //a typedQuery method
        return transactions;


    }

    //----------------------------------------------
    // Search Transactions
    // Date Range, Company, client type, client
    //---------------------------------------------

    @Transactional
    public List<InvTransaction> searchTransaction(TransactionSearchRequest searchRequest) throws Exception {

        List<InvTransaction> results = new ArrayList<>();

        try {
            CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<InvTransaction> criteriaQuery = criteriaBuilder.createQuery(InvTransaction.class);
            Root<InvTransaction> invTransactionRoot = criteriaQuery.from(InvTransaction.class);
            Join<InvTransaction, Company> clientJoin = invTransactionRoot.join("client");
            Join<InvTransaction, Company> myCompanyJoin = invTransactionRoot.join("mycompany");
            Join<InvTransaction, Company> orderJoin = invTransactionRoot.join("order");
            Join<InvTransaction, Company> invoiceJoin = invTransactionRoot.join("invoice");

            ArrayList<Predicate> predicates = new ArrayList<>();

            Predicate predicateTenanat = criteriaBuilder.equal(
                    invTransactionRoot.get("tenantid"), getTenantId());
            predicates.add(predicateTenanat);

            // My Company
            if(searchRequest.getMyCompanyId() != null && searchRequest.getMyCompanyId() > 1){
                Predicate predicateMyCompany = criteriaBuilder.equal(
                        myCompanyJoin.get("id"), searchRequest.getMyCompanyId());
                predicates.add(predicateMyCompany);
            }

            // Date Range
            if(DatePreset.getType(searchRequest.getDatePreset()) != DatePreset.UNKNOWN){
                LocalDateTime start = DateUtil.toLocalDateTime(searchRequest.getStartDate());
                LocalDateTime end = DateUtil.toLocalDateTime(searchRequest.getEndDate());
                Predicate predicateDateRange = criteriaBuilder.between(
                        invTransactionRoot.get("transdate"), start, end);
                predicates.add(predicateDateRange);
            }
            // Client Type
            if(searchRequest.getClientType() != null) {
                predicates.add(criteriaBuilder.equal(clientJoin.get("type"), CompanyType.getTypeCode(searchRequest.getClientType())));
            }

            // Client Id
            if(searchRequest.getClientId() != null){
                Predicate predicateClientId = criteriaBuilder.equal(
                        clientJoin.get("id"), searchRequest.getClientId());
                predicates.add(predicateClientId);
            }

            // Invoice No
            if(searchRequest.getInvoiceno() != null){
                Predicate predicateInvoiced = criteriaBuilder.equal(
                        invoiceJoin.get("invoiceno"), searchRequest.getInvoiceno());
                predicates.add(predicateInvoiced);
            }

            // Order No
            if(searchRequest.getOrderno() != null){
                Predicate predicateOrder = criteriaBuilder.equal(
                        orderJoin.get("orderno"), searchRequest.getOrderno());
                predicates.add(predicateOrder);
            }

            // Transaction Type
            if(searchRequest.getTransType() != null){
                Predicate predicateOrder = criteriaBuilder.equal(
                        invTransactionRoot.get("type"), InvTransactionType.getTypeCode(searchRequest.getTransType()));
                predicates.add(predicateOrder);
            }

            Predicate finalPredicate = criteriaBuilder.and(predicates.toArray(new Predicate[] {}));
            criteriaQuery.where(finalPredicate);
            criteriaQuery.orderBy(criteriaBuilder.desc(invTransactionRoot.get("transdate")));
            results = getEntityManager().createQuery(criteriaQuery).getResultList();


        } catch (Exception e) {
            addToSessionAppLog("Transaction Service Error 901: " + e.getMessage());
            throw e;

        }
        return  results;


    }



    /************************************
     Open new Financial Year
     ***********************************/
    @Deprecated
    @Transactional
    public void openFinancialYear() throws Exception {

        FinancialYear curFinYear = accountService.getFinyear(LocalDateTime.now());

        Journal openingStock;
        InvTransaction transaction = new InvTransaction();

        List<Company> allCompanies = companyService.getAllCompanies();
        for (Company company : allCompanies) {

            journalEntryRules.openingBalance(transaction, accountService.getAccount(company));
        }


    }


}
