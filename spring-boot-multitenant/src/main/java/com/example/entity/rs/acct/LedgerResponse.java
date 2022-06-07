package com.example.entity.rs.acct;

import com.example.entity.dto.acct.BalanceSheetDto;
import com.example.entity.dto.acct.JournalDto;
import com.example.entity.rs.base.BaseResponse;
import com.example.entity.rs.order.JournalSearchRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LedgerResponse extends BaseResponse {

    private List<JournalDto> journals;
    private JournalSearchRequest searchRequest;

    public List<JournalDto> getJournals() {
        return journals;
    }

    public JournalSearchRequest getSearchRequest() {
        return searchRequest;
    }

    public void setSearchRequest(JournalSearchRequest searchRequest) {
        this.searchRequest = searchRequest;
    }

    public void setJournals(List<JournalDto> journals) {
        this.journals = journals;
    }
}
