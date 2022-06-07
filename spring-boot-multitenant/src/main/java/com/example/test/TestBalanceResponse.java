package com.example.test;

import com.example.entity.rs.base.BaseResponse;
import com.example.entity.dto.order.CompanyDto;
import com.example.entity.dto.order.InvTransactionDto;
import com.example.entity.dto.order.PurchaseOrderDto;

import java.util.ArrayList;
import java.util.List;

public class TestBalanceResponse extends BaseResponse {

    CompanyDto customer;

    CompanyDto supplier;

    CompanyDto myCompany;

    PurchaseOrderDto purchase;

    PurchaseOrderDto sale;

    InvTransactionDto transactions;

    List<String> logs = new ArrayList<>();



    public CompanyDto getCustomer() {
        return customer;
    }

    public void setCustomer(CompanyDto customer) {
        this.customer = customer;
    }

    public CompanyDto getSupplier() {
        return supplier;
    }

    public void setSupplier(CompanyDto supplier) {
        this.supplier = supplier;
    }

    public CompanyDto getMyCompany() {
        return myCompany;
    }

    public void setMyCompany(CompanyDto myCompany) {
        this.myCompany = myCompany;
    }

    public PurchaseOrderDto getPurchase() {
        return purchase;
    }

    public void setPurchase(PurchaseOrderDto purchase) {
        this.purchase = purchase;
    }

    public PurchaseOrderDto getSale() {
        return sale;
    }

    public void setSale(PurchaseOrderDto sale) {
        this.sale = sale;
    }

    public InvTransactionDto getTransactions() {
        return transactions;
    }

    public void setTransactions(InvTransactionDto transactions) {
        this.transactions = transactions;
    }

    public List<String> getLogs() {
        return logs;
    }

    public void setLogs(List<String> logs) {
        this.logs = logs;
    }

    public void addLog(String log) {
        if(this.logs == null){
        this.logs = new ArrayList<>();

        }
        this.logs.add(log);

    }
}
