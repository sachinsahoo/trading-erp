package com.example.entity.rs.order;

import com.example.entity.rs.base.BaseResponse;
import com.example.entity.dto.order.PurchaseOrderDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PurchaseOrderListResponse extends BaseResponse {

private List<PurchaseOrderDto> orders;
private OrderSearchRequest searchRequest;

    public List<PurchaseOrderDto> getOrders() {
        return orders;
    }

    public void setOrders(List<PurchaseOrderDto> orders) {
        this.orders = orders;
    }

    public OrderSearchRequest getSearchRequest() {
        return searchRequest;
    }

    public void setSearchRequest(OrderSearchRequest searchRequest) {
        this.searchRequest = searchRequest;
    }
}
