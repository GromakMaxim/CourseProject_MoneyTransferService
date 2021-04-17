package com.example.mtservice1.repository;

import com.example.mtservice1.model.card.ClientCard;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

class CardsDBTest {
    private CardsDB cardsDB = new CardsDB();


    @Test
    void isExist() {
        assertTrue(cardsDB.isExist("5610591081018250"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"","  ","   ","123","12314345","23rd3t5","=/*---/","wedwfetgytrhty5","----","null"})
    void isExist_expectFalse(String args){
        assertFalse(cardsDB.isExist(args));
    }

    @Test
    void getCardByNumber_expectTrue() {
        String expected = "5610591081018250";
        ClientCard card = cardsDB.getCardByNumber(expected);
        String actual = card.getCardNumber();

        assertEquals(expected,actual);
    }

    @ParameterizedTest()
    @ValueSource(strings = {"","  ","   ","123","12314345","23rd3t5","=/*---/","wedwfetgytrhty5","----","null"})
    void getCardByNumber_expectNPE(String args) {
        try{
            cardsDB.getCardByNumber(args);
        } catch (NullPointerException npe){
            Assert.assertTrue(npe.getMessage().equals("null"));
        }
    }

}