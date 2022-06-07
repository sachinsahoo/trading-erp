package com.example.entity.dto.acct;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BalanceSheetDto implements Serializable {



    private List<AccountDto> assets;

    private List<AccountDto> liabilities;

    private List<AccountDto> incomes;;

    private List<AccountDto> expenses;


    public List<AccountDto> getAssets() {
        return assets;
    }

    public void setAssets(List<AccountDto> assets) {
        this.assets = assets;
    }

    public List<AccountDto> getLiabilities() {
        return liabilities;
    }

    public void setLiabilities(List<AccountDto> liabilities) {
        this.liabilities = liabilities;
    }

    public List<AccountDto> getIncomes() {
        return incomes;
    }

    public void setIncomes(List<AccountDto> incomes) {
        this.incomes = incomes;
    }

    public List<AccountDto> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<AccountDto> expenses) {
        this.expenses = expenses;
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
