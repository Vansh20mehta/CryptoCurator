package com.cryptoapp.req_res;

import com.cryptoapp.entities.OrderType;

import lombok.Data;

@Data
public class OrderReq {
    private String coinId;
    private double quantity;
    private OrderType orderType; 
}
