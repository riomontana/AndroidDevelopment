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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lfo.p2.Controller.Controller;
import com.lfo.p2.R;

/**
 * A simple {@link Fragment} subclass.
 */

public class AddGroupDialogFragment extends DialogFragment {

    private Controller controller;
    private EditText inputGroupName;

    public AddGroupDialogFragment() {

    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_add_group_dialog, null);
        inputGroupName = (EditText) view.findViewById(R.id.inputGroupName);
        alertDialogBuilder.setView(view);

        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(inputGroupName.length() < 1) {
                    Toast.makeText(getActivity(), getString(R.string.input_minimum),
                            Toast.LENGTH_SHORT).show();
                    dismiss();
                } else {
                    controller.registerGroup(inputGroupName.getText().toString());
                    controller.updateGroups();
                    Toast.makeText(getActivity(), getString(R.string.member_of) + " " +
                                    inputGroupName.getText().toString(),
                            Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            }
        });
        return alertDialogBuilder.create();
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

}
