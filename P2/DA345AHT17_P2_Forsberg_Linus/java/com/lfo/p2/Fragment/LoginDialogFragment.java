package com.lfo.p2.Fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.lfo.p2.Controller.Controller;
import com.lfo.p2.R;

public class LoginDialogFragment extends DialogFragment {

    private Controller controller;
    private EditText inputUser;
    private String user;

    public LoginDialogFragment() {

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_edit_name, null);
        alertDialogBuilder.setView(view);
        inputUser = (EditText) view.findViewById(R.id.txtYourName);
        setCancelable(false);


        alertDialogBuilder.setPositiveButton("OK",  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                user = inputUser.getText().toString();
                if(user.length() < 1) {
                    Toast.makeText(getActivity(), R.string.input_minimum,
                            Toast.LENGTH_SHORT).show();
                    controller.setLoginFragment();
                } else {
                    controller.setUser(user);
                    controller.newConnection();
                    controller.updateGroups();
                    controller.welcomeToast();
                }
                dismiss();
            }
        });
        return alertDialogBuilder.create();
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
}