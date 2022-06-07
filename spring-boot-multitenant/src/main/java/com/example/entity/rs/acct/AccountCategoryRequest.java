package com.example.entity.rs.acct;

import com.example.common.exception.BaseException;
import com.example.entity.dto.acct.AccountCategoryDto;
import com.example.entity.dto.acct.AccountGroupDto;
import com.example.entity.rs.base.AbstractBaseRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountCategoryRequest extends AbstractBaseRequest {


    private AccountCategoryDto categoryDto;


    public AccountCategoryDto getCategoryDto() {
        return categoryDto;
    }

    public void setCategoryDto(AccountCategoryDto categoryDto) {
        this.categoryDto = categoryDto;
    }

    @Override
    public void validate() throws BaseException {

    }
}
