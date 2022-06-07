package com.example.controller.manager;

import com.example.common.exception.InputValidationException;
import com.example.constant.InvTransactionType;
import com.example.controller.service.InvTransactionService;
import com.example.entity.order.InvTransaction;
import com.example.entity.dto.order.InvTransactionDto;
import com.example.entity.mapper.InvTransactionMapper;
import com.example.entity.rs.order.TransactionSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvTransactionManager extends BaseManager {

    @Autowired
    private InvTransactionService invTransactionService;

    public InvTransactionDto saveTransaction(InvTransactionDto invTransactionDto) throws Exception {
        InvTransaction transaction;
        //TODO
        //validate for payment

        // Remove Deleted Entities
        invTransactionService.removeDeletedJournals(invTransactionDto);
        InvTransactionType type = InvTransactionType.getType(invTransactionDto.getType());

        switch (type) {
            case PAYMENT:
            case RECEIPT:
                transaction = invTransactionService.savePayment(invTransactionDto);
                break;
            case JOURNAL:
                transaction = invTransactionService.saveJournal(invTransactionDto);
                break;
            default:
                throw new InputValidationException("Transaction Type not Supported " + invTransactionDto.getType());

        }


        invTransactionDto = InvTransactionMapper.mapperEntityToDto.apply(transaction);
        return invTransactionDto;
    }

    public List<InvTransactionDto> getAllTransactions() throws Exception {
        List<InvTransaction> transactions= invTransactionService.getAllTransaction();

        List<InvTransactionDto> invTransactionDtoList = new ArrayList<>();
        if(transactions != null)
            invTransactionDtoList = transactions.stream().map(InvTransactionMapper.mapperEntityToDto).collect(Collectors.toList());
        return invTransactionDtoList;

    }

    public InvTransactionDto getTransaction(Long id) throws Exception {

        InvTransaction transaction = invTransactionService.getTransaction(id);
        if(transaction != null) {
            return InvTransactionMapper.mapperEntityToDto.apply(transaction);
        } else {
            return null;
        }

    }


    //------------
    // Search
    //------------
    public List<InvTransactionDto> search(TransactionSearchRequest searchRequest) throws Exception {
       searchRequest.validate();
        List<InvTransactionDto> transactions = invTransactionService.searchTransaction(searchRequest).stream()
                .map(InvTransactionMapper.mapperEntityToDto).collect(Collectors.toList());

        return transactions;
    }




}
