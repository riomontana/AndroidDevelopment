package com.example.lfo.p1;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class IncomeInputFragment extends Fragment {
    private EditText inputPrice;
    private EditText inputDate;
    private EditText inputTitle;
    private TextView tvAddNewIncome;
    private TextView tvCategories;
    private Button btnAddIncome;
    private CheckBox cbSalary;
    private CheckBox cbOther;
    private Controller controller;
    private String category = "";

    public IncomeInputFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_income_input, container, false);
        initComponents(view);
        registerListeners();
        setController(controller);
        return view;
    }

    private void initComponents(View view) {
        inputDate = (EditText) view.findViewById(R.id.inputDate);
        inputPrice = (EditText) view.findViewById(R.id.inputPrice);
        inputTitle = (EditText) view.findViewById(R.id.inputTitle);
        tvAddNewIncome = (TextView) view.findViewById(R.id.tvAddNewIncome);
        tvCategories = (TextView) view.findViewById(R.id.tvCategories);
        btnAddIncome = (Button) view.findViewById(R.id.btnAddIncomeTransaction);
        cbOther = (CheckBox) view.findViewById(R.id.cbOther);
        cbSalary = (CheckBox) view.findViewById(R.id.cbSalary);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    private void registerListeners() {
        View.OnClickListener checkBoxListener = new CheckBoxListener();
        View.OnClickListener addIncomeListener = new AddIncomeListener();
        btnAddIncome.setOnClickListener(addIncomeListener);
        cbSalary.setOnClickListener(checkBoxListener);
        cbOther.setOnClickListener(checkBoxListener);

    }

    private class CheckBoxListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            if(cbSalary.isChecked()) {
                category = "Salary";
                Toast.makeText(getActivity(), "Chosen category: Salary",
                        Toast.LENGTH_SHORT).show();
//                cbOther.setChecked(false);
            }
            if(cbOther.isChecked()) {
                category = "Other";
                Toast.makeText(getActivity(), "Chosen category: Other",
                        Toast.LENGTH_SHORT).show();
                cbSalary.setChecked(false);
            }
        }
    }

    private class AddIncomeListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (inputDate.getText() == null || inputPrice.getText() == null || inputTitle.getText() == null || category == "") {
                Toast.makeText(getActivity(), "Please input all values",
                        Toast.LENGTH_SHORT).show();
            } else {
                String date = inputDate.getText().toString();
                String title = inputTitle.getText().toString();
                double amount = Double.parseDouble(inputPrice.getText().toString());
                controller.createIncomeTransaction(date, title, category, amount);

                Toast.makeText(getActivity(), "Income added",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}