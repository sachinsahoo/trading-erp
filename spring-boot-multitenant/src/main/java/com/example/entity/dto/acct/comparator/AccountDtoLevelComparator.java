package com.example.entity.dto.acct.comparator;

import com.example.entity.dto.acct.AccountDto;

import java.util.Comparator;

public class AccountDtoLevelComparator implements Comparator<AccountDto> {

    public static AccountDtoLevelComparator getInstance() { return new AccountDtoLevelComparator();}


    // Compare by Level
    public int compare(AccountDto acct1, AccountDto acct2) {
        return Integer.compare(acct1.getLevel(), acct2.getLevel());
    }

}
