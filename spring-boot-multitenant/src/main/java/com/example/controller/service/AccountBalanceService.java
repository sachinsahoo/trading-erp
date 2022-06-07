package com.example.controller.service;

import com.example.constant.DrCrType;
import com.example.controller.service.base.BaseDaoService;
import com.example.entity.acct.Account;
import com.example.entity.acct.AccountBalance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountBalanceService extends BaseDaoService {

    @Autowired
    HttpSession session;

    @Autowired
    UserService userService;

    @Autowired
    AccountService accountService;

    public AccountBalanceService() {
    }


    /* -------------------------
     *  Update Account Balances
     * --------------------------
     */

    @Transactional
    public void updateAccountBalances(Long myCompanyId) throws Exception {

        List<Account> allAccounts = accountService.getAllAccounts();

        TypedQuery<AccountBalance> listBalanceQry = createNamedQuery(AccountBalance.Q_LIST, AccountBalance.class);
        listBalanceQry.setParameter("tenantid", getTenantId());
        List<AccountBalance> accountBalances = listBalanceQry.getResultList();

        AccountBalance acctBalance = null;
        for(Account account: allAccounts){

            acctBalance = null;
            List<AccountBalance> allCompAcctBalances = accountBalances.stream().filter(b -> b.getAcctid().equals(account.getId())).collect(Collectors.toList());

            if(!allCompAcctBalances.isEmpty()) {
                if (myCompanyId != null && myCompanyId > 1l) {
                    // Filter selected Self Company
                    AccountBalance selCompAcBalance = allCompAcctBalances.stream().filter(b -> b.getMycompanyid().equals(myCompanyId)).findFirst().orElse(null);
                    acctBalance = selCompAcBalance;

                } else {
                    // Combine Total
                    AccountBalance combinedAcBalance = new AccountBalance();
                    combinedAcBalance.setTotcramt(allCompAcctBalances.stream().map(AccountBalance::getTotcramt).reduce(BigDecimal.ZERO, BigDecimal::add));
                    combinedAcBalance.setTotdramt(allCompAcctBalances.stream().map(AccountBalance::getTotdramt).reduce(BigDecimal.ZERO, BigDecimal::add));
                    acctBalance = combinedAcBalance;
                }


            }

            BigDecimal balance = BigDecimal.ZERO;
            DrCrType drCrType = DrCrType.DEBIT;
            if (acctBalance != null) {
                // Calculate Balance ---
                BigDecimal debitBalance = acctBalance.getTotdramt() != null ? acctBalance.getTotdramt() : BigDecimal.ZERO;
                BigDecimal creditBalance = acctBalance.getTotcramt() != null ? acctBalance.getTotcramt() : BigDecimal.ZERO;

                if (debitBalance.compareTo(creditBalance) > 0) {
                    balance = debitBalance.subtract(creditBalance);
                    drCrType = DrCrType.DEBIT;
                } else {
                    balance = creditBalance.subtract(debitBalance);
                    drCrType = DrCrType.CREDIT;
                }

            }


            // Update Account ---
            account.setBalance(balance);
            account.setDrcr(drCrType.getCode());
            merge(account);
            refresh(account);
        }



    }





}
