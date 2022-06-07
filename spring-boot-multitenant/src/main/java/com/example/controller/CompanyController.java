package com.example.controller;

import com.example.common.exception.InputValidationException;
import com.example.constant.CompanyType;
import com.example.constant.RsStatusType;
import com.example.controller.manager.CompanyManager;
import com.example.entity.rs.order.*;
import com.example.controller.service.CompanyService;
import com.example.entity.dto.order.CompanyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;
    @Autowired
    private CompanyManager companyManager;

    @RequestMapping(value = "/save.ws", method = RequestMethod.POST)
    public ResponseEntity<?> add(@RequestBody CompanyAddRequest companyAddRequest, HttpServletRequest request) {
        CompanyResponse response = new CompanyResponse();
        CompanyDto companyDto = null;
        response.setStatus(RsStatusType.SUCCESS);
        try {

            companyAddRequest.validate();
            companyDto = companyManager.saveCompany(companyAddRequest.getCompany());
            response.setCompany(companyDto);

        } catch (InputValidationException e) {
            response.setErrorMessage(e.getError());
            response.setStatus(RsStatusType.FAILURE);

        } catch (Exception e) {
            response.setStatus(RsStatusType.FAILURE);
            response.setErrorMessage(e.getMessage());
        }
        return new ResponseEntity<CompanyResponse>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/list.ws", method = RequestMethod.POST)
    public ResponseEntity<?> list(HttpServletRequest request) {
        CompanyListResponse response = new CompanyListResponse();
        response.setStatus(RsStatusType.SUCCESS);
        try {
            List<CompanyDto> companyDtos = companyManager.getAllCompanies();
            response.setCompanies(companyDtos);
        } catch (Exception e) {
            response.setStatus(RsStatusType.FAILURE);
            response.setErrorMessage(e.getMessage());
        }
        return new ResponseEntity<CompanyListResponse>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/listbytype.ws", method = RequestMethod.POST)
    public ResponseEntity<?> listByType(HttpServletRequest request) {
        CompanyListByTypeResponse response = new CompanyListByTypeResponse();
        response.setStatus(RsStatusType.SUCCESS);
        try {
            List<CompanyDto> allCompanies = companyManager.getAllCompanies();
            response.setCustomers(allCompanies.stream().filter(c-> c.getType() != null && CompanyType.CUSTOMER.getType().equalsIgnoreCase(c.getType())).collect(Collectors.toList()));
            response.setSuppliers(allCompanies.stream().filter(c-> c.getType() != null && CompanyType.SUPPLIER.getType().equalsIgnoreCase(c.getType())).collect(Collectors.toList()));
            response.setMyCompanies(allCompanies.stream().filter(c-> c.getType() != null && CompanyType.SELF.getType().equalsIgnoreCase(c.getType())).collect(Collectors.toList()));
        } catch (Exception e) {
            response.setStatus(RsStatusType.FAILURE);
            response.setErrorMessage(e.getMessage());
        }
        return new ResponseEntity<CompanyListByTypeResponse>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/savecontact.ws", method = RequestMethod.POST)
    public ResponseEntity<?> saveContact(@RequestBody ContactAddRequest contactAddRequest, HttpServletRequest request) {
        CompanyResponse response = new CompanyResponse();
        CompanyDto companyDto = null;
        response.setStatus(RsStatusType.SUCCESS);
        try {
            contactAddRequest.validate();
            companyDto= companyManager.saveCompanyContact(contactAddRequest.getCompanyId(), contactAddRequest.getContact());
            response.setCompany(companyDto);

        } catch (InputValidationException e) {
            response.setErrorMessage(e.getError());
            response.setStatus(RsStatusType.FAILURE);
        } catch (Exception e) {
            response.setStatus(RsStatusType.FAILURE);
            response.setErrorMessage(e.getMessage());
        }
        return new ResponseEntity<CompanyResponse>(response, HttpStatus.OK);
    }
}

