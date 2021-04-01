package com.example.mtservice1.service;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class CardValidatorTest {
    private CardValidator cv = new CardValidator();

    @ParameterizedTest
    @ValueSource(strings = {"1234567890123450", "1111111111111111", "2222222222222222", "3333333333333333", "0000000000000000"})
    void validateCardNumber_used16symb(String argument) {
        assertTrue(cv.validateCardNumber(argument));
    }

    @ParameterizedTest
    @ValueSource(strings = {"123", "111", "", "333333333", "000000000000"})
    void validateCardNumber_usedLessThan16symb(String argument) {
        assertFalse(cv.validateCardNumber(argument));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1234567890123450254345", "111111111111111111111111111", "222222222222222234654564547467", "3333333333333333444444444444", "000000000000000044444444"})
    void validateCardNumber_usedMoreThan16symb(String argument) {
        assertFalse(cv.validateCardNumber(argument));
    }

    @ParameterizedTest
    @ValueSource(strings = {"aaaaaaaaaaaaaaaa", "bbbbbbbbbbbbbbbb", "cccccccccccccccc", "", "fioerjf", "uerhfiuerhferuifh", "112233445566778q", "1122334455667789q", "ergfe4r3r3r34r3df3f3f"})
    void validateCardNumber_usedLetters(String argument) {
        assertFalse(cv.validateCardNumber(argument));
    }

    @ParameterizedTest
    @ValueSource(strings = {"-23425-", "-1-32-4-4-6", "--4--5--7--", "", "----------------", "++++++++++++++++", "-1+3*4+5-5+**356+", "//////////////", "/*-+-*/*-++-/-*"})
    void validateCardNumber_usedOperationsSigns(String argument) {
        assertFalse(cv.validateCardNumber(argument));
    }

    @ParameterizedTest
    @ValueSource(strings = {"123", "111", "333", "321", "222", "333", "444", "555", "666", "777", "888", "999", "000", "678"})
    void validateSVV_usedLegalCombinations(String argument) {
        assertTrue(cv.validateSVV(argument));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1111", "2222", "1333", "2321", "3222", "4333", "5444", "6555", "7866", "8777", "9888", "0999", "0000", "0678"})
    void validateSVV_usedMoreThan3symb(String argument) {
        assertFalse(cv.validateSVV(argument));
    }

    @ParameterizedTest
    @ValueSource(strings = {"11", "", "1", "2", "32", "43", "5", "65", "78", "87", "98", "09", "0", "06"})
    void validateSVV_usedLessThan3symb(String argument) {
        assertFalse(cv.validateSVV(argument));
    }

    @ParameterizedTest
    @ValueSource(strings = {"aaa", "", "bbb", "aa", "bb", "aetgteargrtg", "ff", "qq", "etg", "23r", "3e2", "0o0", "0O0", "4GF"})
    void validateSVV_usedLetters(String argument) {
        assertFalse(cv.validateSVV(argument));
    }

    @ParameterizedTest
    @ValueSource(strings = {"-00", "", "+11", "1+1", "1-1", "2+2", "+2+", "*4*", "4*4", "1/1", "/2/", "---", "+++", "***", "/*-", "*-/"})
    void validateSVV_usedWrongSymb(String argument) {
        assertFalse(cv.validateSVV(argument));
    }

    @ParameterizedTest
    @ValueSource(strings = {"RUR"})
    void validateCurrency_usedLegalOption(String argument) {
        assertTrue(cv.validateCurrency(argument));
    }

    @ParameterizedTest
    @ValueSource(strings = {"RURr", "123", "", "SGF", "---", "+++", "Q", "qw", "12", "///", "*1*", "wfgergfergfer", "123424545"})
    void validateCurrency_usedIllegalOptions(String argument) {
        assertFalse(cv.validateCurrency(argument));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "123", "1234", "13435245", "23425346545645", "13345566", "02354", "000555"})
    void validatePayment_usedLegalValues(String argument) {
        assertTrue(cv.validatePayment(argument));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "qwe", "qq", "13435245q", "q23425346545645", "/*-", "-*/", "-*8+9*-*/+5/"})
    void validatePayment_usedIllegalValues(String argument) {
        assertFalse(cv.validatePayment(argument));
    }

    @ParameterizedTest
    @ValueSource(strings = {"111", "123", "123", "555", "666", "777", "000", "007"})
    void validateCode_usedLegalValues(String argument) {
        assertTrue(cv.validateCode(argument));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1111", "12", "123q", "5d5", "f666", "g77", "egrthg", "", "///", "-*/", "+++", "+3+", "f+f", "ertgfegerg", "134234234", "d*4d+d-34/*4+f+4-5*6/"})
    void validateCode_usedIllegalValues(String argument) {
        assertFalse(cv.validateCode(argument));
    }

    @ParameterizedTest
    @ValueSource(strings = {"qq/ee","13414","","1","/*-+","ww/22","rr|ff","3e:5t","98/fdf","////","**/**","--/--","++/++"})
    void validatePeriod_usedIllegalValues(String argument) {
        assertFalse(cv.validatePeriod(argument));
    }

}