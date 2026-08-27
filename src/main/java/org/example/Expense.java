package org.example;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Expense {
    private int id;
    private String date;
    private String description;
    private double amount;

    public Expense() {}

    @JsonCreator
    public Expense(@JsonProperty("id") int id,
                   @JsonProperty("date") String date,
                   @JsonProperty("description") String description,
                   @JsonProperty("amount") double amount
    ) {
        this.id = id;
        this.date = date;
        this.description = description;
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

}
