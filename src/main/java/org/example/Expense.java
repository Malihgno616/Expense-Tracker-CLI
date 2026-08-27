package org.example;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Expense {
    private int id;
    private static int nextId = 1;
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
        if (id >= nextId) {
            nextId = id + 1;
        }
    }

    public Expense(String description, double amount) {
        this.id = nextId++;
        this.date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.description = description;
        this.amount = amount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

}
