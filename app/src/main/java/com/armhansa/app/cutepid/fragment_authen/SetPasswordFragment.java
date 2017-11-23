package com.armhansa.app.cutepid.fragment_authen;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.armhansa.app.cutepid.HomeActivity;
import com.armhansa.app.cutepid.LoginActivity;
import com.armhansa.app.cutepid.R;
import com.armhansa.app.cutepid.controller.CommonSharePreference;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SetPasswordFragment extends Fragment implements View.OnClickListener{

    View rootView;

    TextView passwordTxt;

    EditText password;

    Button nextBtn;

    boolean isLogin;

    public SetPasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_set_password, container, false);

        passwordTxt = rootView.findViewById(R.id.passwordTxt);
// Set Password Text Must Have New Argument

        password = rootView.findViewById(R.id.passwordInput);

        nextBtn = rootView.findViewById(R.id.phoneLoginBtn);
        nextBtn.setOnClickListener(this);

        isLogin = getArguments().getBoolean("IsLogin");

        return rootView;
    }

    @Override
    public void onClick(View view) {

        if(isLogin) {
            if (LoginActivity.user.getPassword().equals(password.getText().toString())) {
                login();

            } else {
                Toast.makeText(getActivity(), "Invalid Password, please try again.", Toast.LENGTH_LONG).show();
            }
        } else {
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("users").child(LoginActivity.user.getId()).setValue(LoginActivity.user);

            Toast.makeText(getActivity(), "Set User to firebase.", Toast.LENGTH_LONG).show();

            login();

        }

    }

    public void login() {
        CommonSharePreference preference = new CommonSharePreference(getActivity());
        preference.save("User", LoginActivity.user);

        Intent menuActivity = new Intent(getActivity(), HomeActivity.class);
        startActivity(menuActivity);
        getActivity().finish();
    }

    public static SetPasswordFragment newInstance(boolean isLogin) {

        Bundle args = new Bundle();

        SetPasswordFragment fragment = new SetPasswordFragment();
        args.putBoolean("IsLogin", isLogin);
        fragment.setArguments(args);
        return fragment;
    }
}
