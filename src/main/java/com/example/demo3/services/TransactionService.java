package com.example.demo3.services;

import com.example.demo3.model.ExchangeRateDTO;
import com.example.demo3.model.TransactionDTO;
import com.example.demo3.model.TransactionRequest;
import com.example.demo3.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class TransactionService {
    private  TransactionRepository transactionRepo;

    private ExchangeRateService exchangeRateService;


    @Autowired
    public TransactionService(
            TransactionRepository transactionRepository,
            ExchangeRateService exchangeRateService
             ) {
        this.transactionRepo = transactionRepository;
        this.exchangeRateService = exchangeRateService;
    }


    public TransactionDTO addTransaction(TransactionRequest data) throws IOException, InterruptedException {
        TransactionDTO transaction = new TransactionDTO();

        transaction.setInit_currency(data.getInit_currency());
        transaction.setFrom(data.getFrom());
        transaction.setTo(data.getTo());
        String currencyType = transaction.getInit_currency().toUpperCase();
        transaction.setInit_currency(currencyType);
        transaction.setFinal_currency("USD");
        Map<String, Double> rates = exchangeRateService.getRates();
        Double convertedAmount = data.getAmount() / rates.get(currencyType);
        transaction.setAmount(convertedAmount);

        LocalDateTime time = LocalDateTime.now();
        transaction.setTransactionTime(time);
        int day = time.getDayOfMonth();
        int month = time.getMonthValue();
        int year = time.getYear();
        transaction.setDay(day);
        transaction.setYear(year);
        transaction.setMonth(month);
        return transactionRepo.save(transaction);
    }


    public List<TransactionDTO> getAllTransactionOfMonth(int month, int year) {
        return transactionRepo.findAllByMonthAndYear(month, year);
    }



    public List<TransactionDTO> getMonthlyDebitOfUser(int month, int year) {
        return transactionRepo.findAllByMonthAndYearAndFrom(month, year);
    }



    public List<TransactionDTO> getMonthlyCreditOfUser(int month, int year) {
        return transactionRepo.findAllByMonthAndYearAndTo(month, year);
    }



    public List<TransactionDTO> getYearlyDebitOfUser(int year) {
        List<TransactionDTO> l = transactionRepo.findByYearAndFrom(year);
        return l;
    }

    public List<TransactionDTO> getYearlyCreditOfUser(int year) {
        List<TransactionDTO> transactions = transactionRepo.findByYearAndTo(year);
        LocalDateTime start = LocalDateTime.of(year, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(year + 1, 1, 1, 0, 0, 0);
        return transactionRepo.findByTransactionTimeBetweenAndTo(start, end);
    }

    public List<TransactionDTO> getCreditTransactionOfPastWeek() {
        LocalDateTime currentTime = LocalDateTime.now();
        // take start point as 7 days ago
        LocalDateTime startPoint = currentTime.minusDays(7);

        return transactionRepo.findByTransactionTimeBetweenAndTo(startPoint, currentTime);
    }


    public List<TransactionDTO> getDebitTransactionOfPastWeek() {
        LocalDateTime currentTime = LocalDateTime.now();
        // take start point as 7 days ago
        LocalDateTime startPoint = currentTime.minusDays(7);

        return transactionRepo.findByTransactionTimeBetweenAndFrom(startPoint, currentTime);
    }

    public List<TransactionDTO> getAllTransactions() {
        return transactionRepo.findAll();
    }
}
