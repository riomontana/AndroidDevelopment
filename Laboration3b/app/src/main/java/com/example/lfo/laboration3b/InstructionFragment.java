package com.example.lfo.laboration3b;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class InstructionFragment extends Fragment {
    private TextView tvWhatToDo;
    private TextView tvContent;
    private String whatToDo="";
    private String content="";

    public InstructionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_instruction, container, false);
        tvWhatToDo = (TextView)view.findViewById(R.id.tvWhatToDo);
        tvContent = (TextView)view.findViewById(R.id.tvContent);
        return view;
    }

    public void setWhatToDo(String whatToDo) {
        tvWhatToDo.setText(whatToDo);
    }

    public void setContent(String content) {
        tvContent.setText(content);
    }

}
