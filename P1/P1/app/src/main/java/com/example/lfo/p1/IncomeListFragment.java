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
    private Button btnChooseDates;
    private Button btnAddIncome;
    private Button btnBackToMenu;
    private TextView tvUser;
    private TextView tvIncome;

    public IncomeListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_income_list, container, false);
        tvUser = (TextView)view.findViewById(R.id.tvTitleIncome);
        tvIncome = (TextView)view.findViewById(R.id.tvIncomes);
        lvIncomeTransactions = (ListView)view.findViewById(R.id.lvIncomeTransactions);
        btnAddIncome = (Button)view.findViewById(R.id.btnAddIncome);
        btnChooseDates = (Button)view.findViewById(R.id.btnIncomeListChooseDates);
        btnBackToMenu = (Button)view.findViewById(R.id.btnBackToMenu);
        tvUser.setText(controller.getUserSharedPref());
        btnChooseDates.setOnClickListener(new ButtonListener());
        btnAddIncome.setOnClickListener(new ButtonListener());
        btnBackToMenu.setOnClickListener(new ButtonListener());
        lvIncomeTransactions.setOnItemClickListener(new IncomeListListener());
        lvIncomeTransactions.setAdapter(adapter);
        populateIncomeList(getActivity());
        return view;
    }

    public void populateIncomeList(FragmentActivity activity) {
        lvIncomeTransactions.setAdapter(new IncomeTransactionAdapter(activity,controller.getIncomeArray()));
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
            switch(view.getId()) {
                case R.id.btnAddIncome :
                    controller.showIncomeInputFragment();
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
