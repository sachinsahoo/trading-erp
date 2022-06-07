package com.example.constant;

import com.example.common.exception.InputValidationException;
import com.example.entity.order.PurchaseOrder;

import java.util.Arrays;
import java.util.Optional;

public enum InvTransactionType {

    PURCHASE(100,"Purchase", "Purchase Invoice"),
    PURCHASE_ADVANCE(101, "PurchaseAdvance", "Purchase Advance"),
    SALE(200,"Sale", "Sale Invoice"),
    SALE_ADVANCE(201, "SaleAdvance", "Sale Advance"),
    PAYMENT(400, "Payment", "Payment"),
    RECEIPT(500,"Receipt", "Receipt"),
    COMM_PAYABLE(600,"Commpayable", "Commission Payable"),
    COMM_PAID(700,"Commpaid", "Commision Paid"),
    COMM_RECEIVABLE(800,"Commreceivable", "Commission Receivable"),
    COMM_RECEIVED(900,"Commreceived", "Commission Received"),

    OPENING_BALANCE(1000, "openingbal", "Opening Balance"),
    JOURNAL(1100, "Journal", "Journal"),
    UNKNOWN(0,"unknown", "Unknown");

    private static final long serialVersionUID = 1L;

    private Integer code;
    private final String type;
    private final String desc;

    InvTransactionType(Integer code, String type, String desc) {
        this.code = code;
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static InvTransactionType getType(String type) {
        Optional<InvTransactionType> transactionType =  Arrays.stream(InvTransactionType.values()).filter(ctype -> ctype.getType().equalsIgnoreCase(type)).findFirst();
        if(!transactionType.isPresent()) {
            return UNKNOWN;
        } else
            return transactionType.get();
    }

    public static InvTransactionType getType(Integer code) {
        Optional<InvTransactionType> transactionType =  Arrays.stream(InvTransactionType.values()).filter(ctype -> ctype.getCode().equals(code)).findFirst();
        if(!transactionType.isPresent()) {
            return UNKNOWN;
        } else
            return transactionType.get();
    }

    public static String getTypeString(Integer code) {
        return getType(code).type;
    }

    public static Integer getTypeCode(String type) {
        return getType(type).code;
    }



    public static String getTypeString(String type) throws Exception {
        return getType(type).type;
    }

}


