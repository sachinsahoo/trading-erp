package com.example.entity;


import com.example.entity.rs.CreateUserRequest;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "crm_user", schema = "public")
@NamedQueries({
        @NamedQuery(name = "user.findByName", query = "SELECT u from CRMUser u where u.username=:username")

})

public class CRMUser implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String Q_FIND_BY_NAME = "user.findByName";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long tenantid;

    @Column(length = 30)
    private String username;
    @Column(length = 100)
    private String password;
    @Column(length = 40)
    private String email;
    @Column(length = 20)
    private String phone;
    private Boolean active;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private Set<UserRole> roles;

    public CRMUser(String username, String password, String email, String phone) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

    public CRMUser(CreateUserRequest request) {
        this.username = request.getUsername();
        this.password = request.getPassword();
        this.email = request.getEmail();
        this.phone = request.getPhone();
    }


    public CRMUser() {

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
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
