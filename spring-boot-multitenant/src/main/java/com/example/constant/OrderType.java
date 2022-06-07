package com.example.constant;

import com.example.common.exception.InputValidationException;

import java.util.Arrays;
import java.util.Optional;

public enum OrderType {

    PURCHASE(1,"purchase"),
    SALE(2, "sale"),
    COMMISSION(3,"comm"),
    UNKNOWN(4,"UNK");

    private static final long serialVersionUID = 1L;

    private final String type;
    private Integer code;

    OrderType( Integer code, String type) {
        this.type = type;
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public Integer getCode() {
        return code;
    }

    public static OrderType getType(String type){
       Optional<OrderType> orderType =  Arrays.stream(OrderType.values()).filter(ctype -> ctype.getType().equalsIgnoreCase(type)).findFirst();
        return orderType.orElse(UNKNOWN);
    }

    public static OrderType getType(Integer code) {
        Optional<OrderType> orderType =  Arrays.stream(OrderType.values()).filter(ctype -> ctype.getCode().equals(code)).findFirst();
        return orderType.orElse(UNKNOWN);
    }

    public static String getTypeString(Integer code) {
        return getType(code).getType();

    }

    public static Integer getTypeCode(String type) {
        return getType(type).getCode();

    }

    public static String getPrefix(String type) throws Exception {
            return getType(type).equals(PURCHASE) ? "PO" :getType(type).equals(SALE) ? "SO" : "CO";
    }

    public static String getInvPrefix(Integer type) throws Exception {
        return getType(type).equals(PURCHASE) ? "PI" :getType(type).equals(SALE) ? "SI" : "CI";
    }
}
