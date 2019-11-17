package com.example.lfo.p1;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

    public UserLoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_login, container, false);
        initComponents(view);
        registerListener();
        return view;
    }

    private void initComponents(View view) {
        btnLogin = (Button)view.findViewById(R.id.btnLogin);
        inputFirstname = (EditText)view.findViewById(R.id.textFirstname);
        inputLastname = (EditText)view.findViewById(R.id.textLastname);
        setUserTextView();
    }

    private void setUserTextView() {
        String firstname = getPreferences().getString("firstname","");
        String lastname = getPreferences().getString("lastname", "");
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

    private class LoginListener implements View.OnClickListener { // TODO fixa input restrictions minst 3 tecken
        @Override
        public void onClick(View view) {
            if(inputFirstname == null || inputLastname == null) {
                Toast.makeText(getActivity(), "Please input your firstname and lastname",
                        Toast.LENGTH_SHORT).show();
            } else {
                saveUser();
                controller.initMenuFragment();
            }
        }
    }
}
