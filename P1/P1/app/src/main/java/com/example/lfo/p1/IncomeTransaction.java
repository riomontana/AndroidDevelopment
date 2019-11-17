package com.example.lfo.p1;

/**
 * Created by LFO on 2017-09-14.
 */

public class IncomeTransaction {
    private String date;
    private String title;
    private String category;
    private double amount;

    public IncomeTransaction(String date, String title, String category, double amount) {
        this.date = date;
        this.title = title;
        this.category = category;
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
