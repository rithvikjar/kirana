package com.example.demo3.model;

import lombok.Data;

@Data
public class TransactionRequest {
    private String from;
    private String to;
    private Double amount;
    private String init_currency;

}
