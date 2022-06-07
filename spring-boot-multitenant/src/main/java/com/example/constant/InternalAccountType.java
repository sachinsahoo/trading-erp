package com.example.constant;

import java.util.Arrays;
import java.util.Optional;

public enum InternalAccountType {


    //---------------
    //  ASSET 10000
    //---------------

    BANK(14201, "Bank", AcGroupNode.BANK_ACCOUNTS),
    CASH(14300, "Cash", AcGroupNode.CAPITAL_ACCOUNTS),

    //---------------
    //  LIABILITY 30000
    //---------------
    CAPITAL(31100, "Share Capital/Proprietor's Capital/Partner's Capital", AcGroupNode.CAPITAL_ACCOUNTS),
    RESERVES_SURPLUS(31200, "Reserves and Surplus", AcGroupNode.RESERVES_SURPLUS),

    SECURED_LOAN(32100, "Secured Loan", AcGroupNode.CURRENT_LIABILITIES),
    UNSECURED_LOAN(32200, "Unsecured Loan", AcGroupNode.CURRENT_LIABILITIES),
    BANK_OVERDRAFT(32300, "Bank Overdraft Account", AcGroupNode.CURRENT_LIABILITIES),


    DUTIES_TAXES(33200, "Duties & Taxes", AcGroupNode.CURRENT_LIABILITIES),
    PROVISIONS(33400, "Provisions", AcGroupNode.CURRENT_LIABILITIES),

    //---------------
    //  INCOME 50000
    //---------------
    SALE_LOCAL(51101, "Sale Local", AcGroupNode.SALES_ACCOUNTS),
    SALE_OUTSIDE(51102, "Sale Outside", AcGroupNode.SALES_ACCOUNTS),
    SALE_OF_SERVICES(51200, "Sale of Services", AcGroupNode.SALES_ACCOUNTS),


    RENT_INCOME(53100, "Rent Income", AcGroupNode.INDIRECT_INCOME),
    INTEREST_INCOME(53200, "Interest Income", AcGroupNode.INDIRECT_INCOME),
    DIVIDEND_INCOME(53300, "Dividend Income", AcGroupNode.INDIRECT_INCOME),
    GAIN_LOSS_ON_SALE_OF_INVESTMENT(53400, "Gain/Loss on Sale of Investments", AcGroupNode.INDIRECT_INCOME),
    OTHER_NON_OPERATING_REVENUES(53500, "Other Non-Operating Revenues", AcGroupNode.INDIRECT_INCOME),

    //---------------
    // EXPENSES 70000
    //---------------
    PURCHASE_RAW_MATERIAL(71100, "Purchase of Raw Materials", AcGroupNode.PURCHASE_ACCOUNTS),
    PURCHASE_LOCAL(71301, "Purchase Goods Local",  AcGroupNode.PURCHASE_ACCOUNTS),
    PURCHASE_OUTSIDE(71302, "Purchase Goods Outside",  AcGroupNode.PURCHASE_ACCOUNTS),
    PURCHASE_SERVICES(71400, "Purchase of Services", AcGroupNode.PURCHASE_ACCOUNTS),
    PURCHASE_OTHER(71500, "Other Purchases", AcGroupNode.PURCHASE_ACCOUNTS),

    POWER_FUEL(72100, "Power and Fuel",  AcGroupNode.DIRECT_EXPENSES),
    TRANSPORTING_CHARGES(72200, "Transporting Charges",  AcGroupNode.DIRECT_EXPENSES),
    LOADING_UNLOADING_CHARGES(72300, "Loading / Unloading Charges",  AcGroupNode.DIRECT_EXPENSES),
    SALARY_WAGES_PLANT(72400, "Salary & Wages - Plant", AcGroupNode.DIRECT_EXPENSES),
    REPAIRS_MAINTAINENCE(72500, "Repairs and Maintenance", AcGroupNode.DIRECT_EXPENSES),

    SALARY_OFFICE(73100, "Salary - Office", AcGroupNode.INDIRECT_EXPENSES),
    INTEREST_EXPENSES(73200, "Interest Expenses", AcGroupNode.INDIRECT_EXPENSES),
    DEPRICIATION(73300, "Depreciation", AcGroupNode.INDIRECT_EXPENSES),
    RATE_FEES_TAXES(73400, "Rate, Fees & Taxes", AcGroupNode.INDIRECT_EXPENSES),
    LEGAL_PROFESSIONAL_EXPENSES(73500, "Legal and Professional Expenses", AcGroupNode.INDIRECT_EXPENSES),
    SECURITY_EXPENSES(73600, "Security Expenses", AcGroupNode.INDIRECT_EXPENSES),
    TELEPHONE_INTERNET_EXPENSES(73700, "Telephone and Internet Expenses", AcGroupNode.INDIRECT_EXPENSES),
    PRINTING_STATIONARY_EXPENSES(73800, "Printing and Stationery Expenses", AcGroupNode.INDIRECT_EXPENSES),
    LIASIONING_EXPENSES(73900, "Liasioning Expensese", AcGroupNode.INDIRECT_EXPENSES),
    UNKNOWN(0, "Unknown", AcGroupNode.ROOT);


    private static final long serialVersionUID = 1L;

    private Integer code = 0;
    private final String desc;
    private AcGroupNode group;

    InternalAccountType(Integer code, String desc, AcGroupNode group) {
        this.code = code;
        this.desc = desc;
        this.group = group;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getCode() {
        return code;
    }

    public AcGroupNode getGroup() {
        return group;
    }

    public static InternalAccountType getType(Integer code) {
        Optional<InternalAccountType> optAcType =  Arrays.stream(InternalAccountType.values()).filter(ctype -> ctype.getCode().equals(code)).findFirst();
        if(optAcType.isPresent())
            return optAcType.get();
        else
            return UNKNOWN;
    }


}
