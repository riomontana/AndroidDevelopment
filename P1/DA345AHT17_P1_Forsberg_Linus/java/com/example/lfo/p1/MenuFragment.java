package com.example.lfo.p1;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;


public class MenuFragment extends Fragment {
    private Controller controller;
    private Button btnViewExpenses;
    private Button btnViewIncomes;
    private Button btnAddExpense;
    private Button btnAddIncome;
    private TextView tvUser;
    private Button btnChooseDate;
    private TextView tvIncomesAmount;
    private TextView tvExpensesAmount;
    private TextView tvTotalAmount;
    private double totalIncomes;
    private double totalExpenses;
    private double totalSum;
    private TextView tvDateFrom;
    private TextView tvDateTo;
    private String dateFrom = "";
    private String dateTo = "";
    private Bundle bundle;

    public MenuFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        initComponents(view);
        registerListeners();
//        setBundle(savedInstanceState);
        setTextViews(savedInstanceState);
        return view;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

//    public void onResume() {
//        super.onResume();
//        setTextViews(bundle);
//
//    }

    private void initComponents(View view) {
        btnViewExpenses = (Button)view.findViewById(R.id.btnExpenses);
        btnViewIncomes = (Button)view.findViewById(R.id.btnIncomes);
        btnAddExpense = (Button)view.findViewById(R.id.btnAddExpenseTransaction);
        btnAddIncome = (Button)view.findViewById(R.id.btnAddIncome);
        tvUser = (TextView)view.findViewById(R.id.tvWelcomeUser);
        btnChooseDate = (Button)view.findViewById(R.id.btnChooseDates);
        tvIncomesAmount = (TextView)view.findViewById(R.id.tvIncomesAmount);
        tvExpensesAmount = (TextView)view.findViewById(R.id.tvExpensesAmount);
        tvTotalAmount = (TextView)view.findViewById(R.id.tvTotalAmount);
        tvDateFrom = (TextView)view.findViewById(R.id.tvDateFrom);
        tvDateTo = (TextView)view.findViewById(R.id.tvDatesTo);
    }

    private void registerListeners() {
        View.OnClickListener addTransactionListener = new AddTransactionListener();
        View.OnClickListener viewTransactionsListener = new ViewTransactionsListener();
        btnAddIncome.setOnClickListener(addTransactionListener);
        btnAddExpense.setOnClickListener(addTransactionListener);
        btnViewExpenses.setOnClickListener(viewTransactionsListener);
        btnViewIncomes.setOnClickListener(viewTransactionsListener);
        btnChooseDate.setOnClickListener(new dateButtonListener());
    }

    public void onSaveInstanceState(Bundle onSavedInstanceState) {
        super.onSaveInstanceState(onSavedInstanceState);
        onSavedInstanceState.putString("dateFrom",tvDateFrom.getText().toString());
        onSavedInstanceState.putString("dateTo",tvDateTo.getText().toString());
        onSavedInstanceState.putString("totalIncomes", tvIncomesAmount.getText().toString());
        onSavedInstanceState.putString("totalExpenses",tvExpensesAmount.getText().toString());
        onSavedInstanceState.putString("totalAmount",tvTotalAmount.getText().toString());
        Log.d("MenuFrag", "onSaveInstanceState");
    }


    public void setTextViews(Bundle bundle) {
        if(bundle!=null) {
            tvUser.setText("Welcome " + controller.getUserSharedPref());
            tvDateFrom.setText(bundle.getString("dateFrom"));
            tvDateTo.setText(bundle.getString("dateTo"));
            tvIncomesAmount.setText(bundle.getString("totalIncomes"));
            tvExpensesAmount.setText(bundle.getString("totalExpenses"));
            tvTotalAmount.setText(bundle.getString("totalAmount"));
        } else {
            controller.setMenuDateViews();
            tvUser.setText("Welcome " + controller.getUserSharedPref());
            tvDateFrom.setText(dateFrom);
            tvDateTo.setText(dateTo);
            tvIncomesAmount.setText("Incomes: " + String.valueOf(totalIncomes));
            tvExpensesAmount.setText("Expenses: -" + String.valueOf(totalExpenses));
            tvTotalAmount.setText("Total: " + String.valueOf(totalSum));
        }
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setTotalIncomes(double totalIncomes) {
        this.totalIncomes = totalIncomes;
    }

    public void setTotalExpenses(double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public void setTotalSum() {
        totalSum = (totalIncomes - totalExpenses);
    }

    public void setDateViews(String dateFrom, String dateTo) {
        this.dateFrom = "Date from: " + dateFrom;
        this.dateTo = "Date to: " + dateTo;
    }

    private class dateButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            controller.showDatePickerFragment();
        }
    }

    private class AddTransactionListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnAddIncome :
                    controller.showIncomeInputFragment();
                    break;
                case R.id.btnAddExpenseTransaction:
                    controller.showExpenseInputFragment();
                    break;
            }
        }
    }

    private class ViewTransactionsListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.btnIncomes :
                    controller.showIncomeTransactions();
                    break;
                case R.id.btnExpenses :
                    controller.showExpenseTransactions();
                    break;
            }
        }
    }
}
