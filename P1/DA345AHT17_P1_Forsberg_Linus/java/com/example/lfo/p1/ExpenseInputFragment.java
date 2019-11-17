package com.example.lfo.p1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class ExpenseInputFragment extends Fragment {
    private TextView tvAddNewExpense;
    private TextView tvCategories;
    private TextView tvFood;
    private TextView tvTravel;
    private TextView tvMisc;
    private TextView tvLeisure;
    private TextView tvLiving;
    private ImageButton ibFood;
    private ImageButton ibLeisure;
    private ImageButton ibTravel;
    private ImageButton ibMisc;
    private ImageButton ibLiving;
    private EditText inputDate;
    private EditText inputTitle;
    private EditText inputPrice;
    private Button btnAddExpenseTransaction;
    private Controller controller;
    private int category = 0;

    public ExpenseInputFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expense_input, container, false);
        initComponents(view);
        registerListener();
        return view;
    }

    private void initComponents(View view) {
        tvAddNewExpense = (TextView) view.findViewById(R.id.tvAddNewExpense);
        tvCategories = (TextView) view.findViewById(R.id.tvCategories);
        tvFood = (TextView) view.findViewById(R.id.tvFood);
        tvTravel = (TextView) view.findViewById(R.id.tvTravel);
        tvMisc = (TextView) view.findViewById(R.id.tvMisc);
        tvLeisure = (TextView) view.findViewById(R.id.tvLeisure);
        tvLiving = (TextView) view.findViewById(R.id.tvHouse);
        ibFood = (ImageButton) view.findViewById(R.id.ibFood);
        ibLeisure = (ImageButton) view.findViewById(R.id.ibSparetime);
        ibTravel = (ImageButton) view.findViewById(R.id.ibTravel);
        ibMisc = (ImageButton) view.findViewById(R.id.ibMisc);
        ibLiving = (ImageButton) view.findViewById(R.id.ibHouse);
        inputDate = (EditText) view.findViewById(R.id.inputDate);
        inputTitle = (EditText) view.findViewById(R.id.inputTitle);
        inputPrice = (EditText) view.findViewById(R.id.inputPrice);
        btnAddExpenseTransaction = (Button) view.findViewById(R.id.btnAddExpenseTransaction);
    }

    private void registerListener() {
        View.OnClickListener categoryListener = new CategoryButtonListener();
        View.OnClickListener addExpenseListener = new AddExpenseButtonListener();
        ibFood.setOnClickListener(categoryListener);
        ibLeisure.setOnClickListener(categoryListener);
        ibTravel.setOnClickListener(categoryListener);
        ibMisc.setOnClickListener(categoryListener);
        ibLiving.setOnClickListener(categoryListener);
        btnAddExpenseTransaction.setOnClickListener(addExpenseListener);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    private class CategoryButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ibFood:
                    category = R.drawable.food;
                    Toast.makeText(getActivity(), "Chosen category: Food",
                            Toast.LENGTH_SHORT).show();
                    break;
                case R.id.ibSparetime:
                    category = R.drawable.sparetime;
                    Toast.makeText(getActivity(), "Chosen category: Leisure",
                            Toast.LENGTH_SHORT).show();
                    break;
                case R.id.ibHouse:
                    category = R.drawable.house;
                    Toast.makeText(getActivity(), "Chosen category: Living",
                            Toast.LENGTH_SHORT).show();
                    break;
                case R.id.ibMisc:
                    category = R.drawable.misc;
                    Toast.makeText(getActivity(), "Chosen category: Miscellaneous",
                            Toast.LENGTH_SHORT).show();
                    break;
                case R.id.ibTravel:
                    category = R.drawable.travel;
                    Toast.makeText(getActivity(), "Chosen category: Travel",
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    private class AddExpenseButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (inputDate.getText() == null || inputPrice.getText() == null || inputTitle.getText() == null || category == 0) {
                Toast.makeText(getActivity(), "Please input all values",
                        Toast.LENGTH_SHORT).show();
            } else {
                String date = inputDate.getText().toString();
                String title = inputTitle.getText().toString();
                double price = Double.parseDouble(inputPrice.getText().toString());
                controller.createExpenseTransaction(date, title, category, price);
                Toast.makeText(getActivity(), "Expense added",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}

