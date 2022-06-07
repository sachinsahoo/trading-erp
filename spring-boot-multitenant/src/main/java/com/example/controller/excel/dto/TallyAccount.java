package com.example.controller.excel.dto;

import com.example.common.exception.InputValidationException;
import com.example.entity.dto.acct.AccountDto;
import com.example.entity.dto.acct.AccountGroupDto;
import com.example.entity.dto.order.CompanyDto;
import com.example.entity.dto.order.ContactDto;
import com.example.entity.dto.order.InvTransactionDto;
import com.example.entity.rs.base.AbstractBaseRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.poi.ss.usermodel.Row;

@JsonIgnoreProperties (ignoreUnknown = true)
public class TallyAccount extends AbstractBaseRequest {

    private String name;
    private String group;
    private String subgroup;
    private String country;
    private String state;
    private String pincode;
    private String contactNo;
    private String mobileNo;
    private String contactPerson;
    private String email;
    private String address;
    private String panNo;
    private String tinNo;
    private String cstNo;
    private String registrationType;
    private String gstin;
    private String openingBalance;
    private String closingBalance;

    // For Reconciliation
    private AccountGroupDto parentGroupDto;
    private AccountGroupDto groupDto;
    private CompanyDto clientDto;
    private ContactDto contactDto;
    private AccountDto accountDto;
    private InvTransactionDto transactionDto;

    @Override
    public void validate () throws InputValidationException {
    }

    public TallyAccount() {
    }

    public TallyAccount(Row row) {

        try {

            this.setName(row.getCell(1).getStringCellValue());
            this.setGroup(row.getCell(3).getStringCellValue());
            this.setSubgroup(row.getCell(4).getStringCellValue());
            this.setCountry(row.getCell(5).getStringCellValue());
            this.setState(row.getCell(6).getStringCellValue());
            this.setPincode(row.getCell(7).getStringCellValue());
            this.setContactNo(row.getCell(8).getStringCellValue());
            this.setMobileNo(row.getCell(9).getStringCellValue());
            this.setContactPerson(row.getCell(10).getStringCellValue());
            this.setEmail(row.getCell(11).getStringCellValue());
            this.setAddress(row.getCell(12).getStringCellValue());
            this.setPanNo(row.getCell(15).getStringCellValue());
            this.setTinNo(row.getCell(16).getStringCellValue());
            this.setCstNo(row.getCell(17).getStringCellValue());
            this.setRegistrationType(row.getCell(18).getStringCellValue());
            this.setGstin(row.getCell(23).getStringCellValue());
            this.setOpeningBalance(row.getCell(25).getStringCellValue());
            this.setClosingBalance(row.getCell(26).getStringCellValue());
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }



    public String getGroup_Parent() {
        String parent = this.subgroup != null ? this.subgroup : "";
        String group = this.group != null ? this.group : "";
        return group +"|" + parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getSubgroup() {
        return subgroup;
    }

    public void setSubgroup(String subgroup) {
        this.subgroup = subgroup;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPanNo() {
        return panNo;
    }

    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }

    public String getTinNo() {
        return tinNo;
    }

    public void setTinNo(String tinNo) {
        this.tinNo = tinNo;
    }

    public String getCstNo() {
        return cstNo;
    }

    public void setCstNo(String cstNo) {
        this.cstNo = cstNo;
    }

    public String getRegistrationType() {
        return registrationType;
    }

    public void setRegistrationType(String registrationType) {
        this.registrationType = registrationType;
    }

    public String getGstin() {
        return gstin;
    }

    public void setGstin(String gstin) {
        this.gstin = gstin;
    }

    public String getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(String openingBalance) {
        this.openingBalance = openingBalance;
    }

    public String getClosingBalance() {
        return closingBalance;
    }

    public void setClosingBalance(String closingBalance) {
        this.closingBalance = closingBalance;
    }

    public AccountGroupDto getParentGroupDto() {
        return parentGroupDto;
    }

    public void setParentGroupDto(AccountGroupDto parentGroupDto) {
        this.parentGroupDto = parentGroupDto;
    }

    public AccountGroupDto getGroupDto() {
        return groupDto;
    }

    public void setGroupDto(AccountGroupDto groupDto) {
        this.groupDto = groupDto;
    }

    public CompanyDto getClientDto() {
        return clientDto;
    }

    public void setClientDto(CompanyDto clientDto) {
        this.clientDto = clientDto;
    }

    public ContactDto getContactDto() {
        return contactDto;
    }

    public void setContactDto(ContactDto contactDto) {
        this.contactDto = contactDto;
    }

    public AccountDto getAccountDto() {
        return accountDto;
    }

    public void setAccountDto(AccountDto accountDto) {
        this.accountDto = accountDto;
    }

    public InvTransactionDto getTransactionDto() {
        return transactionDto;
    }

    public void setTransactionDto(InvTransactionDto transactionDto) {
        this.transactionDto = transactionDto;
    }
}
