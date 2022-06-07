package com.example.common.exception;

import com.example.common.constant.ErrorCode;

public class InputValidationException extends BaseException {

    private String error;

    private InputValidationException() {
    }

    public InputValidationException(ErrorCode errorCode) {
        super(errorCode);
        this.error = errorCode.toString();
    }

    public InputValidationException(String m) {
        super(m);
        this.error = m;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
