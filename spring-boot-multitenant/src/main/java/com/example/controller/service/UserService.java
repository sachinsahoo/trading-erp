package com.example.controller.service;

import com.example.common.constant.RoleType;
import com.example.controller.manager.AccountManager;
import com.example.controller.service.base.BaseDaoService;
import com.example.entity.CRMUser;
import com.example.entity.Tenant;
import com.example.entity.UserRole;
import com.example.entity.order.Preference;
import com.example.entity.dto.CRMUserDto;
import com.example.entity.dto.TenantDto;
import com.example.entity.mapper.TenantMapper;
import com.example.entity.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService extends BaseDaoService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountManager accountManager;

    public UserService() {
    }

    @Transactional
    public void saveUser(CRMUserDto userDto) {

        CRMUser user = UserMapper.mapperDtoToEntity.apply(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        user.setRoles(getUserRoles(userDto.getRolesStr()));
        persist(user);
    }

    private Set<UserRole> getUserRoles(String role) {
        RoleType roleType = RoleType.get(role);
        Set<UserRole> roles = new HashSet<>();
        roles.add(new UserRole(RoleType.USER.getCode()));
        if (RoleType.ADMIN.equals(roleType) || RoleType.SUPERADMIN.equals(roleType)) {
            roles.add(new UserRole(RoleType.ADMIN.getCode()));
        }
        if (RoleType.SUPERADMIN.equals(roleType)) {
            roles.add(new UserRole(RoleType.SUPERADMIN.getCode()));
        }
        return roles;
    }

    @Transactional
    public CRMUserDto getByUsername(String username) throws Exception {

        TypedQuery<CRMUser> queryUserByName = createNamedQuery(CRMUser.Q_FIND_BY_NAME, CRMUser.class);
        queryUserByName.setParameter("username", username);
        CRMUser resUser = null;
        CRMUserDto userDto = null;
        try {
            resUser = queryUserByName.getSingleResult();
        }catch (NoResultException nre){

        }
        if(resUser != null) {
            userDto = UserMapper.mapperEntityToDto.apply(resUser);
        }

        return userDto;

    }

    @Transactional
    public Preference getPreferencePoNo() throws Exception {

        Preference preference1 = null;
        TypedQuery<Preference> preferenceTypedQuery = createNamedQuery(Preference.Q_FIND, Preference.class);
        preferenceTypedQuery.setParameter("tenantid", getTenantId());
        List<Preference> preference = preferenceTypedQuery.getResultList();
        if(preference == null || preference.size() == 0){
             preference1 = new Preference();
           int curYear = LocalDateTime.now().getYear();
           String nextYear = String.valueOf(curYear + 1);
           nextYear = nextYear.substring(2);
           String curYearStr = curYear + "-" + nextYear;

            preference1.setFinyear(curYearStr);
            preference1.setPono(1l);
            preference1.setTenantid(getTenantId());
            preference1.setUpdatebalance(1);
            persist(preference1);

        } else {
            preference1 = preference.get(0);
            preference1.setPono(preference1.getPono() + 1);
            merge(preference1);

        }
        return  preference1;


    }

    @Transactional
    public int isBalanceUpdateRequired() throws Exception {
        return getPreferencePoNo().getUpdatebalance();
    }

    @Transactional
    public void setBalanceUpdateRequired(int status) throws Exception {
        Preference preference = getPreferencePoNo();
        preference.setUpdatebalance(status);
        merge(preference);
    }


    @Transactional
    public Tenant saveTenant(TenantDto tenantDto) {
        Tenant tenant = TenantMapper.mapperDtoToEntity.apply(tenantDto);

        boolean persisted = persist(tenant);
        return tenant;

    }

    @Transactional
    public List<TenantDto> getAllTenants() throws Exception {
        TypedQuery<Tenant> queryAllTenant = createNamedQuery(Tenant.Q_LIST, Tenant.class);
        List<TenantDto> tenants = queryAllTenant.getResultStream().map(TenantMapper.mapperEntityToDto).collect(Collectors.toList());
        return tenants;
    }


}
