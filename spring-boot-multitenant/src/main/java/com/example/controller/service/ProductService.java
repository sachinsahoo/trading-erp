package com.example.controller.service;

import com.example.constant.InvoiceStatus;
import com.example.constant.OrderStatus;
import com.example.constant.OrderType;
import com.example.controller.service.base.BaseDaoService;
import com.example.entity.dto.order.*;
import com.example.entity.order.CompletedOrdersView;
import com.example.entity.order.Product;
import com.example.entity.dto.CRMUserDto;
import com.example.entity.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService extends BaseDaoService {

    @Autowired
    HttpSession session;

    @Autowired
    PurchaseOrderService purchaseOrderService;


    public ProductService() {
    }


    @Transactional
    public Product saveProduct(ProductDto productDto) throws Exception {
        CRMUserDto userDto = getUser();

        Product product = ProductMapper.mapperDtoToEntity.apply(productDto);
        product.setTenantid(userDto.getTenantid());
        EntityManager entityManager = getEntityManager();
        entityManager.persist(product);
        return product;

    }

    public List<Product> getAllProducts() throws Exception {
        CRMUserDto userDto = getUser();
        TypedQuery<Product> allProducts = createNamedQuery(Product.Q_PRODUCT_LIST, Product.class);
        allProducts.setParameter("tenantid", userDto.getTenantid());
        List<Product> products = allProducts.getResultList();
        return products;
    }


    public Product findByProductName(String name) {
        Product product = null;
        try {
            TypedQuery<Product> findQry = createNamedQuery(Product.Q_FIND_BY_NAME, Product.class);
            findQry.setParameter("name", name);

             product = findQry.getSingleResult();

        }catch ( Exception e) {

        }
        return product;
    }




    @Transactional
    public void updateProductAvailableStock() throws Exception {
        List<Product> productList = getAllProducts();
        TypedQuery<CompletedOrdersView> listCompOrdersQry = createNamedQuery(CompletedOrdersView.Q_LIST, CompletedOrdersView.class);
        listCompOrdersQry.setParameter("tenantid", getTenantId());
        List<CompletedOrdersView> completedOrders = listCompOrdersQry.getResultList();

        for(Product product: productList){
            Optional<CompletedOrdersView> compProductOrderOptional = completedOrders.stream().filter(b -> b.getProductid().equals(product.getId())).findFirst();
            if(compProductOrderOptional.isPresent()) {

                // Calculate Available stock (Total Purchases - Total Sales) ---
                CompletedOrdersView completedProductOrderTotal = compProductOrderOptional.get();
                product.setQuantity(completedProductOrderTotal.getTotpurqty().subtract(completedProductOrderTotal.getTotsaleqty()));

                // Update Product ---
                merge(product);
                refresh(product);
            }
        }
    }

    @Transactional
    public void updatePendingStock() throws Exception {

        List<PurchaseOrderDto> confirmOrders = purchaseOrderService.getConfirmedOrders();
        List<Product> allProducts = getAllProducts();

        List<InvProductDto>  allPurchRemainingInvPOPList = new ArrayList<>();
        List<InvProductDto>  allSaleRemainingInvPOPList = new ArrayList<>();

        // Calculate Pending Quantity in Invoices
        // Order Qty - total invoiced Qty
        // If 0 update Order status to complete
        for(PurchaseOrderDto order : confirmOrders) {
            List<InvProductDto>  remainingInvPOPList = new ArrayList<>();
            List<POProductDto> pop1List = order.getPoproductlist();

            // Initialize with total Order Qty
            for(POProductDto pop : order.getPoproductlist()) {
                InvProductDto remainingInvPop = new InvProductDto(pop.getId(), pop.getProductId(), pop.getQuantity());
                remainingInvPOPList.add(remainingInvPop);
            }

            // Reduce Completed Invoice
            for(InvoiceDto inv : order.getInvoices()) {
                for(InvProductDto invPop : inv.getProductlist()){
                    if(InvoiceStatus.COMPLETE.getType().equalsIgnoreCase(inv.getStatus())){
                        InvProductDto remInvPOp = remainingInvPOPList.stream().filter(invPop1 -> {
                            return invPop1.getPopid().equals(invPop.getPopid());}).findFirst().orElse(null);
                        remInvPOp.setQuantity(remInvPOp.getQuantity().subtract(invPop.getQuantity()));
                    }
                }
            }

            // Check if pending QTY is Zero
            List<BigDecimal> pendingQtys = remainingInvPOPList.stream().map(ipop-> ipop.getQuantity()).collect(Collectors.toList());
            BigDecimal pendingQty = pendingQtys.stream().map(Objects::requireNonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
            if(pendingQty.compareTo(BigDecimal.ZERO) <= 0){
                purchaseOrderService.updateStatus(order.getId(), OrderStatus.COMPLETE, LocalDateTime.now());
            }else {
                if(OrderType.PURCHASE.getType().equalsIgnoreCase(order.getType())) {
                    allPurchRemainingInvPOPList.addAll(remainingInvPOPList);
                } else if(OrderType.SALE.getType().equalsIgnoreCase(order.getType())) {
                    allSaleRemainingInvPOPList.addAll(remainingInvPOPList);
                }
            }
        }

        // Update Product with Pending Arrival and Pending Dispatch Qty
        for(Product product : allProducts) {
            BigDecimal pendingSaleDispOrderQty = BigDecimal.ZERO;
            BigDecimal pendingPurchArrivalOrderQty = BigDecimal.ZERO;
            BigDecimal compSaleOrderQty = BigDecimal.ZERO;
            BigDecimal compInvPurOrderQty = BigDecimal.ZERO;

            List<BigDecimal> pendingSaleDispQtyList = allSaleRemainingInvPOPList.stream()
                    .filter(invpop -> invpop.getProdid().equals(product.getId()))
                    .map(InvProductDto::getQuantity).collect(Collectors.toList());
            pendingSaleDispOrderQty = pendingSaleDispQtyList.stream().map(Objects::requireNonNull).reduce(BigDecimal.ZERO, BigDecimal::add);

            List<BigDecimal> pendingPurchArrivalQtyList = allPurchRemainingInvPOPList.stream()
                    .filter(invpop -> invpop.getProdid().equals(product.getId()))
                    .map(InvProductDto::getQuantity).collect(Collectors.toList());
            pendingPurchArrivalOrderQty = pendingPurchArrivalQtyList.stream().map(Objects::requireNonNull).reduce(BigDecimal.ZERO, BigDecimal::add);

            product.setPendingdispatch(pendingSaleDispOrderQty);
            product.setPendingarrival(pendingPurchArrivalOrderQty);
            merge(product);
        }
    }
}
