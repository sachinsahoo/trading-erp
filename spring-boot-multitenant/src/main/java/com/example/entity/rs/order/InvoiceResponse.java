package com.example.entity.rs.order;

import com.example.entity.dto.order.InvoiceDto;
import com.example.entity.dto.order.PurchaseOrderDto;
import com.example.entity.rs.base.BaseResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InvoiceResponse extends BaseResponse {


    private InvoiceDto invoice;
    private PurchaseOrderDto purchaseOrder;

    public InvoiceDto getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceDto invoice) {
        this.invoice = invoice;
    }

    public PurchaseOrderDto getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrderDto purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }
}
