package com.example.constant;

import javax.swing.text.html.Option;
import java.util.Arrays;
import java.util.Optional;

public enum CompanyType {

    SELF(1,"self"),
    SUPPLIER(2, "supplier"),
    CUSTOMER(3,"customer"),
    AGENT(4,"agent"),
    UNKNOWN(100,"unknown");

  private static final long serialVersionUID = 1L;

    private Integer code;
    private final String type;

    CompanyType(Integer code, String type) {
        this.code = code;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public Integer getCode() {
        return code;
    }

    public static CompanyType getType(String type) {
       Optional<CompanyType> typeOpt =  Arrays.stream(CompanyType.values()).filter(ctype -> ctype.getType().equalsIgnoreCase(type)).findFirst();
       if(typeOpt.isPresent()) {
           return typeOpt.get();
       } else {
           return UNKNOWN;
       }
    }



    public static CompanyType getType(Integer code) {
        Optional<CompanyType> typeOpt =  Arrays.stream(CompanyType.values()).filter(ctype -> ctype.getCode().equals(code)).findFirst();
        if(typeOpt.isPresent()) {
            return typeOpt.get();
        } else {
            return UNKNOWN;
        }
    }

    public static String getTypeString(Integer code) {
        return getType(code).getType();

    }

    public static Integer getTypeCode(String type) {
        return getType(type).getCode();

    }

}
