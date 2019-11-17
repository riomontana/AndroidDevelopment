package com.example.lfo.p1;

/**
 * Created by LFO on 2017-09-14.
 */

public class ExpenseTransaction {
    private String date;
    private String title;
    private int category;
    private double price;

    public ExpenseTransaction(String date, String title, int category, double price) {
        this.date = date;
        this.title = title;
        this.category = category;
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public int getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }
}
