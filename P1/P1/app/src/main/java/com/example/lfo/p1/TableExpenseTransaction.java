package com.example.lfo.p1;

/**
 * Created by LFO on 2017-09-22.
 */

public class TableExpenseTransaction {
    public static final String TABLE_NAME = "EXPENSE_TRANSACTION";
    public static final String DATE = "DATE";
    public static final String TITLE = "TITLE";
    public static final String CATEGORY = "CATEGORY";
    public static final String PRICE = "AMOUNT";
//    public static final String TRANSACTION_ID = "_id";

    public static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + DATE + " NUMERIC, "
                    + TITLE + " TEXT, "
                    + CATEGORY + " INTEGER, "
                    + PRICE + " REAL);";

}
