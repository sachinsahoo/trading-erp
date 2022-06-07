package com.example.entity.mapper;

import com.example.constant.InternalAccountType;
import com.example.entity.acct.Account;
import com.example.entity.acct.AccountGroup;
import com.example.entity.dto.acct.AccountDto;
import com.example.entity.dto.acct.AccountGroupDto;

import java.util.function.BiFunction;
import java.util.function.Function;

public class AccountMapper {

    public static final Function<Account, AccountDto> mapperEntityToDto = (account) -> {
        AccountDto dto = new AccountDto();
        if (account == null) {
            return null;
        }
        dto.setId(account.getId());
        dto.setGroupId(account.getGroupid());

        dto.setClientId(account.getClientid());
        dto.setCode(account.getCode());
        dto.setIacType(InternalAccountType.getType(account.getCode()));
        //dto.setAcGroup(AcGroup.getType(account.getAcgroup()));
        // TODO
        dto.setBalance(account.getBalance());
        dto.setDrcr(account.getDrcr());
        dto.setName(account.getAcname());

        return dto;

    };

    public static final BiFunction<AccountDto, Account, Account> mapDtoToEntity = (accountDto, account) -> {

        if (accountDto == null) {
            return null;
        }
        account.setAcname(accountDto.getName());
        account.setGroupid(accountDto.getGroupId());
        account.setClientid(accountDto.getClientId());
        account.setCode(accountDto.getCode());
        return account;
    };



}
