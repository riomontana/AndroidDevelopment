package com.lfo.p2.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lfo.p2.Controller.Controller;
import com.lfo.p2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GroupListDialogFragment extends DialogFragment {

    private Controller controller;
    private ListView lvGroups;
    private String[] groups;
    private TextView tvNoGroups;

    public GroupListDialogFragment() {
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        if(groups.length == 0 || groups == null) {
            View view = inflater.inflate(R.layout.no_groups,null);
            tvNoGroups = (TextView)view.findViewById(R.id.tvNoGroups);
            alertDialogBuilder.setView(view);
        } else {
            View view = inflater.inflate(R.layout.fragment_group_list_dialog, null);
            lvGroups = (ListView) view.findViewById(R.id.lvGroups);
            lvGroups.setAdapter(getArrayAdapter());
            lvGroups.setOnItemClickListener(new GroupListListener());
            alertDialogBuilder.setView(view);
        }
        return alertDialogBuilder.create();
    }

    public void setGroupArray(JSONObject message) {
        try {
            JSONObject jsonMessage = message;
            JSONArray jsonArray = jsonMessage.getJSONArray("groups");
            groups = new String[jsonArray.length()];
            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                groups[i] = json.getString("group");
                Log.d("setGroupArray", groups[i]);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public ArrayAdapter<String> getArrayAdapter() {
        ArrayAdapter<String> groupAdapter =
                new ArrayAdapter<String>(super.getContext(), android.R.layout.simple_list_item_1, groups);
        return groupAdapter;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    private class GroupListListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            controller.registerGroup(groups[position]);
            Toast.makeText(getActivity(), getString(R.string.member_of) + " " + groups[position],
                    Toast.LENGTH_SHORT).show();
            dismiss();
        }
    }
}
