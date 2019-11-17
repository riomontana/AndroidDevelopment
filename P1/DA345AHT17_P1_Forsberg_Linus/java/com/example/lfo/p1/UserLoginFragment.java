package com.example.lfo.p1;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserLoginFragment extends Fragment {
    private Button btnLogin;
    private EditText inputFirstname;
    private EditText inputLastname;
    private Controller controller;
    private String firstname, lastname;

    public UserLoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_login, container, false);
        initComponents(view);
        registerListener();
        Log.d("userLoginFrag", "onCreateView");
        return view;
    }


    public void onSaveInstanceState(Bundle savedInstanceState) {
        Log.d("userLoginFrag", "onSaveinstance");
        super.onSaveInstanceState(savedInstanceState);
        saveUser();
    }

    private void initComponents(View view) {
        Log.d("userLoginFrag","initComp");
        btnLogin = (Button)view.findViewById(R.id.btnLogin);
        inputFirstname = (EditText)view.findViewById(R.id.textFirstname);
        inputLastname = (EditText)view.findViewById(R.id.textLastname);
        setUserTextView();
    }

    private void setUserTextView() {
        firstname = getPreferences().getString("firstname", "");
        lastname = getPreferences().getString("lastname", "");
        inputFirstname.setText(firstname);
        inputLastname.setText(lastname);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void registerListener() {
        btnLogin.setOnClickListener(new LoginListener());
    }

    private SharedPreferences getPreferences() {
        SharedPreferences sharedPreferences =
                getActivity().getSharedPreferences("userInfo", getContext().MODE_PRIVATE);
        return sharedPreferences;
    }

    private void saveUser() {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putString("firstname", inputFirstname.getText().toString());
        editor.putString("lastname", inputLastname.getText().toString());
        editor.apply();
    }

    private class LoginListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if(inputFirstname.toString().length() == 0 || inputLastname.toString().length() == 0) {
                Toast.makeText(getActivity(), "Please input your firstname and lastname",
                        Toast.LENGTH_SHORT).show();
            } else {
                saveUser();
                controller.initMenuFragment();
            }
        }
    }
}
