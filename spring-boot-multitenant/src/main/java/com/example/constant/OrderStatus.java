package com.example.constant;

import com.example.common.exception.InputValidationException;

import java.util.Arrays;
import java.util.Optional;

public enum OrderStatus {

    DRAFT(1, "draft"),
    RFQ(2, "rfq"),
    CONFIRM(3,"confirm"),
    COMPLETE(4,"complete"),
    UNKNOWN(5,"unknown");


    private static final long serialVersionUID = 1L;

    private final String type;
    private final Integer code;

    OrderStatus(Integer code, String type) {
        this.code = code;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public Integer getCode() {
        return code;
    }

    public static OrderStatus getType(String type)  {
        Optional<OrderStatus> orderStatus =  Arrays.stream(OrderStatus.values()).filter(ctype -> ctype.getType().equalsIgnoreCase(type)).findFirst();
        return orderStatus.orElse(UNKNOWN);
    }

    public static OrderStatus getType(Integer code)  {
        Optional<OrderStatus> orderStatus =  Arrays.stream(OrderStatus.values()).filter(ctype -> ctype.getCode().equals(code)).findFirst();
        return orderStatus.orElse(UNKNOWN);
    }

    public static String getTypeString(Integer code) {
        return getType(code).getType();
    }

    public static Integer getTypeCode(String type) {
        return getType(type).getCode();
    }
}
