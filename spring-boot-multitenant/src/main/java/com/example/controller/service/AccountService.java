package com.example.controller.service;

import com.example.common.exception.InputValidationException;
import com.example.common.util.DateUtil;
import com.example.constant.AcGroupNode;
import com.example.constant.DatePreset;
import com.example.constant.InternalAccountType;
import com.example.controller.service.base.BaseDaoService;
import com.example.entity.acct.*;
import com.example.entity.dto.acct.AccountDto;
import com.example.entity.dto.acct.AccountGroupDto;
import com.example.entity.dto.acct.JournalDto;
import com.example.entity.mapper.AccountGroupMapper;
import com.example.entity.mapper.AccountMapper;
import com.example.entity.mapper.JournalMapper;
import com.example.entity.order.Company;
import com.example.entity.rs.order.JournalSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AccountService extends BaseDaoService {

    @Autowired
    HttpSession session;

    @Autowired
    UserService userService;

    @Autowired
    AccountGroupService accountGroupService;

    public AccountService() {
    }


    /* -----------------
     *  Journal
     * -----------------
     */
    @Transactional
    public Set<Journal> saveJournals(Set<Journal> journals) throws Exception {
        for (Journal journal : journals) {
            Account account = (Account) find(Account.class, journal.getAccount().getId());
            journal.setAccount(account);
            persist(journal);
        }
        userService.setBalanceUpdateRequired(1);
        return journals;
    }


    @Transactional
    public List<JournalDto> searchJournals(JournalSearchRequest searchRequest) throws Exception {

        List<Journal> results = new ArrayList<>();
        List<JournalDto> journalDtos = new ArrayList<>();

        try {
            CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<Journal> criteriaQuery = criteriaBuilder.createQuery(Journal.class);
            Root<Journal> invJournalRoot = criteriaQuery.from(Journal.class);
            Join<Journal, Account> accountJoin = invJournalRoot.join("account");


            ArrayList<Predicate> predicates = new ArrayList<>();

            Predicate predicateTenanat = criteriaBuilder.equal(
                    invJournalRoot.get("tenantid"), getTenantId());
            predicates.add(predicateTenanat);

            // My Company
            if (searchRequest.getMyCompanyId() != null) {
                Predicate predicateSelfCompany = criteriaBuilder.equal(
                        invJournalRoot.get("mycompanyid"), searchRequest.getMyCompanyId());

                predicates.add(predicateSelfCompany);
            }

            // Account
            if (searchRequest.getAcctId() != null && searchRequest.getAcctId() > 0l) {
                Predicate predicateAccount = criteriaBuilder.equal(
                        accountJoin.get("id"), searchRequest.getAcctId());
                predicates.add(predicateAccount);
            }

            // Date Range
            if (!DatePreset.getType(searchRequest.getDatePreset()).equals(DatePreset.UNKNOWN)) {
                LocalDateTime start = DateUtil.toLocalDateTime(searchRequest.getStartDate());
                LocalDateTime end = DateUtil.toLocalDateTime(searchRequest.getEndDate());
                Predicate predicateDateRange = criteriaBuilder.between(
                        invJournalRoot.get("transdate"), start, end);
                predicates.add(predicateDateRange);
            }


            Predicate finalPredicate = criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
            criteriaQuery.where(finalPredicate);
            criteriaQuery.orderBy(criteriaBuilder.desc(invJournalRoot.get("transdate")));
            results = getEntityManager().createQuery(criteriaQuery).setMaxResults(300).getResultList();

            journalDtos = results.stream().map(JournalMapper.mapperEntityToDto).collect(Collectors.toList());

        } catch (Exception e) {
            addToSessionAppLog("Order Service Error 801: " + e.getMessage());
            throw e;
        }
        return journalDtos;

    }

    @Transactional
    public List<Journal> getAccountBalance(FinancialYear financialYear) {

        List<Journal> journals = new ArrayList<>();

        return journals;
    }


    /* -----------------
     *  GetAllAccounts
     * -----------------*/
    @Transactional
    public List<Account> getAllAccounts() throws Exception {

        Account account = null;
        TypedQuery<Account> findAcQry = createNamedQuery(Account.Q_ALL, Account.class);
        findAcQry.setParameter("tenantid", getTenantId());
        List<Account> accounts = findAcQry.getResultList();
        return accounts;
    }


    /** ---------------------------------
     *  GetAccount (InternalAccountType)
     *  ---------------------------------
     */
    public Account getAccount(InternalAccountType iacType) throws Exception {
        return findAccountByCode(iacType.getCode());
    }

    /** ---------------------------------
     *  GetAccount (client)
     *  ---------------------------------
     */
    public Account getAccount(Company client) throws Exception {
        return findAccount(client.getId());

    }

    //---------------------------
    // CreateNewAccount (client)
    //---------------------------
    @Transactional
    public Account createClientAccount(Company client) throws Exception {

        Account clientAccount = new Account(client);
        AcGroupNode acgroup = AcGroupNode.getClientAccountGroupType(client);
        AccountGroup group = accountGroupService.findGroupByCode(acgroup.getCode(), getTenantId());
        if (group == null) {
            throw new InputValidationException("Group not found." + acgroup.getDesc());
        }
        clientAccount.setGroupid(group.getId());
        clientAccount.setTenantid(getTenantId());
        persist(clientAccount);
        client.setAccount(clientAccount);
        merge(client);

        return clientAccount;
    }

    //-------------------------------------------
    // Create Internal Account if does not exists
    //-------------------------------------------
    @Transactional
    public Account createInternalAccount(InternalAccountType iacType, long tenantId) throws Exception{
        Account account = null;
        AccountGroup group = null;
        try {
            // Find ---
            account = findAccountByCode(iacType.getCode());

            // Create New ---
            if (account == null) {
                group = accountGroupService.findGroupByCode(iacType.getGroup().getCode(), tenantId);
                if (group == null) {
                    throw new InputValidationException("Group Not Found" + iacType.getGroup().getCode());
                }
                account = new Account(iacType, tenantId, group);
                persist(account);

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;

        }
        return account;
    }

    //-------------------------------------------
    // FindAccount (Code)
    //-------------------------------------------
    @Transactional
    public Account findAccountByCode(int code) throws Exception {
        Account account = null;
        TypedQuery<Account> findAcQry = createNamedQuery(Account.Q_BY_CODE, Account.class);
        findAcQry.setParameter("code", code);
        findAcQry.setParameter("tenantid", getTenantId());
        List<Account> accts = findAcQry.getResultList();
        if (accts != null && !accts.isEmpty()) {
            account = accts.get(0);
        }
        return account;
    }


    //--------------------
    // FindAccount (clientid)
    //--------------------
    @Transactional
    public Account findAccount(long clientid) throws Exception {
        Account account = null;
        TypedQuery<Account> findAcQry = createNamedQuery(Account.Q_CLIENT_ACCT, Account.class);
        findAcQry.setParameter("clientid", clientid);
        List<Account> accts = findAcQry.getResultList();
        if (accts != null && !accts.isEmpty()) {
            account = accts.get(0);
        }

        return account;
    }

    //--------------------
    // FindAccount (name)
    //--------------------
    @Transactional
    public Account findAccount(String acname, long tenantId) throws Exception {
        Account account = null;
        TypedQuery<Account> findAcQry = createNamedQuery(Account.Q_BY_NAME, Account.class);
        findAcQry.setParameter("tenantid", tenantId);
        findAcQry.setParameter("acname", acname);
        List<Account> accts = findAcQry.getResultList();
        if (accts != null && !accts.isEmpty()) {
            account = accts.get(0);
        }

        return account;
    }

    @Transactional
    public Account saveAccount(AccountDto accountDto) throws Exception{
        Account account = null;
        if (accountDto.getId() != null) {
            //finds and returns entity if it already exists
            account = (Account) find(Account.class, accountDto.getId());
        } else {
            account = new Account();
        }
        account = AccountMapper.mapDtoToEntity.apply(accountDto,account);
        account.setTenantid(getTenantId());

        if(account.getId() == null){
            persist(account);
        }
        else{
            merge(account);
        }

        return account;
    }


    /**************************
     FinancialYear
     **************************/

    @Transactional
    public FinancialYear getFinyear(LocalDateTime dateTime) throws Exception {
        return new FinancialYear();
    }

    @Transactional
    public AccountGroup saveAccountGroup(AccountGroupDto groupDto) throws Exception{
        AccountGroup group = null;
        if (groupDto.getId() != null) {
            //finds and returns entity if it already exists
            group = (AccountGroup) find(AccountGroup.class, groupDto.getId());
        } else {
            group = new AccountGroup();
        }
        group = AccountGroupMapper.mapDtoToEntity.apply(groupDto,group);
        group.setTenantid(getTenantId());

        if(group.getId() == null){
            persist(group);

        }
        else{
            merge(group);
        }

        return group;
    }









}
