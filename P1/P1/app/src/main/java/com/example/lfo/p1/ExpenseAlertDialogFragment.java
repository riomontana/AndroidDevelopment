package com.example.lfo.p1;


import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class ExpenseAlertDialogFragment extends Fragment {
    private TextView tvDate;
    private TextView tvTitle;
    private ImageView ivCategory;
    private TextView tvPrice;
    private String date;
    private String title;
    private int category;
    private double price;
    private Controller controller;

    public ExpenseAlertDialogFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expense_alert_dialog, container, false);
        tvDate = (TextView)view.findViewById(R.id.tvDateTo);
        tvTitle = (TextView)view.findViewById(R.id.tvTitle);
        ivCategory = (ImageView) view.findViewById(R.id.ivCategory);
        tvPrice = (TextView)view.findViewById(R.id.tvPrice);
        tvDate.setText(date);
        tvTitle.setText(title);
        ivCategory.setImageResource(category);
        tvPrice.setText(String.valueOf(price));
        return view;
    }

    public void setText(String date, String title, int category, double amount) {
        this.date = date;
        this.title = title;
        this.category = category;
        this.price = amount;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
}
