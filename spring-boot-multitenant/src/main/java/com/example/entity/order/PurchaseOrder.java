package com.example.entity.order;


import com.example.constant.OrderType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "purchaseorder", schema="inventory")

@NamedQueries({
        @NamedQuery(name = "purchase.list", query = "SELECT p from PurchaseOrder p where p.tenantid=:tenantid"),
        @NamedQuery(name = "purchase.confirm", query = "SELECT p from PurchaseOrder p where p.tenantid=:tenantid and p.status = 3"),
        @NamedQuery(name = "purchase.findByCompany", query = "SELECT p from PurchaseOrder p where p.tenantid=:tenantid and (p.supplier.id = :companyid or p.customer.id = :companyid)")

})
public class PurchaseOrder implements Serializable {
    public static final String Q_ORDER_LIST = "purchase.list";
    public static final String Q_CONFIRM = "purchase.confirm";
    public static final String Q_FIND_BY_COMPANY = "purchase.findByCompany";


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long tenantid;

    private Integer type;
    @Column(length = 30)
    private String referenceno;
    private Long orderno;

    @Column(length = 40)
    private String taxes; // Ex GST@2%
    private BigDecimal advance;
    private BigDecimal taxAmount;
    private BigDecimal totalamount= BigDecimal.ZERO;
    private BigDecimal totalcommpay= BigDecimal.ZERO;
    private BigDecimal profit= BigDecimal.ZERO;
    private BigDecimal balance= BigDecimal.ZERO; // todo remove

    private Long mycompanyid;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "supplierid", foreignKey=@ForeignKey(name="order_supp_company_fkey"))
    private Company supplier;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customerid", foreignKey=@ForeignKey(name="order_cust_company_fkey"))
    private Company customer;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "agentid", foreignKey=@ForeignKey(name="order_agent_company_fkey"))
    private Company agent;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "oid", foreignKey=@ForeignKey(name="order_poproduct_fkey"))
    private Set<POProduct> poproductlist;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "oid", foreignKey=@ForeignKey(name="order_invoice_fkey"))
    private Set<Invoice> invoices;


    private LocalDateTime orderdate;
    private LocalDateTime confirmdate;
    private LocalDateTime completedate;

    private Integer status; // Draft / rfq / rfqsent/ PO

    public PurchaseOrder() {
    }

    public PurchaseOrder(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTenantid() {
        return tenantid;
    }

    public void setTenantid(Long tenantid) {
        this.tenantid = tenantid;
    }



    public String getTaxes() {
        return taxes;
    }

    public void setTaxes(String taxes) {
        this.taxes = taxes;
    }

    public BigDecimal getAdvance() {
        return advance;
    }

    public void setAdvance(BigDecimal advance) {
        this.advance = advance;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public Set<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(Set<Invoice> invoices) {
        this.invoices = invoices;
    }


    public String getReferenceno() {
        return referenceno;
    }

    public Long getOrderno() {
        return orderno;
    }

    public void setOrderno(Long orderno) {
        this.orderno = orderno;
    }

    public BigDecimal getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(BigDecimal totalamount) {
        this.totalamount = totalamount;
    }

    public BigDecimal getTotalcommpay() {
        return totalcommpay;
    }

    public void setTotalcommpay(BigDecimal totalcommpay) {
        this.totalcommpay = totalcommpay;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setReferenceno(String referenceno) {
        this.referenceno = referenceno;
    }

    public Long getMycompanyid() {
        return mycompanyid;
    }

    public void setMycompanyid(Long mycompanyid) {
        this.mycompanyid = mycompanyid;
    }

    public Company getSupplier() {
        return supplier;
    }

    public void setSupplier(Company supplier) {
        this.supplier = supplier;
    }

    public Company getCustomer() {
        return customer;
    }

    public void setCustomer(Company customer) {
        this.customer = customer;
    }

    public Company getAgent() {
        return agent;
    }

    public void setAgent(Company agent) {
        this.agent = agent;
    }

    public Set<POProduct> getPoproductlist() {
        return poproductlist;
    }

    public void setPoproductlist(Set<POProduct> poproductlist) {
        this.poproductlist = poproductlist;
    }

    public LocalDateTime getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(LocalDateTime orderdate) {
        this.orderdate = orderdate;
    }

    public LocalDateTime getConfirmdate() {
        return confirmdate;
    }

    public void setConfirmdate(LocalDateTime confirmdate) {
        this.confirmdate = confirmdate;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public LocalDateTime getCompletedate() {
        return completedate;
    }

    public void setCompletedate(LocalDateTime completedate) {
        this.completedate = completedate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public boolean isPurchase(){
        try {
            OrderType type = OrderType.getType(getType());
            return type.equals(OrderType.PURCHASE);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isSale() {
        try {
            OrderType type = OrderType.getType(getType());
            return type.equals(OrderType.SALE);
        }catch (Exception e) {
            return false;
        }
    }

    public boolean isCommission() {
        try {
            OrderType type = OrderType.getType(getType());
            return type.equals(OrderType.COMMISSION);
        }catch (Exception e) {
            return false;
        }
    }

    public OrderType getOrderType() throws Exception {
        return OrderType.getType(getType());
    }

    public Company getClient() {

        Company client = isPurchase() ?
                getSupplier() : isSale() ? getCustomer() : isCommission() ? getSupplier() : null;
        if(client == null) {
            client = new Company();
        }

        return client;
    }

    public Company getMyCompany() {

        Company client = isPurchase() ?
                getCustomer() : (isSale() ? getSupplier() : isCommission() ? getAgent() : null);
        if(client == null) {
            client = new Company();
        }

        return client;
    }


    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object object) {
        return EqualsBuilder.reflectionEquals(this, object, false);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
