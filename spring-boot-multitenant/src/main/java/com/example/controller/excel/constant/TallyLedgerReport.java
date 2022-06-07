package com.example.controller.excel.constant;

import java.util.Arrays;
import java.util.Optional;

public enum TallyLedgerReport {

    ASSET(10000, "Assets"),
    LIABILITY(30000, "Equity and Liabilities"),
    INCOME(50000, "Income"),
    EXPENSE(70000, "Expense");
    
    private static final long serialVersionUID = 1L;

    private Integer col = 0;
    private final String field;

    TallyLedgerReport(Integer col, String type) {
        this.col = col;
        this.field = type;
    }

    public String getField() {
        return field;
    }

    public Integer getCol() {
        return col;
    }

    public static Optional<TallyLedgerReport> getType(String type) {
        return Arrays.stream(TallyLedgerReport.values()).filter(ctype -> ctype.getField().equalsIgnoreCase(type)).findFirst();
    }

    public static String getTypeString(String type) {
        Optional<TallyLedgerReport> typeOptional = getType(type);
        String strType = typeOptional.isPresent() ? typeOptional.get().getField() : "";
        return strType;
    }
}


