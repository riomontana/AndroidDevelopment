package com.example.lfo.p1;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by LFO on 2017-09-19.
 */

public class TransactionDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "transactions";
    private static final int DB_VERSION = 1;

    public TransactionDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        Log.d("DBHelper ","init DBHelper");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("onCreate","adding data");
        db.execSQL(TableIncomeTransaction.TABLE_CREATE);
        db.execSQL(TableExpenseTransaction.TABLE_CREATE);

//        insertExpense(db, 20170203, "Körv", R.drawable.food, 15);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TableIncomeTransaction.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TableExpenseTransaction.TABLE_NAME);
        onCreate(db);
        Log.d("DBHelper - onUpgrade","");
    }
}
