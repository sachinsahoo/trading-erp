package com.example.entity.rs;

import com.example.common.exception.InputValidationException;
import com.example.entity.dto.CRMUserDto;
import com.example.entity.rs.base.AbstractBaseRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.StringUtils;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EntityRequest extends AbstractBaseRequest {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public void validate() throws InputValidationException {
        if(id == null ) {
           throw new InputValidationException("Please provide Entity id");
        }


    }

}
