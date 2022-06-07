package com.example.controller;

import com.example.common.util.DateUtil;
import com.example.constant.InvTransactionType;
import com.example.constant.InvoiceStatus;
import com.example.constant.OrderStatus;
import com.example.constant.OrderType;
import com.example.controller.manager.*;
import com.example.controller.service.*;
import com.example.entity.dto.order.*;
import com.example.entity.mapper.InvoiceMapper;
import com.example.entity.order.Invoice;
import com.example.entity.order.PurchaseOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Query;
import java.math.BigDecimal;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/test")
public class TestData {



    @Autowired
    protected PurchaseOrderService purchaseService;

    @Autowired
    protected PurchaseOrderManager purchaseManager;

    @Autowired
    protected InvoiceService invoiceService;

    @Autowired
    protected ProductManager productManager;

    @Autowired
    protected InvTransactionService invTransactionService;

    @Autowired
    protected CompanyService companyService;

    @Autowired
    protected CompanyManager companyManager;

    @Autowired
    protected ContactService contactService;

    @Autowired
    protected InvTransactionManager invTransactionManager;

    @Autowired
    protected AccountManager accountManager;


    //Test Data
    protected ProductDto product1, product2, product3, product4, product5, product6, product7, product8, product9, product10, product11, product12, product13,
            product14, product15, product16, product17, product18, product19, product20;

    protected List<ProductDto> productList = new ArrayList<>();

    protected List<POProductDto> poPlist1, poPlist2, poPlist3, poPlist4, poPlist5, soPlist1, soPlist2, soPlist3, soPlist4, soPlist5;


    protected POProductDto pop1,  pop2, pop3, pop4, pop5, pop6, pop7, pop8, pop9, pop10, pop11, pop12,pop13, pop14, pop15, pop16, pop17, pop18, pop19, pop20;
    protected POProductDto sop1,  sop2, sop3, sop4, sop5, sop6, sop7, sop8, sop9, sop10, sop11, sop12,sop13, sop14, sop15, sop16, sop17, sop18, sop19, sop20;

    protected CompanyDto selfComp1, selfComp2, selfComp3;
    protected CompanyDto sup1, sup2, sup3, sup4, sup5, sup6, sup7, sup8, sup9, sup10;
    protected CompanyDto cust1, cust2, cust3, cust4, cust5, cust6, cust7, cust8, cust9, cust10;
    protected CompanyDto agent1;
    protected PurchaseOrderDto po1, po2, po3, po4, po5, po6, po7, po8, po9, po10;
    protected PurchaseOrderDto so1, so2, so3, so4, so5, so6, so7, so8, so9, so10;

