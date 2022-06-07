package com.example.constant;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public enum AcCategory {



    // ASSET 3000
    PROPERTY(11000, "Property, Plant and Equipment",  AccountType.ASSET),
    INVESTMENTS(12000, "Investment",  AccountType.ASSET),
    LOANS_ADVANCES(13000, "Loans and Advances",  AccountType.ASSET),
    CURRENT_ASSETS(14000, "Current Assets",  AccountType.ASSET),

    // LIABILITY 4000
    CAPITAL(31000, "Capital Account",  AccountType.LIABILITY),
    LOAN_BORROWINGS(32000, "Loans and Borrowings",  AccountType.LIABILITY),
    CURRENT_LIABILITIES(33000, "Current Liabilities",  AccountType.LIABILITY),

    // INCOME 1000
    SALES(51000, "Sales Accounts",  AccountType.INCOME),
    DIRECT_INCOME(52000, "Direct Incomes",  AccountType.INCOME),
    INDIRECT_INCOME(53000, "Indirect Incomes",  AccountType.INCOME),
    CLOSING_STOCK(54000, "Closing Stock",  AccountType.INCOME),

    // EXPENSE 2000
    PURCHASE(71000, "Purchase Accounts",  AccountType.EXPENSE),
    DIRECT_EXPENSES(72000, "Direct Expenses",  AccountType.EXPENSE),
    INDIRECT_EXPENSES(73000, "Indirect Expenses",  AccountType.EXPENSE),
    OPENING_STOCK(74000, "Opening Stock",  AccountType.EXPENSE);




    private static final long serialVersionUID = 1L;

    private Integer code = 0;
    private final String desc;
    private AccountType accountType;

    AcCategory(Integer code, String desc, AccountType actype) {
        this.code = code;
        this.desc = desc;
        this.accountType = actype;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getCode() {
        return code;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public static Optional<AcCategory> getType(Integer code) {
        return Arrays.stream(AcCategory.values()).filter(ctype -> ctype.getCode().equals(code)).findFirst();
    }

    public static List<AcCategory> getByAcctType(AccountType acType) {
        return Arrays.stream(AcCategory.values()).filter(ctype -> ctype.getAccountType().equals(acType)).collect(Collectors.toList());
    }







}
