package com.example.lfo.laboration2c;

import android.content.res.Resources;

/**
 * Created by LFO on 2017-09-03.
 */

public class Controller {

    private Instruction[] instructions = new Instruction[3];
    private int index = 0;
    private MainActivity mainActivity;

    public Controller(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        initializeResources();
    }

    private void initializeResources() {
        Resources res = mainActivity.getResources();
        String whatToDo = res.getString(R.string.what_to_do1);
        String content = res.getString(R.string.content1);
        instructions[0] = new Instruction(whatToDo, content);
        instructions[1] = new Instruction(res.getString(R.string.what_to_do2), res.getString(R.string.content2));
        instructions[2] = new Instruction(res.getString(R.string.what_to_do3), res.getString(R.string.content3));
    }

    public void previousClick() {
        index--;
        if(index<0) {
            index = instructions.length-1;
        }
        mainActivity.setWhatToDo(instructions[index].getWhatToDo());
        mainActivity.setContent(instructions[index].getContent());
    }

    public void nextClick() {
        index++;
        if(index==instructions.length) {
            index = 0;
        }
        mainActivity.setWhatToDo(instructions[index].getWhatToDo());
        mainActivity.setContent(instructions[index].getContent());
    }
}
