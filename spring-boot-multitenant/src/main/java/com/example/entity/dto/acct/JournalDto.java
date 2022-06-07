package com.example.entity.dto.acct;

import com.example.common.util.MathUtil;
import com.example.constant.DrCrType;
import com.example.entity.dto.order.InvTransactionDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JournalDto implements Serializable {

    private Long id;
    private String desc;
    private Long transid;
    private Long transDate;
    private int drcrtype;
    private Long accid;
    private String acname;
    private int accode;
    private BigDecimal amount;

    public String getRupAmount(){
        return MathUtil.rupeesFormat(amount);
    }


    public JournalDto() {
    }

    public JournalDto( String description, InvTransactionDto transaction, Long accountid, DrCrType drcr, BigDecimal amount) {
        this.desc = description;
        this.transid = transaction.getId();
        this.transDate = transaction.getDate();
        this.accid = accountid;
        this.drcrtype = drcr.getCode();
        this.amount = amount;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Long getTransid() {
        return transid;
    }

    public void setTransid(Long transid) {
        this.transid = transid;
    }

    public Long getTransDate() {
        return transDate;
    }

    public void setTransDate(Long transDate) {
        this.transDate = transDate;
    }

    public int getDrcrtype() {
        return drcrtype;
    }

    public void setDrcrtype(int drcrtype) {
        this.drcrtype = drcrtype;
    }

    public String getAcname() {
        return acname;
    }

    public void setAcname(String acname) {
        this.acname = acname;
    }

    public Long getAccid() {
        return accid;
    }

    public void setAccid(Long accid) {
        this.accid = accid;
    }

    public int getAccode() {
        return accode;
    }

    public void setAccode(int accode) {
        this.accode = accode;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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
