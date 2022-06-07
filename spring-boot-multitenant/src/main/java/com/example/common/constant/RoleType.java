package com.example.common.constant;

import org.apache.commons.lang3.StringUtils;

public enum RoleType {

    USER("user"),
    ADMIN("admin"),
    SUPERADMIN("super");

    private String code;

    private RoleType(String code) {
        this.code = code;
    }

    public static RoleType get(String role){
        if(StringUtils.isEmpty(role)) {
            return USER;
        }
        role = role.toLowerCase();

        if(role.contains(SUPERADMIN.code)){
            return SUPERADMIN;
        } else if(role.contains(ADMIN.code)) {
            return ADMIN;
        } else {
            return USER;
        }

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
