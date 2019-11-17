package com.example.lfo.p1;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by LFO on 2017-09-14.
 */

public class ExpenseTransaction implements Parcelable {
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

    protected ExpenseTransaction(Parcel in) {
        date = in.readString();
        title = in.readString();
        category = in.readInt();
        price = in.readDouble();
    }

    public static final Creator<ExpenseTransaction> CREATOR = new Creator<ExpenseTransaction>() {
        @Override
        public ExpenseTransaction createFromParcel(Parcel in) {
            return new ExpenseTransaction(in);
        }

        @Override
        public ExpenseTransaction[] newArray(int size) {
            return new ExpenseTransaction[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(date);
        parcel.writeString(title);
        parcel.writeInt(category);
        parcel.writeDouble(price);
    }
}
