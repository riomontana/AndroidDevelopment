package com.example.lfo.p1;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by LFO on 2017-09-14.
 */

public class ExpenseTransactionAdapter extends ArrayAdapter<ExpenseTransaction> {
    private LayoutInflater inflater;
    private ExpenseTransaction expenseTransaction;


    public ExpenseTransactionAdapter(Context context, ExpenseTransaction[] expenseTransaction) {
        super(context, 0, expenseTransaction);
        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        expenseTransaction = getItem(position);
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.expense_transaction_item, parent, false);
        TextView tvDate = (TextView)convertView.findViewById(R.id.tvTransactionDate);
        tvDate.setText(expenseTransaction.getDate());
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTransactionTitle);
        tvTitle.setText(expenseTransaction.getTitle());
        ImageView ivCategory = (ImageView) convertView.findViewById(R.id.ivTransactionCategory);
        ivCategory.setImageResource(expenseTransaction.getCategory());
        TextView tvAmount = (TextView) convertView.findViewById((R.id.tvTransactionPrice));
        tvAmount.setText(String.valueOf(expenseTransaction.getPrice()));

        return convertView;
    }
}
