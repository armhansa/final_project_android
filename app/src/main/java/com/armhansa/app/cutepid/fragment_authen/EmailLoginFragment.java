package com.armhansa.app.cutepid.fragment_authen;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.armhansa.app.cutepid.MainActivity;
import com.armhansa.app.cutepid.R;
import com.armhansa.app.cutepid.controller.CommonSharePreference;
import com.armhansa.app.cutepid.model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class EmailLoginFragment extends Fragment
implements View.OnClickListener{

    View rootView;

    EditText email;
    EditText password;
    EditText firstName;
    EditText birthDay;
    EditText gender;
    Button register;

    public EmailLoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_email_login, container, false);


        email = rootView.findViewById(R.id.email);
        password = rootView.findViewById(R.id.password);
        firstName = rootView.findViewById(R.id.firstName);
        birthDay = rootView.findViewById(R.id.birthDay);
        gender = rootView.findViewById(R.id.gender);

        register = rootView.findViewById(R.id.register);
        register.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        // Validation

        // write to firebase
        DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();

        DatabaseReference mUsersRef = mRootRef.child("users");
//        DatabaseReference mMessagesRef = mRootRef.child("messages");

        User user = new User();
        user.setEmail(email.getText().toString());
        user.setPassword(password.getText().toString());
        user.setFirstName(firstName.getText().toString());
        user.setBirthDay(new Date());
        user.setGender(gender.getText().toString());

        mUsersRef.child(email.getText().toString()).setValue(user);

        CommonSharePreference preference = new CommonSharePreference(getActivity());
        preference.save(email.getText().toString(), user);


        // Sent to Login Page
        Intent menuActivity = new Intent(getActivity(), MainActivity.class);
        startActivity(menuActivity);
        getActivity().finish();

    }

}
