package com.example.entity.rs.order;

import com.example.common.exception.InputValidationException;
import com.example.common.util.DateUtil;
import com.example.constant.CompanyType;
import com.example.constant.DatePreset;
import com.example.constant.InvTransactionType;
import com.example.entity.rs.base.AbstractBaseRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;

@JsonIgnoreProperties (ignoreUnknown = true)
public class OrderSearchRequest extends AbstractBaseRequest {


    private Long myCompanyId;
    private String datePreset = "last3";
    private Long startDate;
    private Long endDate;

    private Integer count;

    public OrderSearchRequest() {
        this.datePreset = "last3";
    }

    public OrderSearchRequest(Long myCompanyId, DatePreset datePreset, Long startDate, Long endDate) {
        this.myCompanyId = myCompanyId;
        this.datePreset = datePreset.getCode();
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public void validate () throws InputValidationException {
        DatePreset datePreset1 = DatePreset.getType(this.datePreset);

        if(datePreset1.equals(DatePreset.UNKNOWN)) {
           this.datePreset = null;
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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
