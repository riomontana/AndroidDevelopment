package com.example.lfo.laboration3c;

import android.app.Activity;
import android.content.res.Resources;

/**
 * Created by tsroax on 2014-09-08.
 */
public class Controller {
    private Instruction[] instructions = new Instruction[3];
    private InstructionFragment instructionFragment;
    private MainActivity activity;

    public Controller(MainActivity activity) {
        this.activity = activity;
        initInstructions(activity);
        WhatToDoAdapter adapter = new WhatToDoAdapter(activity,instructions);
        WhatToDoFragment whatToDoFragment = new WhatToDoFragment();
        whatToDoFragment.setAdapter(adapter);
        whatToDoFragment.setController(this);
        activity.setFragment(whatToDoFragment,false);
        instructionFragment= new InstructionFragment();
    }

    private void initInstructions(Activity activity) {
        Resources res = activity.getResources();
        String whatToDo = res.getString(R.string.what_to_do);
        String content = res.getString(R.string.content);
        instructions[0] = new Instruction(whatToDo, content);
        instructions[1] = new Instruction(res.getString(R.string.what_to_do2), res.getString(R.string.content2));
        instructions[2] = new Instruction(res.getString(R.string.what_to_do3), res.getString(R.string.content3));
    }

    public void itemClicked(int position) {
        instructionFragment.setWhatToDo(instructions[position].getWhatToDo());
        instructionFragment.setContent(instructions[position].getContent());
        activity.setFragment(instructionFragment,true);
    }
}
