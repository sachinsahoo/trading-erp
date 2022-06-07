package com.example.entity.rs.order;

import com.example.entity.rs.base.BaseResponse;
import com.example.entity.dto.order.InvTransactionDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionResponse extends BaseResponse {

private InvTransactionDto transaction;

    public InvTransactionDto getTransaction() {
        return transaction;
    }

    public void setTransaction(InvTransactionDto transaction) {
        this.transaction = transaction;
    }
}
