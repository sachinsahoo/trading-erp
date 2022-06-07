package com.example.controller;

import com.example.constant.RsStatusType;
import com.example.controller.manager.AccountManager;
import com.example.entity.acct.AccountGroup;
import com.example.entity.dto.acct.AccountCategoryDto;
import com.example.entity.dto.acct.AccountGroupDto;
import com.example.entity.rs.EntityRequest;
import com.example.entity.rs.acct.*;
import com.example.entity.rs.order.JournalSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("api/account")
public class AccountController {

    @Autowired
    AccountManager accountManager;



    @RequestMapping(value = "/balancesheet.ws", method = RequestMethod.POST)
    public ResponseEntity<?> balanceSheet(@RequestBody EntityRequest entityRequest,  HttpServletRequest request) {

        BalanceSheetResponse response = new BalanceSheetResponse();
        response.setStatus(RsStatusType.SUCCESS);

        try {
            response.setBalanceSheet(accountManager.getBalanceSheet(entityRequest.getId()));
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(RsStatusType.FAILURE);
            response.setErrorMessage(e.getMessage());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/aclist.ws", method = RequestMethod.POST)
    public ResponseEntity<?> acList(HttpServletRequest request) {

        AccountListResponse response = new AccountListResponse();
        response.setStatus(RsStatusType.SUCCESS);

        try {
            response.setAccounts(accountManager.getAccountList());
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(RsStatusType.FAILURE);
            response.setErrorMessage(e.getMessage());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/ledger.ws", method = RequestMethod.POST)
    public ResponseEntity<?> ledger(@RequestBody JournalSearchRequest searchRequest, HttpServletRequest request) {
        LedgerResponse response = new LedgerResponse();
        response.setStatus(RsStatusType.SUCCESS);

        try {
            response.setJournals(accountManager.getLedger(searchRequest));
            searchRequest.setCount(response.getJournals().size());
            response.setSearchRequest(searchRequest);
        } catch (Exception e) {
            response.setStatus(RsStatusType.FAILURE);
            response.setErrorMessage(e.getMessage());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @RequestMapping(value = "/savegroup.ws", method = RequestMethod.POST)
    public ResponseEntity<?> saveGroup(@RequestBody AccountGroupRequest accountGroupRequest, HttpServletRequest request) {
        AccountGroupResponse response = new AccountGroupResponse();
         AccountGroupDto accountGroupDto = null  ;

        response.setStatus(RsStatusType.SUCCESS);

        try {
            accountGroupRequest.validate();
            long tenantId = accountManager.getUserTenant();

            accountGroupDto = accountManager.saveAccountGroup(accountGroupRequest.getGroupDto(), tenantId);

           response.setAccountGroupList(accountGroupDto);
        } catch (Exception e) {
            response.setStatus(RsStatusType.FAILURE);
            response.setErrorMessage(e.getMessage());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
