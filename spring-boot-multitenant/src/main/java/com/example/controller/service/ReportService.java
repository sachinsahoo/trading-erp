package com.example.controller.service;

import com.example.controller.service.base.BaseDaoService;
import com.example.entity.dto.report.*;
import com.example.entity.mapper.report.*;
import com.example.entity.report.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.TypedQuery;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService extends BaseDaoService {

    @Autowired
    HttpSession session;


    public ReportService() {
    }


    /**
     *    Purchase / Sale Monthly
     */
    @Transactional
    public List<PurchaseSaleMonthlyReportDto> getPurchaseSaleMonthlyReport(Long start, Long end) throws Exception {

        TypedQuery<PurchaseSaleMonthlyReport> reportQry = createNamedQuery(PurchaseSaleMonthlyReport.Q_PURCHASESALEMONTHLYREPORT_LIST, PurchaseSaleMonthlyReport.class);
        reportQry.setParameter("tenantid", getTenantId());
        List<PurchaseSaleMonthlyReport> purchaseSaleMonthlyReports = reportQry.getResultList();
        List<PurchaseSaleMonthlyReportDto> purchaseSaleMonthlyReportDtos = purchaseSaleMonthlyReports.stream().map(PurchaseSaleMonthlyReportMapper.mapperEntityToDto).collect(Collectors.toList());

        return purchaseSaleMonthlyReportDtos;


    }


    /**
     *  Product  Purchase / Sale Monthly
     */
    @Transactional
    public List<ProductMonthlyReportDto> getProductReport(Long start, Long end) throws Exception {

        TypedQuery<ProductMonthlyReport> reportQry = createNamedQuery(ProductMonthlyReport.Q_PRODUCTMONTHLYREPORT_LIST, ProductMonthlyReport.class);
        reportQry.setParameter("tenantid", getTenantId());
        List<ProductMonthlyReport> productMonthlyReports = reportQry.getResultList();
        List<ProductMonthlyReportDto> productMonthlyReportDtos = productMonthlyReports.stream().map(ProductReportMapper.mapperEntityToDto).collect(Collectors.toList());

        return productMonthlyReportDtos;


    }


    /**
     *  Customer -  Monthly Sales
     */
    @Transactional
    public List<CustomerMonthlyReportDto> getCustomerMonthlyReport(Long start, Long end) throws Exception {

        TypedQuery<CustomerMonthlyReport> reportQry = createNamedQuery(CustomerMonthlyReport.Q_CUSTOMERMONTHLYREPORT_LIST, CustomerMonthlyReport.class);
        reportQry.setParameter("tenantid", getTenantId());
        List<CustomerMonthlyReport> customerMonthlyReports = reportQry.getResultList();
        List<CustomerMonthlyReportDto> customerMonthlyReportDtos = customerMonthlyReports.stream().map(CustomerMonthlyReportMapper.mapperEntityToDto).collect(Collectors.toList());

        return customerMonthlyReportDtos;


    }

    /**
     *  Supplier -  Monthly Purchases
     */
    @Transactional
    public List<SupplierMonthlyReportDto> getSupplierMonthlyReport(Long start, Long end) throws Exception {

        TypedQuery<SupplierMonthlyReport> reportQry = createNamedQuery(SupplierMonthlyReport.Q_SUPPLIERMONTHLYREPORT_LIST, SupplierMonthlyReport.class);
        reportQry.setParameter("tenantid", getTenantId());
        List<SupplierMonthlyReport> supplierMonthlyReports = reportQry.getResultList();
        List<SupplierMonthlyReportDto> supplierMonthlyReportDtos = supplierMonthlyReports.stream().map(SupplierMonthlyReportMapper.mapperEntityToDto).collect(Collectors.toList());
        return supplierMonthlyReportDtos;

    }

    /**
     *  Product Supplier -  Monthly Purchases
     */
    @Transactional
    public List<ProductSupplierMonthlyReportDto> getProductSupplierMonthlyReport(Long start, Long end) throws Exception {

        TypedQuery<ProductSupplierMonthlyReport> reportQry = createNamedQuery(ProductSupplierMonthlyReport.Q_LIST, ProductSupplierMonthlyReport.class);
        reportQry.setParameter("tenantid", getTenantId());
        List<ProductSupplierMonthlyReport> prodSupplierMonthlyReports = reportQry.getResultList();
        List<ProductSupplierMonthlyReportDto> prodSupplierMonthlyReportDtos = prodSupplierMonthlyReports.stream().map(ProductSupplierMonthlyReportMapper.mapperEntityToDto).collect(Collectors.toList());
        return prodSupplierMonthlyReportDtos;

    }

    /**
     *  Product Customer -  Monthly Purchases
     */
    @Transactional
    public List<ProductCustomerMonthlyReportDto> getProductCustomerMonthlyReport(Long start, Long end) throws Exception {

        TypedQuery<ProductCustomerMonthlyReport> reportQry = createNamedQuery(ProductCustomerMonthlyReport.Q_LIST, ProductCustomerMonthlyReport.class);
        reportQry.setParameter("tenantid", getTenantId());
        List<ProductCustomerMonthlyReport> prodCustomerMonthlyReports = reportQry.getResultList();
        List<ProductCustomerMonthlyReportDto> prodCustomerMonthlyReportDtos = prodCustomerMonthlyReports.stream().map(ProductCustMonthlyReportMapper.mapperEntityToDto).collect(Collectors.toList());
        return prodCustomerMonthlyReportDtos;

    }





}
