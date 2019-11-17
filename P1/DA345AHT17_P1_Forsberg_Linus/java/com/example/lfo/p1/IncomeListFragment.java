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


public class IncomeListFragment extends Fragment {
    private IncomeTransactionAdapter adapter;
    private Controller controller;
    private ListView lvIncomeTransactions;
    private Button btnAddIncome;
    private TextView tvUser;
    private IncomeTransaction[] incomeTransactions;
    private String dateFrom;
    private String dateTo;

    public IncomeListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_income_list, container, false);
        tvUser = (TextView)view.findViewById(R.id.tvTitleIncome);
        lvIncomeTransactions = (ListView)view.findViewById(R.id.lvIncomeTransactions);
        btnAddIncome = (Button)view.findViewById(R.id.btnAddIncome);
        tvUser.setText("Incomes for " + controller.getUserSharedPref());
        btnAddIncome.setOnClickListener(new ButtonListener());
        lvIncomeTransactions.setOnItemClickListener(new IncomeListListener());
        populateIncomeList(getActivity(),savedInstanceState);
        lvIncomeTransactions.setAdapter(adapter);
        return view;
    }

    public void setDates(String dateFrom,String dateTo) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("dateFrom", dateFrom);
        savedInstanceState.putString("dateTo", dateTo);
    }

    public void populateIncomeList(FragmentActivity activity, Bundle bundle) {
        if (bundle != null) {
            dateFrom = bundle.getString("dateFrom");
            dateTo = bundle.getString("dateTo");
        }
        incomeTransactions = controller.getAllIncomeTransactions(dateFrom,dateTo);
        adapter = new IncomeTransactionAdapter(activity, incomeTransactions);
    }


    public void setController(Controller controller) {
        this.controller = controller;
    }

    private class IncomeListListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            controller.showIncomeAlertDialog(position);
        }
    }

    private class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            controller.showIncomeInputFragment();
        }
    }
}
