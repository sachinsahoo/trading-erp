package com.example.entity.rs.order;

import com.example.common.exception.InputValidationException;
import com.example.constant.OrderStatus;
import com.example.entity.rs.base.AbstractBaseRequest;
import com.example.entity.order.POProduct;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PurchaseOrderStatusRequest extends AbstractBaseRequest {


    private Long oid;
    private String status;
    private Long date;


    public Long getOid() {
        return oid;
    }

    public void setOid(Long oid) {
        this.oid = oid;
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

        if (oid == null) {
            throw new InputValidationException("Missing poid");
        }

        if( status == null || OrderStatus.getType(status).equals(OrderStatus.UNKNOWN)) {
            throw new InputValidationException("Status can be " + OrderStatus.values());
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
