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
import com.armhansa.app.cutepid.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

public class PhoneNumberLoginFragment extends Fragment
implements View.OnClickListener{

    View rootView;

    private EditText phone;
    private Button nextBtn;

    public PhoneNumberLoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_phone_number_login, container, false);

        phone = rootView.findViewById(R.id.phoneNumber);
        nextBtn = rootView.findViewById(R.id.phoneLogin);
        nextBtn.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {

        final String phoneNumber = phone.getText().toString();

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(phoneNumber).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user_tmp = dataSnapshot.getValue(User.class);
                if(user_tmp != null) {
                    LoginActivity.user = user_tmp;

                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.mainLoginFragment, SetPasswordFragment.newInstance(true))
                            .addToBackStack(null)
                            .commit();

                } else {
                    LoginActivity.user.setId(phoneNumber);

                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.mainLoginFragment, new SetProfileFragment())
                            .addToBackStack(null)
                            .commit();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_SHORT).show();
            }
        });


    }

}
