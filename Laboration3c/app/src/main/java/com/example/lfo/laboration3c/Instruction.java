package com.example.lfo.laboration3c;

/**
 * Created by tsroax on 2014-09-08.
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
