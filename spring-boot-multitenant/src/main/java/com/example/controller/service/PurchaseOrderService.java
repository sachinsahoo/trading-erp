package com.example.controller.service;

import com.example.common.util.DateUtil;
import com.example.common.util.InventoryUtil;
import com.example.constant.*;
import com.example.controller.service.base.BaseDaoService;
import com.example.controller.util.OrderCalculator;
import com.example.entity.dto.order.POPTaxDto;
import com.example.entity.mapper.POPTaxMapper;
import com.example.entity.order.*;
import com.example.entity.dto.CRMUserDto;
import com.example.entity.dto.order.POProductDto;
import com.example.entity.dto.order.PurchaseOrderDto;
import com.example.entity.mapper.POProductMapper;
import com.example.entity.mapper.PurchaseOrderMapper;
import com.example.entity.rs.order.OrderSearchRequest;
import com.example.entity.rs.order.TransactionSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PurchaseOrderService extends BaseDaoService {

    @Autowired
    HttpSession session;

    @Autowired
    InvTransactionService invTransactionService;

    @Autowired
    UserService userService;

    public PurchaseOrderService() {
    }


    /************************************************************************************
     *
     *  Save Supplier, Customer, Agent
     *  Save POProducts
     *  Calculate and save taxes
     *  Calculate profit
     ***********************************************************************************/

    @Transactional
    public PurchaseOrderDto savePurchaseOrder(PurchaseOrderDto purchaseDto) throws Exception {

        PurchaseOrder purchase = null;
        try {

            if (purchaseDto.getId() != null) {
                purchase = (PurchaseOrder) find(PurchaseOrder.class, purchaseDto.getId());
            } else {
                purchase = new PurchaseOrder();
                Preference preference = userService.getPreferencePoNo();
                String refNo = OrderType.getPrefix(purchaseDto.getType()) + "-" + preference.getPono(); //+ "/" + preference.getFinyear();
                purchase.setReferenceno(refNo);
                purchase.setOrderno(preference.getPono());
                purchase.setStatus(OrderStatus.DRAFT.getCode());
            }

            purchase = PurchaseOrderMapper.mapDtoToEntity.apply(purchaseDto, purchase);

            purchase.setTenantid(getTenantId());

            Company supplier = null;
            Company customer = null;
            Company agent = null;


            //supplier
            if (purchaseDto.getSupplierid() != null) {
                supplier = (Company) find(Company.class, purchaseDto.getSupplierid());
                purchase.setSupplier(supplier);
                if(CompanyType.getType(supplier.getType()).equals(CompanyType.SELF)) {
                    purchase.setMycompanyid(supplier.getId());
                }
            }

            //customer
            if (purchaseDto.getCustomerid() != null) {
                customer = (Company) find(Company.class, purchaseDto.getCustomerid());
                purchase.setCustomer(customer);
                if(CompanyType.getType(customer.getType()).equals(CompanyType.SELF)) {
                    purchase.setMycompanyid(customer.getId());
                }
            }

            //agent
            if (purchaseDto.getAgentid() != null) {
                agent = (Company) find(Company.class, purchaseDto.getAgentid());
                purchase.setAgent(agent);
                if(CompanyType.getType(agent.getType()).equals(CompanyType.SELF)) {
                    purchase.setMycompanyid(agent.getId());
                }
            }
            if (purchase.getId() == null) {
                persist(purchase);
                //generate internal reference
            } else {
                merge(purchase);
            }


            if (purchaseDto.getPoproductlist() != null) {
                for (POProductDto poProductDto : purchaseDto.getPoproductlist()) {
                    POProduct poProduct = null;
                    if (poProductDto.getId() != null) {
                        poProduct = (POProduct) find(POProduct.class, poProductDto.getId());
                    } else {
                        poProduct = new POProduct();

                    }

                    poProduct = POProductMapper.mapDtoToEntity.apply(poProductDto, poProduct);
                    poProduct.setTenantid(getTenantId());
                    poProduct.setOid(purchase.getId());
                    if (poProduct.getId() != null) {
                        merge(poProduct);
                    } else {
                        persist(poProduct);
                    }

                    if (poProductDto.getTaxes() == null) poProductDto.setTaxes(new ArrayList<>());
                    //List<POPTax> taxes = poProductDto.getTaxes().stream().map(POPTaxMapper.mapperDtoToEntity).collect(Collectors.toList());
                    for (POPTaxDto taxDto : poProductDto.getTaxes()) {

                        POPTax tax = null;
                        if (taxDto.getId() != null) {
                            tax = (POPTax) find(POPTax.class, taxDto.getId());
                        } else {
                            tax = new POPTax();
                        }

                        tax = POPTaxMapper.mapperDtoToEntity.apply(taxDto, tax);
                        tax.setPopid(poProduct.getId());
                        if (tax.getId() != null)
                            merge(tax);
                        else
                            persist(tax);

                    }
                    refresh(poProduct);

                }
                refresh(purchase);
                // Recalculate Total, tax , commission -----------
                purchase = OrderCalculator.calculateOrder(purchase);
                merge(purchase);

                refresh(purchase);

                // Calculate Profit ---
                updateProfit(purchase);
                merge(purchase);
            }

            getEntityManager().flush();
            refresh(purchase);
        }catch (Exception e) {
            throw e;
        }
        return PurchaseOrderMapper.mapperEntityToDto.apply(purchase);

    }

    @Transactional
    public PurchaseOrder removeDeletedEntities(PurchaseOrderDto purchaseDto) {

        PurchaseOrder purchase = null;
        if (purchaseDto.getId() != null) {
            purchase = (PurchaseOrder) find(PurchaseOrder.class, purchaseDto.getId());
        } else {
            return null;
        }


        Set<Long> poids = new HashSet<>();
        Set<Long> poDtoIds = new HashSet<>();
        //PO Product
        if (purchase.getPoproductlist() != null) {
            poids = purchase.getPoproductlist().stream().map(POProduct::getId).collect(Collectors.toSet());
        }
        if (purchaseDto.getPoproductlist() != null) {
            poDtoIds = purchaseDto.getPoproductlist().stream().map(POProductDto::getId).collect(Collectors.toSet());
        }
        //Delete PO products not in DTO
        for (Long id : poids) {
            if (!poDtoIds.contains(id)) {
                POProduct poProduct = (POProduct) find(POProduct.class, id);
                purchase.getPoproductlist().remove(poProduct);
                remove(poProduct);
            }
        }

        merge(purchase);

        Set<Long> taxIdsToBeRemoved = new HashSet<>();
        Set<Long> taxDtoIds = new HashSet<>();

        purchaseDto.getPoproductlist().forEach(popDto -> {
            if (popDto.getTaxes() != null) {
                popDto.getTaxes().forEach(popTaxDto -> {
                    if (popTaxDto.getId() != null) {
                        taxDtoIds.add(popTaxDto.getId());
                    }
                });
            }
        });


        purchase.getPoproductlist().forEach(pop -> {
            if (pop.getTaxes() != null) {
                pop.getTaxes().forEach(tax -> {
                    if (!taxDtoIds.contains(tax.getId())) {
                        taxIdsToBeRemoved.add(tax.getId());
                    }
                });
            }
        });

        for (Long popTaxid : taxIdsToBeRemoved) {
            POPTax popTax = (POPTax) find(POPTax.class, popTaxid);
            POProduct pop = (POProduct) find(POProduct.class, popTax.getPopid());
            pop.getTaxes().remove(popTax);
            remove(popTax);
            merge(pop);
        }

        refresh(purchase);
        return purchase;


    }

    @Transactional
    public BigDecimal updateProfit(PurchaseOrder order) throws Exception {

        BigDecimal profit = BigDecimal.ZERO;
        if (order.isPurchase()) {
            order.setProfit(BigDecimal.ZERO);
            return BigDecimal.ZERO;
        } else if (order.isSale()) {
            //PurchaseOrder purchaseOrder = (PurchaseOrder) find(PurchaseOrder.class, order.getPurchase().getId());

            for (POProduct item : order.getPoproductlist()) {
                BigDecimal itemProfit = BigDecimal.ZERO;
                BigDecimal itemCostPrice = BigDecimal.ZERO;

                PurchaseOrder purchaseOrder = null; //(PurchaseOrder) find(PurchaseOrder.class, item.getLpoid());
                // Cost price from Linked PO or Product
                if (purchaseOrder != null) {
                    Optional<POProduct> purchaseItem = purchaseOrder.getPoproductlist().stream()
                            .filter(po -> po.getProduct().getId().equals(item.getProduct().getId())).findFirst();
                    if (purchaseItem.isPresent()) {
                        itemCostPrice = purchaseItem.get().getPrice();
                    }
                } else {
                    itemCostPrice = item.getProduct().getCostprice();
                }

                itemProfit = InventoryUtil.calcProfit(item.getPrice(), itemCostPrice, item.getQuantity());
                profit = profit.add(itemProfit);

                item.setCostprice(itemCostPrice);
                item.setProfit(itemProfit);
                merge(item);
            }
            order.setProfit(profit);
        } else if (order.isCommission()) {

        }

        return profit;


    }

    @Transactional
    public PurchaseOrderDto updateStatus(Long poid, OrderStatus status, LocalDateTime dateTime) throws Exception {

        PurchaseOrder purchaseOrder = (PurchaseOrder) find(PurchaseOrder.class, poid);

        switch (status) {
            case RFQ:
                purchaseOrder.setStatus(OrderStatus.RFQ.getCode());
                break;
            case CONFIRM:
                purchaseOrder.setStatus(OrderStatus.CONFIRM.getCode());
                if (purchaseOrder.getConfirmdate() == null) {
                    updatePendingStock(purchaseOrder, status);
                    purchaseOrder.setConfirmdate(dateTime);
                    merge(purchaseOrder);
                    //invTransactionService.enterTransactionForOrder(purchaseOrder);
                } else {
                    purchaseOrder.setConfirmdate(dateTime);
                }
                break;

        }
        merge(purchaseOrder);

        return PurchaseOrderMapper.mapperEntityToDto.apply(purchaseOrder);
    }


    //  Update Not Received / Not Dispatched once order is [ CONFIRM ]
    @Transactional
    public void updatePendingStock(PurchaseOrder purchaseOrder, OrderStatus orderStatus) throws Exception {
        switch (purchaseOrder.getOrderType()) {
            case PURCHASE:
                // Add Product po not received
                if (purchaseOrder.getConfirmdate() == null && OrderStatus.CONFIRM.equals(orderStatus)) {
                    for (POProduct poProduct : purchaseOrder.getPoproductlist()) {
                        Product product = (Product) find(Product.class, poProduct.getProduct().getId());
                        product.setPendingarrival(product.getPendingarrival().add(poProduct.getQuantity()));
                        merge(product);
                        refresh(poProduct);
                    }
                }
                break;
            case SALE:
                // Add Product SO not Dispatched
                if (purchaseOrder.getConfirmdate() == null && OrderStatus.CONFIRM.equals(orderStatus)) {
                    for (POProduct poProduct : purchaseOrder.getPoproductlist()) {
                        Product product = (Product) find(Product.class, poProduct.getProduct().getId());
                        product.setPendingdispatch(product.getPendingdispatch().add(poProduct.getQuantity()));
                        merge(product);
                        refresh(poProduct);
                    }
                }
                break;
        }
    }

    @Transactional
    public PurchaseOrderDto getOrderById(Long id) throws Exception {

        PurchaseOrder po = (PurchaseOrder) find(PurchaseOrder.class, id);
        //Hibernate.initialize(po.getInvoices());
        po.setCustomer((Company) find(Company.class, po.getCustomer().getId()));
        po.setSupplier((Company) find(Company.class, po.getSupplier().getId()));

        po.getInvoices().size();
        if(po.getCustomer().getContacts() != null)
            po.getCustomer().getContacts().size();
        if(po.getSupplier().getContacts() != null)
            po.getSupplier().getContacts().size();

        PurchaseOrderDto dto =  PurchaseOrderMapper.mapperEntityToDtoFULL.apply(po);
        return dto;

    }


    @Transactional
    public List<PurchaseOrderDto> getAllPurchases() throws Exception {
        CRMUserDto userDto = getUser();
        TypedQuery<PurchaseOrder> allOrders = createNamedQuery(PurchaseOrder.Q_ORDER_LIST, PurchaseOrder.class);
        allOrders.setParameter("tenantid", userDto.getTenantid());
        List<PurchaseOrder> orders = allOrders.getResultList();
        orders.forEach(order -> {
            if(order.getInvoices() != null)
                order.getInvoices().size();
        });
        List<PurchaseOrderDto> orderDtos = orders.stream().map(PurchaseOrderMapper.mapperEntityToDto).collect(Collectors.toList());

        return orderDtos;
    }

    @Transactional
    public List<PurchaseOrderDto> getConfirmedOrders() throws Exception {
        CRMUserDto userDto = getUser();
        TypedQuery<PurchaseOrder> allOrders = createNamedQuery(PurchaseOrder.Q_CONFIRM, PurchaseOrder.class);
        allOrders.setParameter("tenantid", userDto.getTenantid());
        List<PurchaseOrder> orders = allOrders.getResultList();
        orders.forEach(order -> {
            if(order.getInvoices() != null)
                order.getInvoices().size();
        });
        List<PurchaseOrderDto> orderDtos = orders.stream().map(PurchaseOrderMapper.mapperEntityToDto).collect(Collectors.toList());

        return orderDtos;
    }

    @Transactional
    public List<PurchaseOrderDto> getOrderByTypeAndStatus(OrderType type, OrderStatus status) throws Exception {

        CRMUserDto userDto = getUser();


        TypedQuery<PurchaseOrder> allOrders = createNamedQuery(PurchaseOrder.Q_ORDER_LIST, PurchaseOrder.class);
        allOrders.setParameter("tenantid", userDto.getTenantid());
        List<PurchaseOrder> orders = allOrders.getResultList();
        orders.forEach(order -> {
            if(order.getInvoices() != null)
                order.getInvoices().size();
        });
        List<PurchaseOrderDto> orderDtos = orders.stream().map(PurchaseOrderMapper.mapperEntityToDto).collect(Collectors.toList());

        return orderDtos;
    }



    @Transactional
    public List<PurchaseOrderDto> searchOrder(OrderSearchRequest searchRequest) throws Exception {

        List<PurchaseOrder> results = new ArrayList<>();
        List<PurchaseOrderDto> orderDtos = new ArrayList<>();

        try {
            CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<PurchaseOrder> criteriaQuery = criteriaBuilder.createQuery(PurchaseOrder.class);
            Root<PurchaseOrder> invOrderRoot = criteriaQuery.from(PurchaseOrder.class);

            ArrayList<Predicate> predicates = new ArrayList<>();

            Predicate predicateTenanat = criteriaBuilder.equal(
                    invOrderRoot.get("tenantid"), getTenantId());
            predicates.add(predicateTenanat);

            // My Company
            if(searchRequest.getMyCompanyId() != null && searchRequest.getMyCompanyId() > 1){
                Predicate predicateMyCompany = criteriaBuilder.equal(
                        invOrderRoot.get("mycompanyid"), searchRequest.getMyCompanyId());

                predicates.add(predicateMyCompany);
            }

            // Date Range
            if(!DatePreset.getType(searchRequest.getDatePreset()).equals(DatePreset.UNKNOWN)){
                LocalDateTime start = DateUtil.toLocalDateTime(searchRequest.getStartDate());
                LocalDateTime end = DateUtil.toLocalDateTime(searchRequest.getEndDate());
                Predicate predicateDateRange = criteriaBuilder.between(
                        invOrderRoot.get("orderdate"), start, end);
                predicates.add(predicateDateRange);
            }


            Predicate finalPredicate = criteriaBuilder.and(predicates.toArray(new Predicate[] {}));
            criteriaQuery.where(finalPredicate);
            criteriaQuery.orderBy(criteriaBuilder.desc(invOrderRoot.get("orderdate")));
            results = getEntityManager().createQuery(criteriaQuery).getResultList();


            results.forEach(order -> {
                if(order.getInvoices() != null)
                    order.getInvoices().size();
            });
            orderDtos = results.stream().map(PurchaseOrderMapper.mapperEntityToDto).collect(Collectors.toList());

        } catch (Exception e) {
            addToSessionAppLog("Order Service Error 801: " + e.getMessage());
            throw e;
        }
        return orderDtos;
    }




}
