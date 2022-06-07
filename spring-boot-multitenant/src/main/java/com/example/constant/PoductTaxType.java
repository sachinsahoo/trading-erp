package com.example.constant;

import java.util.Arrays;
import java.util.Optional;

public enum PoductTaxType {

    CGST("CGST"),
    SGST("SGST"),
    IGST("IGST"),
    UNKNOWN("UNK");


    private static final long serialVersionUID = 1L;

    private String code = "";


    PoductTaxType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static PoductTaxType getType(String code) {
        Optional<PoductTaxType> taxOpt = Arrays.stream(PoductTaxType.values()).filter(ctype -> ctype.getCode().equalsIgnoreCase(code)).findFirst();
        if(taxOpt.isPresent()){
            return taxOpt.get();
        } else {
            return UNKNOWN;
        }
    }

}


