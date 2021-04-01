package com.example.mtservice1.service;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

public class CardValidator {
    public boolean validateCardNumber(String cardNumber) {
        final Pattern cardPattern = Pattern.compile("^[0-9]{16}$");
        return cardPattern.matcher(cardNumber).find();
    }

    public boolean validateSVV(String SVV) {
        final Pattern svvPattern = Pattern.compile("^[0-9]{3}$");
        return svvPattern.matcher(SVV).find();
    }

    public boolean validatePeriod(String period) {
        int checkMonth, checkYear;
        try {
            checkMonth = Integer.parseInt(period.split("/")[0]);
            checkYear = Integer.parseInt(period.split("/")[1]);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public boolean validateCurrency(String currency) {
        return currency.equalsIgnoreCase("RUR");
    }

    public boolean validatePayment(String payment) {
        try {
            Long p = Long.parseLong(payment);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public boolean validateCode(String code) {
        final Pattern codePattern = Pattern.compile("^[0-9]{3}$");
        return codePattern.matcher(code).find();
    }

    private static String getCurrentMonthString() {
        Calendar calendar = new GregorianCalendar();
        DateFormat df = new SimpleDateFormat("MM");
        return df.format(calendar.getTime());
    }
}
