package com.example.entity.rs.acct;

import com.example.entity.dto.acct.AccountGroupDto;
import com.example.entity.rs.base.BaseResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountGroupResponse extends BaseResponse {

    private AccountGroupDto accountGroupList;

    public AccountGroupDto getAccountGroupList() {
        return accountGroupList;
    }

    public void setAccountGroupList(AccountGroupDto accountGroupList) {
        this.accountGroupList = accountGroupList;
    }
}
