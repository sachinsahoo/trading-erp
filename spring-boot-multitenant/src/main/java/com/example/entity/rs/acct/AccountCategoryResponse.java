package com.example.entity.rs.acct;

import com.example.entity.dto.acct.AccountCategoryDto;
import com.example.entity.rs.base.BaseResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountCategoryResponse extends BaseResponse {

    private AccountCategoryDto accountCategoryList;

    public AccountCategoryDto getAccountCategoryList() {
        return accountCategoryList;
    }

    public void setAccountCategoryList(AccountCategoryDto accountCategoryList) {
        this.accountCategoryList = accountCategoryList;
    }
}
