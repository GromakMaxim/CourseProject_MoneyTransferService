package com.example.mtservice1.model.transaction;

/**
 * all possible card's status placed here
 */
public enum TransactionStatus {
    WAIT, //each new incoming transaction gets this status. Not validated yet.
    ACCEPTED, //the transaction in which the cards were validated
    REJECTED, //can't complete transaction by different reasons
    DONE //correct transaction, was completed
}
