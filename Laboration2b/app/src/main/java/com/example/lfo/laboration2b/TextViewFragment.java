package com.example.lfo.laboration2b;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class TextViewFragment extends Fragment {

    TextView tvCounter;

    public TextViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_text_view, container, false);

        initializeCompontent(view);

        return view;
    }

    private void initializeCompontent(View view) {
        tvCounter = (TextView) view.findViewById(R.id.tvCounter);
    }

    public TextView getTvCounter() {
        return tvCounter;
    }

}
