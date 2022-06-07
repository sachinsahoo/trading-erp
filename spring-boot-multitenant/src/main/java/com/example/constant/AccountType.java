package com.example.constant;

import java.util.Arrays;
import java.util.Optional;

public enum AccountType {

    ASSET(10000, "Assets"),
    LIABILITY(30000, "Equity and Liabilities"),
    INCOME(50000, "Income"),
    EXPENSE(70000, "Expense"),
    UNKNOWN(0, "");

    private static final long serialVersionUID = 1L;

    private int code = 0;
    private final String type;

    AccountType(int code, String type) {
        this.code = code;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public int getCode() {
        return code;
    }

    public static Optional<AccountType> getType(String type) {
        return Arrays.stream(AccountType.values()).filter(ctype -> ctype.getType().equalsIgnoreCase(type)).findFirst();
    }

    public static String getTypeString(String type) {
        Optional<AccountType> typeOptional = getType(type);
        String strType = typeOptional.isPresent() ? typeOptional.get().getType() : "";
        return strType;
    }
}


