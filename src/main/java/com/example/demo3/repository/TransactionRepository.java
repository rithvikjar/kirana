package com.example.demo3.repository;

import com.example.demo3.model.TransactionDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface TransactionRepository extends MongoRepository<TransactionDTO, String> {

    List<TransactionDTO> findAllByDayAndMonthAndYear(int day, int month, int year);


    List<TransactionDTO> findAllByMonthAndYear(int month, int year);


    List<TransactionDTO> findAllByMonthAndYearAndFrom(int month, int year);

    List<TransactionDTO> findAllByMonthAndYearAndTo(int month, int year);


    List<TransactionDTO> findAllByDayAndMonthAndYearAndFrom(
            int day, int month, int year, String from);


    List<TransactionDTO> findAllByDayAndMonthAndYearAndTo(int day, int month, int year, String to);


    List<TransactionDTO> findByTransactionTimeBetweenAndTo(
            LocalDateTime day1, LocalDateTime day2);


    List<TransactionDTO> findByTransactionTimeBetweenAndFrom(
            LocalDateTime day1, LocalDateTime day2);


    List<TransactionDTO> findAllByYear(int year);


    List<TransactionDTO> findAllByYearAndFrom(int year);


    List<TransactionDTO> findByYearAndFrom(int year);

    List<TransactionDTO> findByYearAndTo(int year);



}

