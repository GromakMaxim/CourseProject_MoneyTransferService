package com.example.mtservice1.controller;

import com.example.mtservice1.model.transaction.Operation;
import com.example.mtservice1.model.transaction.Transaction;
import com.example.mtservice1.model.transaction.TransactionStatus;
import com.example.mtservice1.service.CardDBService;
import com.example.mtservice1.service.MoneyExchangerService;
import com.example.mtservice1.service.TransactionDBService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;


@RestController()
@RequestMapping("/")
public class Controller {
    private TransactionDBService transactionDBService;
    private CardDBService cardDBService;
    private MoneyExchangerService moneyExchangerService;
    private AtomicInteger repIndex = new AtomicInteger(100);

    public Controller(TransactionDBService transactionDBService, CardDBService cardDBService, MoneyExchangerService moneyExchangerService) {
        this.transactionDBService = transactionDBService;
        this.cardDBService = cardDBService;
        this.moneyExchangerService = moneyExchangerService;
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/transfer")
    public Object catchTransferOperation(@Valid @RequestBody Transaction transaction) {
        if (cardDBService.isExist(transaction) && !transactionDBService.checkIfCardNumbersSame(transaction)) {//check both cards from transaction
            Integer code = repIndex.incrementAndGet();
            transactionDBService.save(Integer.toString(code), transaction);//register new operation using generated code

            HashMap<String, String> map = new HashMap<>();//temp. map using for response only
            map.put("code", "0000");
            map.put("operationId", code.toString());
            return map;
        } else {
            transaction.setStatus(TransactionStatus.REJECTED);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST);
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/confirmOperation")
    public Object catchConfirm(@Valid @RequestBody Operation operation) {
        String opId = String.valueOf(operation.getOperationId());
        if (transactionDBService.isExist(opId)) {//check if operation was registered
            Transaction transaction = transactionDBService.getTransactionByID(opId);//find registered operation
            transaction.setStatus(TransactionStatus.ACCEPTED);

            moneyExchangerService.process(transaction);//init. exchange

            HashMap<String, String> map = new HashMap<>();
            map.put("operationId", opId);
            return map;
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("operation code is not valid");
        }
    }

}