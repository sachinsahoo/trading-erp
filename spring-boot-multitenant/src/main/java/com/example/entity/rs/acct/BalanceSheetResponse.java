package com.example.entity.rs.acct;

import com.example.entity.dto.acct.BalanceSheetDto;
import com.example.entity.rs.base.BaseResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BalanceSheetResponse extends BaseResponse {

    private BalanceSheetDto balanceSheet;

    public BalanceSheetDto getBalanceSheet() {
        return balanceSheet;
    }

    public void setBalanceSheet(BalanceSheetDto balanceSheet) {
        this.balanceSheet = balanceSheet;
    }
}
