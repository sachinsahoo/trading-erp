package com.example.entity.mapper;

import com.example.entity.CRMUser;
import com.example.entity.UserRole;
import com.example.entity.dto.CRMUserDto;
import com.example.entity.dto.UserRoleDto;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

public class UserMapper {

    public static final Function<CRMUser, CRMUserDto> mapperEntityToDto = (user)->{
        CRMUserDto dto = new CRMUserDto();
        if(user == null){
            return null;
        }

        dto.setUsername(user.getUsername());
        dto.setActive(user.getActive());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setId(user.getId());
        dto.setTenantid(user.getTenantid());
        dto.setPhone(user.getPhone());
        StringBuffer roles = new StringBuffer("");
        Set<UserRoleDto> roleDtoSet = new HashSet<>();
        for(UserRole role : user.getRoles()){
           roles.append(role.getRole());
           roleDtoSet.add(new UserRoleDto(role.getId(), role.getRole()));

        }
        dto.setRolesStr(roles.toString());
        dto.setRoles(roleDtoSet);

        return dto;

    };

    public static final Function<CRMUserDto, CRMUser> mapperDtoToEntity = (user)->{
        CRMUser entity = new CRMUser();
        if(user == null){
            return null;
        }

        entity.setUsername(user.getUsername());
        entity.setActive(user.getActive());
        entity.setEmail(user.getEmail());
        entity.setPassword(user.getPassword());
        entity.setId(user.getId());
        entity.setTenantid(user.getTenantid());
        entity.setPhone(user.getPhone());

        return entity;

    };

}
