package com.example.entity.mapper;

import com.example.constant.CompanyType;
import com.example.entity.acct.AccountGroup;
import com.example.entity.dto.acct.AccountGroupDto;
import com.example.entity.dto.order.CompanyDto;
import com.example.entity.dto.order.ContactDto;
import com.example.entity.order.Company;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AccountGroupMapper {

    public static final Function<AccountGroup, AccountGroupDto> mapperEntityToDto = (group) -> {
        AccountGroupDto accdto = new AccountGroupDto();
        if (group == null) {
            return null;
        }

        accdto.setId(group.getId());
        accdto.setTenantid(group.getTenantid());
        accdto.setParentId(group.getParentid());

        accdto.setName(group.getName());
        accdto.setIndex(group.getIndex());
        accdto.setCode(group.getInternalcode());
        accdto.setLevel(group.getLevel());
        return accdto;

    };



    public static final BiFunction<AccountGroupDto, AccountGroup, AccountGroup> mapDtoToEntity = (accdto, group) -> {
        group.setName(accdto.getName());
        group.setIndex(accdto.getIndex());
        return group;

    };


}
