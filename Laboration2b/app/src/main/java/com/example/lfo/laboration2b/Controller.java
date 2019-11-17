package com.example.lfo.laboration2b;

import android.widget.TextView;

/**
 * Created by LFO on 2017-09-03.
 */

public class Controller {

    int counter = 0;

    private TextViewFragment tvFragment;
    private MainActivity mainActivity;

    public Controller(TextViewFragment tvFragment) {
        this.tvFragment = tvFragment;
    }

    public void updateTvCounter() {
        counter++;
        tvFragment.getTvCounter().setText(String.valueOf(counter));
    }
}
