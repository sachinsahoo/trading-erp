package com.example.entity.rs.order;

import com.example.common.exception.InputValidationException;
import com.example.constant.OrderType;
import com.example.entity.dto.order.InvoiceDto;
import com.example.entity.dto.order.PurchaseOrderDto;
import com.example.entity.order.POProduct;
import com.example.entity.rs.base.AbstractBaseRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceRequest extends AbstractBaseRequest {


    private InvoiceDto invoice;

    public InvoiceDto getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceDto invoice) {
        this.invoice = invoice;
    }

    /**
     * validates for saving to DB
     * @throws InputValidationException
     */
    @Override
    public void validate() throws InputValidationException {

        if (invoice == null) {
            throw new InputValidationException("No Invoice present");
        }

        if(invoice.getOid() == null) {
            throw new InputValidationException("Order id required");
        }

    }





}
