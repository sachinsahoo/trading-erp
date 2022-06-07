package com.example.entity.rs.order;

import com.example.common.exception.InputValidationException;
import com.example.constant.OrderType;
import com.example.entity.rs.base.AbstractBaseRequest;
import com.example.entity.order.POProduct;
import com.example.entity.dto.order.PurchaseOrderDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PurchaseOrderRequest extends AbstractBaseRequest {


    private PurchaseOrderDto purchaseOrder;


    public PurchaseOrderDto getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrderDto purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }


    /**
     * validates for saving to DB
     * @throws InputValidationException
     */
    @Override
    public void validate() throws InputValidationException {

        if (purchaseOrder == null) {
            throw new InputValidationException("No purchase order present");
        }

        if(purchaseOrder.getCustomerid() == null) {
            throw new InputValidationException("Customer Company id required");
        }

        if(purchaseOrder.getSupplierid() == null) {
            throw new InputValidationException("Supplier Company id required");
        }

        if(purchaseOrder.getType() == null || OrderType.getType(purchaseOrder.getType()).equals("")) {
            throw new InputValidationException("Order type can be: purchase or sale");
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
