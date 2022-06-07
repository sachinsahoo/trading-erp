package com.example.constant;

import com.example.entity.acct.AccountGroup;
import com.example.entity.dto.acct.AccountGroupDto;
import com.example.entity.order.Company;

import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public enum AcGroupNode {


    ROOT(100, "Root", null, 0, null),

    // Groups
    CURRENT_ASSETS(11000, "Current Assets", AcGroupNode.ROOT, 1, AccountType.ASSET),
    SUSPENSE_ACCOUNT(12000, "Suspense Account", AcGroupNode.ROOT, 1, AccountType.ASSET),
    FIXED_ASSETS(13000, "Fixed Assets", AcGroupNode.ROOT, 1, AccountType.ASSET),
    INVESTMENTS(14000, "Investments", AcGroupNode.ROOT, 1, AccountType.ASSET),

    CAPITAL_ACCOUNTS(31000, "Capital Accounts", AcGroupNode.ROOT, 1, AccountType.LIABILITY),
    LOANS(32000, "Loans (Liability)", AcGroupNode.ROOT, 1, AccountType.LIABILITY),
    CURRENT_LIABILITIES(33000, "Current Liabilities", AcGroupNode.ROOT, 1, AccountType.LIABILITY),

    SALES_ACCOUNTS(51000, "Sales Accounts", AcGroupNode.ROOT, 1, AccountType.INCOME),
    INDIRECT_INCOME(52000, "Indirect Income", AcGroupNode.ROOT, 1, AccountType.INCOME),
    DIRECT_INCOME(53000, "Direct Income", AcGroupNode.ROOT, 1, AccountType.INCOME),

    INDIRECT_EXPENSES(71000, "Indirect Expenses", AcGroupNode.ROOT, 1, AccountType.EXPENSE),
    MISC_EXPENSES(72000, "Misc. Expenses", AcGroupNode.ROOT, 1, AccountType.EXPENSE),
    PURCHASE_ACCOUNTS(73000, "Purchase Accounts", AcGroupNode.ROOT, 1, AccountType.EXPENSE),
    DIRECT_EXPENSES(74000, "Direct Expenses", AcGroupNode.ROOT, 1, AccountType.EXPENSE),



    // Sub groups
    STOCK_IN_HAND(11100, "Stock in Hand", AcGroupNode.CURRENT_ASSETS, 2, AccountType.ASSET),
    SUNDRY_DEBTORS(11200, "Sundry Debtors", AcGroupNode.CURRENT_ASSETS, 2, AccountType.ASSET),
    BANK_ACCOUNTS(11300, "Bank Accounts", AcGroupNode.CURRENT_ASSETS, 2, AccountType.ASSET),
    CASH_IN_HAND(11400, "Cash in Hand", AcGroupNode.CURRENT_ASSETS, 2, AccountType.ASSET),
    DEPOSITS(14100, "Deposits", AcGroupNode.INVESTMENTS, 2, AccountType.ASSET),
    LOAN_ADVANCES(14200, "Loans & Advances (Asset)", AcGroupNode.INVESTMENTS, 2, AccountType.ASSET),

    SUNDRY_CREDITORS(11300, "Sundry Creditors", AcGroupNode.CURRENT_LIABILITIES, 2, AccountType.LIABILITY),
    DUTIES_TAXES(11300, "Duties & Taxes", AcGroupNode.CURRENT_LIABILITIES, 2, AccountType.LIABILITY),
    PROVISIONS(11300, "Provisions", AcGroupNode.CURRENT_LIABILITIES, 2, AccountType.LIABILITY),
    UNSECURED_LOAN(11300, "Unsecured Loans", AcGroupNode.LOANS, 2, AccountType.LIABILITY),
    SECURED_LOANS(11300, "Secured Loans", AcGroupNode.LOANS, 2, AccountType.LIABILITY),
    BANK_OD_ACCOUNTS(11300, "Banks OD Accounts", AcGroupNode.LOANS, 2, AccountType.LIABILITY),
    RESERVES_SURPLUS(11300, "Reserves & Surplus", AcGroupNode.CAPITAL_ACCOUNTS, 2, AccountType.LIABILITY);



    private static final long serialVersionUID = 1L;

    private Integer code = 0;
    private final String desc;
    private AcGroupNode parent;
    private int level;
    private AccountType type;

    AcGroupNode(Integer code, String desc, AcGroupNode parent, int level, AccountType type) {
        this.code = code;
        this.desc = desc;
        this.parent = parent;
        this.level = level;
        this.type = type;
    }

    public int getLevel() {
        return level;
    }

    public AccountType getAccountType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getCode() {
        return code;
    }

    public AcGroupNode getParent() {
        return parent;
    }

    public void setParent(AcGroupNode parent) {
        this.parent = parent;
    }

    public static AcGroupNode get(Integer code) {
        Optional<AcGroupNode> optGroup =  Arrays.stream(AcGroupNode.values()).filter(ctype -> ctype.getCode().equals(code)).findFirst();
        if(optGroup.isPresent())
            return optGroup.get();
        else
            return ROOT;
    }

    public static AcGroupNode get(String name) {
        Optional<AcGroupNode> optGroup =  Arrays.stream(AcGroupNode.values()).filter(ctype -> ctype.getDesc().equalsIgnoreCase(name.trim())).findFirst();
        if(optGroup.isPresent())
            return optGroup.get();
        else
            return ROOT;
    }



    /*
     TODO: Add Transport
     */
    public static AcGroupNode getClientAccountGroupType(Company client) {
        CompanyType companyType = CompanyType.getType(client.getType());
        if(CompanyType.CUSTOMER.equals(companyType)) {
            return SUNDRY_DEBTORS;
        } else if(CompanyType.SUPPLIER.equals(companyType) || CompanyType.AGENT.equals(companyType)) {
            return SUNDRY_CREDITORS;
        } else {
            return ROOT;
        }
    }



}
