package com.example.lfo.laboration1;

/**
 * Created by LFO on 2017-09-02.
 */

public class Instruction {
    private String whatToDo;
    private String content;

    public Instruction(String whatToDo, String content) {
        this.whatToDo = whatToDo;
        this.content = content;
    }

    public String getWhatToDo() {
        return whatToDo;
    }

    public String getContent() {
        return content;
    }
}
