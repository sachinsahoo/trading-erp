package com.example.entity.dto.acct;

import com.example.common.util.MathUtil;
import com.example.constant.*;
import com.example.controller.excel.dto.TallyAccount;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountDto implements Serializable {

    private Long id;
    private Long clientId;
    private Long groupId;
    private boolean isGroup;
    private String name;
    private Integer code;
    private Integer drcr;
    private BigDecimal balance;

    private int level;
        private int accountType;

    List<AccountDto> acctList;

    @JsonIgnore
    private InternalAccountType iacType;

    @JsonIgnore
    private AcGroupNode acGroup;

    public AccountDto() {
    }


    public AccountDto(AccountGroupDto group,  List<AccountDto> accountList ) {
    this.id = group.getId();
    this.code = group.getCode();
    this.groupId = group.getParentId();
    AcGroupNode groupNode = AcGroupNode.get(group.getCode());
    this.accountType = groupNode.getAccountType()!= null ? groupNode.getAccountType().getCode() : 0;
    this.name = group.getName();
    this.isGroup = true;
    this.level = group.getLevel();
    this.acctList = accountList;
    setBalance(this.acctList);

    }

    public AccountDto(TallyAccount tallyAccount) {

        this.code = 100000; // Todo dynamic
        this.groupId = tallyAccount.getGroupDto().getId();
        this.name = tallyAccount.getName();

    }


    public AccountDto(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public AccountDto(AcGroupNode acGroupNode) {

        this.groupId = null;
        this.isGroup = true;
        this.code = acGroupNode.getCode();
        this.name = acGroupNode.getDesc();
        this.level = acGroupNode.getLevel();
        this.accountType = acGroupNode.getAccountType().getCode();
        this.acctList = new ArrayList<>();
        this.acGroup = acGroupNode.getParent();
    }


    public int getLevel() {
        return level;
    }

    @JsonIgnore
    public boolean isType(AccountType type) {
        return (isGroup && type.getCode() == accountType);
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getRupBalance() {
        return MathUtil.rupeesFormat(this.balance);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public void setGroup(boolean group) {
        isGroup = group;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDrcr() {
        return drcr;
    }

    public void setDrcr(Integer drcr) {
        this.drcr = drcr;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public void setBalance(List<AccountDto> accts) {

        if ( accts == null || accts.isEmpty()){
            return;
        }
        BigDecimal debitBal =
                accts.stream().map(acct -> {
                    if (DrCrType.DEBIT.getCode().equals(acct.drcr)) {
                        return acct.getBalance();
                    } else return BigDecimal.ZERO;
                }).map(Objects::requireNonNull).reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal creditBal =
                accts.stream().map(acct -> {
                if (DrCrType.CREDIT.getCode().equals(acct.drcr)) {
                    return acct.getBalance();
                } else return BigDecimal.ZERO;
            }).map(Objects::requireNonNull).reduce(BigDecimal.ZERO, BigDecimal::add);

        if(debitBal == null) {
            debitBal = BigDecimal.ZERO;
        }

        if(creditBal == null)
            creditBal = BigDecimal.ZERO;

        if (debitBal.compareTo(creditBal) > 0) {
            this.balance = debitBal.subtract(creditBal);
            this.drcr = DrCrType.DEBIT.getCode();
        } else {
            this.balance = creditBal.subtract(debitBal);
            this.drcr = DrCrType.DEBIT.getCode();
        }


    }

    public InternalAccountType getIacType() {
        return iacType;
    }

    public void setIacType(InternalAccountType iacType) {
        this.iacType = iacType;
    }

    public AcGroupNode getAcGroup() {
        return acGroup;
    }

    public void setAcGroup(AcGroupNode acGroup) {
        this.acGroup = acGroup;
    }

    public List<AccountDto> getAcctList() {
        return acctList;
    }

    public void setAcctList(List<AccountDto> acctList) {
        this.acctList = acctList;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
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
