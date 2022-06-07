package com.example.entity.rs.acct;

import com.example.common.exception.BaseException;
import com.example.entity.dto.acct.AccountGroupDto;
import com.example.entity.rs.base.AbstractBaseRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountGroupRequest extends AbstractBaseRequest {
    private AccountGroupDto groupDto;

    public AccountGroupDto getGroupDto() {
        return groupDto;
    }

    public void setGroupDto(AccountGroupDto groupDto) {
        this.groupDto = groupDto;
    }

    @Override
    public void validate() throws BaseException {

    }
}
