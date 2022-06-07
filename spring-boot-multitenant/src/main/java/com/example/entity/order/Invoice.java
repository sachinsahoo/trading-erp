package com.example.entity.order;


import com.example.constant.OrderType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "invoice", schema="inventory")

@NamedQueries({
        @NamedQuery(name = "invoice.list", query = "SELECT i from Invoice i where i.tenantid=:tenantid"),
        @NamedQuery(name = "invoice.findByOid", query = "SELECT i from Invoice i where i.oid =:oid")

})
public class Invoice implements Serializable {
    public static final String Q_LIST = "invoice.list";
    public static final String Q_FIND_BY_POID = "invoice.findByOid";


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long tenantid;
    private Long oid;

    private Integer type;
    @Column(length = 30)
    private String referenceno;
    private Long invoiceno;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "invid", foreignKey=@ForeignKey(name="invoice_invproduct_fkey"))
    private List<InvProduct> productlist;

    private BigDecimal taxAmount = BigDecimal.ZERO;
    private BigDecimal totalamount= BigDecimal.ZERO;
    private BigDecimal totalcommpay= BigDecimal.ZERO;
    private BigDecimal balance= BigDecimal.ZERO;

    private LocalDateTime invdate;
    private LocalDateTime completedate;

    @Column(length = 80)
    private String transportername;
    @Column(length = 80)
    private String trackingno;
    @Column(length = 60)
    private String truckno;
    @Column(length = 60)
    private String containerno;

    private Integer status; // Draft / rfq / rfqsent/ PO

    @Column(length = 40)
    private String taxes;

    public Invoice() {
    }

    public Invoice(Long id) {
        this.id = id;
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

    public OrderType getOrderType() throws Exception {
        return OrderType.getType(getType());
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

    public Long getOid() {
        return oid;
    }

    public void setOid(Long oid) {
        this.oid = oid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getReferenceno() {
        return referenceno;
    }

    public void setReferenceno(String referenceno) {
        this.referenceno = referenceno;
    }

    public List<InvProduct> getProductlist() {
        return productlist;
    }

    public void setProductlist(List<InvProduct> productlist) {
        this.productlist = productlist;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public LocalDateTime getInvdate() {
        return invdate;
    }

    public void setInvdate(LocalDateTime invdate) {
        this.invdate = invdate;
    }

    public LocalDateTime getCompletedate() {
        return completedate;
    }

    public void setCompletedate(LocalDateTime completedate) {
        this.completedate = completedate;
    }


    public String getTransportername() {
        return transportername;
    }

    public void setTransportername(String transportername) {
        this.transportername = transportername;
    }

    public String getTrackingno() {
        return trackingno;
    }

    public void setTrackingno(String trackingno) {
        this.trackingno = trackingno;
    }

    public String getTruckno() {
        return truckno;
    }

    public void setTruckno(String truckno) {
        this.truckno = truckno;
    }

    public String getContainerno() {
        return containerno;
    }

    public void setContainerno(String containerno) {
        this.containerno = containerno;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTaxes() {
        return taxes;
    }

    public void setTaxes(String taxes) {
        this.taxes = taxes;
    }

    public Long getInvoiceno() {
        return invoiceno;
    }

    public void setInvoiceno(Long invoiceno) {
        this.invoiceno = invoiceno;
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
