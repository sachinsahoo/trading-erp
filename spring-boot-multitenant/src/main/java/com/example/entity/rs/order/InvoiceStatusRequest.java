package com.example.entity.rs.order;

import com.example.common.exception.InputValidationException;
import com.example.constant.InvoiceStatus;
import com.example.entity.order.POProduct;
import com.example.entity.rs.base.AbstractBaseRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceStatusRequest extends AbstractBaseRequest {


    private Long invid;
    private String status;
    private Long date;

    public Long getInvid() {
        return invid;
    }

    public void setInvid(Long invid) {
        this.invid = invid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    /**
     * validates for saving to DB
     * @throws InputValidationException
     */
    @Override
    public void validate() throws InputValidationException {

        if (invid == null) {
            throw new InputValidationException("Missing Invoice Id");
        }

        if( status == null || InvoiceStatus.getType(status).equals(InvoiceStatus.UNKNOWN)) {
            throw new InputValidationException("Status can be " + InvoiceStatus.values());
        }

        if(date == null) {
            throw new InputValidationException("date required");
        }

    }

    /**
     *  :TODO validate all atributes before sending PO to Vendor
     *
     * @throws InputValidationException
     */
    public void validateRFQ() throws InputValidationException {






    }

    /**
     *  productid not null
     *
     * @param dto
     * @return
     */
    public void isValidPOProduct(POProduct dto) throws InputValidationException {
        if(dto.getProduct() == null || dto.getProduct().getId() == null){
            throw new InputValidationException("No product present");
        }


    }



}
