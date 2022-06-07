package com.example.controller.util;

import com.example.common.util.MathUtil;
import com.example.entity.dto.order.CompanyDto;
import com.example.entity.dto.order.POProductDto;
import com.example.entity.dto.order.PurchaseOrderDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

public class HtmlUtil {

    private  final String PO_TEMPLATE = "purchaseorder.html";

    public String getPOTemplate() throws Exception {

        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(PO_TEMPLATE);
        InputStreamReader isReader = new InputStreamReader(is);
        BufferedReader reader = new BufferedReader(isReader);
        StringBuffer sb = new StringBuffer();
        String str;
        while((str = reader.readLine()) != null) {
            sb.append(str);
        }
        is.close();
        return sb.toString();
    }


    public String getHtmlPurchaseOrder(PurchaseOrderDto po){
        //
        String htmlstring = "";
        //open the html file
        try
        {
            String html = getPOTemplate();
             Document document = Jsoup.parse(html);

             //PO  Values
            CompanyDto customer = po.getCustomer() !=null ? po.getCustomer() : new CompanyDto();
            CompanyDto supplier = po.getSupplier() !=null ? po.getSupplier() : new CompanyDto();

            String orderDate = po.getOrderdate().toString();
            String ref_no = po.getReferenceno();

            String custName = po.getCustomerName();
            String cgstin = customer.getGstin1();
            String phone = customer.getPhone();
            String c_email = customer.getEmail();

            String c_i_add1 = customer.getInvoiceContact().getAddress1();
            String c_i_add2 = customer.getInvoiceContact().getAddress2();
            String c_i_state ="State :" + customer.getInvoiceContact().getState() + ", PIN :" +
                    customer.getInvoiceContact().getPincode();

            String c_d_add1 = customer.getDispatchContact().getAddress1();
            String c_d_add2 = customer.getDispatchContact().getAddress2();
            String c_d_state = "State :" + customer.getDispatchContact().getState() + ", PIN: " +
                    customer.getDispatchContact().getPincode();

            String sup_name = po.getSupplierName();
            String s_gstin = supplier.getGstin1();
            String s_i_add1 = supplier.getInvoiceContact().getAddress1();
            String s_i_state = "State: " + supplier.getInvoiceContact().getState() + ", PIN- " +
                    supplier.getInvoiceContact().getPincode();


            Element e_poRow = document.getElementById("po-row").clone();
            Element e_taxRow = document.getElementById("tax-row").clone();
            Element e_poItems = document.getElementById("po-items").clone();
            e_poItems.getElementsByTag("tr").remove();


            // Populate HTML
            document.getElementById("c-name").text(custName);
            document.getElementById("c-gstin").text(cgstin);
            document.getElementById("c-email").text(c_email);
            document.getElementById("c-inv-add-1").text(c_i_add1);
            document.getElementById("c-inv-add-2").text(c_i_add2);
            document.getElementById("c-inv-state").text(c_i_state);
            document.getElementById("c-disp-add-1").text(c_d_add1);
            document.getElementById("c-disp-add-2").text(c_d_add2);
            document.getElementById("c-disp-state").text(c_d_state);
            document.getElementById("s-name").text(sup_name);
            document.getElementById("s-gstin").text(s_gstin);
            document.getElementById("s-inv-add-1").text(s_i_add1);
            document.getElementById("s-inv-state").text(s_i_state);
            document.getElementById("o-ref").text(ref_no);
            document.getElementById("o-date").text(orderDate);
            document.getElementById("c-name").text(custName);
            document.getElementById("c-name").text(custName);
            document.getElementById("c-name").text(custName);
            document.getElementById("c-name").text(custName);
            document.getElementById("c-name").text(custName);
            document.getElementById("c-name").text(custName);
            document.getElementById("c-name").text(custName);

            // PO Products
            Integer index = 1;
            for(POProductDto pop : po.getPoproductlist()){
                Element porow = e_poRow.clone();
                porow.getElementById("po-index").text(index.toString());
                porow.getElementById("po-name").text(pop.getProductName());
                porow.getElementById("po-qty").text(pop.getQuantity().toString());
                porow.getElementById("po-price").text(pop.getPrice().toString());
                porow.getElementById("po-amt").text(MathUtil.rupeesFormat(pop.getPrice().multiply(pop.getQuantity())));
                e_poItems.appendChild(porow);
                index++;
            }

            // Taxes
            for(Map.Entry entry : MathUtil.getTaxMap(po.getTaxes()).entrySet()) {
                Element taxrow = e_taxRow.clone();
                taxrow.getElementById("tax").text(entry.getKey().toString());
                taxrow.getElementById("tax-amt").text(entry.getValue().toString());
                e_poItems.appendChild(taxrow);
            }

            document.getElementById("total-amt").text(po.getRupTotalAmount());
            htmlstring =   document.toString();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return htmlstring;

    }




}
