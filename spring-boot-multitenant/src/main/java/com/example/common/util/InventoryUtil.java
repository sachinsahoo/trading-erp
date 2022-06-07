package com.example.common.util;


import com.example.common.exception.InputValidationException;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class InventoryUtil {

    public static BigDecimal calcProfit(BigDecimal salePrice, BigDecimal costPrice, BigDecimal quantity) {
        return (salePrice.multiply(quantity)).subtract(costPrice.multiply(quantity));

    }

    public static BigDecimal calcTaxOnOrder(BigDecimal price, BigDecimal qty, BigDecimal taxPercent) throws Exception {

        if (price == null || qty == null || taxPercent == null)
            throw new InputValidationException("Calc Tax : Invalid Null Data");

        // Tax Calculation
        BigDecimal taxAmt = (price.multiply(qty).multiply(taxPercent)).divide(new BigDecimal("100"), 3, RoundingMode.CEILING);

        if (taxAmt.compareTo(BigDecimal.ONE) < 1)
            throw new InputValidationException("Tax Error + Price: " + price + " Quantity: " + qty + "Tax Percent " + taxPercent);

        return taxAmt;
    }


}
