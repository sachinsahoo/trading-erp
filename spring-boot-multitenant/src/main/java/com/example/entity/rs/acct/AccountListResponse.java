package com.example.entity.rs.acct;

import com.example.entity.dto.acct.AccountDto;
import com.example.entity.dto.acct.JournalDto;
import com.example.entity.rs.base.BaseResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountListResponse extends BaseResponse {

    private List<AccountDto> accounts;

    public List<AccountDto> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountDto> accounts) {
        this.accounts = accounts;
    }
}
