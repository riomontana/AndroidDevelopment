package com.example.lfo.p1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LFO on 2017-09-13.
 */

public class IncomeTransactionAdapter extends ArrayAdapter<IncomeTransaction> {
    private LayoutInflater inflater;
    private IncomeTransaction incomeTransaction;

    public IncomeTransactionAdapter(Context context, IncomeTransaction[] incomeTransaction) {
        super(context, 0, incomeTransaction);
        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        incomeTransaction = getItem(position);
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.income_transaction_item, parent, false);
        TextView tvDate = (TextView)convertView.findViewById(R.id.tvTransactionDate);
        tvDate.setText(incomeTransaction.getDate());
        TextView tvTitle = (TextView)convertView.findViewById(R.id.tvTransactionTitle);
        tvTitle.setText(incomeTransaction.getTitle());
        TextView tvCategory = (TextView)convertView.findViewById(R.id.tvTransactionCategory);
        tvCategory.setText(incomeTransaction.getCategory());
        TextView tvAmount = (TextView)convertView.findViewById((R.id.tvTransactionAmount));
        tvAmount.setText(String.valueOf(incomeTransaction.getAmount()));

        return convertView;
    }
}
