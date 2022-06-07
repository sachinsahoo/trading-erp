package com.example.entity.report;

import com.example.entity.dto.order.CompanyDto;
import com.example.entity.dto.order.PurchaseOrderDto;
import com.example.entity.order.PurchaseOrder;

public class OrderPDFData {




    public OrderPDFData(PurchaseOrderDto po) {
        order = po;
        customer = po.getCustomer() != null ? po.getCustomer() : new CompanyDto();
        supplier = po.getSupplier() != null ? po.getSupplier() : new CompanyDto();

        orderDate = po.getOrderdate().toString();
        ref_no = po.getReferenceno();

        custName = po.getCustomerName();
        cgstin = customer.getGstin1();
        phone = customer.getPhone();
        c_email = customer.getEmail();
        c_pan = customer.getPan();

        c_i_add1 = customer.getInvoiceContact().getAddress1();
        c_i_add2 = customer.getInvoiceContact().getAddress2();
        c_i_state = "State :" + customer.getInvoiceContact().getState() + ", PIN :" +
                customer.getInvoiceContact().getPincode();

        c_d_add1 = customer.getDispatchContact().getAddress1();
        c_d_add2 = customer.getDispatchContact().getAddress2();
        c_d_state = "State :" + customer.getDispatchContact().getState() + ", PIN: " +
            customer.getDispatchContact().getPincode();

        sup_name = po.getSupplierName();
        s_gstin = supplier.getGstin1();
        s_i_add1 = supplier.getInvoiceContact().getAddress1();
        s_i_state = "State: " + supplier.getInvoiceContact().getState() + ", PIN- " +
                supplier.getInvoiceContact().getPincode();

    }



    protected PurchaseOrderDto order ;
    protected CompanyDto customer ;
    protected CompanyDto supplier ;

    protected String orderDate ;
    protected String ref_no;

    protected String custName ;
    protected String cgstin;
    protected String phone;
    protected String c_email;
    protected String c_pan;

    protected String c_i_add1;
    protected String c_i_add2;
    protected String c_i_state;


    protected String c_d_add1;
    protected String c_d_add2;
    protected String c_d_state;

    protected String sup_name;
    protected String s_gstin;
    protected String s_i_add1;
    protected String s_i_state;

    public PurchaseOrderDto getOrder() {
        return order;
    }

    public void setOrder(PurchaseOrderDto order) {
        this.order = order;
    }

    public CompanyDto getCustomer() {
        return customer;
    }

    public void setCustomer(CompanyDto customer) {
        this.customer = customer;
    }

    public CompanyDto getSupplier() {
        return supplier;
    }

    public void setSupplier(CompanyDto supplier) {
        this.supplier = supplier;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getRef_no() {
        return ref_no;
    }

    public void setRef_no(String ref_no) {
        this.ref_no = ref_no;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCgstin() {
        return cgstin;
    }

    public void setCgstin(String cgstin) {
        this.cgstin = cgstin;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getC_email() {
        return c_email;
    }

    public void setC_email(String c_email) {
        this.c_email = c_email;
    }

    public String getC_pan() {
        return c_pan;
    }

    public void setC_pan(String c_pan) {
        this.c_pan = c_pan;
    }

    public String getC_i_add1() {
        return c_i_add1;
    }

    public void setC_i_add1(String c_i_add1) {
        this.c_i_add1 = c_i_add1;
    }

    public String getC_i_add2() {
        return c_i_add2;
    }

    public void setC_i_add2(String c_i_add2) {
        this.c_i_add2 = c_i_add2;
    }

    public String getC_i_state() {
        return c_i_state;
    }

    public void setC_i_state(String c_i_state) {
        this.c_i_state = c_i_state;
    }

    public String getC_d_add1() {
        return c_d_add1;
    }

    public void setC_d_add1(String c_d_add1) {
        this.c_d_add1 = c_d_add1;
    }

    public String getC_d_add2() {
        return c_d_add2;
    }

    public void setC_d_add2(String c_d_add2) {
        this.c_d_add2 = c_d_add2;
    }

    public String getC_d_state() {
        return c_d_state;
    }

    public void setC_d_state(String c_d_state) {
        this.c_d_state = c_d_state;
    }

    public String getSup_name() {
        return sup_name;
    }

    public void setSup_name(String sup_name) {
        this.sup_name = sup_name;
    }

    public String getS_gstin() {
        return s_gstin;
    }

    public void setS_gstin(String s_gstin) {
        this.s_gstin = s_gstin;
    }

    public String getS_i_add1() {
        return s_i_add1;
    }

    public void setS_i_add1(String s_i_add1) {
        this.s_i_add1 = s_i_add1;
    }

    public String getS_i_state() {
        return s_i_state;
    }

    public void setS_i_state(String s_i_state) {
        this.s_i_state = s_i_state;
    }
}
