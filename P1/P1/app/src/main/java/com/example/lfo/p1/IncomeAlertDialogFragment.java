package com.example.lfo.p1;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class IncomeAlertDialogFragment extends Fragment {
    private TextView tvDate;
    private TextView tvTitle;
    private TextView tvCategory;
    private TextView tvAmount;
    private String date;
    private String title;
    private String category;
    private double amount;
    private Controller controller;

    public IncomeAlertDialogFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_income_alert_dialog,container,false);
        tvDate = (TextView)view.findViewById(R.id.tvDateTo);
        tvTitle = (TextView)view.findViewById(R.id.tvTitle);
        tvCategory = (TextView)view.findViewById(R.id.tvCategory);
        tvAmount = (TextView)view.findViewById(R.id.tvPrice);
        tvDate.setText(date);
        tvTitle.setText(title);
        tvCategory.setText(category);
        tvAmount.setText(String.valueOf(amount));
        return view;
    }

    public void setText(String date, String title, String category, double amount) {
        this.date = date;
        this.title = title;
        this.category = category;
        this.amount = amount;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
}
