//package com.example.constant;
//
//import com.example.entity.order.Company;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//public enum AcGroup {
//
//
//    //---------------
//    //  ASSET 10000
//    //---------------
//    TANGIBLE_ASSETS(11100, "Tangible Assets", AcCategory.PROPERTY),
//    INTANGIBLE_ASSETS(11200, "Intangible Assets", AcCategory.PROPERTY),
//    CAPITAL_WIP(11300, "Capital Work-in-Progress", AcCategory.PROPERTY),
//    INTANGIBLE_ASSETS_UNDER_DEV(11400, "Intangible Assets under development", AcCategory.PROPERTY),
//
//    INVESTMENT_IN_EQUITY(12100, "Investment in Equity Instruments", AcCategory.INVESTMENTS),
//    INVESTMENT_IN_PARTNERSHIP(12200, "Investment in Partnership Firms", AcCategory.INVESTMENTS),
//    INVESTMENT_IN_MUTUAL_FUNDS(12300, "Investment in Mutual Fund", AcCategory.INVESTMENTS),
//    OTHER_INVESTMENTS(12400, "Other Investments", AcCategory.INVESTMENTS),
//
//    CAPITAL_ADVANCES(13100, "Capital Advances", AcCategory.LOANS_ADVANCES),
//    SECURITY_DEPOSITS(13200, "Security Deposits", AcCategory.LOANS_ADVANCES),
//    LOANS_ADVANCES_TO_RELATED_PARTIES(13300, "Loans and Advances to Related Parties", AcCategory.LOANS_ADVANCES),
//    OTHER_LOANS_ADVANCES(13400, "Other Loans and Advances", AcCategory.LOANS_ADVANCES),
//
//    TRADE_RECEIVABLES(14100, "Trade Receivables", AcCategory.CURRENT_ASSETS),
//    BANK_BALANCES(14200, "Bank Balances", AcCategory.CURRENT_ASSETS),
//    CASH_IN_HAND(14300, "Cash in hand", AcCategory.CURRENT_ASSETS),
//    INVENTORIES(14400, "Inventories", AcCategory.CURRENT_ASSETS),
//    OTHER_CURRENT_ASSETS(14500, "Other Current Assets", AcCategory.CURRENT_ASSETS),
//
//    //---------------
//    //  LIABILITY 20000
//    //---------------
//    CAPITAL(31100, "Share Capital/Proprietor's Capital/Partner's Capital", AcCategory.CAPITAL),
//    RESERVES_SURPLUS(31200, "Reserves and Surplus", AcCategory.CAPITAL),
//
//    SECURED_LOAN(32100, "Secured Loan", AcCategory.LOAN_BORROWINGS),
//    UNSECURED_LOAN(32200, "Unsecured Loan", AcCategory.LOAN_BORROWINGS),
//    BANK_OVERDRAFT(32300, "Bank Overdraft Account", AcCategory.LOAN_BORROWINGS),
//
//    TRADE_PAYABLES(33100, "Trade Payables", AcCategory.CURRENT_LIABILITIES),
//    DUTIES_TAXES(33200, "Duties & Taxes", AcCategory.CURRENT_LIABILITIES),
//    OTHER_CURRENT_LIABILITIES(33300, "Other Current Liabilities", AcCategory.CURRENT_LIABILITIES),
//    PROVISIONS(33400, "Provisions", AcCategory.CURRENT_LIABILITIES),
//
//    //---------------
//    //  INCOME 10000
//    //---------------
//    SALE_OF_GOODS(51100, "Sale of Goods", AcCategory.SALES),
//    SALE_OF_SERVICES(51200, "Sale of Goods", AcCategory.SALES),
//
//    COMMISSION(52100, "Other Operating revenues", AcCategory.DIRECT_INCOME),
//    OTHER_OPERATING_REVENUES(52200, "Other Operating revenues", AcCategory.DIRECT_INCOME),
//
//    RENT_INCOME(53100, "Rent Income", AcCategory.INDIRECT_INCOME),
//    INTEREST_INCOME(53200, "Interest Income", AcCategory.INDIRECT_INCOME),
//    DIVIDEND_INCOME(53300, "Dividend Income", AcCategory.INDIRECT_INCOME),
//    GAIN_LOSS_ON_SALE_OF_INVESTMENT(53400, "Gain/Loss on Sale of Investments", AcCategory.INDIRECT_INCOME),
//    OTHER_NON_OPERATING_REVENUES(53500, "Other Non-Operating Revenues", AcCategory.INDIRECT_INCOME),
//
//    CS_RAW_MATERIAL(54100, "CS Raw Material", AcCategory.CLOSING_STOCK),
//    CS_WORK_IN_PROGRESS(54200, "CS Work-in-Progress", AcCategory.CLOSING_STOCK),
//    CS_FINISHED_GOODS(54300, "CS Finished Goods", AcCategory.CLOSING_STOCK),
//    CS_TRADED_GOODS(54400, "CS Traded Goods", AcCategory.CLOSING_STOCK),
//
//    //---------------
//    // EXPENSES 2000
//    //---------------
//    OS_RAW_MATERIAL(74100, "OS Raw Material", AcCategory.OPENING_STOCK),
//    OS_WORK_IN_PROGRESS(74200, "OS Work-in-Progress", AcCategory.OPENING_STOCK),
//    OS_FINISHED_GOODS(74300, "OS Finished Goods", AcCategory.OPENING_STOCK),
//    OS_TRADED_GOODS(74400, "OS Traded Goods", AcCategory.OPENING_STOCK),
//
//    PURCHASE_RAW_MATERIAL(71100, "Purchase of Raw Materials", AcCategory.PURCHASE),
//    PURCHASE_STORES_CONSUMABLES(71200, "Purchase of Stores and Consumables", AcCategory.PURCHASE),
//    PURCHASE_TRADED_GOODS(71300, "Purchase of Traded Goods", AcCategory.PURCHASE),
//    PURCHASE_SERVICES(71400, "Purchase of Services", AcCategory.PURCHASE),
//    PURCHASE_OTHER(71500, "Other Purchases", AcCategory.PURCHASE),
//
//    POWER_FUEL(72100, "Power and Fuel", AcCategory.DIRECT_EXPENSES),
//    TRANSPORTING_CHARGES(72200, "Transporting Charges", AcCategory.DIRECT_EXPENSES),
//    LOADING_UNLOADING_CHARGES(72300, "Loading / Unloading Charges", AcCategory.DIRECT_EXPENSES),
//    SALARY_WAGES_PLANT(72400, "Salary & Wages - Plant", AcCategory.DIRECT_EXPENSES),
//    REPAIRS_MAINTAINENCE(72500, "Repairs and Maintenance", AcCategory.DIRECT_EXPENSES),
//
//    SALARY_OFFICE(73100, "Salary - Office", AcCategory.INDIRECT_EXPENSES),
//    INTEREST_EXPENSES(73200, "Interest Expenses", AcCategory.INDIRECT_EXPENSES),
//    DEPRICIATION(73300, "Depreciation", AcCategory.INDIRECT_EXPENSES),
//    RATE_FEES_TAXES(73400, "Rate, Fees & Taxes", AcCategory.INDIRECT_EXPENSES),
//    LEGAL_PROFESSIONAL_EXPENSES(73500, "Legal and Professional Expenses", AcCategory.INDIRECT_EXPENSES),
//    SECURITY_EXPENSES(73600, "Security Expenses", AcCategory.INDIRECT_EXPENSES),
//    TELEPHONE_INTERNET_EXPENSES(73700, "Telephone and Internet Expenses", AcCategory.INDIRECT_EXPENSES),
//    PRINTING_STATIONARY_EXPENSES(73800, "Printing and Stationery Expenses", AcCategory.INDIRECT_EXPENSES),
//    LIASIONING_EXPENSES(73900, "Liasioning Expensese", AcCategory.INDIRECT_EXPENSES),
//    UNKNOWN(0, "UNKNOWN", AcCategory.INDIRECT_EXPENSES);
//
//
//    private static final long serialVersionUID = 1L;
//
//    private Integer code = 0;
//    private final String name;
//    private AcCategory category;
//
//    AcGroup(Integer code, String name, AcCategory category) {
//        this.code = code;
//        this.name = name;
//        this.category = category;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public Integer getCode() {
//        return code;
//    }
//
//    public AcCategory getCategory() {
//        return category;
//    }
//
//    public static AcGroup getType(Integer code) {
//        Optional<AcGroup> optGroup =  Arrays.stream(AcGroup.values()).filter(ctype -> ctype.getCode().equals(code)).findFirst();
//        if(optGroup.isPresent())
//            return optGroup.get();
//        else
//            return UNKNOWN;
//    }
//
//    public static List<AcGroup> getByAcctCategory(AcCategory category) {
//        return Arrays.stream(AcGroup.values()).filter(ctype -> ctype.getCategory().equals(category)).collect(Collectors.toList());
//    }
//
//    public static AcGroup getClientAccountGroupType(Company client) {
//        CompanyType companyType = CompanyType.getType(client.getType());
//        if(CompanyType.CUSTOMER.equals(companyType)) {
//            return TRADE_RECEIVABLES;
//        } else if(CompanyType.SUPPLIER.equals(companyType) || CompanyType.AGENT.equals(companyType)) {
//            return TRADE_PAYABLES;
//        } else {
//            return UNKNOWN;
//        }
//
//    }
//
//}
