package com.example.constant;

import java.util.Arrays;
import java.util.Optional;

public enum DatePreset {

    LAST_1_MONTH("last1", "Last 1 Month"),
    LAST_3_MONTH("last3", "Last 3 Month"),
    CUSTOM("custom", "Custom"),
    UNKNOWN("unknown", "Unknown");



    private static final long serialVersionUID = 1L;

    private final String code;
    private final String desc;

    DatePreset(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static DatePreset getType(String code) {
        if(code == null){
            return UNKNOWN;
        }
        Optional<DatePreset> typeOpt =  Arrays.stream(DatePreset.values()).filter(ctype -> ctype.getCode().equalsIgnoreCase(code)).findFirst();
        return typeOpt.orElse(UNKNOWN);
    }

}


