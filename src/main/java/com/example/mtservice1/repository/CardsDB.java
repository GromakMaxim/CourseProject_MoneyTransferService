package com.example.mtservice1.repository;

import com.example.mtservice1.model.card.ClientCard;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * a class containing the service's customer cards. Money can be transferred between these cards
 */
@Repository
public class CardsDB {
    private Map<String, ClientCard> cards = new ConcurrentHashMap<>();

    public CardsDB() {
        //TEST CARDS
        cards.put("5610591081018250", new ClientCard("5610591081018250", "MaximGromak", "11/25", "123", new AtomicLong(100_000)));//Australian BankCard
        cards.put("5019717010103742", new ClientCard("5019717010103742", "Fredd Wilick", "11/25", "321", new AtomicLong(10_000)));//Dankort (PBS)
        cards.put("6011111111111117", new ClientCard("6011111111111117", "Samantha Smitsen", "11/25", "122", new AtomicLong(1_000)));//Discover
        cards.put("6011000990139424", new ClientCard("6011000990139424", "Mirabel Barmabold", "11/25", "133", new AtomicLong(20_000)));//Discover
        cards.put("3530111333300000", new ClientCard("3530111333300000", "Lucius Va", "11/25", "311", new AtomicLong(80_000)));//JCB
        cards.put("3566002020360505", new ClientCard("3566002020360505", "La John", "11/25", "211", new AtomicLong(1_000_000)));//JCB
        cards.put("5105105105105100", new ClientCard("5105105105105100", "Su Van Zuong", "11/25", "345", new AtomicLong(200_000)));//Master Card
        cards.put("5555555555554444", new ClientCard("5555555555554444", "Michail Globov", "11/25", "543", new AtomicLong(400_000)));//Master Card
        cards.put("4111111111111111", new ClientCard("4111111111111111", "Ian Pavlov", "11/25", "111", new AtomicLong(10_000)));//Visa
        cards.put("4012888888881881", new ClientCard("4012888888881881", "Svetlana Sisoeva", "11/25", "222", new AtomicLong(500_000)));//Visa

        cards.put("1111111111111111", new ClientCard("1111111111111111", "Test1", "11/25", "222", new AtomicLong(5_000)));//Visa
        cards.put("2222222222222222", new ClientCard("2222222222222222", "Test2", "11/25", "222", new AtomicLong(0)));//Visa
    }

    public boolean isExist(String cardFromNumber) {
        return cards.containsKey(cardFromNumber);
    }

    public ClientCard getCardByNumber(String cardNumber) {
        return cards.get(cardNumber);
    }
}
