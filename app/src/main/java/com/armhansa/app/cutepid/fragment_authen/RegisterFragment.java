package com.armhansa.app.cutepid.fragment_authen;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.armhansa.app.cutepid.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment
implements View.OnClickListener{

    View rootView;

    EditText name;
    EditText age;
    EditText gender;
    EditText username;
    EditText password;
    Button register;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_register, container, false);

        name = rootView.findViewById(R.id.name);
        age = rootView.findViewById(R.id.age);
        gender = rootView.findViewById(R.id.gender);
        username = rootView.findViewById(R.id.username);
        password = rootView.findViewById(R.id.password);

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
        DatabaseReference mMessagesRef = mRootRef.child("messages");

        mUsersRef.push().setValue(name.getText().toString());

        // Sent to Login Page
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.popBackStack();
        fragmentManager.beginTransaction()
                .replace(R.id.mainLoginFragment, new EmailLoginFragment())
                .addToBackStack(null)
                .commit();
    }
}
