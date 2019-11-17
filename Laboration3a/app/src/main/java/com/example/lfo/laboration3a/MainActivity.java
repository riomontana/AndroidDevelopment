package com.example.lfo.laboration3a;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeComponents();
    }

    private void initializeComponents() {
        FragmentManager fm = getSupportFragmentManager();
        BlankFragment fragment = (BlankFragment) fm.findFragmentById(R.id.fragment);
        Controller controller = new Controller(fragment);
    }
}
