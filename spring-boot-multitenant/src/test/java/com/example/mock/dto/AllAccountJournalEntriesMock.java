package com.example.mock.dto;

import com.example.constant.InternalAccountType;
import com.example.entity.dto.order.CompanyDto;
import com.example.manager.dto.TestJournalTransaction;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class AllAccountJournalEntriesMock {
    public static Supplier<List<TestJournalTransaction>> mockAllAccount =
            new Supplier<List<TestJournalTransaction>>() {
                @Override
                public List<TestJournalTransaction> get() {

                    List<TestJournalTransaction> resultList = new ArrayList<>();
                    //liabilities
                    resultList.add(new TestJournalTransaction("Capital", "1000", InternalAccountType.BANK, InternalAccountType.CAPITAL));
                    resultList.add(new TestJournalTransaction("Reserves and Surplus", "1000", InternalAccountType.BANK, InternalAccountType.RESERVES_SURPLUS));
                    resultList.add(new TestJournalTransaction("Secured Loan", "1000", InternalAccountType.BANK, InternalAccountType.SECURED_LOAN));
                    resultList.add(new TestJournalTransaction("Unsecured Loan", "1000", InternalAccountType.BANK, InternalAccountType.UNSECURED_LOAN));
                    resultList.add(new TestJournalTransaction("Bank Overdraft Account", "1000", InternalAccountType.BANK, InternalAccountType.BANK_OVERDRAFT));
                    resultList.add(new TestJournalTransaction("Duties and Taxes", "1000", InternalAccountType.BANK, InternalAccountType.DUTIES_TAXES));
                    resultList.add(new TestJournalTransaction("Provisions", "1000", InternalAccountType.BANK, InternalAccountType.PROVISIONS));
                    //income
                    resultList.add(new TestJournalTransaction("Sale Local", "1000", InternalAccountType.BANK, InternalAccountType.SALE_LOCAL));
                    resultList.add(new TestJournalTransaction("Sale Outside", "1000", InternalAccountType.BANK, InternalAccountType.SALE_OUTSIDE));
                    resultList.add(new TestJournalTransaction("Sale of Services", "1000", InternalAccountType.CASH, InternalAccountType.SALE_OF_SERVICES));
                    resultList.add(new TestJournalTransaction("Rent Income", "1000", InternalAccountType.CASH, InternalAccountType.RENT_INCOME));
                    resultList.add(new TestJournalTransaction("Interest Income", "1000", InternalAccountType.BANK, InternalAccountType.INTEREST_INCOME));
                    resultList.add(new TestJournalTransaction("Dividend Income", "1000", InternalAccountType.BANK, InternalAccountType.DIVIDEND_INCOME));
                    resultList.add(new TestJournalTransaction("Gain/Loss on Sale of Investments", "1000", InternalAccountType.BANK, InternalAccountType.GAIN_LOSS_ON_SALE_OF_INVESTMENT));
                    resultList.add(new TestJournalTransaction("Other Non-Operating Revenues", "1000", InternalAccountType.BANK, InternalAccountType.OTHER_NON_OPERATING_REVENUES));
                    //expenses
                    resultList.add(new TestJournalTransaction("Purchase Raw Material", "1000", InternalAccountType.PURCHASE_RAW_MATERIAL, InternalAccountType.BANK));
                    resultList.add(new TestJournalTransaction("Purchase Local", "1000", InternalAccountType.PURCHASE_LOCAL, InternalAccountType.CASH));
                    resultList.add(new TestJournalTransaction("Purchase Outside", "1000", InternalAccountType.PURCHASE_OUTSIDE, InternalAccountType.BANK));
                    resultList.add(new TestJournalTransaction("Purchase Services", "1000", InternalAccountType.PURCHASE_SERVICES, InternalAccountType.BANK));
                    resultList.add(new TestJournalTransaction("Purchase Other", "1000", InternalAccountType.PURCHASE_OTHER, InternalAccountType.BANK));
                    resultList.add(new TestJournalTransaction("Power Fuel", "1000", InternalAccountType.POWER_FUEL, InternalAccountType.BANK));
                    resultList.add(new TestJournalTransaction("Transporting Charges", "1000", InternalAccountType.TRANSPORTING_CHARGES, InternalAccountType.BANK));
                    resultList.add(new TestJournalTransaction("Loading and Unloading Charges", "1000", InternalAccountType.LOADING_UNLOADING_CHARGES, InternalAccountType.CASH));
                    resultList.add(new TestJournalTransaction("Salary and Wages - Plant", "1000", InternalAccountType.SALARY_WAGES_PLANT, InternalAccountType.BANK));
                    resultList.add(new TestJournalTransaction("Repairs and Maintenance", "1000", InternalAccountType.REPAIRS_MAINTAINENCE, InternalAccountType.BANK));
                    resultList.add(new TestJournalTransaction("Salary - Office", "1000", InternalAccountType.SALARY_OFFICE, InternalAccountType.BANK));
                    resultList.add(new TestJournalTransaction("Interest Expenses", "1000", InternalAccountType.INTEREST_EXPENSES, InternalAccountType.BANK));
                    resultList.add(new TestJournalTransaction("Depreciation", "1000", InternalAccountType.DEPRICIATION, InternalAccountType.BANK));
                    resultList.add(new TestJournalTransaction("Rate, Fees, and Taxes", "1000", InternalAccountType.RATE_FEES_TAXES, InternalAccountType.BANK));
                    resultList.add(new TestJournalTransaction("Legal and Professional Expenses", "1000", InternalAccountType.LEGAL_PROFESSIONAL_EXPENSES, InternalAccountType.BANK));
                    resultList.add(new TestJournalTransaction("Security Expenses", "1000", InternalAccountType.SECURITY_EXPENSES, InternalAccountType.BANK));
                    resultList.add(new TestJournalTransaction("Telephone and Internet Expenses", "1000", InternalAccountType.TELEPHONE_INTERNET_EXPENSES, InternalAccountType.BANK));
                    resultList.add(new TestJournalTransaction("Printing and Stationary Expenses", "1000", InternalAccountType.PRINTING_STATIONARY_EXPENSES, InternalAccountType.BANK));
                    resultList.add(new TestJournalTransaction("Liasioning Expenses", "1000", InternalAccountType.LIASIONING_EXPENSES, InternalAccountType.BANK));
                    resultList.add(new TestJournalTransaction("Unknown", "1000", InternalAccountType.UNKNOWN, InternalAccountType.BANK));

                    return resultList;
                }
            };
}
