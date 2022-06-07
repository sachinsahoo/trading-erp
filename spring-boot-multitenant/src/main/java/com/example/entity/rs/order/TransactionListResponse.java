package com.example.entity.rs.order;

import com.example.entity.dto.order.InvTransactionDto;
import com.example.entity.rs.base.BaseResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionListResponse extends BaseResponse {

private List<InvTransactionDto> transactions;
private TransactionSearchRequest searchRequest;

    public List<InvTransactionDto> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<InvTransactionDto> transactions) {
        this.transactions = transactions;
    }

    public TransactionSearchRequest getSearchRequest() {
        return searchRequest;
    }

    public void setSearchRequest(TransactionSearchRequest searchRequest) {
        this.searchRequest = searchRequest;
    }
}
