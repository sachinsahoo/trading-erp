package com.example.entity.order;

import javax.persistence.*;

@Entity
@Table(name = "preference", schema = "inventory")

@NamedQueries({
        @NamedQuery(name = "preference.find", query = "SELECT p from Preference p where p.tenantid = :tenantid ")

})


public class Preference {

    public static final String Q_FIND = "preference.find";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long tenantid;
    @Column(length = 15)
    String finyear;
    Long pono;
    int updatebalance;

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

    public String getFinyear() {
        return finyear;
    }

    public void setFinyear(String finyear) {
        this.finyear = finyear;
    }

    public Long getPono() {
        return pono;
    }

    public void setPono(Long pono) {
        this.pono = pono;
    }

    public int getUpdatebalance() {
        return updatebalance;
    }

    public void setUpdatebalance(int updatebalance) {
        this.updatebalance = updatebalance;
    }
}
