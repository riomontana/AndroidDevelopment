package com.example.lfo.p1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by LFO on 2017-09-12.
 */

public class Controller  {
    private MainActivity mainActivity;
    private UserLoginFragment userLoginFragment;
    private MenuFragment menuFragment;
    private IncomeTransaction[] incomeTransactionsArray;
    private ExpenseTransaction[] expenseTransactionsArray;
    private DatabaseController dbController;
    private String dateFrom;
    private String dateTo;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private ExpenseListFragment expenseListFragment;
    private IncomeListFragment incomeListFragment;
    private DatePickerFragment datePickerFragment;
    private IncomeInputFragment incomeInputFragment;
    private ExpenseInputFragment expenseInputFragment;
    private IncomeAlertDialogFragment incomeAlertDialog;
    private ExpenseAlertDialogFragment expenseAlertDialog;
    private String currentFragment;
    private boolean toBackstack = false;


    public Controller(MainActivity mainActivity, Bundle savedInstanceState) {
        this.mainActivity = mainActivity;
        loadFragments();
        dbController = new DatabaseController(mainActivity, this);
        if(savedInstanceState==null) {
            currentFragment = "loginFragment";
        }
    }

    public void saveInstanceState(Bundle bundle) {
        bundle.putString("currentFragment",currentFragment);
        bundle.putString("dateFrom", dateFrom);
        bundle.putString("dateTo",dateTo);
    }

    private void loadFragments() {
        FragmentManager fm = mainActivity.getSupportFragmentManager();
        userLoginFragment = (UserLoginFragment)fm.findFragmentByTag("loginFragment");
        if(userLoginFragment == null) {
            userLoginFragment = new UserLoginFragment();
        }
        userLoginFragment.setController(this);

        menuFragment = (MenuFragment)fm.findFragmentByTag("menuFragment");
        if(menuFragment == null) {
            menuFragment = new MenuFragment();
        }
        menuFragment.setController(this);

        expenseListFragment = (ExpenseListFragment)fm.findFragmentByTag("expenseListFragment");
        if(expenseListFragment == null) {
            expenseListFragment = new ExpenseListFragment();
        }
        expenseListFragment.setController(this);

        expenseInputFragment = (ExpenseInputFragment)fm.findFragmentByTag("expenseInputFragment");
        if(expenseInputFragment == null) {
            expenseInputFragment = new ExpenseInputFragment();
        }
        expenseInputFragment.setController(this);

        expenseAlertDialog = (ExpenseAlertDialogFragment)fm.findFragmentByTag("expenseAlertDialog");
        if(expenseAlertDialog == null) {
            expenseAlertDialog = new ExpenseAlertDialogFragment();
        }
        expenseAlertDialog.setController(this);

        incomeListFragment = (IncomeListFragment)fm.findFragmentByTag("incomeListFragment");
        if(incomeListFragment == null) {
            incomeListFragment = new IncomeListFragment();
        }
        incomeListFragment.setController(this);

        incomeInputFragment = (IncomeInputFragment)fm.findFragmentByTag("incomeInputFragment");
        if(incomeInputFragment == null) {
            incomeInputFragment = new IncomeInputFragment();
        }
        incomeInputFragment.setController(this);

        incomeAlertDialog = (IncomeAlertDialogFragment)fm.findFragmentByTag("incomeAlertDialog");
        if(incomeAlertDialog == null) {
            incomeAlertDialog = new IncomeAlertDialogFragment();
        }
        incomeAlertDialog.setController(this);

        datePickerFragment = (DatePickerFragment)fm.findFragmentByTag("datePickerFragment");
        if(datePickerFragment == null) {
            datePickerFragment = new DatePickerFragment();
        }
        datePickerFragment.setController(this);
    }

    public void loadInstanceState(Bundle savedInstanceState) {
        currentFragment = savedInstanceState.getString("currentFragment");
    }

    public void onResume() {
        switch(currentFragment) {
            case "loginFragment": mainActivity.setFragment(
                    userLoginFragment, currentFragment, toBackstack);
                break;
//            case "menuFragment" : mainActivity.setFragment(
//                    menuFragment, currentFragment, toBackstack);
//                break;
//            case "datePickerFragment" : mainActivity.setFragment(
//                    datePickerFragment, currentFragment, toBackstack);
//                break;
//            case "incomeListFragment" : mainActivity .setFragment(
//                    incomeListFragment, currentFragment, toBackstack);
//                break;
//            case "expenseListFragment" : mainActivity.setFragment(
//                    expenseListFragment, currentFragment, toBackstack);
//                break;
//            case "incomeAlertDialog" : mainActivity.setFragment(
//                    incomeAlertDialog, currentFragment, toBackstack);
//                break;
//            case "expenseAlertDialog" : mainActivity.setFragment(
//                    expenseAlertDialog, currentFragment, toBackstack);
//                break;
//            case "incomeInputFragment" : mainActivity.setFragment(
//                    incomeInputFragment, currentFragment, toBackstack);
//                break;
//            case "expenseInputFragment" : mainActivity.setFragment(
//                    expenseInputFragment, currentFragment, toBackstack);
//                break;
        }
        toBackstack=true;
    }

