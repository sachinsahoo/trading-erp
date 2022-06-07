package com.example.entity.rs.order;

import com.example.common.exception.InputValidationException;
import com.example.common.util.DateUtil;
import com.example.constant.CompanyType;
import com.example.constant.DatePreset;
import com.example.constant.InvTransactionType;
import com.example.entity.dto.order.CompanyDto;
import com.example.entity.order.Company;
import com.example.entity.rs.base.AbstractBaseRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.Date;

@JsonIgnoreProperties (ignoreUnknown = true)
public class TransactionSearchRequest extends AbstractBaseRequest {


    private Long myCompanyId;
    private String datePreset = "all";
    private String clientType;
    private Long clientId;
    private Long startDate;
    private Long endDate;
    private String transType;
    private Long orderno;
    private Long invoiceno;

    private Integer count;

    public TransactionSearchRequest() {
        this.datePreset = "last3";
    }

    public TransactionSearchRequest(Long myCompanyId, DatePreset datePreset, CompanyType clientType , Long clientId, Long startDate, Long endDate) {
        this.myCompanyId = myCompanyId;
        this.datePreset = datePreset.getCode();
        this.clientType = clientType.getType();
        this.clientId = clientId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public void validate () throws InputValidationException {
        DatePreset datePreset1 = DatePreset.getType(this.datePreset);
        CompanyType companyType = CompanyType.getType(this.clientType);
        InvTransactionType transactionType = InvTransactionType.getType(this.transType);
        if(CompanyType.UNKNOWN.equals(companyType)){
            this.clientType = null;
        }
        if(datePreset1.equals(DatePreset.UNKNOWN)) {
           this.datePreset = null;
        }
        if(InvTransactionType.UNKNOWN.equals(transactionType)){
            this.transType = null;
        }
        if(datePreset1.equals(DatePreset.CUSTOM)  ){
            if(this.startDate == null || this.endDate == null){
                throw new InputValidationException("Required Start Date and End Date for Custom Date range");
            }

        }  else {
            switch (datePreset1) {
                case LAST_1_MONTH:
                    this.startDate = DateUtil.getEpochTime(LocalDateTime.now().minusMonths(1));
                    this.endDate = DateUtil.getEpochTime(LocalDateTime.now());
                    break;
                case LAST_3_MONTH:
                    this.startDate = DateUtil.getEpochTime(LocalDateTime.now().minusMonths(3));
                    this.endDate = DateUtil.getEpochTime(LocalDateTime.now());
                    break;
            }
        }
    }

    public Long getMyCompanyId() {
        return myCompanyId;
    }

    public void setMyCompanyId(Long myCompanyId) {
        this.myCompanyId = myCompanyId;
    }

    public String getDatePreset() {
        return datePreset;
    }

    public void setDatePreset(String datePreset) {
        this.datePreset = datePreset;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public Long getOrderno() {
        return orderno;
    }

    public void setOrderno(Long orderno) {
        this.orderno = orderno;
    }

    public Long getInvoiceno() {
        return invoiceno;
    }

    public void setInvoiceno(Long invoiceno) {
        this.invoiceno = invoiceno;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
