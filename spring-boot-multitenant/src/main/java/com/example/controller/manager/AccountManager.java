package com.example.controller.manager;


import com.example.common.constant.ErrorCode;
import com.example.common.exception.InputValidationException;
import com.example.common.exception.ServiceException;
import com.example.constant.AcGroupNode;
import com.example.constant.AccountType;
import com.example.constant.InternalAccountType;
import com.example.controller.service.AccountBalanceService;
import com.example.controller.service.AccountGroupService;
import com.example.controller.service.AccountService;
import com.example.controller.service.UserService;
import com.example.entity.Tenant;
import com.example.entity.acct.Account;
import com.example.entity.acct.AccountGroup;
import com.example.entity.dto.acct.AccountDto;
import com.example.entity.dto.acct.AccountGroupDto;
import com.example.entity.dto.acct.BalanceSheetDto;
import com.example.entity.dto.acct.JournalDto;
import com.example.entity.dto.acct.comparator.AccountDtoLevelComparator;
import com.example.entity.mapper.AccountGroupMapper;
import com.example.entity.mapper.AccountMapper;
import com.example.entity.rs.order.JournalSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountManager extends BaseManager {


    @Autowired
    HttpSession session;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountBalanceService accountBalanceService;

    @Autowired
    private UserService userService;

    @Autowired
    private AccountGroupService accountGroupService;

    //------------------------------------------
    //  Create All internal Accounts and Groups
    //------------------------------------------
    @Transactional
    public void createInternalAccountForTenant(long tenantId) throws Exception {
        if(tenantId < 1) {
            throw new InputValidationException(ErrorCode.Tenant_Not_Found);
        }

        // create groups()
        createInternalGroups(tenantId);
        // create internal accounts
        for (InternalAccountType iacType : InternalAccountType.values()) {
            accountService.createInternalAccount(iacType, tenantId);
        }
    }



    /**------------------------------
     * Create Internal Group Tree
     * Level 0 - 10
     * -------------------------------
     */
    @Transactional
    public void createInternalGroups(long tenantId) throws Exception {

        List<AcGroupNode> acGroupNodes = Arrays.asList(AcGroupNode.values());

        for(int i=0; i<10; i++ ){
            int level = i;
            List<AcGroupNode> level_i_Nodes = acGroupNodes.stream().filter(acnode -> {return acnode.getLevel() == level; }).collect(Collectors.toList());
            if(level_i_Nodes == null || level_i_Nodes.isEmpty()){
                break;
            }
            for(AcGroupNode node : level_i_Nodes) {
                accountGroupService.saveAccountGroupNode_ifNotExists(node, tenantId);
            }
        }

    }




    /** -----------------------------
     *  GetBalanceSheet (myCompanyId)
     *  -----------------------------
     */
    public BalanceSheetDto getBalanceSheet(Long myCompanyId) throws InputValidationException, Exception {
        BalanceSheetDto balanceSheet = new BalanceSheetDto();

        accountBalanceService.updateAccountBalances(myCompanyId);

        // Get all accounts and Groups from DB
        List<AccountDto> allAccts = accountService.getAllAccounts().stream().map(AccountMapper.mapperEntityToDto).collect(Collectors.toList());
        List<AccountGroupDto> allGroups = accountGroupService.getAllGroups().stream().map(AccountGroupMapper.mapperEntityToDto).collect(Collectors.toList());

        // Find Root group
        AccountGroupDto rootGroup = allGroups.stream().filter(accountGroupDto -> accountGroupDto.getCode().equals(AcGroupNode.ROOT.getCode())).findFirst().orElse(null);
        if(rootGroup == null) {
            throw new ServiceException("Root Account not found for Tenant " + getUserTenant());
        }
        AccountDto root = new AccountDto(rootGroup, new ArrayList<>());


        // Create Group Accounts with accounts in Group
        List<AccountDto> allGroupList = new ArrayList<>();
        for(AccountGroupDto group : allGroups) {
            List acctInGrop1 = allAccts.stream().filter(accountDto -> {
                return accountDto.getGroupId().equals(group.getId());
            }).collect(Collectors.toList());
            AccountDto groupAcct = new AccountDto(group, acctInGrop1);
            allGroupList.add(groupAcct);
        }

        // Order Group list by level
        Collections.sort(allGroupList, AccountDtoLevelComparator.getInstance());

        List<AccountDto> level1Groups = allGroupList.stream().filter(accountDto -> accountDto.getLevel() == 1).collect(Collectors.toList());
        root.getAcctList().addAll(level1Groups);
        List<AccountDto> level2nAboveGroups = allGroupList.stream().filter(accountDto -> accountDto.getLevel() > 1).collect(Collectors.toList());

        // Add level 1 groups to the root


        // Add Groups to tree
        Collections.sort(level2nAboveGroups, AccountDtoLevelComparator.getInstance());
        for(AccountDto groupDto : level2nAboveGroups) {
            addNodeToAccountTree(root, groupDto);
        }

        List<AccountDto> assets = root.getAcctList().stream().filter(accountDto -> accountDto.isType(AccountType.ASSET)).collect(Collectors.toList());
        List<AccountDto> liabilities = root.getAcctList().stream().filter(accountDto -> accountDto.isType(AccountType.LIABILITY)).collect(Collectors.toList());
        List<AccountDto> income = root.getAcctList().stream().filter(accountDto -> accountDto.isType(AccountType.INCOME)).collect(Collectors.toList());
        List<AccountDto> expenses = root.getAcctList().stream().filter(accountDto -> accountDto.isType(AccountType.EXPENSE)).collect(Collectors.toList());

        balanceSheet.setAssets(assets);
        balanceSheet.setLiabilities( liabilities);
        balanceSheet.setIncomes(income);
        balanceSheet.setExpenses(expenses);

        return balanceSheet;

    }

    // -------------------------
    // Put accountGroup in Tree
    // ------------------------
    public void addNodeToAccountTree(AccountDto root, AccountDto node){
        if(null != root.getAcctList() && !root.getAcctList().isEmpty()) {
            AccountDto eligibleNode = root.getAcctList().stream().filter(accountDto ->
            { return accountDto.isGroup() && accountDto.getId().equals(node.getGroupId());} ).findFirst().orElse(null);
            if(eligibleNode != null){
                eligibleNode.getAcctList().add(node);
            } else {
                for(AccountDto accountDto : root.getAcctList()) {
                    // Recursive call
                    addNodeToAccountTree(accountDto, node);
                }
            }
        }
    }


    //-------------------------------
    //  Get Ledger
    //-------------------------------
    public List<JournalDto> getLedger(JournalSearchRequest searchRequest) throws Exception {
        searchRequest.validate();
        return accountService.searchJournals(searchRequest);

    }

    //-------------------------------
    //  Get All Accounts
    //-------------------------------
    public List<AccountDto> getAccountList() throws Exception {

        List<AccountDto> allAccts = accountService.getAllAccounts().stream().map(AccountMapper.mapperEntityToDto).collect(Collectors.toList());
        return allAccts;
    }

    // ---------------------
    // Save Account Group
    // ---------------------
    public AccountGroupDto saveAccountGroup (AccountGroupDto groupDto, long tenantId) throws InputValidationException, Exception {
        AccountGroupDto accountGroupDto = null;

        if(tenantId < 1l) {
            throw new InputValidationException(ErrorCode.Tenant_Not_Found);
        }

        if(groupDto.getId() == null  && accountGroupService.findByGroupName(groupDto.getName(), getUserTenant()) != null) {
            throw new InputValidationException("Account Group with  name: " + groupDto.getName() + "already exists");
        } else {
            if(groupDto.getIndex() == null){
                groupDto.setIndex(90000);
            }
            AccountGroup group = accountService.saveAccountGroup(groupDto);
            accountGroupDto = AccountGroupMapper.mapperEntityToDto.apply(group);
        }
        return accountGroupDto;

    }

    // --------------------------
    // Save Account If not Exists
    // --------------------------

    public AccountDto saveAccount_IfNotExist (AccountDto accountDto, long tenantId) throws InputValidationException, Exception {
        AccountDto accountDtoResult = null;
        Account dbAccount = null;

        if(tenantId < 1) {
            throw new ServiceException("Tenant Id not found " + tenantId);
        }

        dbAccount = accountService.findAccount(accountDto.getName(), tenantId);
        if(dbAccount == null) {
            dbAccount =  accountService.saveAccount(accountDto);
        }
        accountDtoResult = AccountMapper.mapperEntityToDto.apply(dbAccount);
        return accountDtoResult;

    }


}
