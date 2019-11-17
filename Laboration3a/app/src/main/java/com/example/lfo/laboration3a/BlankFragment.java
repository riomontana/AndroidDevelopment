package com.example.lfo.laboration3a;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {
    private TextView textView;
    private Button button;
    private ListView listView;
    private Controller controller;

    public BlankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank, container, false);

        initializeComponents(view);
        return view;
    }

    private void initializeComponents(View view) {
        button = (Button) view.findViewById(R.id.button);
        textView = (TextView) view.findViewById(R.id.textView);
        listView = (ListView) view.findViewById(R.id.listView);
        button.setOnClickListener(new ButtonListener());
        listView.setOnItemClickListener(new ListViewListener());
    }

    public void setButtonText(String buttonText) {
        button.setText(buttonText);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setTextViewColor(int textViewColor) {
        textView.setBackgroundColor(textViewColor);
    }

    public void setColors(String[] colors) {
        listView.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,colors));
    }

    private class ButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            controller.btnColorClicked((String) button.getText().toString());
        }
    }

    private class ListViewListener implements AdapterView.OnItemClickListener {
        public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
            controller.clickList(pos);
        }

    }
}
