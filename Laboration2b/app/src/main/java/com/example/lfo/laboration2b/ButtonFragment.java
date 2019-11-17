package com.example.lfo.laboration2b;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class ButtonFragment extends Fragment {

    private Controller controller;
    private Button button;

    public ButtonFragment() {
        // Required empty public constructor
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_button, container, false);
        initializeComponent(view);
        addListener();

        return view;
    }

    public void addListener() {
        button.setOnClickListener(new ButtonListener());
    }

    public void initializeComponent(View view) {
        button = (Button) view.findViewById(R.id.button);
    }

    private class ButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            controller.updateTvCounter();
        }
    }

}
