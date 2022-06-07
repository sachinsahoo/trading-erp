package com.example.common.exception;

public class DBException extends BaseException {

    private String error;

    private DBException() {
    }

    public DBException(String m) {
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
