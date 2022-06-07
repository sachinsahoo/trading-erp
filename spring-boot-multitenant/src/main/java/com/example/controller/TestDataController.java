package com.example.controller;

import com.example.common.util.MathUtil;
import com.example.constant.InvTransactionType;
import com.example.constant.RsStatusType;
import com.example.entity.order.Company;
import com.example.entity.order.PurchaseOrder;
import com.example.entity.dto.order.*;
import com.example.entity.mapper.CompanyMapper;
import com.example.entity.mapper.PurchaseOrderMapper;
import com.example.entity.rs.EntityRequest;
import com.example.entity.rs.order.ProductAddRequest;
import com.example.test.TestBalanceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/test")
public class TestDataController extends TestData {





    @RequestMapping(value = "/setup.ws", method = RequestMethod.POST)
    public ResponseEntity<?> setup(HttpServletRequest request) {

        TestBalanceResponse response = new TestBalanceResponse();
        response.setStatus(RsStatusType.SUCCESS);


        try {

            setUp();

            po1 = createPurchaseOrder("purchase", null, poPlist1, sup1, selfComp1, Month.APRIL, 21, 2015);

            InvoiceDto invpurchase = createInvoice(po1.getId());
            //pol, poPlist1, total cost price: 90000000
            //payable: 90000000  sup1: YumChina      selfComp1: Johnson & Johnson
            so1 = createPurchaseOrder("sale", po1.getId(), soPlist1, selfComp1, cust1, Month.APRIL, 23, 2015);

            InvoiceDto invsale = createInvoice(so1.getId());
            //so1, poPlist6, total sales price: 15000000
            //receivable: 15000000  selfComp1: Johnson & Johnson   cust1: Walmart Retail

            recordpayment(InvTransactionType.PAYMENT, selfComp1, sup1, invpurchase, po1.getTotalAmount().toString(), Month.APRIL, 22, 2015, "Paid Supplier");
            //payable: 90000000 - 20000 = 89980000
            recordpayment(InvTransactionType.RECEIPT, selfComp1, cust1, invsale, so1.getTotalAmount().toString(), Month.APRIL, 24, 2015, "Received Money for Sales");
            //receivable: =15000000 - 1000 = 14999000

            response.setMyCompany(CompanyMapper.mapperEntityToDto.apply((Company) companyService.find(Company.class, selfComp1.getId())));
            response.setCustomer(CompanyMapper.mapperEntityToDto.apply((Company) companyService.find(Company.class, cust1.getId())));
            response.setSupplier(CompanyMapper.mapperEntityToDto.apply((Company) companyService.find(Company.class, sup1.getId())));
            response.setPurchase(PurchaseOrderMapper.mapperEntityToDto.apply((PurchaseOrder) companyService.find(PurchaseOrder.class, po1.getId())));
            response.setSale(PurchaseOrderMapper.mapperEntityToDto.apply((PurchaseOrder) companyService.find(PurchaseOrder.class, so1.getId())));


        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(RsStatusType.FAILURE);
            response.setErrorMessage(e.getMessage());

        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @RequestMapping(value = "/monthly.ws", method = RequestMethod.POST)
    public ResponseEntity<?> monthly(HttpServletRequest request) {

        TestBalanceResponse response = new TestBalanceResponse();
        response.setStatus(RsStatusType.SUCCESS);


        try {

            setUpMasterData();

            createMonthlyOrderTrans(Month.JANUARY, 2019, "50", "50");
            createMonthlyOrderTrans(Month.FEBRUARY, 2019, "50", "50");
//            createMonthlyOrderTrans(Month.MARCH, 2019, "50", "50");
//            createMonthlyOrderTrans(Month.APRIL, 2019, "50", "50");
//            createMonthlyOrderTrans(Month.MAY, 2019, "50", "50");
//            createMonthlyOrderTrans(Month.JUNE, 2019, "50", "50");
//            createMonthlyOrderTrans(Month.JULY, 2019, "50", "50");
//            createMonthlyOrderTrans(Month.AUGUST, 2019, "50", "50");
//            createMonthlyOrderTrans(Month.SEPTEMBER, 2019, "50", "50");
//            createMonthlyOrderTrans(Month.OCTOBER, 2019, "50", "50");
//            createMonthlyOrderTrans(Month.NOVEMBER, 2019, "50", "50");
//            createMonthlyOrderTrans(Month.DECEMBER, 2019, "50", "50");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(RsStatusType.FAILURE);
            response.setErrorMessage(e.getMessage());

        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    public void createMonthlyOrderTrans(Month month, int year, String purPer, String salePerc) throws Exception {
        createPOProductList();
        BigDecimal purPercent = new BigDecimal(purPer);
        BigDecimal salePercent = new BigDecimal(salePerc);
        BigDecimal cent = new BigDecimal("100");
        List<List<POProductDto>> pops = Arrays.asList(poPlist1, poPlist2, poPlist3, poPlist4, poPlist1, poPlist2, poPlist3, poPlist4, poPlist1, poPlist2, poPlist3, poPlist4);
        List<List<POProductDto>> sops = Arrays.asList(soPlist1, soPlist2, soPlist3, soPlist4, soPlist1, soPlist2, soPlist3, soPlist4, soPlist1, soPlist2, soPlist3, soPlist4);
        List<CompanyDto> suppliers = Arrays.asList(sup1, sup2, sup3, sup4, sup5, sup6, sup7, sup8, sup9, sup10);
        List<CompanyDto> customers = Arrays.asList(cust1, cust2, cust3, cust4, cust5, cust6, cust7, cust8, cust9, cust10);
        List<CompanyDto> self = Arrays.asList(selfComp1, selfComp2, selfComp3, selfComp1, selfComp2, selfComp3, selfComp1, selfComp2, selfComp3, selfComp1, selfComp2, selfComp3);

        int[] custVaraiant = {23, 67, 89, 44, 77, 46, 54, 65, 78, 44};
        int[] supVaraiant = {67, 37, 69, 74, 47, 96, 84, 25, 48, 54};

        for (List<POProductDto> popss : pops) {
            popss.forEach(p -> p.setQuantity(MathUtil.roundUp(p.getQuantity().multiply(purPercent).divide(cent, 3, RoundingMode.CEILING))));
        }
        for (List<POProductDto> popss : sops) {
            popss.forEach(p -> p.setQuantity(MathUtil.roundUp(p.getQuantity().multiply(salePercent).divide(cent, 3, RoundingMode.CEILING))));
        }

        int index = 0;
        for (CompanyDto supplier : suppliers) {


            po1 = createPurchaseOrder("purchase",null, pops.get(index), supplier, self.get(index), month, index + 1, year);
            InvoiceDto invPurchase = createInvoice(po1.getId());

            List<POProductDto> sopList = sops.get(index);
            sopList.forEach(s -> s.setLpoid(po1.getId()));
            so1 = createPurchaseOrder("sale", po1.getId(), sopList, self.get(index), customers.get(index), month, index + 5, year);
            InvoiceDto invSale = createInvoice(so1.getId());

            recordpayment(InvTransactionType.PAYMENT, self.get(index), supplier, invPurchase,
                    (po1.getTotalAmount().multiply(new BigDecimal(0.75))).toString(),
                    month, index + 7, year, "Paid Supplier");
            recordpayment(InvTransactionType.PAYMENT, self.get(index), supplier, invPurchase,
                    (po1.getTotalAmount().multiply(new BigDecimal(0.24))).toString(),
                    month, index + 7, year, "Paid Supplier");

            recordpayment(InvTransactionType.RECEIPT, self.get(index), customers.get(index), invSale,
                    (so1.getTotalAmount().multiply(new BigDecimal(0.85))).toString(),
                    month, index + 12, year, "Recieved from Customer");

            recordpayment(InvTransactionType.RECEIPT, self.get(index), customers.get(index), invSale,
                    (so1.getTotalAmount().multiply(new BigDecimal(0.14))).toString(),
                    month, index + 17, year, "Recieved from Customer");


            index = index + 1;

        }


    }

}





/*


delete from poptax;
delete from invproduct;
delete from poproduct;
delete from journal;
delete from invtransaction;
delete from invoice;
delete from purchaseOrder;
delete from product;
update company set dispcontactid = null;
update company set invcontactid = null;

delete from contact;
delete from company;
delete from account




 */
