package com.armhansa.app.cutepid.fragment_authen;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.armhansa.app.cutepid.R;

public class MenuLoginFragment extends Fragment implements View.OnClickListener{

    View rootView;

    public MenuLoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_menu_login, container, false);

        Button facebookBtn = rootView.findViewById(R.id.facebookBtn);
        facebookBtn.setOnClickListener(this);
        Button loginBtn = rootView.findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(this);
        TextView registerBtn = rootView.findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.facebookBtn:
                facebookLogin();
                break;
            case R.id.loginBtn:
                nextPageLogin();
                break;
            case R.id.registerBtn:
                nextPageRegister();
                break;
        }
    }

    private void facebookLogin() {
    }

    private void nextPageLogin() {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainLoginFragment, new EmailLoginFragment())
                .addToBackStack(null)
                .commit();
    }

    private void nextPageRegister() {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainLoginFragment, new RegisterFragment())
                .addToBackStack(null)
                .commit();
    }

}
