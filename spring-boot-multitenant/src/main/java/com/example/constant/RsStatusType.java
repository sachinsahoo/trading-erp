package com.example.constant;

public enum RsStatusType {

    SUCCESS("SUCCESS"), FAILURE("FAILURE");
    private static final long serialVersionUID = 1L;

    private final String status;

    RsStatusType(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
