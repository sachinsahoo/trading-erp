package com.example.constant;

import java.util.Arrays;
import java.util.Optional;

public enum VoucherType {

    PAYMENT(1, "payment"),
    RECEIPT(2, "receipt"),
    CONTRA(3, "contra"),
    PURCHASE(4, "purchase"),
    SALE(5, "sale"),
    DEBIT_NOTE(6, "debit"),
    CREDIT_NOTE(7, "credit"),
    JOURNAL(8, "journal");


    private static final long serialVersionUID = 1L;

    private int code = 0;
    private final String type;

    VoucherType(int code, String type) {
        this.code = code;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public int getCode() {
        return code;
    }

    public static Optional<VoucherType> getType(String type) {
        return Arrays.stream(VoucherType.values()).filter(ctype -> ctype.getType().equalsIgnoreCase(type)).findFirst();
    }

    public static String getTypeString(String type) {
        Optional<VoucherType> typeOptional = getType(type);
        String strType = typeOptional.isPresent() ? typeOptional.get().getType() : "";
        return strType;
    }
}


