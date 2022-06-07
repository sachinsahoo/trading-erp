package com.example.controller.service;

import com.example.controller.service.base.BaseDaoService;

import com.example.entity.dto.acct.StockValue;
import com.example.entity.dto.order.POProductDto;
import com.example.entity.dto.order.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
public class InventoryMathService extends BaseDaoService {

    @Autowired
    HttpSession session;

    @Autowired
    PurchaseOrderService purchaseOrderService;

    public InventoryMathService() {
    }

    public BigDecimal calcInventoryValueFIFO(POProductDto inputPOProduct) throws Exception{

        List<POProductDto> popPurchasesRecvd = new ArrayList<>();//purchaseOrderService.getAllPurchases();
        List<POProductDto> popPurchasesConfirm = new ArrayList<>();//purchaseOrderService.getAllPurchases();
        List<POProductDto> popPurchases = new ArrayList<>();//purchaseOrderService.getAllPurchases();

        popPurchases.addAll(popPurchasesRecvd);
        popPurchases.addAll(popPurchasesConfirm);

        //updateStock()
        ProductDto productDto = new ProductDto();

        //sort by date descending
        List<StockValue> productStockValues = new ArrayList<>();
        BigDecimal availableStock = productDto.getQuantity();

        // Generate FIFO pricing Slab
        productStockValues.addAll(getStockValue(popPurchasesRecvd, productDto.getQuantity()));
        productStockValues.addAll(getStockValue(popPurchasesConfirm, productDto.getPendingarrival()));
        productStockValues.add(new StockValue(BigDecimal.valueOf(Integer.MAX_VALUE), productDto.getCostPrice()));

        //Calculate cost for Quantity
        BigDecimal cost = calcualteCost(productStockValues, inputPOProduct);
        return cost;

    }

    public List<StockValue> getStockValue(List<POProductDto> popPurchases, BigDecimal stockQty) {

        BigDecimal availableStock = stockQty;
        List<StockValue> productStockValues = new ArrayList<>();
        // Make FIFO Stock Value List
        if(! popPurchases.isEmpty()) {
            StockValue sval;
            for (int i = 0; i < popPurchases.size() || availableStock.compareTo(BigDecimal.ZERO) > 0; i++) {
                POProductDto purchase = popPurchases.get(i);
                if (availableStock.compareTo(purchase.getQuantity()) > 0) {
                    sval = new StockValue(purchase.getQuantity(), purchase.getPrice());
                    availableStock = availableStock.subtract(purchase.getQuantity());
                } else {
                    sval = new StockValue(availableStock, purchase.getPrice());
                    availableStock = BigDecimal.ZERO;
                }
                productStockValues.add(sval);
            }
            // Remaining stock valued as Lost Purchase
            if(availableStock.compareTo(BigDecimal.ZERO) > 0) {
                // Log advance order costing (todo Verify)
                sval = new StockValue(availableStock, popPurchases.get(0).getCostPrice());
                productStockValues.add(sval);
            }
        }
        Collections.reverse(productStockValues);
        return productStockValues;

    }

    public BigDecimal calcualteCost(List<StockValue> productStockValues, POProductDto inputPOProduct) {
        BigDecimal remQty = inputPOProduct.getQuantity();
        BigDecimal costValue = BigDecimal.ZERO;

        if(! productStockValues.isEmpty()) {
            for (int i = 0; i < productStockValues.size() || remQty.compareTo(BigDecimal.ZERO) > 0; i++) {
                StockValue stockValue = productStockValues.get(i);
                if (remQty.compareTo(stockValue.getQty()) > 0) {
                    costValue = costValue.add(stockValue.getQty().multiply(stockValue.getPrice()));
                    remQty = remQty.subtract(stockValue.getQty());
                } else {
                    costValue = costValue.add(remQty).multiply(stockValue.getPrice());
                    remQty = BigDecimal.ZERO;
                }
            }
        }
        return costValue;
    }



}

