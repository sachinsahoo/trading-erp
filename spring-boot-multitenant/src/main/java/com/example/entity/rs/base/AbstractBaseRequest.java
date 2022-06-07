package com.example.entity.rs.base;

import com.example.common.exception.BaseException;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class AbstractBaseRequest implements Serializable {
    private static final long serialVersionUID = 1L;


    public abstract void validate() throws BaseException;

}
