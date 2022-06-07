package com.example.manager.dto;

import com.example.constant.InternalAccountType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class TestJournalTransaction implements Serializable {

    private String notes;
    private String amt;
    private InternalAccountType debitAc;
    private InternalAccountType creditAc;

    public TestJournalTransaction(String notes, String amt, InternalAccountType debitAc, InternalAccountType creditAc) {
        this.notes = notes;
        this.amt = amt;
        this.debitAc = debitAc;
        this.creditAc = creditAc;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public InternalAccountType getDebitAc() {
        return debitAc;
    }

    public void setDebitAc(InternalAccountType debitAc) {
        this.debitAc = debitAc;
    }

    public InternalAccountType getCreditAc() {
        return creditAc;
    }

    public void setCreditAc(InternalAccountType creditAc) {
        this.creditAc = creditAc;
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
