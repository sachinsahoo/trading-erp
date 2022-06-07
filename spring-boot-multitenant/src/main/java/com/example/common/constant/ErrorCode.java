package com.example.common.constant;

import java.util.Arrays;
import java.util.Optional;

public enum ErrorCode {

    Tenant_Not_Found(1001, "Tenant not found"),
    Company_Not_Found(1002, "Company not found"),
    UNKNOWN(0, "");

    private static final long serialVersionUID = 1L;

    private int code = 0;
    private final String desc;

    ErrorCode(int code, String type) {
        this.code = code;
        this.desc = type;
    }

    public String getDesc() {
        return desc;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return getCode() + ": " + getDesc();
    }

    public static Optional<ErrorCode> getType(String type) {
        return Arrays.stream(ErrorCode.values()).filter(ctype -> ctype.getDesc().equalsIgnoreCase(type)).findFirst();
    }

    public static String getTypeString(String type) {
        Optional<ErrorCode> typeOptional = getType(type);
        String strType = typeOptional.isPresent() ? typeOptional.get().getDesc() : "";
        return strType;
    }
}


