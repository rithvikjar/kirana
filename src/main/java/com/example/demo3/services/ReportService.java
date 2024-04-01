package com.example.demo3.services;

import com.example.demo3.model.MonthlyReport;
import com.example.demo3.model.TransactionDTO;
import com.example.demo3.model.WeeklyReport;
import com.example.demo3.model.YearlyReport;
import com.example.demo3.repository.TransactionRepository;
import com.example.demo3.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;

@Service
public class ReportService {
    private final TransactionService transactionsService;
    private final TransactionRepository transactionRepo;


    @Autowired
    public ReportService(
            TransactionService transactionsService,
            TransactionRepository transactionRepository
    ) {
        this.transactionRepo = transactionRepository;
        this.transactionsService = transactionsService;
    }

    /*
      Map to get the number of days in a month based on the month number.
     */
    private static final Map<Integer, Integer> monthDaysMap =
            new HashMap<Integer, Integer>() {
                {
                    put(1, 31); // January
                    put(2, 28); // February
                    put(3, 31); // March
                    put(4, 30); // April
                    put(5, 31); // May
                    put(6, 30); // June
                    put(7, 31); // July
                    put(8, 31); // August
                    put(9, 30); // September
                    put(10, 31); // October
                    put(11, 30); // November
                    put(12, 31); // December
                }
            };


    public double amountSum(List<TransactionDTO> transactions) {
        double sum = 0.0;
        for (TransactionDTO transaction : transactions) {
            sum += transaction.getAmount();
        }
        return sum;
    }


    public OptionalDouble dailyAverage(int day, int month, int year) {
        List<TransactionDTO> transactions =
                transactionRepo.findAllByDayAndMonthAndYear(day, month, year);
        OptionalDouble average =
                transactions.stream().mapToDouble(TransactionDTO::getAmount).average();
        return average;

    }


    public OptionalDouble monthlyAverage(int month, int year) {
        List<TransactionDTO> transactions = transactionRepo.findAllByMonthAndYear(month, year);
        OptionalDouble average =
                transactions.stream().mapToDouble(TransactionDTO::getAmount).average();
        return average;
    }


    public OptionalDouble YearlyAverage(int year) {

        List<TransactionDTO> transactions = transactionRepo.findAllByYear(year);
        OptionalDouble average =
                transactions.stream().mapToDouble(TransactionDTO::getAmount).average();
        return average;
    }



    public MonthlyReport createMonthlyReport(int month, int year) {
        // Fetch monthly credit and debit transactions for the specified user
        List<TransactionDTO> monthlyCredit =
                transactionsService.getMonthlyCreditOfUser(month, year);
        List<TransactionDTO> monthlyDebit =
                transactionsService.getMonthlyDebitOfUser(month, year);

        // Calculate total credit and debit amounts, and round off to 2 decimal places
        Double totalCreditAmount = (amountSum(monthlyCredit));
        Double totalDebitAmount = (amountSum(monthlyDebit));

        // Calculate total amount, average credit, average debit, and average transaction
        Double totalAmount = totalDebitAmount + totalCreditAmount;
        Double averageCredit = (totalCreditAmount / monthDaysMap.get(month));
        Double averageDebit = (totalDebitAmount / monthDaysMap.get(month));
        Double averageTransaction = (totalAmount / monthDaysMap.get(month));

        // Create and return MonthlyReport
        MonthlyReport report = new MonthlyReport();
        report.setTotalTransaction(totalAmount);
        report.setTotalDebit(totalDebitAmount);
        report.setTotalCredit(totalCreditAmount);
        report.setAverageCredit(averageCredit);
        report.setAverageDebit(averageDebit);
        report.setAverageTransaction(averageTransaction);

        return report;
    }


    public YearlyReport createYearlyReport(int year) {
        // Fetch monthly credit and debit transactions for the specified user
        List<TransactionDTO> YearlyCredit = transactionsService.getYearlyCreditOfUser(year);
        List<TransactionDTO> YearlyDebit = transactionsService.getYearlyDebitOfUser(year);

        // Calculate total credit and debit amounts
        double totalCreditAmount = amountSum(YearlyCredit);
        double totalDebitAmount = amountSum(YearlyDebit);

        // Calculate total amount, average credit, average debit, and average transaction
        double totalDays = 365.0;
        double totalAmount = totalDebitAmount + totalCreditAmount;
        double averageCredit = (totalCreditAmount / totalDays);
        double averageDebit = (totalDebitAmount / totalDays);
        double averageTransaction = (totalAmount / totalDays);
        double netAmount = (totalCreditAmount - totalDebitAmount);

        // Create and return MonthlyReport
        YearlyReport report = new YearlyReport();
        report.setNetProfit(netAmount);
        report.setTotalTransaction(totalAmount);
        report.setTotalDebit(totalDebitAmount);
        report.setTotalCredit(totalCreditAmount);
        report.setAverageCredit(averageCredit);
        report.setAverageDebit(averageDebit);
        report.setAverageTransaction(averageTransaction);
        report.setNetProfit(netAmount);

        return report;
    }


    public WeeklyReport createWeeklyReport() {
        // Fetch monthly credit and debit transactions for the specified user
        List<TransactionDTO> weeklyCredit =
                transactionsService.getCreditTransactionOfPastWeek();
        List<TransactionDTO> weeklyDebit = transactionsService.getDebitTransactionOfPastWeek();

        // Calculate total credit and debit amounts, and round off to 2 decimal places
        Double totalCreditAmount = (amountSum(weeklyCredit));
        Double totalDebitAmount = (amountSum(weeklyDebit));

        // Calculate total amount, average credit, average debit, and average transaction
        Double totalDays = 7.0;
        Double totalAmount = totalDebitAmount + totalCreditAmount;
        Double averageCredit = (totalCreditAmount / totalDays);
        Double averageDebit = (totalDebitAmount / totalDays);
        Double averageTransaction = (totalAmount / totalDays);
        Double netAmount = totalCreditAmount - totalDebitAmount;

        WeeklyReport report = new WeeklyReport();
        report.setNetProfit(netAmount);
        report.setTotalTransaction(totalAmount);
        report.setTotalDebit(totalDebitAmount);
        report.setTotalCredit(totalCreditAmount);
        report.setAverageCredit(averageCredit);
        report.setAverageDebit(averageDebit);
        report.setAverageTransaction(averageTransaction);

        return report;
    }
}

