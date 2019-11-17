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


public class ExpenseListFragment extends Fragment {

    private Controller controller;
    private ListView lvExpenseTransactions;
    private Button btnAddExpense;
    private TextView tvUser;
    private ExpenseTransactionAdapter adapter;
    private ExpenseTransaction[] expenseTransactions;
    private String dateFrom, dateTo;

    public ExpenseListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expenses_list, container, false);
        btnAddExpense = (Button)view.findViewById(R.id.btnAddExpense);
        tvUser = (TextView)view.findViewById(R.id.tvUser);
        lvExpenseTransactions = (ListView)view.findViewById(R.id.lvExpenseTransactions);
        setListeners();
        tvUser.setText("Expenses for " + controller.getUserSharedPref());
        populateExpenseList(getActivity(),savedInstanceState);
        lvExpenseTransactions.setAdapter(adapter);
        return view;
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("dateFrom", dateFrom);
        savedInstanceState.putString("dateTo", dateTo);
        savedInstanceState.putParcelableArray("expenseArray", controller.getAllExpenseTransactions(dateFrom,dateTo));
    }

    public void populateExpenseList(FragmentActivity activity, Bundle bundle) {
        if(bundle != null) {
                dateFrom = bundle.getString("dateFrom");
                dateTo = bundle.getString("dateTo");
        }
        expenseTransactions = controller.getAllExpenseTransactions(dateFrom, dateTo);
        adapter = new ExpenseTransactionAdapter(activity,expenseTransactions);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setListeners() {
        btnAddExpense.setOnClickListener(new ButtonListener());
        lvExpenseTransactions.setOnItemClickListener(new ExpenseListListener());
    }

    public void setDates(String dateFrom, String dateTo) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
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
            controller.showExpenseInputFragment();
        }
    }
}