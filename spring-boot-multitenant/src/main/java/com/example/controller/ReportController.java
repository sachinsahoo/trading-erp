package com.example.controller;

import com.example.common.util.DateUtil;
import com.example.constant.RsStatusType;
import com.example.controller.manager.ReportManager;
import com.example.entity.rs.order.ReportResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.Month;

@RestController
@RequestMapping("api/report")
public class ReportController {

    @Autowired
    ReportManager reportManager;



    @RequestMapping(value = "/year.ws", method = RequestMethod.POST)
    public ResponseEntity<?> list(HttpServletRequest request) {

        ReportResponse response = new ReportResponse();
        response.setStatus(RsStatusType.SUCCESS);

        try {
            Long start = DateUtil.getLong(Month.JANUARY, 1, 2019);
            Long end = DateUtil.getLong(Month.DECEMBER, 31, 2019);

            response.setReport(reportManager.getYearlyReport(start, end));
        } catch (Exception e) {
            response.setStatus(RsStatusType.FAILURE);
            response.setErrorMessage(e.getMessage());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
