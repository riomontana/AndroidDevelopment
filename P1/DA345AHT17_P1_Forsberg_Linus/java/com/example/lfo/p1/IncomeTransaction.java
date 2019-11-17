package com.example.lfo.p1;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by LFO on 2017-09-14.
 */

public class IncomeTransaction implements Parcelable {
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

    protected IncomeTransaction(Parcel in) {
        date = in.readString();
        title = in.readString();
        category = in.readString();
        amount = in.readDouble();
    }

    public static final Creator<IncomeTransaction> CREATOR = new Creator<IncomeTransaction>() {
        @Override
        public IncomeTransaction createFromParcel(Parcel in) {
            return new IncomeTransaction(in);
        }

        @Override
        public IncomeTransaction[] newArray(int size) {
            return new IncomeTransaction[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(date);
        parcel.writeString(title);
        parcel.writeString(category);
        parcel.writeDouble(amount);
    }
}
