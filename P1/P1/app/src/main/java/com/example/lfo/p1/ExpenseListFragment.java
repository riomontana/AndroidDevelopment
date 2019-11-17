package com.example.lfo.p1;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class ExpenseListFragment extends Fragment {
//    private IncomeTransactionAdapter adapter;
    private Controller controller;
    private ListView lvExpenseTransactions;
    private Button btnAddExpense;
    private Button btnChooseDates;
    private TextView tvUser;
    private Button btnBackToMenu;
    private ExpenseTransactionAdapter adapter;

    public ExpenseListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expenses_list, container, false);
        btnAddExpense = (Button)view.findViewById(R.id.btnAddExpense);
        btnChooseDates = (Button)view.findViewById(R.id.btnExpensesListChooseDates);
        btnBackToMenu = (Button)view.findViewById(R.id.btnBackToMenu);
        tvUser = (TextView)view.findViewById(R.id.tvUser);
        tvUser.setText(controller.getUserSharedPref()); //TODO fixa nullpointer
        btnChooseDates.setOnClickListener(new ButtonListener());
        btnAddExpense.setOnClickListener(new ButtonListener());
        btnBackToMenu.setOnClickListener(new ButtonListener());
        lvExpenseTransactions = (ListView)view.findViewById(R.id.lvExpenseTransactions);
        lvExpenseTransactions.setOnItemClickListener(new ExpenseListListener());
        populateExpenseList(getActivity());
        return view;
    }

    public void populateExpenseList(FragmentActivity activity) {
        adapter = new ExpenseTransactionAdapter(activity,controller.getExpenseArray());
//        lvExpenseTransactions.setAdapter(new ExpenseTransactionAdapter(activity,controller.getExpenseArray()));
    }

    public void onResume() {
        super.onResume();
        lvExpenseTransactions.setAdapter(adapter);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    private class ExpenseListListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            controller.showExpenseAlertDialog(position);
        }
    }

    private class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.btnAddExpense :
                    controller.showExpenseInputFragment();
                    break;
                case R.id.btnDates :
                    controller.showDatePickerFragment();
                    break;
                case R.id.btnBackToMenu :
                    controller.initMenuFragment();
            }
        }
    }

}