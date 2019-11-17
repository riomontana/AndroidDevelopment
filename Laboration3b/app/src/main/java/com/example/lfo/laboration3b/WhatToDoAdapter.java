package com.example.lfo.laboration3b;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by tsroax on 2014-09-08.
 */
public class WhatToDoAdapter extends ArrayAdapter<Instruction> {
    private LayoutInflater inflater;
    private Instruction[] instructions;

    public WhatToDoAdapter(Context context, Instruction[] objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
        this.instructions = objects;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tv;
        if(convertView==null) {
            tv = (TextView)inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        } else {
            tv = (TextView) convertView;
        }
        tv.setText(instructions[position].getWhatToDo());
        return tv;
    }
}
