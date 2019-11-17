package com.example.lfo.laboration2b;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeSystem();
    }

    private void initializeSystem() {
        FragmentManager fm = getSupportFragmentManager();
        TextViewFragment vf = (TextViewFragment) fm.findFragmentById(R.id.fragmentTextView);
        ButtonFragment bf = (ButtonFragment) fm.findFragmentById(R.id.fragmentButton);
        Controller controller = new Controller(vf);
        bf.setController(controller);
    }
}
