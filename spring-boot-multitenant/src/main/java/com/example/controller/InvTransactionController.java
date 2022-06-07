package com.example.controller;

import com.example.common.exception.InputValidationException;
import com.example.constant.RsStatusType;
import com.example.controller.manager.InvTransactionManager;
import com.example.controller.service.InvTransactionService;
import com.example.entity.rs.EntityRequest;
import com.example.entity.rs.base.BaseResponse;
import com.example.entity.rs.order.TransactionListResponse;
import com.example.entity.rs.order.TransactionResponse;
import com.example.entity.rs.order.TransactionSaveRequest;
import com.example.entity.rs.order.TransactionSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/transaction")
public class InvTransactionController {


        @Autowired
        private InvTransactionService invTransactionService;

        @Autowired
        InvTransactionManager invTransactionManager;

        @RequestMapping(value = "/save.ws", method = RequestMethod.POST)
        public ResponseEntity<?> save(@RequestBody TransactionSaveRequest transactionRequest, HttpServletRequest request) {

            TransactionResponse response = new TransactionResponse();
            response.setStatus(RsStatusType.SUCCESS);

            try {
                transactionRequest.validate();
                response.setTransaction(invTransactionManager.saveTransaction(transactionRequest.getTransaction()));
            } catch (InputValidationException e) {
                response.setErrorMessage(e.getError());
                response.setStatus(RsStatusType.FAILURE);

            } catch (Exception e) {
                response.setStatus(RsStatusType.FAILURE);
                response.setErrorMessage(e.getMessage());

            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

    @RequestMapping(value = "/search.ws", method = RequestMethod.POST)
    public ResponseEntity<?> search(@RequestBody TransactionSearchRequest searchRequest, HttpServletRequest request) {

        TransactionListResponse response = new TransactionListResponse();
        response.setStatus(RsStatusType.SUCCESS);

        try {

            response.setTransactions(invTransactionManager.search(searchRequest));
            searchRequest.setCount(response.getTransactions().size());
            response.setSearchRequest(searchRequest);
        } catch (InputValidationException e) {
            response.setErrorMessage(e.getError());
            response.setStatus(RsStatusType.FAILURE);

        } catch (Exception e) {
            response.setStatus(RsStatusType.FAILURE);
            response.setErrorMessage(e.getMessage());

        }
        return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);
    }


    @RequestMapping(value = "/list.ws", method = RequestMethod.POST)
    public ResponseEntity<?> list( HttpServletRequest request) {

        TransactionListResponse response = new TransactionListResponse();
        response.setStatus(RsStatusType.SUCCESS);
        try {

            TransactionSearchRequest request1 = new TransactionSearchRequest();
            response.setTransactions(invTransactionManager.search(request1));
            request1.setCount(response.getTransactions().size());
            response.setSearchRequest(request1);
        } catch (InputValidationException e) {
            response.setErrorMessage(e.getError());
            response.setStatus(RsStatusType.FAILURE);

        } catch (Exception e) {
            response.setStatus(RsStatusType.FAILURE);
            response.setErrorMessage(e.getMessage());

        }
        return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);
    }



    @RequestMapping(value = "/get.ws", method = RequestMethod.POST)
    public ResponseEntity<?> get(@RequestBody EntityRequest entityRequest, HttpServletRequest request) {

        TransactionResponse response = new TransactionResponse();
        response.setStatus(RsStatusType.SUCCESS);

        try {

            response.setTransaction(invTransactionManager.getTransaction(entityRequest.getId()));
        } catch (InputValidationException e) {
            response.setErrorMessage(e.getError());
            response.setStatus(RsStatusType.FAILURE);

        } catch (Exception e) {
            response.setStatus(RsStatusType.FAILURE);
            response.setErrorMessage(e.getMessage());

        }
        return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);
    }

}
