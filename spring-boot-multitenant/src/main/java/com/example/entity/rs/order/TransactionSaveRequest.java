package com.example.entity.rs.order;

import com.example.common.exception.InputValidationException;
import com.example.constant.InvTransactionType;
import com.example.entity.rs.base.AbstractBaseRequest;
import com.example.entity.dto.order.InvTransactionDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties (ignoreUnknown = true)
public class TransactionSaveRequest extends AbstractBaseRequest {


    private InvTransactionDto transaction;

    public InvTransactionDto getTransaction() {
        return transaction;
    }

    public void setTransaction(InvTransactionDto transaction) {
        this.transaction = transaction;
    }

    @Override
    public void validate () throws InputValidationException {

        if(transaction.getMyCompanyId() == null) {
            throw new InputValidationException("My company missing");
        }

        if(transaction.getAmount() == null) {
            throw new InputValidationException("amount missing");
        }

        if(transaction.getType() == null || InvTransactionType.getType(transaction.getType()).equals("")) {
            throw new InputValidationException(" Transaction type can be, payment, received, purchase or sale");
        }

        // :TODO Validate based on transaction type
        // Payment
        // Received
        // Journal

    }

}
