package com.example.demo3.controller;

import com.example.demo3.model.MonthlyReport;
import com.example.demo3.model.WeeklyReport;
import com.example.demo3.model.YearlyReport;
import com.example.demo3.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/report")
public class ReportController {
    private final ReportService reportService;


    @Autowired
    public ReportController(
            ReportService reportService) {
        this.reportService = reportService;
    }


    @GetMapping("/monthly/{month}/{year}")
    public MonthlyReport getMonthlyReport(

            @RequestParam int month,
            @RequestParam int year) {

        return (
                reportService.createMonthlyReport(month, year));
    }


    @GetMapping("/yearly/{year}")
    public YearlyReport getYearlyReport(


            @PathVariable("year") int year) {
        return (reportService.createYearlyReport(year));
    }


    @GetMapping("/weekly")
    public WeeklyReport getWeeklyReport(
            ) {

        return (reportService.createWeeklyReport());
    }
}