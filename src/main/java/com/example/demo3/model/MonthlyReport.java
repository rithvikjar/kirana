package com.example.demo3.model;

import lombok.Data;

@Data
public class MonthlyReport {
    private Double totalTransaction;

    private Double totalDebit;

    private Double totalCredit;

    private Double averageCredit;

    private Double averageDebit;

    private Double averageTransaction;
}
