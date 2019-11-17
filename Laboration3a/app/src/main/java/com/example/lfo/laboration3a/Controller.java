package com.example.lfo.laboration3a;

import android.graphics.Color;

/**
 * Created by LFO on 2017-09-04.
 */

public class Controller {

    private BlankFragment fragment;
    private String[] colorName = {"RÖD", "GUL", "BLÅ", "GRÖN"};
    private int[] colors = {Color.RED, Color.YELLOW, Color.BLUE, Color.GREEN};

    public Controller(BlankFragment fragment) {
        this.fragment = fragment;
        fragment.setController(this);
        fragment.setButtonText(colorName[0]);
        fragment.setTextViewColor(colors[0]);
        fragment.setColors(colorName);
    }

    public void clickList(int pos) {

        fragment.setButtonText(colorName[pos]);
    }

    public void btnColorClicked(String colorString) {
        int pos = -1;
        for(int i = 0; i < colorName.length; i++) {
            if(colorName[i].equals(colorString)) {
                pos = i;
                fragment.setTextViewColor(colors[pos]);
                break;
            }
        }
    }
}
