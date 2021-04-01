package com.example.mtservice1.controller;

import com.example.mtservice1.service.CardValidator;
import com.example.mtservice1.service.MyService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicInteger;


@RestController
@RequestMapping("/")
public class MyController {
    private MyService service;
    private CardValidator cardValidator;
    private AtomicInteger repIndex = new AtomicInteger(100);

    public MyController(MyService service, CardValidator cardValidator) {
        this.service = service;
        this.cardValidator = cardValidator;
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/transfer")
    public Object catchTransferOperation(@RequestBody String requestBodyString) throws ParseException {
        Object obj;
        try {
            obj = new JSONParser().parse(requestBodyString);
        } catch (ParseException pe) {
            return new ResponseEntity<>("wrong input data", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        JSONObject jo = (JSONObject) obj;

        JSONObject paymentData = (JSONObject) jo.get("amount");
        String sender = (String) jo.get("cardFromNumber");
        String payee = (String) jo.get("cardToNumber");
        String CVV = (String) jo.get("cardFromCVV");
        String period = "";
        String currency = (String) paymentData.get("currency");
        String value = paymentData.get("value").toString();

        JSONObject jobj = new JSONObject();

        if (!cardValidator.validateCardNumber(sender)) {//err400
            jobj.put("message", "wrong sender's card number");
            jobj.put("id", 400);
            return new ResponseEntity<>(jobj, HttpStatus.BAD_REQUEST);
        }
        if (!cardValidator.validateCardNumber(payee)) {//err400
            jobj.put("message", "wrong payee's card number");
            jobj.put("id", 400);
            return new ResponseEntity<>(jobj, HttpStatus.BAD_REQUEST);
        }
        if (!cardValidator.validateSVV(CVV)) {//err400
            jobj.put("message","wrong payee's card сvv");
            jobj.put("id", 400);
            return new ResponseEntity<>(jobj, HttpStatus.BAD_REQUEST);
        }
        if (!cardValidator.validateCurrency(currency)){//err400
            jobj.put("message","wrong currency");
            jobj.put("id", 400);
            return new ResponseEntity<>(jobj, HttpStatus.BAD_REQUEST);
        }
        if (!cardValidator.validatePayment(value)){//err400
            jobj.put("message","wrong value");
            jobj.put("id", 400);
            return new ResponseEntity<>("wrong value", HttpStatus.BAD_REQUEST);
        }
        Integer code = repIndex.incrementAndGet();
        String strCode = code.toString();

        service.createPaymentOperationAndSave(strCode, sender, payee, CVV, period, currency, Long.parseLong(value));
        jobj.put("operationId", code.toString());

        return jobj;
    }

    @PostMapping("/confirmOperation")
    public Object catchConfirm(@RequestBody String requestBodyString) {
        Object obj;
        JSONObject jobj = new JSONObject();
        try {
            obj = new JSONParser().parse(requestBodyString);
        } catch (ParseException pe) {
            jobj.put("message","wrong input data");
            jobj.put("id", 400);
            return new ResponseEntity<>(jobj, HttpStatus.BAD_REQUEST);
        }
        JSONObject jo = (JSONObject) obj;
        String code = (String) jo.get("operationId");

        if (!cardValidator.validateCode(code)) {//err500
            jobj.put("message","wrong code");
            jobj.put("id", 500);
            return new ResponseEntity<>(jobj, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (!service.isRegistered(code)) {//err400
            jobj.put("message","such transaction is not registered");
            jobj.put("id", 400);
            return new ResponseEntity<>(jobj, HttpStatus.BAD_REQUEST);
        }
        service.updatePaymentStatus(code);

        jobj.put("operationId", code);
        return new ResponseEntity<>(jobj, HttpStatus.OK);
    }

}