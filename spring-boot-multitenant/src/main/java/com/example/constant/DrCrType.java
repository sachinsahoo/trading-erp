package com.example.constant;

import java.util.Arrays;
import java.util.Optional;

public enum DrCrType {

    DEBIT(1, "debit"),
    CREDIT(2, "credit"),
    OTHER(3, "credit");


    private static final long serialVersionUID = 1L;

    private Integer code = 0;
    private final String type;

    DrCrType(Integer code, String type) {
        this.code = code;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public Integer getCode() {
        return code;
    }

    public static DrCrType getType(String type) {
        Optional<DrCrType> typeOpt =  Arrays.stream(DrCrType.values()).filter(ctype -> ctype.getType().equalsIgnoreCase(type)).findFirst();
        if(typeOpt.isPresent()) {
            return typeOpt.get();
        } else {
            return OTHER;
        }
    }

    public static DrCrType getType(Integer code) {
        Optional<DrCrType> typeOpt =  Arrays.stream(DrCrType.values()).filter(ctype -> ctype.getCode().equals(code)).findFirst();
        if(typeOpt.isPresent()) {
            return typeOpt.get();
        } else {
            return OTHER;
        }
    }


    public static String getTypeString(Integer code) {
        return getType(code).getType();

    }

    public static Integer getTypeCode(String type) {
        return getType(type).getCode();

    }
}


