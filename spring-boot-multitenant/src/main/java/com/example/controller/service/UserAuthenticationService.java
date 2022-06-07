package com.example.controller.service;

import com.example.entity.dto.CRMUserDto;
import com.example.entity.dto.UserRoleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserAuthenticationService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName){

        CRMUserDto user = new CRMUserDto();
        List<GrantedAuthority> authorities = new ArrayList<>();
        try {
            user = userService.getByUsername(userName);
            authorities = getUserAuthority(user.getRoles());

        }catch (Exception e){

        }
        return buildUserForAuthentication(user, authorities);
    }

    private List<GrantedAuthority> getUserAuthority(Set<UserRoleDto> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<>();
        for (UserRoleDto role : userRoles) {
            roles.add(new SimpleGrantedAuthority(role.getRole()));
        }
        return new ArrayList<>(roles);
    }

    private UserDetails buildUserForAuthentication(CRMUserDto user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword().trim(),
                user.getActive(), true, true, true, authorities);
    }
}
