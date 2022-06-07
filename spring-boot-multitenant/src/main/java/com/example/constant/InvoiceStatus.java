package com.example.constant;

import java.util.Arrays;
import java.util.Optional;

public enum InvoiceStatus {

    INTRANSIT(1, "intransit"),
    COMPLETE(2, "complete"),
    UNKNOWN(3, "unknown");

    private static final long serialVersionUID = 1L;

    private Integer code;
    private final String type;

    InvoiceStatus(Integer code, String type) {
        this.code = code;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public Integer getCode() {
        return code;
    }

    public static InvoiceStatus getType(String type)  {
        Optional<InvoiceStatus> orderStatus =  Arrays.stream(InvoiceStatus.values()).filter(ctype -> ctype.getType().equalsIgnoreCase(type)).findFirst();
        return   orderStatus.orElse(UNKNOWN);
    }

    public static InvoiceStatus getType(Integer code)  {
        Optional<InvoiceStatus> orderStatus =  Arrays.stream(InvoiceStatus.values()).filter(ctype -> ctype.getCode().equals(code)).findFirst();
        return orderStatus.orElse(UNKNOWN);
    }

    public static String getTypeString(Integer code) {
        return getType(code).getType();

    }

    public static Integer getTypeCode(String type) {
        return getType(type).getCode();

    }
}
