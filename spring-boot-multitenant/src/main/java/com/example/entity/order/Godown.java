package com.example.entity.order;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "godown", schema = "inventory")
public class Godown implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private long tenantid;

    @OneToMany
    @JoinColumn(name = "godownid", foreignKey=@ForeignKey(name="godown_stock_fkey"))
    private Set<Stock> stocks;

    @Column(length = 30)
    private String gname;
    @Column(length = 30)
    private String gcode;
    @Column(length = 40)
    private String address;
    @Column(length = 30)
    private String city;
    @Column(length = 30)
    private String State;
    @Column(length = 20)
    private String pin;
    @Column(length = 20)
    private String inchargename;
    @Column(length = 20)
    private String phone;
    @Column(length = 40)
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getTenantid() {
        return tenantid;
    }

    public void setTenantid(long tenantid) {
        this.tenantid = tenantid;
    }

    public Set<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(Set<Stock> stocks) {
        this.stocks = stocks;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public String getGcode() {
        return gcode;
    }

    public void setGcode(String gcode) {
        this.gcode = gcode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getInchargename() {
        return inchargename;
    }

    public void setInchargename(String inchargename) {
        this.inchargename = inchargename;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
