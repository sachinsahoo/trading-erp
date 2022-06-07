package com.example.controller.util;


import com.example.common.util.InventoryUtil;
import com.example.controller.service.PurchaseOrderService;
import com.example.entity.order.POPTax;
import com.example.entity.order.POProduct;
import com.example.entity.order.PurchaseOrder;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

public class OrderCalculator {

    @Autowired
    PurchaseOrderService purchaseOrderService;

    /****************************************
     * Calculates
     *  - Total Order Amount
     *  - Commission Payable
     *  - Tax Amount
     *  - Round OFF amount (TODO)
     *
     ************************************/

    public static PurchaseOrder calculateOrder(PurchaseOrder order) throws Exception {

        BigDecimal totalOrderAmount = BigDecimal.ZERO;
        BigDecimal totalCommpayAmount = BigDecimal.ZERO;
        BigDecimal totalTaxAmount = BigDecimal.ZERO;
        if(order.getPoproductlist() == null || order.getPoproductlist().isEmpty()) {
            return order;
        }

        for (POProduct poProduct : order.getPoproductlist()) {
            BigDecimal taxAmt = BigDecimal.ZERO;
            if(poProduct.getTaxes() != null && !poProduct.getTaxes().isEmpty()) {
                for (POPTax tax : poProduct.getTaxes()) {
                    BigDecimal poTax = InventoryUtil.calcTaxOnOrder(poProduct.getPrice(), poProduct.getQuantity(), tax.getPercent());
                    taxAmt = taxAmt.add(poTax);
                }
            }

            // Total Amt & Tax
            totalTaxAmount = totalTaxAmount.add(taxAmt);
            totalOrderAmount = totalOrderAmount.add(poProduct.getPrice().multiply(poProduct.getQuantity())).add(taxAmt);
            totalCommpayAmount = totalCommpayAmount.add(poProduct.getCommpay().multiply(poProduct.getQuantity()));

        }
        order.setTotalamount(totalOrderAmount);
        order.setTaxAmount(totalTaxAmount);
        order.setTotalcommpay(totalCommpayAmount);
        return order;
    }







}
