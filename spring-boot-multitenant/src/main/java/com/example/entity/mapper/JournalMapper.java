package com.example.entity.mapper;

import com.example.common.util.DateUtil;
import com.example.entity.acct.Account;
import com.example.entity.acct.Journal;
import com.example.entity.dto.acct.JournalDto;

import java.util.function.BiFunction;
import java.util.function.Function;

public class JournalMapper {

    public static final Function<Journal, JournalDto> mapperEntityToDto = (journal) -> {
        JournalDto dto = new JournalDto();
        if (journal == null) {
            return null;
        }
        dto.setId(journal.getId());
        dto.setAccid(journal.getAccount().getId());
       dto.setAccode(journal.getAccount().getCode());
       dto.setAcname(journal.getAccount().getAcname());
       dto.setAmount(journal.getAmount());
       dto.setDesc(journal.getDescription());
       dto.setDrcrtype(journal.getDrcrtype());
       dto.setTransid(journal.getTransid());
       dto.setTransDate(DateUtil.getEpochTime(journal.getTransdate()));

        return dto;


    };


    public static final BiFunction<JournalDto, Journal, Journal> mapperDtoToEntity = (journal, entity) -> {

        if (journal == null) {
            return null;
        }
        Account account = new Account();
        account.setId(journal.getAccid());
        entity.setAccount(account);
        entity.setAmount(journal.getAmount());
        entity.setDescription(journal.getDesc());
        entity.setDrcrtype(journal.getDrcrtype());
        entity.setTransid(journal.getTransid());
        entity.setTransdate(DateUtil.toLocalDateTime(journal.getTransDate()));

        return entity;


    };



}