    public String getUserSharedPref() {
        SharedPreferences sharedPreferences =
                mainActivity.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String firstname = sharedPreferences.getString("firstname","");
        String lastname = sharedPreferences.getString("lastname", "");
        return firstname + " " + lastname;
    }

    public void setMenuDateViews() {
        if(dateFrom != null && dateTo != null) {
            menuFragment.setDateViews(dateFrom, dateTo);
        }
        menuFragment.setTotalIncomes(getTotalIncomes());
        menuFragment.setTotalExpenses(getTotalExpenses());
        menuFragment.setTotalSum();
    }

    public void setDates(String dateFrom, String dateTo) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public double getTotalIncomes() {
        double totalIncomes = 0;
        int index = 0;
        ArrayList<IncomeTransaction> incomeTransactionsList = dbController.getIncomeTransactions();
        incomeTransactionsArray = new IncomeTransaction[incomeTransactionsList.size()];
        Date start, end;

        if(dateFrom != null && dateTo != null) {
            for (IncomeTransaction incomeTransaction : incomeTransactionsList) {
                try {
                    start = simpleDateFormat.parse(dateFrom);
                    end = simpleDateFormat.parse(dateTo);
                    incomeTransactionsArray[index++] = incomeTransaction;
                    Date transactionDate = simpleDateFormat.parse(incomeTransaction.getDate());
                    if (transactionDate.after(start) && transactionDate.before(end)) {
                        totalIncomes += incomeTransaction.getAmount();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        else {
            for (IncomeTransaction incomeTransaction : incomeTransactionsList) {
                incomeTransactionsArray[index++] = incomeTransaction;
                totalIncomes += incomeTransaction.getAmount();
            }
        }
        return totalIncomes;
    }

    public double getTotalExpenses() {
        double totalExpenses = 0;
        int index = 0;
        ArrayList<ExpenseTransaction> expenseTransactionList = dbController.getExpenseTransactions();
        expenseTransactionsArray = new ExpenseTransaction[expenseTransactionList.size()];
        Date start, end;

        if(dateFrom != null && dateTo != null) {
            for (ExpenseTransaction expenseTransaction : expenseTransactionList) {
                try {
                    start = simpleDateFormat.parse(dateFrom);
                    end = simpleDateFormat.parse(dateTo);
                    expenseTransactionsArray[index++] = expenseTransaction;
                    Date transactionDate = simpleDateFormat.parse(expenseTransaction.getDate());
                    if (transactionDate.after(start) && transactionDate.before(end)) {
                        totalExpenses += expenseTransaction.getPrice();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        else {
            for (ExpenseTransaction expenseTransaction : expenseTransactionList) {
                expenseTransactionsArray[index++] = expenseTransaction;
                totalExpenses += expenseTransaction.getPrice();
            }
        }
        return totalExpenses;
    }

    public IncomeTransaction[] getAllIncomeTransactions(String dateFrom,String dateTo) {
        ArrayList<IncomeTransaction> incomeTransactionList = dbController.getIncomeTransactions();
        int index=0;
        int counter =0;
        Date start, end;
        if(dateFrom != null && dateTo != null) {
            try {
                start = simpleDateFormat.parse(dateFrom);
                end = simpleDateFormat.parse(dateTo);

                for (IncomeTransaction incomeTransaction : incomeTransactionList) {
                    Date transactionDate = simpleDateFormat.parse(incomeTransaction.getDate());
                    if (transactionDate.after(start) && transactionDate.before(end)) {
                        counter++;
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            incomeTransactionsArray = new IncomeTransaction[counter];

            try {
                start = simpleDateFormat.parse(dateFrom);
                end = simpleDateFormat.parse(dateTo);

                for(IncomeTransaction incomeTransactionInterval : incomeTransactionList) {
                    Date transactionDate = simpleDateFormat.parse(incomeTransactionInterval.getDate());
                    if (transactionDate.after(start) && transactionDate.before(end)) {
                        incomeTransactionsArray[index++] = incomeTransactionInterval;
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            incomeTransactionsArray = new IncomeTransaction[incomeTransactionList.size()];
            for (IncomeTransaction incomeTransaction : incomeTransactionList) {
                incomeTransactionsArray[index++] = incomeTransaction;
            }
        }
        return incomeTransactionsArray;
    }

    public ExpenseTransaction[] getAllExpenseTransactions(String dateFrom, String dateTo) {
        ArrayList<ExpenseTransaction> expenseTransactionList = dbController.getExpenseTransactions();
        int index=0;
        int counter =0;
        Date start, end;
        if(dateFrom != null && dateTo != null) {
            try {
                start = simpleDateFormat.parse(dateFrom);
                end = simpleDateFormat.parse(dateTo);

                for (ExpenseTransaction expenseTransaction : expenseTransactionList) {
                    Date transactionDate = simpleDateFormat.parse(expenseTransaction.getDate());
                    if (transactionDate.after(start) && transactionDate.before(end)) {
                        counter++;
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            expenseTransactionsArray = new ExpenseTransaction[counter];

            try {
                start = simpleDateFormat.parse(dateFrom);
                end = simpleDateFormat.parse(dateTo);

                for(ExpenseTransaction expenseTransactionInterval : expenseTransactionList) {
                    Date transactionDate = simpleDateFormat.parse(expenseTransactionInterval.getDate());
                    if (transactionDate.after(start) && transactionDate.before(end)) {
                        expenseTransactionsArray[index++] = expenseTransactionInterval;
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            expenseTransactionsArray = new ExpenseTransaction[expenseTransactionList.size()];
            for (ExpenseTransaction expenseTransaction : expenseTransactionList) {
                expenseTransactionsArray[index++] = expenseTransaction;
            }
        }
        return expenseTransactionsArray;
    }

    public void createIncomeTransaction(String date, String title, String category, double amount) {
        IncomeTransaction incomeTransaction = new IncomeTransaction(date, title, category, amount);
        dbController.setIncomeTransaction(incomeTransaction);
    }

    public void createExpenseTransaction(String date, String title, int category, double price) {
        ExpenseTransaction expenseTransaction = new ExpenseTransaction(date, title, category, price);
        dbController.setExpenseTransaction(expenseTransaction);
    }

    public void initMenuFragment() {
        setMenuDateViews();
        mainActivity.setFragment(menuFragment,"menuFragment",true);
        currentFragment="menuFragment";
    }

    public void showDatePickerFragment() {
        mainActivity.setFragment(datePickerFragment,"datePickerFragment",true);
        currentFragment = "datePickerFragment";
    }

    public void showIncomeTransactions() {
        incomeListFragment.setDates(dateFrom,dateTo);
        mainActivity.setFragment(incomeListFragment,"incomeListFragment",true);
        currentFragment = "incomeListFragment";
    }

    public void showExpenseTransactions() {
        expenseListFragment.setDates(dateFrom,dateTo);
        mainActivity.setFragment(expenseListFragment,"expenseListFragment",true);
        currentFragment = "expenseListFragment";
    }

    public void showIncomeInputFragment() {
        mainActivity.setFragment(incomeInputFragment,"incomeInputFragment",true);
        currentFragment = "incomeInputFragment";
    }

    public void showExpenseInputFragment() {
        mainActivity.setFragment(expenseInputFragment,"expenseInputFragment",true);
        currentFragment = "expenseInputFragment";
    }

    public void showIncomeAlertDialog(int position) {
        IncomeTransaction[] incomeTransaction = getAllIncomeTransactions(dateFrom,dateTo);
        String date = incomeTransaction[position].getDate();
        String title = incomeTransaction[position].getTitle();
        String category = incomeTransaction[position].getCategory();
        double amount = incomeTransaction[position].getAmount();
        incomeAlertDialog.setText(date, title, category, amount);
        mainActivity.setFragment(incomeAlertDialog,"incomeAlertDialog",true);
        currentFragment = "incomeAlertDialog";
    }

    public void showExpenseAlertDialog(int position) {
        ExpenseTransaction[] expenseTransaction = getAllExpenseTransactions(dateFrom,dateTo);
        String date = expenseTransaction[position].getDate();
        String title = expenseTransaction[position].getTitle();
        int category = expenseTransaction[position].getCategory();
        double price = expenseTransaction[position].getPrice();
        expenseAlertDialog.setText(date,title,category,price);
        mainActivity.setFragment(expenseAlertDialog,"expenseAlertDialog",true);
        currentFragment = "expenseAlertDialog";
    }
}
