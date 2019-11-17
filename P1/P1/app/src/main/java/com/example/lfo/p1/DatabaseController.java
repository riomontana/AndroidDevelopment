package com.example.lfo.p1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by LFO on 2017-09-20.
 */

public class DatabaseController {
    Controller controller;
    TransactionDatabaseHelper dbHelper;

    public DatabaseController(Context context, Controller controller) {
        dbHelper = new TransactionDatabaseHelper(context);
        this.controller = controller;
    }

    public ArrayList<IncomeTransaction> getIncomeTransactions() {
        ArrayList<IncomeTransaction> incomeTransactionList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "SELECT * FROM " + TableIncomeTransaction.TABLE_NAME +
                " ORDER BY " + TableIncomeTransaction.DATE + " DESC;";
        Cursor cursor = db.rawQuery(sql,null);
        int date = cursor.getColumnIndex(TableIncomeTransaction.DATE);
        int title = cursor.getColumnIndex(TableIncomeTransaction.TITLE);
        int category = cursor.getColumnIndex(TableIncomeTransaction.CATEGORY);
        int amount = cursor.getColumnIndex(TableIncomeTransaction.AMOUNT);

        for(int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            incomeTransactionList.add(new IncomeTransaction(cursor.getString(date),
                    cursor.getString(title),cursor.getString(category),cursor.getDouble(amount)));
        }
        return incomeTransactionList;
    }

    public ArrayList<ExpenseTransaction> getExpenseTransactions() {
        ArrayList<ExpenseTransaction> expenseTransactionList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "SELECT * FROM " + TableExpenseTransaction.TABLE_NAME +
                " ORDER BY " + TableExpenseTransaction.DATE + " DESC;";
        Cursor cursor = db.rawQuery(sql,null);
        int date = cursor.getColumnIndex(TableExpenseTransaction.DATE);
        int title = cursor.getColumnIndex(TableExpenseTransaction.TITLE);
        int category = cursor.getColumnIndex(TableExpenseTransaction.CATEGORY);
        int price = cursor.getColumnIndex(TableExpenseTransaction.PRICE);

        for(int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            expenseTransactionList.add(new ExpenseTransaction(cursor.getString(date),
                    cursor.getString(title),cursor.getInt(category),cursor.getDouble(price)));
        }
        return expenseTransactionList;
    }

    public void setIncomeTransaction(IncomeTransaction incomeTransaction) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TableIncomeTransaction.DATE, incomeTransaction.getDate());
        values.put(TableIncomeTransaction.TITLE, incomeTransaction.getTitle());
        values.put(TableIncomeTransaction.CATEGORY, incomeTransaction.getCategory());
        values.put(TableIncomeTransaction.AMOUNT, incomeTransaction.getAmount());
        db.insert(TableIncomeTransaction.TABLE_NAME, " ", values);

        ArrayList<IncomeTransaction> incomeTransactionList = getIncomeTransactions();
        controller.updateIncomeList(incomeTransactionList);
    }

    public void setExpenseTransaction(ExpenseTransaction expenseTransaction) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TableExpenseTransaction.DATE, expenseTransaction.getDate());
        values.put(TableExpenseTransaction.TITLE, expenseTransaction.getTitle());
        values.put(TableExpenseTransaction.CATEGORY, expenseTransaction.getCategory());
        values.put(TableExpenseTransaction.PRICE, expenseTransaction.getPrice());
        db.insert(TableExpenseTransaction.TABLE_NAME, " ", values);

        ArrayList<ExpenseTransaction> expenseTransactionList = getExpenseTransactions();
        controller.updateExpenseList(expenseTransactionList);
    }
}
