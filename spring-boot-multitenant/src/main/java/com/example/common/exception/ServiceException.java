package com.example.common.exception;

public class ServiceException extends BaseException {

    private String error;

    private ServiceException() {
    }

    public ServiceException(String m) {
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
