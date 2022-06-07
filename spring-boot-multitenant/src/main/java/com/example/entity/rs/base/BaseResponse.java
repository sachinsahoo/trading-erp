package com.example.entity.rs.base;

import com.example.constant.RsStatusType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public  class BaseResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    String errorCode;
    String errorMessage;
    RsStatusType status;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public RsStatusType getStatus() {
        return status;
    }

    public void setStatus(RsStatusType status) {
        this.status = status;
    }
}
