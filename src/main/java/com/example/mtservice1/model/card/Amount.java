package com.example.mtservice1.model.card;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * a class for correct json parsing. Contains the currency and amount fields account.
 */
public class Amount {
    @NotNull
    @Size(min = 3, max = 3)
    private String currency;

    @NotNull
    @Min(1)
    @Max(1_000_000)
    private Long value;

    public Amount(String currency, Long value) {
        this.currency = currency;
        this.value = value;
    }

    public Amount() {
    }

    @JsonGetter("currency")
    public String getCurrency() {
        return currency;
    }
    @JsonSetter("currency")
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    @JsonGetter("value")
    public Long getValue() {
        return value;
    }
    @JsonSetter("value")
    public void setValue(Long value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Amount{" +
                "currency='" + currency + '\'' +
                ", value=" + value +
                '}';
    }
}