    protected void setUp() throws Exception{
        try {
            //deleteAllTransData();
            setUpMasterData();
            createPOProductList();
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    protected void setUpMasterData() throws Exception {
        createProducts();
        createCompanies();
        accountManager.createInternalAccountForTenant(companyService.getTenantId());

    }


    // TODO update instead of delete only for selected tenant

    protected void deleteAllTransData() {
        Query q1 = companyService.getEntityManager().createQuery("DELETE FROM InvProduct");
        Query q2 = companyService.getEntityManager().createQuery("DELETE FROM POProduct");
        Query q3 = companyService.getEntityManager().createQuery("DELETE FROM InvTransaction");
        Query q4 = companyService.getEntityManager().createQuery("DELETE FROM Invoice");
        Query q5 = companyService.getEntityManager().createQuery("DELETE FROM PurchaseOrder");
        Query q6 = companyService.getEntityManager().createQuery("UPDATE Company set dispcontactid = null");
        Query q7 = companyService.getEntityManager().createQuery("UPDATE Company set invcontactid = null");
        Query q8 = companyService.getEntityManager().createQuery("DELETE FROM Contact");
        Query q9 = companyService.getEntityManager().createQuery("DELETE FROM Company");

        companyService.getEntityManager().getTransaction().begin();
        q1.executeUpdate();
        companyService.getEntityManager().getTransaction().commit();
        companyService.getEntityManager().getTransaction().begin();
        q2.executeUpdate();
        companyService.getEntityManager().getTransaction().commit();
        companyService.getEntityManager().getTransaction().begin();
        q3.executeUpdate();
        companyService.getEntityManager().getTransaction().commit();
        companyService.getEntityManager().getTransaction().begin();
        q4.executeUpdate();
        companyService.getEntityManager().getTransaction().commit();
        companyService.getEntityManager().getTransaction().begin();
        q5.executeUpdate();
        companyService.getEntityManager().getTransaction().commit();
        companyService.getEntityManager().getTransaction().begin();
        q6.executeUpdate();
        companyService.getEntityManager().getTransaction().commit();
        companyService.getEntityManager().getTransaction().begin();
        q7.executeUpdate();
        companyService.getEntityManager().getTransaction().commit();
        companyService.getEntityManager().getTransaction().begin();
        q8.executeUpdate();
        companyService.getEntityManager().getTransaction().commit();
        companyService.getEntityManager().getTransaction().begin();
        q9.executeUpdate();
        companyService.getEntityManager().getTransaction().commit();

    }


    /**
     *  SETUP Products, companies
     *
     */
    protected void createCompanies() throws Exception {




        long tenantId = companyService.getTenantId();

        selfComp1 = new CompanyDto("self", "Johnson & Johnson", "203-495-5943", "johnson@gmail.com", "3489432", "8487589475", "4343435", "Frost Bank", "Checking", "28398434", "1232444");
        selfComp1 = companyManager.saveCompany(selfComp1);
        ContactDto selfContact1 = new ContactDto("Paul Jones", "2345 Bryson Ave.", "", "Austin", "Texas", "2343", "454-484-5893", "221-326-7878", "pjones@gmail.com");
        selfContact1 = contactService.saveContact(selfComp1.getId(), selfContact1, tenantId);
        ContactDto selfContact2 = new ContactDto("Annette Diaz", "1267 Commerce Ave.", "", "Austin", "Texas", "2343", "454-484-5893", "221-326-7878", "pjones@gmail.com");
        selfContact2 = contactService.saveContact(selfComp1.getId(), selfContact2, tenantId);
        selfComp1.setInvoiceContactId(selfContact1.getId());
        selfComp1.setDispatchContactId(selfContact2.getId());
        companyManager.saveCompany(selfComp1);

        selfComp2 = new CompanyDto("self", "Applied Materials", "203-495-5943", "johnson@gmail.com", "3489432", "8487589475", "4343435", "Frost Bank", "Checking", "28398434", "1232444");
        selfComp2 = companyManager.saveCompany(selfComp2);
        ContactDto selfContact3 = new ContactDto("Jason Phillips", "2345 Bryson Ave.", "", "Austin", "Texas", "2343", "454-484-5893", "221-326-7878", "pjones@gmail.com");
        selfContact3 = contactService.saveContact(selfComp2.getId(), selfContact3, tenantId);
        ContactDto selfContact4 = new ContactDto("Angelina Greer", "1267 Commerce Ave.", "", "Austin", "Texas", "2343", "454-484-5893", "221-326-7878", "pjones@gmail.com");
        selfContact4 = contactService.saveContact(selfComp2.getId(), selfContact4, tenantId);
        selfComp2.setInvoiceContactId(selfContact3.getId());
        selfComp2.setDispatchContactId(selfContact4.getId());
        companyManager.saveCompany(selfComp2);

        selfComp3 = new CompanyDto("self", "United Technologies", "203-495-5943", "johnson@gmail.com", "3489432", "8487589475", "4343435", "Frost Bank", "Checking", "28398434", "1232444");
        selfComp3 = companyManager.saveCompany(selfComp3);
        ContactDto selfContact5 = new ContactDto("Tania Huber", "2345 Bryson Ave.", "", "Austin", "Texas", "2343", "454-484-5893", "221-326-7878", "pjones@gmail.com");
        selfContact5 = contactService.saveContact(selfComp3.getId(), selfContact5, tenantId);
        ContactDto selfContact6 = new ContactDto("Immanuel Glenn", "1267 Commerce Ave.", "", "Austin", "Texas", "2343", "454-484-5893", "221-326-7878", "pjones@gmail.com");
        selfContact6 = contactService.saveContact(selfComp3.getId(), selfContact6, tenantId);
        selfComp3.setInvoiceContactId(selfContact5.getId());
        selfComp3.setDispatchContactId(selfContact6.getId());
        companyManager.saveCompany(selfComp3);


        sup1 = new CompanyDto("supplier", "YumChina", "343-434-5479", "yumchk@gmail.com", "4848955", "483985", "343554", "Bank of China", "Checking", "34445545", "344555");
        sup1 = companyManager.saveCompany(sup1);
        ContactDto supContact1 = new ContactDto("Peter Chan", "3453 Indigo St.", "", "San Antonio", "TX", "33234", "383-384-3992", "323-495-5858", "chan@gmail.com");
        supContact1 = contactService.saveContact(sup1.getId(), supContact1, tenantId);
        ContactDto supContact2 = new ContactDto("Linda Perez", "3453 Indigo St.", "", "San Antonio", "TX", "33234", "383-384-3992", "323-495-5858", "chan@gmail.com");
        supContact2 = contactService.saveContact(sup1.getId(), supContact2, tenantId);
        sup1.setInvoiceContactId(supContact1.getId());
        sup1.setDispatchContactId(supContact2.getId());
        companyManager.saveCompany(sup1);

        sup2 = new CompanyDto("supplier", "XPO Logistics", "343-434-5479", "yumchk@gmail.com", "4848955", "483985", "343554", "Bank of China", "Checking", "34445545", "344555");
        sup2 = companyManager.saveCompany(sup2);
        ContactDto supContact3 = new ContactDto("Armani Snow", "3453 Indigo St.", "", "San Antonio", "TX", "33234", "383-384-3992", "323-495-5858", "chan@gmail.com");
        supContact3 = contactService.saveContact(sup2.getId(), supContact3, tenantId);
        ContactDto supContact4 = new ContactDto("Destin Sellers", "3453 Indigo St.", "", "San Antonio", "TX", "33234", "383-384-3992", "323-495-5858", "chan@gmail.com");
        supContact4 = contactService.saveContact(sup2.getId(), supContact4, tenantId);
        sup2.setInvoiceContactId(supContact3.getId());
        sup2.setDispatchContactId(supContact4.getId());
        companyManager.saveCompany(sup2);

        sup3 = new CompanyDto("supplier", "Xerox", "343-434-5479", "yumchk@gmail.com", "4848955", "483985", "343554", "Bank of China", "Checking", "34445545", "344555");
        sup3 = companyManager.saveCompany(sup3);
        ContactDto supContact5 = new ContactDto("Adam Hodges", "3453 Indigo St.", "", "San Antonio", "TX", "33234", "383-384-3992", "323-495-5858", "chan@gmail.com");
        supContact5 = contactService.saveContact(sup3.getId(), supContact5, tenantId);
        ContactDto supContact6 = new ContactDto("Layla Frey", "3453 Indigo St.", "", "San Antonio", "TX", "33234", "383-384-3992", "323-495-5858", "chan@gmail.com");
        supContact6 = contactService.saveContact(sup3.getId(), supContact6, tenantId);
        sup3.setInvoiceContactId(supContact5.getId());
        sup3.setDispatchContactId(supContact6.getId());
        companyManager.saveCompany(sup3);

        sup4 = new CompanyDto("supplier", "Xcel Energy", "343-434-5479", "yumchk@gmail.com", "4848955", "483985", "343554", "Bank of China", "Checking", "34445545", "344555");
        sup4 = companyManager.saveCompany(sup4);
        ContactDto supContact7 = new ContactDto("Emelia Lewis", "3453 Indigo St.", "", "San Antonio", "TX", "33234", "383-384-3992", "323-495-5858", "chan@gmail.com");
        supContact7 = contactService.saveContact(sup4.getId(), supContact7, tenantId);
        ContactDto supContact8 = new ContactDto("Samuel Frye", "3453 Indigo St.", "", "San Antonio", "TX", "33234", "383-384-3992", "323-495-5858", "chan@gmail.com");
        supContact8 = contactService.saveContact(sup4.getId(), supContact8, tenantId);
        sup4.setInvoiceContactId(supContact1.getId());
        sup4.setDispatchContactId(supContact2.getId());
        companyManager.saveCompany(sup4);

        sup5 = new CompanyDto("supplier", "WPP plc", "343-434-5479", "yumchk@gmail.com", "4848955", "483985", "343554", "Bank of China", "Checking", "34445545", "344555");
        sup5 = companyManager.saveCompany(sup5);
        ContactDto supContact9 = new ContactDto("Jocelyn Chandler", "3453 Indigo St.", "", "San Antonio", "TX", "33234", "383-384-3992", "323-495-5858", "chan@gmail.com");
        supContact9 = contactService.saveContact(sup5.getId(), supContact9, tenantId);
        ContactDto supContact10 = new ContactDto("Keenan Calhoun", "3453 Indigo St.", "", "San Antonio", "TX", "33234", "383-384-3992", "323-495-5858", "chan@gmail.com");
        supContact10 = contactService.saveContact(sup5.getId(), supContact10, tenantId);
        sup5.setInvoiceContactId(supContact9.getId());
        sup5.setDispatchContactId(supContact10.getId());
        companyManager.saveCompany(sup5);

        sup6 = new CompanyDto("supplier", "World Fuel Services", "343-434-5479", "yumchk@gmail.com", "4848955", "483985", "343554", "Bank of China", "Checking", "34445545", "344555");
        sup6 = companyManager.saveCompany(sup6);
        ContactDto supContact11 = new ContactDto("Victoria Morse", "3453 Indigo St.", "", "San Antonio", "TX", "33234", "383-384-3992", "323-495-5858", "chan@gmail.com");
        supContact11 = contactService.saveContact(sup6.getId(), supContact11, tenantId);
        ContactDto supContact12 = new ContactDto("Skylar Vaughan", "3453 Indigo St.", "", "San Antonio", "TX", "33234", "383-384-3992", "323-495-5858", "chan@gmail.com");
        supContact12 = contactService.saveContact(sup6.getId(), supContact12, tenantId);
        sup6.setInvoiceContactId(supContact11.getId());
        sup6.setDispatchContactId(supContact12.getId());
        companyManager.saveCompany(sup6);

        sup7 = new CompanyDto("supplier", "Willis Towers Watson", "343-434-5479", "yumchk@gmail.com", "4848955", "483985", "343554", "Bank of China", "Checking", "34445545", "344555");
        sup7 = companyManager.saveCompany(sup7);
        ContactDto supContact13 = new ContactDto("Kailee Reilly", "3453 Indigo St.", "", "San Antonio", "TX", "33234", "383-384-3992", "323-495-5858", "chan@gmail.com");
        supContact13 = contactService.saveContact(sup7.getId(), supContact13, tenantId);
        ContactDto supContact14 = new ContactDto("Sarai Barry", "3453 Indigo St.", "", "San Antonio", "TX", "33234", "383-384-3992", "323-495-5858", "chan@gmail.com");
        supContact14 = contactService.saveContact(sup7.getId(), supContact14, tenantId);
        sup7.setInvoiceContactId(supContact13.getId());
        sup7.setDispatchContactId(supContact14.getId());
        companyManager.saveCompany(sup7);

        sup8 = new CompanyDto("supplier", "Whirlpool Corporation", "343-434-5479", "yumchk@gmail.com", "4848955", "483985", "343554", "Bank of China", "Checking", "34445545", "344555");
        sup8 = companyManager.saveCompany(sup8);
        ContactDto supContact15 = new ContactDto("Azaria Mathews", "3453 Indigo St.", "", "San Antonio", "TX", "33234", "383-384-3992", "323-495-5858", "chan@gmail.com");
        supContact15 = contactService.saveContact(sup8.getId(), supContact15, tenantId);
        ContactDto supContact16 = new ContactDto("Shelby Green", "3453 Indigo St.", "", "San Antonio", "TX", "33234", "383-384-3992", "323-495-5858", "chan@gmail.com");
        supContact16 = contactService.saveContact(sup8.getId(), supContact16, tenantId);
        sup8.setInvoiceContactId(supContact15.getId());
        sup8.setDispatchContactId(supContact16.getId());
        companyManager.saveCompany(sup8);

        sup9 = new CompanyDto("supplier", "WestRock", "343-434-5479", "yumchk@gmail.com", "4848955", "483985", "343554", "Bank of China", "Checking", "34445545", "344555");
        sup9 = companyManager.saveCompany(sup9);
        ContactDto supContact17 = new ContactDto("Claudia Gibson", "3453 Indigo St.", "", "San Antonio", "TX", "33234", "383-384-3992", "323-495-5858", "chan@gmail.com");
        supContact17 = contactService.saveContact(sup9.getId(), supContact17, tenantId);
        ContactDto supContact18 = new ContactDto("Chace Barrett", "3453 Indigo St.", "", "San Antonio", "TX", "33234", "383-384-3992", "323-495-5858", "chan@gmail.com");
        supContact18 = contactService.saveContact(sup9.getId(), supContact18, tenantId);
        sup9.setInvoiceContactId(supContact17.getId());
        sup9.setDispatchContactId(supContact18.getId());
        companyManager.saveCompany(sup9);

        sup10 = new CompanyDto("supplier", "Westlake Chemical", "343-434-5479", "yumchk@gmail.com", "4848955", "483985", "343554", "Bank of China", "Checking", "34445545", "344555");
        sup10 = companyManager.saveCompany(sup10);
        ContactDto supContact19 = new ContactDto("Ross Phillips", "3453 Indigo St.", "", "San Antonio", "TX", "33234", "383-384-3992", "323-495-5858", "chan@gmail.com");
        supContact19 = contactService.saveContact(sup10.getId(), supContact19, tenantId);
        ContactDto supContact20 = new ContactDto("Bennett Jackson", "3453 Indigo St.", "", "San Antonio", "TX", "33234", "383-384-3992", "323-495-5858", "chan@gmail.com");
        supContact20 = contactService.saveContact(sup10.getId(), supContact20, tenantId);
        sup10.setInvoiceContactId(supContact19.getId());
        sup10.setDispatchContactId(supContact20.getId());
        companyManager.saveCompany(sup10);


        cust1 = new CompanyDto("customer", "Walmart Retail", "343-434-5479", "walmart@gmail.com", "4848955", "483985", "343554", "Bank of America", "Checking", "34445545", "344555");
        cust1 = companyManager.saveCompany(cust1);
        ContactDto custContact1 = new ContactDto("Marie Phillo", "2334 Richard St.", "", "San Antonio", "TX", "33234", "383-384-3992", "323-495-5858", "chan@gmail.com");
        custContact1 = contactService.saveContact(cust1.getId(), custContact1, tenantId);
        ContactDto custContact2 = new ContactDto("Jason Lands", "2334 Richard St.", "", "San Antonio", "TX", "33234", "383-384-3992", "323-495-5858", "chan@gmail.com");
        custContact2 = contactService.saveContact(cust1.getId(), custContact2, tenantId);
        cust1.setInvoiceContactId(custContact1.getId());
        cust1.setDispatchContactId(custContact2.getId());
        companyManager.saveCompany(cust1);

        cust2 = new CompanyDto("customer", "Walgreens Boots Alliance", "343-434-5479", "walmart@gmail.com", "4848955", "483985", "343554", "Bank of America", "Checking", "34445545", "344555");
        cust2 = companyManager.saveCompany(cust2);
        ContactDto custContact3 = new ContactDto("Riya Vang", "2334 Richard St.", "", "San Antonio", "TX", "33234", "383-384-3992", "323-495-5858", "chan@gmail.com");
        custContact3 = contactService.saveContact(cust2.getId(), custContact3, tenantId);
        ContactDto custContact4 = new ContactDto("Abram Casey", "2334 Richard St.", "", "San Antonio", "TX", "33234", "383-384-3992", "323-495-5858", "chan@gmail.com");
        custContact4 = contactService.saveContact(cust2.getId(), custContact4, tenantId);
        cust2.setInvoiceContactId(custContact3.getId());
        cust2.setDispatchContactId(custContact4.getId());
        companyManager.saveCompany(cust2);


        cust3 = new CompanyDto("customer", "Valero", "343-434-5479", "walmart@gmail.com", "4848955", "483985", "343554", "Bank of America", "Checking", "34445545", "344555");
        cust3 = companyManager.saveCompany(cust3);
        ContactDto custContact5 = new ContactDto("Gisselle Hines", "2334 Richard St.", "", "San Antonio", "TX", "33234", "383-384-3992", "323-495-5858", "chan@gmail.com");
        custContact5 = contactService.saveContact(cust3.getId(), custContact5, tenantId);
        ContactDto custContact6 = new ContactDto("Avery Davila", "2334 Richard St.", "", "San Antonio", "TX", "33234", "383-384-3992", "323-495-5858", "chan@gmail.com");
        custContact6 = contactService.saveContact(cust3.getId(), custContact6, tenantId);
        cust3.setInvoiceContactId(custContact5.getId());
        cust3.setDispatchContactId(custContact6.getId());
        companyManager.saveCompany(cust3);


        cust4 = new CompanyDto("customer", "Wabtec Corporation", "343-434-5479", "walmart@gmail.com", "4848955", "483985", "343554", "Bank of America", "Checking", "34445545", "344555");
        cust4 = companyManager.saveCompany(cust4);
        ContactDto custContact7 = new ContactDto("Sonny Tanner", "2334 Richard St.", "", "San Antonio", "TX", "33234", "383-384-3992", "323-495-5858", "chan@gmail.com");
        custContact7 = contactService.saveContact(cust4.getId(), custContact7, tenantId);
        ContactDto custContact8 = new ContactDto("Brooke Rowe", "2334 Richard St.", "", "San Antonio", "TX", "33234", "383-384-3992", "323-495-5858", "chan@gmail.com");
        custContact8 = contactService.saveContact(cust4.getId(), custContact8, tenantId);
        cust4.setInvoiceContactId(custContact7.getId());
        cust4.setDispatchContactId(custContact8.getId());
        companyManager.saveCompany(cust4);


        cust5 = new CompanyDto("customer", "W. W. Grainger", "343-434-5479", "walmart@gmail.com", "4848955", "483985", "343554", "Bank of America", "Checking", "34445545", "344555");
        cust5 = companyManager.saveCompany(cust5);
        ContactDto custContact9 = new ContactDto("Lawson Nelson", "2334 Richard St.", "", "San Antonio", "TX", "33234", "383-384-3992", "323-495-5858", "chan@gmail.com");
        custContact9 = contactService.saveContact(cust5.getId(), custContact9, tenantId);
        ContactDto custContact10 = new ContactDto("Alexa Nicholson", "2334 Richard St.", "", "San Antonio", "TX", "33234", "383-384-3992", "323-495-5858", "chan@gmail.com");
        custContact10 = contactService.saveContact(cust5.getId(), custContact10, tenantId);
        cust5.setInvoiceContactId(custContact9.getId());
        cust5.setDispatchContactId(custContact10.getId());
        companyManager.saveCompany(cust5);


        cust6 = new CompanyDto("customer", "Voya Financial", "343-434-5479", "walmart@gmail.com", "4848955", "483985", "343554", "Bank of America", "Checking", "34445545", "344555");
        cust6 = companyManager.saveCompany(cust6);
        ContactDto custContact11 = new ContactDto("Tony Sandoval", "2334 Richard St.", "", "San Antonio", "TX", "33234", "383-384-3992", "323-495-5858", "chan@gmail.com");
        custContact11 = contactService.saveContact(cust6.getId(), custContact11, tenantId);
        ContactDto custContact12 = new ContactDto("Elle Odom", "2334 Richard St.", "", "San Antonio", "TX", "33234", "383-384-3992", "323-495-5858", "chan@gmail.com");
        custContact12 = contactService.saveContact(cust6.getId(), custContact12, tenantId);
        cust6.setInvoiceContactId(custContact11.getId());
        cust6.setDispatchContactId(custContact12.getId());
        companyManager.saveCompany(cust6);


        cust7 = new CompanyDto("customer", "Vistra Energy", "343-434-5479", "walmart@gmail.com", "4848955", "483985", "343554", "Bank of America", "Checking", "34445545", "344555");
        cust7 = companyManager.saveCompany(cust7);
        ContactDto custContact13 = new ContactDto("Meredith Molina", "2334 Richard St.", "", "San Antonio", "TX", "33234", "383-384-3992", "323-495-5858", "chan@gmail.com");
        custContact13 = contactService.saveContact(cust7.getId(), custContact13, tenantId);
        ContactDto custContact14 = new ContactDto("Amani Boyd", "2334 Richard St.", "", "San Antonio", "TX", "33234", "383-384-3992", "323-495-5858", "chan@gmail.com");
        custContact14 = contactService.saveContact(cust7.getId(), custContact14, tenantId);
        cust7.setInvoiceContactId(custContact13.getId());
        cust7.setDispatchContactId(custContact14.getId());
        companyManager.saveCompany(cust7);


        cust8 = new CompanyDto("customer", "Visa Inc.", "343-434-5479", "walmart@gmail.com", "4848955", "483985", "343554", "Bank of America", "Checking", "34445545", "344555");
        cust8 = companyManager.saveCompany(cust8);
        ContactDto custContact15 = new ContactDto("Marie Phillo", "2334 Richard St.", "", "San Antonio", "TX", "33234", "383-384-3992", "323-495-5858", "chan@gmail.com");
        custContact15 = contactService.saveContact(cust8.getId(), custContact15, tenantId);
        ContactDto custContact16 = new ContactDto("Jason Watts", "2334 Richard St.", "", "San Antonio", "TX", "33234", "383-384-3992", "323-495-5858", "chan@gmail.com");
        custContact16 = contactService.saveContact(cust8.getId(), custContact16, tenantId);
        cust8.setInvoiceContactId(custContact15.getId());
        cust8.setDispatchContactId(custContact16.getId());
        companyManager.saveCompany(cust8);


        cust9 = new CompanyDto("customer", "Verizon", "343-434-5479", "walmart@gmail.com", "4848955", "483985", "343554", "Bank of America", "Checking", "34445545", "344555");
        cust9 = companyManager.saveCompany(cust9);
        ContactDto custContact17 = new ContactDto("Elianna Perry", "2334 Richard St.", "", "San Antonio", "TX", "33234", "383-384-3992", "323-495-5858", "chan@gmail.com");
        custContact17 = contactService.saveContact(cust9.getId(), custContact17, tenantId);
        ContactDto custContact18 = new ContactDto("Kolton Friedman", "2334 Richard St.", "", "San Antonio", "TX", "33234", "383-384-3992", "323-495-5858", "chan@gmail.com");
        custContact18 = contactService.saveContact(cust9.getId(), custContact18, tenantId);
        cust9.setInvoiceContactId(custContact17.getId());
        cust9.setDispatchContactId(custContact18.getId());
        companyManager.saveCompany(cust9);


        cust10 = new CompanyDto("customer", "Veritiv", "343-434-5479", "walmart@gmail.com", "4848955", "483985", "343554", "Bank of America", "Checking", "34445545", "344555");
        cust10 = companyManager.saveCompany(cust10);
        ContactDto custContact19 = new ContactDto("George Bassett", "2334 Richard St.", "", "San Antonio", "TX", "33234", "383-384-3992", "323-495-5858", "chan@gmail.com");
        custContact19 = contactService.saveContact(cust10.getId(), custContact19, tenantId);
        ContactDto custContact20 = new ContactDto("Frida Wilson", "2334 Richard St.", "", "San Antonio", "TX", "33234", "383-384-3992", "323-495-5858", "chan@gmail.com");
        custContact20 = contactService.saveContact(cust10.getId(), custContact20, tenantId);
        cust10.setInvoiceContactId(custContact19.getId());
        cust10.setDispatchContactId(custContact20.getId());
        companyManager.saveCompany(cust10);

        agent1 = new CompanyDto("agent", "Blue World", "343-434-5479", "blueworld@gmail.com", "4848955", "483985", "343554", "Bank of America", "Checking", "34445545", "344555");
        agent1 = companyManager.saveCompany(agent1);
        ContactDto agentContact1 = new ContactDto("Zobie Bassett", "2334 Richard St.", "", "San Antonio", "TX", "33234", "383-384-3992", "323-495-5858", "chan@gmail.com");
        agentContact1 = contactService.saveContact(agent1.getId(), custContact19, tenantId);
        ContactDto agentContact2 = new ContactDto("Robie Wilson", "2334 Richard St.", "", "San Antonio", "TX", "33234", "383-384-3992", "323-495-5858", "chan@gmail.com");
        agentContact2 = contactService.saveContact(agent1.getId(), custContact20, tenantId);
        agent1.setInvoiceContactId(agentContact1.getId());
        agent1.setDispatchContactId(agentContact2.getId());
        companyManager.saveCompany(agent1);



        // create 20 product
        // 3 self
        // 10 customers
        // 10 supplier
        // create 10 polist


    }

    protected void createProducts() throws Exception {

        //  Products -----------

        product1 = new ProductDto("Broaching Machine", "10000", "11000", "100",
                2384485L, "ADK48394", "Machinery", "Metalworking",
                "UOM3434", "hsn3483948", "5","5", "10", "Model 3454");
        product1 = productManager.save(product1);

        product2 = new ProductDto("Shear", "10000", "11000", "100",
                2384485L, "ADK48394", "Machinery", "Metalworking",
                "UOM3434", "hsn3483948", "5","5", "10", "Model 3454");
        product2 = productManager.save(product2);

        product3 = new ProductDto("Gear Shaper","10000", "11000", "100",
                2384485L, "ADK48394", "Machinery", "Metalworking",
                "UOM3434", "hsn3483948", "5","5", "10", "Model 3454");
        product3 = productManager.save(product3);

        product4 = new ProductDto("Hone", "10000", "11000", "100",
                2384485L, "ADK48394", "Machinery", "Metalworking",
                "UOM3434", "hsn3483948", "5","5", "10", "Model 3454");
        product4 = productManager.save(product4);

        product5 = new ProductDto("Lathe", "10000", "11000", "100",
                2384485L, "ADK48394", "Machinery", "Metalworking",
                "UOM3434", "hsn3483948", "5","5", "10", "Model 3454");
        product5 = productManager.save(product5);

        product6 = new ProductDto("Screw Machine", "10000", "11000", "100",
                2384485L, "ADK48394", "Machinery", "Metalworking",
                "UOM3434", "hsn3483948", "5","5", "10", "Model 3454");
        product6 = productManager.save(product6);

        product7 = new ProductDto("Milling Machine", "10000", "11000", "100",
                2384485L, "ADK48394", "Machinery", "Metalworking",
                "UOM3434", "hsn3483948", "5","5", "10", "Model 3454");
        product7 = productManager.save(product7);

        product8 = new ProductDto("Saw", "10000", "11000", "100",
                2384485L, "ADK48394", "Machinery", "Metalworking",
                "UOM3434", "hsn3483948", "5","5", "10", "Model 3454");
        product8 = productManager.save(product8);

        product9 = new ProductDto("Pine Board", "10000", "11000", "100",
                2384485L, "ADK48394", "Machinery", "Metalworking",
                "UOM3434", "hsn3483948", "5","5", "10", "Model 3454");
        product9 = productManager.save(product9);

        product10 = new ProductDto("Oak Board", "10000", "11000", "100",
                2384485L, "ADK48394", "Machinery", "Metalworking",
                "UOM3434", "hsn3483948", "5","5", "10", "Model 3454");
        product10 = productManager.save(product10);

        product11 = new ProductDto("Plywood", "10000", "11000", "100",
                2384485L, "ADK48394", "Machinery", "Metalworking",
                "UOM3434", "hsn3483948","5","5", "10", "Model 3454");
        product11 = productManager.save(product11);

        product12 = new ProductDto("Sheet Metal","10000", "11000",  "100",
                2384485L, "ADK48394", "Machinery", "Metalworking",
                "UOM3434", "hsn3483948", "5","5", "10", "Model 3454");
        product12 = productManager.save(product12);

        product13 = new ProductDto("Metal Rod", "10000", "11000", "100",
                2384485L, "ADK48394", "Machinery", "Metalworking",
                "UOM3434", "hsn3483948","5","5", "10", "Model 3454");
        product13 = productManager.save(product13);

        product14 = new ProductDto("Metal Flat Bar", "10000", "11000", "100",
                2384485L, "ADK48394", "Machinery", "Metalworking",
                "UOM3434", "hsn3483948","5","5", "10", "Model 3454");
        product14 = productManager.save(product14);

        product15 = new ProductDto("Drill", "10000", "11000", "100",
                2384485L, "ADK48394", "Machinery", "Metalworking",
                "UOM3434", "hsn3483948", "5","5", "10", "Model 3454");
        product15 = productManager.save(product15);

        product16 = new ProductDto("Screw", "10000", "11000", "100",
                2384485L, "ADK48394", "Machinery", "Metalworking",
                "UOM3434", "hsn3483948","5","5", "10", "Model 3454");
        product16 = productManager.save(product16);

        product17 = new ProductDto("Nail","10000", "11000", "100",
                2384485L, "ADK48394", "Machinery", "Metalworking",
                "UOM3434", "hsn3483948", "5","5", "10", "Model 3454");
        product17 = productManager.save(product17);

        product18 = new ProductDto("Hammer","10000", "11000", "100",
                2384485L, "ADK48394", "Machinery", "Metalworking",
                "UOM3434", "hsn3483948", "5","5", "10", "Model 3454");
        product18 = productManager.save(product18);

        product19 = new ProductDto("Beam", "10000", "11000", "100",
                2384485L, "ADK48394", "Machinery", "Metalworking",
                "UOM3434", "hsn3483948", "5","5", "10", "Model 3454");
        product19 = productManager.save(product19);

        product20 = new ProductDto("Borer", "10000", "11000", "100",
                2384485L, "ADK48394", "Machinery", "Metalworking",
                "UOM3434", "hsn3483948", "5","5", "10", "Model 3454");
        product20 = productManager.save(product20);

        productList.add(product1);
        productList.add(product2);
        productList.add(product3);
        productList.add(product4);
        productList.add(product5);
        productList.add(product6);
        productList.add(product7);
        productList.add(product8);
        productList.add(product9);
        productList.add(product10);
        productList.add(product11);
        productList.add(product12);
        productList.add(product13);
        productList.add(product14);
        productList.add(product15);
        productList.add(product16);
        productList.add(product17);
        productList.add(product18);
        productList.add(product19);
        productList.add(product20);
    }



    protected void createPOProductList() {
        pop1 = new POProductDto(product1.getId(), product1.getCostPrice(), product1.getQuantity());
        pop2 = new POProductDto(product2.getId(), product2.getCostPrice(), product2.getQuantity());
        pop3 = new POProductDto(product3.getId(), product3.getCostPrice(), product3.getQuantity());
        pop4 = new POProductDto(product4.getId(), product4.getCostPrice(), product4.getQuantity());
        pop5 = new POProductDto(product5.getId(), product5.getCostPrice(),  product5.getQuantity());
        pop6 = new POProductDto(product6.getId(), product6.getCostPrice(),  product6.getQuantity());
        pop7 = new POProductDto(product7.getId(), product7.getCostPrice(),  product7.getQuantity());
        pop8 = new POProductDto(product8.getId(), product8.getCostPrice(),  product8.getQuantity());
        pop9 = new POProductDto(product9.getId(), product9.getCostPrice(),  product9.getQuantity());
        pop10 = new POProductDto(product10.getId(), product10.getCostPrice(),  product10.getQuantity());
        pop11 = new POProductDto(product11.getId(), product11.getCostPrice(),  product11.getQuantity());
        pop12 = new POProductDto(product12.getId(), product12.getCostPrice(),  product12.getQuantity());
        pop13 = new POProductDto(product13.getId(), product13.getCostPrice(),  product13.getQuantity());
        pop14 = new POProductDto(product14.getId(), product14.getCostPrice(),  product14.getQuantity());
        pop15 = new POProductDto(product15.getId(), product15.getCostPrice(),  product15.getQuantity());
        pop16 = new POProductDto(product16.getId(), product16.getCostPrice(),  product16.getQuantity());
        pop17 = new POProductDto(product17.getId(), product17.getCostPrice(),  product17.getQuantity());
        pop18 = new POProductDto(product18.getId(), product18.getCostPrice(),  product18.getQuantity());
        pop19 = new POProductDto(product19.getId(), product19.getCostPrice(),  product19.getQuantity());
        pop20 = new POProductDto(product20.getId(), product20.getCostPrice(),  product20.getQuantity());

        sop1 = new POProductDto(product1.getId(), product1.getSalesPrice(), product1.getQuantity() );
        sop2 = new POProductDto(product2.getId(), product2.getSalesPrice(), product2.getQuantity());
        sop3 = new POProductDto(product3.getId(), product3.getSalesPrice(), product3.getQuantity() );
        sop4 = new POProductDto(product4.getId(), product4.getSalesPrice(), product4.getQuantity() );
        sop5 = new POProductDto(product5.getId(), product5.getSalesPrice(), product5.getQuantity() );
        sop6 = new POProductDto(product6.getId(), product6.getSalesPrice(), product6.getQuantity() );
        sop7 = new POProductDto(product7.getId(), product7.getSalesPrice(), product7.getQuantity() );
        sop8 = new POProductDto(product8.getId(), product8.getSalesPrice(), product8.getQuantity() );
        sop9 = new POProductDto(product9.getId(), product9.getSalesPrice(), product9.getQuantity() );
        sop10 = new POProductDto(product10.getId(), product10.getSalesPrice(), product10.getQuantity() );
        sop11 = new POProductDto(product11.getId(), product11.getSalesPrice(), product11.getQuantity() );
        sop12 = new POProductDto(product12.getId(), product12.getSalesPrice(), product12.getQuantity() );
        sop13 = new POProductDto(product13.getId(), product13.getSalesPrice(), product13.getQuantity() );
        sop14 = new POProductDto(product14.getId(), product14.getSalesPrice(), product14.getQuantity() );
        sop15 = new POProductDto(product15.getId(), product15.getSalesPrice(), product15.getQuantity() );
        sop16 = new POProductDto(product16.getId(), product16.getSalesPrice(), product16.getQuantity() );
        sop17 = new POProductDto(product17.getId(), product17.getSalesPrice(), product17.getQuantity() );
        sop18 = new POProductDto(product18.getId(), product18.getSalesPrice(), product18.getQuantity());
        sop19 = new POProductDto(product19.getId(), product19.getSalesPrice(), product19.getQuantity() );
        sop20 = new POProductDto(product20.getId(), product20.getSalesPrice(), product20.getQuantity());


        poPlist1 = new ArrayList<>();
        poPlist1.add(pop1);
        poPlist1.add(pop2);
        poPlist1.add(pop3);
        poPlist1.add(pop4);
        poPlist1.add(pop5);


        poPlist2 = new ArrayList<>();
        poPlist2.add(pop6);
        poPlist2.add(pop7);
        poPlist2.add(pop8);
        poPlist2.add(pop9);
        poPlist2.add(pop10);

        poPlist3 = new ArrayList<>();
        poPlist3.add(pop11);
        poPlist3.add(pop12);
        poPlist3.add(pop13);
        poPlist3.add(pop14);
        poPlist3.add(pop15);

        poPlist4 = new ArrayList<>();
        poPlist4.add(pop16);
        poPlist4.add(pop17);
        poPlist4.add(pop18);
        poPlist4.add(pop19);
        poPlist4.add(pop20);

        poPlist5 = new ArrayList<>();
        poPlist5.add(pop15);
        poPlist5.add(pop16);
        poPlist5.add(pop17);
        poPlist5.add(pop20);
        poPlist5.add(pop3);

        soPlist1 = new ArrayList<>();
        soPlist1.add(sop1);
        soPlist1.add(sop2);
        soPlist1.add(sop3);
        soPlist1.add(sop4);
        soPlist1.add(sop5);


        soPlist2 = new ArrayList<>();
        soPlist2.add(sop6);
        soPlist2.add(sop7);
        soPlist2.add(sop8);
        soPlist2.add(sop9);
        soPlist2.add(sop10);

        soPlist3 = new ArrayList<>();
        soPlist3.add(sop11);
        soPlist3.add(sop12);
        soPlist3.add(sop13);
        soPlist3.add(sop14);
        soPlist3.add(sop15);

        soPlist4 = new ArrayList<>();
        soPlist4.add(sop16);
        soPlist4.add(sop17);
        soPlist4.add(sop18);
        soPlist4.add(sop19);
        soPlist4.add(sop20);

        soPlist5 = new ArrayList<>();
        soPlist5.add(sop15);
        soPlist5.add(sop16);
        soPlist5.add(sop17);
        soPlist5.add(sop20);
        soPlist5.add(sop3);

    }

    protected PurchaseOrderDto createPurchaseOrder(String type, Long lpoid, List<POProductDto> poPlist, CompanyDto sup, CompanyDto cust, Month month, int day, int year) throws Exception {

        poPlist.forEach(pop-> {
            List<POPTaxDto> taxes = new ArrayList<>();
            taxes.add(new POPTaxDto("CGST", "5.0"));
            taxes.add(new POPTaxDto("SGST", "5.0"));
            pop.setTaxes(taxes);
            pop.setLpoid(lpoid);
            pop.setCommpay(new BigDecimal("100"));
        });

        PurchaseOrderDto po = new PurchaseOrderDto(type, sup.getId(), cust.getId(),
                "PO101", DateUtil.getLong(month, day, year), poPlist);
        po.setTaxes("CGST,SGST");
        po.setAgent(agent1);

        po = purchaseManager.savePurchaseOrder(po);
        po = purchaseService.updateStatus(po.getId(), OrderStatus.CONFIRM, DateUtil.getLDT(month, day, year) .plusDays(1));

        return po;

    }

    protected InvoiceDto createInvoice(Long oid) throws Exception{
        PurchaseOrder order = (PurchaseOrder) purchaseService.find(PurchaseOrder.class, oid);
        InvoiceDto invoiceDto = new InvoiceDto();
        invoiceDto.setOid(oid);
        invoiceDto.setTenantid(order.getTenantid());
        invoiceDto.setContainerno("CONT039842KDJS");
        invoiceDto.setTruckno("TRK9384LKS");
        invoiceDto.setType(OrderType.getTypeString(order.getType()));
        invoiceDto.setTrackingno("IUEWKDSJLSDKDL88");
        invoiceDto.setInvdate(DateUtil.getEpochTime(order.getOrderdate().plusDays(2)));
        invoiceDto.setProductlist(order.getPoproductlist().stream().map( poProduct -> {
            InvProductDto invProd = new InvProductDto();
            invProd.setQuantity(poProduct.getQuantity());
            invProd.setPopid(poProduct.getId());
            return invProd;
        }).collect(Collectors.toList()));

        Invoice invoice  = invoiceService.saveInvoice(invoiceDto);
        invoiceDto = InvoiceMapper.mapperEntityToDto.apply(invoice);

        invoiceDto = invoiceService.updateStatus(invoiceDto.getId(), InvoiceStatus.COMPLETE,DateUtil.toLocalDateTime(invoiceDto.getInvdate()));



        return invoiceDto;

    }

    protected void recordpayment(InvTransactionType type ,CompanyDto myCompany, CompanyDto custCompany,  InvoiceDto inv, String amount, Month month, int day, int year, String notes) throws Exception {
        InvTransactionDto payment = new InvTransactionDto(DateUtil.getLong(month, day, year), notes, custCompany.getId()
                , myCompany.getId(), type.getType(), inv.getId(), inv.getOid(), new BigDecimal(amount));
        invTransactionManager.saveTransaction(payment);
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
