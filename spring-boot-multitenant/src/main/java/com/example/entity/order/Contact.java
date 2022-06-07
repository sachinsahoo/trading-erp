package com.example.entity.order;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "contact", schema = "inventory")

@NamedQueries({
        @NamedQuery(name = "contact.list", query = "SELECT c from Contact c where tenantid=:tenantid"),
        @NamedQuery(name = "contact.findByName", query = "SELECT c from Contact c where tenantid=:tenantid AND c.name = :name AND c.companyid = :companyid " )

})
public class Contact implements Serializable {

    public static final String Q_CONTACT_LIST = "contact.list";
    public static final String Q_FIND_BY_NAME = "contact.findByName";


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long tenantid;

    private Long companyid;

    @Column(length = 20)
    private int type;
    @Column(length = 40)
    private String name;
    @Column(length = 40)
    private String address1;
    @Column(length = 40)
    private String address2;
    @Column(length = 30)
    private String city;
    @Column(length = 30)
    private String state;
    @Column(length = 20)
    private String pincode;
    @Column(length = 20)
    private String phone1;
    @Column(length = 20)
    private String phone2;
    @Column(length = 40)
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompanyid() {
        return companyid;
    }

    public void setCompanyid(Long companyid) {
        this.companyid = companyid;
    }

    public Long getTenantid() {
        return tenantid;
    }

    public void setTenantid(Long tenantid) {
        this.tenantid = tenantid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
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
