package com.example.demo3.controller;

import com.example.demo3.model.TransactionDTO;
import com.example.demo3.repository.TransactionRepository;
import org.bson.codecs.jsr310.LocalDateTimeCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
public class TransactionController {
    @Autowired
    private TransactionRepository transactionRepo;

    @GetMapping("/gettransactions")
    public ResponseEntity<?> getAllTransactions(){
        List<TransactionDTO> transactions = transactionRepo.findAll();

        if(transactions.size()>0) {
            return new ResponseEntity<List<TransactionDTO>>(transactions, HttpStatus.OK);

        }
        else{
            return new ResponseEntity<>("No transactions available", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/createtransactions")
    public ResponseEntity<?> createTransaction(@RequestBody TransactionDTO transaction)
    { try {
        transaction.setTransactionTime(LocalDateTime.now());
        transactionRepo.save(transaction);
        return new ResponseEntity<TransactionDTO>(transaction, HttpStatus.OK);
    } catch (Exception e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    }
}
