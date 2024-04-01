package com.example.demo3.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Document(collection="transactions")
public class TransactionDTO {
    @Id
    private String id;
    private String tno;
    private String type;
    private String from;
    private String to;
    private String init_currency;
    private String final_currency;
    private double amount;
    private int day;
    private int month;
    private int year;
    private LocalDateTime transactionTime;
}
