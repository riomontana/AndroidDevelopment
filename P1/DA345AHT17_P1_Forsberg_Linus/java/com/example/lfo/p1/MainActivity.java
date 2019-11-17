package com.example.lfo.p1;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initController(savedInstanceState);
    }

    public void onSaveInstanceState(Bundle outState) {
        Log.d("Activity", "saveinstancestate");
        controller.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d("Activity", "restoreinstancestate");
        controller.loadInstanceState(savedInstanceState);
        super.onRestoreInstanceState(savedInstanceState);
    }


    public void initController(Bundle savedInstanceState) {
        controller = new Controller(this,savedInstanceState);
    }

    public void setFragment(Fragment fragment, String tag, boolean backstack) {
        Log.d("Activity","setFragment");
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fragmentContainer,fragment, tag);
        if(backstack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        controller.onResume();
    }
}
