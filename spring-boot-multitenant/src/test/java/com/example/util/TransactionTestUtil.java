package com.example.util;

import com.example.common.util.DateUtil;
import com.example.constant.InvTransactionType;
import com.example.controller.manager.InvTransactionManager;
import com.example.controller.service.InvTransactionService;
import com.example.entity.dto.order.CompanyDto;
import com.example.entity.dto.order.InvTransactionDto;
import com.example.entity.dto.order.InvoiceDto;
import com.example.entity.order.InvTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionTestUtil {
    @Autowired
    InvTransactionManager transactionManager;

    @Autowired
    InvTransactionService transactionService;

    // Create 4 products
    // createProduct(String name)

    protected void createPayment(InvTransactionType type , CompanyDto myCompany, CompanyDto custCompany, InvoiceDto inv, BigDecimal amount, LocalDateTime date, String notes) throws Exception {
        InvTransactionDto payment = new InvTransactionDto(DateUtil.getEpochTime(date), notes, custCompany.getId()
                , myCompany.getId(), type.getType(), inv.getId(), inv.getOid(), amount);
        transactionManager.saveTransaction(payment);
    }


    public void deleteAllTransactions() throws Exception  {
        List<InvTransaction> transactionList =  transactionService.getAllTransaction();
        for(InvTransaction dto : transactionList) {
            deleteTransactionById(dto.getId());
        }
    }

    @Transactional
    public void deleteTransactionById(Long id) throws Exception {
        InvTransaction transaction = (InvTransaction) transactionService.find(InvTransaction.class, id);
//        for(Journal journal : transaction.getJournals()){
//            journal = (Journal) transactionService.find(Journal.class, journal.getId());
//            transaction.getJournals().remove(journal);
//            transactionService.remove(journal);
//            transactionService.refresh(transaction);
//        }
        transactionService.remove(transaction);
    }



    public void deleteProductById4(Long id) throws Exception  {}

    public void deleteProductById5(Long id) throws Exception  {}

    public void deleteProductById6(Long id) throws Exception  {}

}