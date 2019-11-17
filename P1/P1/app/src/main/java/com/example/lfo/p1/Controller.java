package com.example.lfo.p1;

import android.content.Context;
import android.content.SharedPreferences;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by LFO on 2017-09-12.
 */

public class Controller {
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


    public Controller(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        userLoginFragment = new UserLoginFragment();
        mainActivity.setFragment(userLoginFragment,false);
        userLoginFragment.setController(this);
        dbController = new DatabaseController(mainActivity, this);
    }

    public String getUserSharedPref() {
        SharedPreferences sharedPreferences =
                mainActivity.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String firstname = sharedPreferences.getString("firstname","");
        String lastname = sharedPreferences.getString("lastname", "");
        return firstname + " " + lastname;
    }

    public void initMenuFragment() {
        menuFragment = new MenuFragment();
        menuFragment.setController(this);
        mainActivity.setFragment(menuFragment,true);
        menuFragment.setTotalIncomes(getTotalIncomes());
        menuFragment.setTotalExpenses(getTotalExpenses());
        menuFragment.setTotalSum();
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

    public void setMenuDateViews(String dateFrom, String dateTo) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        initMenuFragment();
        menuFragment.setDateViews(dateFrom,dateTo);
    }

    public void showDatePickerFragment() {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setController(this);
        mainActivity.setFragment(datePickerFragment,true);
    }

    public void showIncomeTransactions() {
        incomeListFragment = new IncomeListFragment();
        incomeListFragment.setController(this);
        ArrayList<IncomeTransaction> incomeTransactions = dbController.getIncomeTransactions();
        updateIncomeList(incomeTransactions);
        mainActivity.setFragment(incomeListFragment,true);
    }

    public void showExpenseTransactions() {
        expenseListFragment = new ExpenseListFragment();
        expenseListFragment.setController(this);
        ArrayList<ExpenseTransaction> expenseTransactions= dbController.getExpenseTransactions();
        mainActivity.setFragment(expenseListFragment,true);
        updateExpenseList(expenseTransactions);

    }

    public void showIncomeInputFragment() {
        IncomeInputFragment incomeInputFragment = new IncomeInputFragment();
        ArrayList<IncomeTransaction> incomeTransactions = dbController.getIncomeTransactions();
        updateIncomeList(incomeTransactions);
        mainActivity.setFragment(incomeInputFragment,true);
        incomeInputFragment.setController(this);
    }

    public void showExpenseInputFragment() {
        ExpenseInputFragment expenseInputFragment = new ExpenseInputFragment();
        ArrayList<ExpenseTransaction> expenseTransactions = dbController.getExpenseTransactions();
        updateExpenseList(expenseTransactions);
        mainActivity.setFragment(expenseInputFragment,true);
        expenseInputFragment.setController(this);
    }

    public void createIncomeTransaction(String date, String title, String category, double amount) {
        IncomeTransaction incomeTransaction = new IncomeTransaction(date, title, category, amount);
        ArrayList<IncomeTransaction> incomeTransactionList = dbController.getIncomeTransactions();
        dbController.setIncomeTransaction(incomeTransaction);
        updateIncomeList(incomeTransactionList);
    }

    public void createExpenseTransaction(String date, String title, int category, double price) {
        ExpenseTransaction expenseTransaction = new ExpenseTransaction(date, title, category, price);
        ArrayList<ExpenseTransaction> expenseTransactionList = dbController.getExpenseTransactions();
        dbController.setExpenseTransaction(expenseTransaction);
        updateExpenseList(expenseTransactionList);
    }

    public void updateIncomeList(ArrayList<IncomeTransaction> incomeTransactionList) {
        Date start, end;
        int index = 0;
        int counter = 0;

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
            for (IncomeTransaction incomeTransaction: incomeTransactionList) {
                incomeTransactionsArray[index++] = incomeTransaction;
            }
        }
        expenseListFragment.populateExpenseList(mainActivity);
    }

    public void updateExpenseList(ArrayList<ExpenseTransaction> expenseTransactionList) {
        Date start, end;
        int index = 0;
        int counter = 0;

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
            for (ExpenseTransaction expenseTransaction : expenseTransactionList) {
                expenseTransactionsArray[index++] = expenseTransaction;
            }
        }
        expenseListFragment.populateExpenseList(mainActivity);
    }

    public IncomeTransaction[] getIncomeArray() {
        return incomeTransactionsArray;
    }

    public ExpenseTransaction[] getExpenseArray() {
        return expenseTransactionsArray;
    }

    public void showIncomeAlertDialog(int position) {
        IncomeAlertDialogFragment incomeAlertDialog = new IncomeAlertDialogFragment();
        incomeAlertDialog.setController(this);
        IncomeTransaction[] incomeTransaction = getIncomeArray();
        String date = incomeTransaction[position].getDate();
        String title = incomeTransaction[position].getTitle();
        String category = incomeTransaction[position].getCategory();
        double amount = incomeTransaction[position].getAmount();
        incomeAlertDialog.setText(date,title,category,amount);
        mainActivity.setFragment(incomeAlertDialog,true);
    }

    public void showExpenseAlertDialog(int position) {
        ExpenseAlertDialogFragment expenseAlertDialog = new ExpenseAlertDialogFragment();
        expenseAlertDialog.setController(this);
        ExpenseTransaction[] expenseTransaction = getExpenseArray();
        String date = expenseTransaction[position].getDate();
        String title = expenseTransaction[position].getTitle();
        int category = expenseTransaction[position].getCategory();
        double price = expenseTransaction[position].getPrice();
        expenseAlertDialog.setText(date,title,category,price);
        mainActivity.setFragment(expenseAlertDialog,true);
    }
}
