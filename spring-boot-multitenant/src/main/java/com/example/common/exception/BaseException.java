package com.example.common.exception;

import com.example.common.constant.ErrorCode;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class BaseException extends Exception{
    private static final long serialVersionUID = 1L;

    ErrorCode errorCode;

    protected BaseException() {
        super();
    }

    public BaseException(String m) {
        super(m);
    }

    public BaseException(ErrorCode errorCode) {
        super(errorCode.toString());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
