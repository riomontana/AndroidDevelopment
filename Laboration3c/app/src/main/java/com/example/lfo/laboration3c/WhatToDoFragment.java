package com.example.lfo.laboration3c;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class WhatToDoFragment extends Fragment {
    private Controller controller;
    private ListView lvWhatToDo;


    public WhatToDoFragment() {
        // Required empty public constructor
    }

    public void setAdapter(WhatToDoAdapter adapter) {
        lvWhatToDo.setAdapter(adapter);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_what_to_do, container, false);
        lvWhatToDo = (ListView)view.findViewById(R.id.lvWhatToDo);
        lvWhatToDo.setOnItemClickListener(new ItemListener());
        return view;
    }


    private class ItemListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            controller.itemClicked(position);
        }
    }
}
