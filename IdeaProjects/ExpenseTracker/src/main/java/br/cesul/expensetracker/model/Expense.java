package br.cesul.expensetracker.model;

import org.bson.types.ObjectId;

import java.time.LocalDate;

public class Expense {
    // Pojo simples que representa uma despesa

    // O id é gerado pelo mongo
    private ObjectId id;
    private String description;
    private double amount;
    private LocalDate date;

    public Expense(){}

    public Expense(String description, double amout, LocalDate date) {
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmout() {
        return amount;
    }

    public void setAmout(double amout) {
        this.amount = amout;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    // id
    // description
    // amout
    // date

}
